package com.ubisys.drone.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.ubisys.drone.common.constant.DroneConstant;
import com.ubisys.drone.config.exception.DroneException;
import com.ubisys.drone.modules.base.entity.DroneConfig;
import com.ubisys.drone.modules.base.entity.DroneConfigAttr;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>Title: 使用聚合发送短信</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: http://www.ubisys.com.cn/</p>
 *
 * @Auther: cw
 * @Date: 2018/12/29 14:56
 */
@Slf4j
@Component
public class JHSMSUtil {


    public static final String DEF_CHATSET = "UTF-8";
    public static final int DEF_CONN_TIMEOUT = 30000;
    public static final int DEF_READ_TIMEOUT = 30000;
    public static String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";

    //配置您申请的KEY
    // public static final String APPKEY = "b2b39d1e11de21955f591a949d4f9e33";

    private String APPKEY;


    //短信模版
    private  String  TPLID;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    //1.屏蔽词检查测
    public void getRequest1() {
        String result = null;
        String url = "http://v.juhe.cn/sms/black";//请求接口地址
        Map params = new HashMap();//请求参数
        params.put("word", "");//需要检测的短信内容，需要UTF8 URLENCODE
        params.put("key", APPKEY);//应用APPKEY(应用详细页查询)

        try {
            result = net(url, params, "GET");

            JSONObject jsonObject = JSONObject.parseObject
                    (result);
            if (jsonObject.get("error_code").toString().equals("0")) {
                System.out.println(jsonObject.get("result"));
            } else {
                System.out.println(jsonObject.get("error_code") + ":" + jsonObject.get("reason"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送短信验证码
     *
     * @param smsid  短信模版id
     * @param mobile 要发送的手机号
     * @return
     */
    public AjaxResponse sendSms(String smsid, String mobile) throws UnsupportedEncodingException {
        checkURL();
        String result = null;
        String url = "http://v.juhe.cn/sms/send";//请求接口地址

        String code = URLEncoder.encode("#code#=" + RandomStringUtils.randomNumeric(6), DEF_CHATSET);
        log.info(" 准备生成验证码为 " + code);
        Map params = new HashMap();//请求参数
        params.put("mobile", mobile);//接收短信的手机号码
        params.put("tpl_id", TPLID);//短信模板ID，请参考个人中心短信模板设置
        params.put("tpl_value", code);//变量名和变量值对。如果你的变量名或者变量值中带有#&=中的任意一个特殊符号，请先分别进行urlencode编码后再传递，<a href="http://www.juhe.cn/news/index/id/50" target="_blank">详细说明></a>
        params.put("key", APPKEY);//应用APPKEY(应用详细页查询)
        params.put("dtype", "json");//返回数据的格式,xml或json，默认json

        try {
            result = net(url, params, "GET");
            JSONObject jsonObject = JSONObject.parseObject(result);
            if ((int) jsonObject.get("error_code") == 0) {
                log.info("  短信发送成功！ ");
                //发送成功后，存redis   60 秒
                stringRedisTemplate.opsForValue().set(DroneConstant.SMS+mobile,code,5, TimeUnit.MINUTES);
                return AjaxResponse.build(200, "短信发送成功！");
            } else {
                log.info("  短信发送失败，错误码为: " + jsonObject.get("error_code"));
                return AjaxResponse.build(501, "短信发送失败，错误码为: " + jsonObject.get("error_code"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return AjaxResponse.success();
    }


    public static void main(String[] args) {
       /* try {
            System.out.println(RandomStringUtils.randomNumeric(6));
            //%23code%23%3d431515
            System.out.println(URLEncoder.encode("#code#=431515", "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/
        // getRequest2();
    }

    /**
     * @param strUrl 请求地址
     * @param params 请求参数
     * @param method 请求方法
     * @return 网络请求字符串
     * @throws Exception
     */
    public static String net(String strUrl, Map params, String method) throws Exception {
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        String rs = null;
        try {
            StringBuffer sb = new StringBuffer();
            if (method == null || method.equals("GET")) {
                strUrl = strUrl + "?" + urlencode(params);
            }
            URL url = new URL(strUrl);
            conn = (HttpURLConnection) url.openConnection();
            if (method == null || method.equals("GET")) {
                conn.setRequestMethod("GET");
            } else {
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
            }
            conn.setRequestProperty("User-agent", userAgent);
            conn.setUseCaches(false);
            conn.setConnectTimeout(DEF_CONN_TIMEOUT);
            conn.setReadTimeout(DEF_READ_TIMEOUT);
            conn.setInstanceFollowRedirects(false);
            conn.connect();
            if (params != null && method.equals("POST")) {
                try {
                    DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                    out.writeBytes(urlencode(params));
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
            InputStream is = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sb.append(strRead);
            }
            rs = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return rs;
    }

    //将map型转为请求参数型
    public static String urlencode(Map<String, Object> data) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry i : data.entrySet()) {
            try {
                sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue() + "", "UTF-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }


    public void checkURL() {
        //从缓存中读取  lz  url
        if (StringUtils.isBlank(APPKEY)) {
            String sensors = (String) stringRedisTemplate.opsForHash().get(DroneConstant.RY_CONFIG, "smsConfig");
            if (StringUtils.isBlank(sensors)) {
                log.error("  囧囧囧   囧 ,  数据库好像不有这个配置  抛个错吧");
                throw new DroneException("囧囧囧   囧 ,  数据库好像木有这个配置 ");
            }
            List<DroneConfigAttr> attrs = JSONObject.parseObject(sensors, DroneConfig.class).getAttrs();
            for (DroneConfigAttr attr : attrs) {
                if (attr.getAttrkey().equals("sensorUrl")) {
                    APPKEY = attr.getAttrvalue();
                }
                if (attr.getAttrkey().equals("smsCode")) {
                    TPLID = attr.getAttrvalue();
                }
            }
        }
    }


}
