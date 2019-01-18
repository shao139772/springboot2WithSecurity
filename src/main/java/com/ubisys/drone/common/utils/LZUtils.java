package com.ubisys.drone.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ubisys.drone.common.constant.DroneConstant;
import com.ubisys.drone.config.exception.DroneException;
import com.ubisys.drone.modules.base.entity.Drone;
import com.ubisys.drone.modules.base.entity.DroneConfig;
import com.ubisys.drone.modules.base.entity.DroneConfigAttr;
import com.ubisys.drone.modules.base.entity.LzDrone;
import com.ubisys.drone.modules.base.model.DroneThirdFloor;
import com.ubisys.drone.modules.base.model.SenorAtuoAttack;
import com.ubisys.drone.modules.base.model.SensorFloor;
import com.ubisys.drone.modules.base.model.WhiteTemporary;
import com.ubisys.drone.modules.base.service.mybatis.IDroneService;
import com.ubisys.drone.modules.base.service.mybatis.ILzDroneService;
import com.ubisys.drone.modules.base.service.mybatis.ISensorService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @Auther: cw
 * @Date: 2018/10/30 19:02
 * @Description: 主要用于调试历正无人机接口
 */
@Slf4j
@Component
public class LZUtils {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ILzDroneService iLzDroneService;

    @Autowired
    private IDroneService iDroneService;

    @Autowired
    private ISensorService iSensorService;

    private String URL;


    @Autowired
    private LZPushUtil lzPushUtil;


    /**
     * 登录历正，用户名和密码为固定参数，登录成功后，将token存入redis
     *
     * @return
     */
    public String login() {
        //从缓存中读取  lz  url
        checkURL();
        log.info(" 通过配置取出的URL  为  " + URL);
        Map<String, String> params = new HashMap<String, String>();
        params.put("username", "demo");
        params.put("password", "secret");
        String result = HttpClientUtil.doPost(URL + "login", params);
        JSONObject jsonObject = JSON.parseObject(result);
        String data = jsonObject.getString("token");
        stringRedisTemplate.opsForValue().set("token", data);
        log.info(" 登录结果  为 " + result.toString());
        return result;
    }


    /**
     * 查询版本号,请求时需要加上 token
     *
     * @return
     */
    public String version() {
        //从缓存中读取  lz  url
        checkURL();
        Map<String, String> params = new HashMap<String, String>();
        params.put("query", "{version}");
        String result = HttpClientUtil.doGetWithAuth(URL + ":3200" + "/graphql", params, stringRedisTemplate.opsForValue().get("token"));
        log.info(" 查询版本结果  为 " + result.toString());
        //将查询结果放入数据库（站点温度是否在线等）
        //转换为jsonObject
        JSONObject jsonObj = JSONObject.parseObject(result);

        jsonObj = jsonObj.getJSONObject("data");

        log.info(" jsonArray.toString " + jsonObj.toString());
        iSensorService.updSensorByLz(jsonObj.toString());
        return result;
    }


    /**
     * 查询机型,请求时需要加上 token
     *
     * @return
     */
    public String dronetypes() {
        //从缓存中读取  lz  url
        checkURL();
        Map<String, String> params = new HashMap<String, String>();
        params.put("query", "{dronetypes}");
        String result = HttpClientUtil.doPostWithAuth(URL + ":8080/" + "rf/graphql", JSON.toJSON(params).toString(), stringRedisTemplate.opsForValue().get("token"));
        log.info(" 查询机型列表结果  为 " + result.toString());
        return result;
    }


    /**
     * 获取无人机
     *
     * @return
     */
    public AjaxResponse queryDrones() {
        checkURL();
        LzDrone lzDrone = new LzDrone();
        Map<String, String> params = new HashMap<String, String>();
        params.put("query", "{drone{id,image,name,state,can_attack,can_takeover,directions{group,direction},peer_directions{group,direction},created_time,deleted_time,whitelisted,attacking,seen_sensor{detected_freq_khz,detected_time}}}");
        String result = HttpClientUtil.doPostWithAuth(URL + ":8080/" + "rf/graphql", JSON.toJSON(params).toString(), stringRedisTemplate.opsForValue().get("token"));
        log.info(" 查询无人机  结果为 " + result);
        if (StringUtils.trim(result).equals("Unauthorized")) {
            log.error("囧囧囧 ，token  已过期  需要重新登录");
            login();
        }
        //转换为jsonObject
        JSONObject jsonObj = JSONObject.parseObject(result);

        jsonObj = jsonObj.getJSONObject("data");
        JSONArray jsonArray = jsonObj.getJSONArray("drone");
        log.info(" jsonArray.toString " + jsonArray.toString());
        //是否向前端推送    1:   新发现无人机   2 无人机消失
        Boolean pushFront = false;
        if (jsonArray.isEmpty()) {
            //没有发现无人机，清空redis缓存
            log.info(" 没有发现无人机，准备清空redis缓存 ");
            Set<Object> keys = stringRedisTemplate.opsForHash().keys(DroneConstant.LZ_DRONE);
            for (Iterator<Object> iterator = keys.iterator(); iterator.hasNext(); ) {
                //只要历正服务获取不到，本地有，说明无人机消失了。也要推送
                log.info(" 历正服务获取不到，本地有，说明无人机消失了。也要推送 ");
                pushFront = true;
                //     Object temp = stringRedisTemplate.opsForHash().get(DroneConstant.LZ_DRONE, String.valueOf(iterator.next()));
                Object temp = stringRedisTemplate.opsForHash().get(DroneConstant.LZ_DRONE, String.valueOf(iterator.next()));
                Drone drone = JSON.parseObject(String.valueOf(temp), Drone.class);
                int j = iDroneService.updateDroneTOEnd(drone);
                if (j == 1) {
                    stringRedisTemplate.opsForHash().delete(DroneConstant.LZ_DRONE, drone.getDroneid());
                }

            }
            if (pushFront) {
                log.info(" 无人机消失不见了，要推送！ ");
                lzPushUtil.pushDroneToFront();
                pushFront = false;
            }
            return AjaxResponse.failed(" 当前没有发现无人机 ");
        }

        //取出无人机数据，存入数据库      新发现无人机  推送
        List<DroneThirdFloor> ll = JSON.parseArray(jsonArray.toJSONString(), DroneThirdFloor.class);

        //访问信息暂时没有用
       /* for (DroneThirdFloor droneThirdFloor : ll) {
            List<DirectionsTemporary> dList = droneThirdFloor.getDirections();
            log.info(" dList.toString " + dList);
            List<PeerDirectionsTemporary> pdList = droneThirdFloor.getPeer_directions();
            log.info(" pdList.toString " + pdList);
        }*/
        return iDroneService.insertDroneByLz(ll);

    }

    /**
     * 打击无人机id
     *
     * @param droneid  无人机id
     * @param cancel   攻击还是取消攻击
     * @param takeover 是否接管无人机，一旦接管之后，需要再发送后续命令控制无人机
     * @return
     */
    public String attackDrones(String droneid, Boolean cancel, Boolean takeover) {
        checkURL();
        //非标准JSON, 需要手动拼接，无法通过map  生成
        // {"query":"mutation attack{attack(id:\"483e241f\", cancel:false, takeover:false)}"}
        StringBuffer sb = new StringBuffer();
        sb.append("{\"query\"").append(":").append("\"mutation attack{attack(id:\\").append("\"").append(droneid).append("\\").append("\",cancel:").append(cancel).append(",takeover:").append(takeover).append(")}\"}");
        log.info(" 攻击参数为   " + sb.toString());
        String result = HttpClientUtil.doPostWithAuth(URL + ":8080/" + "rf/graphql", sb.toString(), stringRedisTemplate.opsForValue().get("token"));
        log.info("  下发攻击无人机指令attackDrones " + result.toString());
        return result;
    }


    //

    /**
     * 设置自动攻击状态,需要token
     *
     * @param autoAttack true自动攻击   false取消自动攻击
     * @return
     */
    public String autoAttack(Boolean autoAttack) {
        checkURL();
        //非标准JSON, 需要手动拼接，无法通过map  生成
        // {"query":"mutation autoAttack{\n        autoAttack(on:true)\n    }"}
        StringBuffer sb = new StringBuffer();
        sb.append("{\"query\"").append(":").append("\"mutation autoAttack{autoAttack(on:").append(autoAttack).append(")}\"}");
        log.info("  下发自动攻击参数为   " + sb.toString());
        String result = HttpClientUtil.doPostWithAuth(URL + ":8080/" + "rf/graphql", sb.toString(), stringRedisTemplate.opsForValue().get("token"));
        log.info("  下发自动攻击无人机指令autoAttack " + result.toString());
        return result;
    }


    /**
     * 查询自动攻击设置的状态,需要token
     *
     * @return
     */
    public String cheackAutoState() {
        checkURL();
        log.info(" 查询自动攻击设置的状态 ");
        Map<String, String> params = new HashMap<String, String>();
        params.put("query", "{autoAttack}");
        String result = HttpClientUtil.doPostWithAuth(URL + ":8080/" + "rf/graphql", JSON.toJSON(params).toString(), stringRedisTemplate.opsForValue().get("token"));
        log.info(" 查询自动攻击设置的状态结果为 " + result);
        //即为未出错的情况下
        if (result.indexOf("error") == -1) {
            JSONObject jsonObject = JSONObject.parseObject(result);
            jsonObject = jsonObject.getJSONObject("data");
            stringRedisTemplate.opsForValue().set(DroneConstant.AUTO_ATTACK, jsonObject.toString());
        }
        return result;
    }


    /**
     * 新增白名单，需要token
     *
     * @param droneid   无人机id
     * @param dronetype 无人机类型
     * @return
     */

    public String addWhitelist(String droneid, String dronetype) {
        checkURL();
        //非标准JSON, 需要手动拼接，无法通过map  生成
        //新增白名单 {"query":"mutation whitelist{\n addWhitelist(id:\"1231232\", dronetype:\"3DR solo\")\n }"}
        StringBuffer sb = new StringBuffer();
        sb.append("{\"query\"").append(":").append("\"mutation whitelist{addWhitelist(id:\\").append("\"").append(droneid).append("\\").append("\",dronetype:\\").append("\"").append(dronetype).append("\\").append("\"").append(")}\"}");
        log.info(" 新增白名单参数为   " + sb.toString());
        String result = HttpClientUtil.doPostWithAuth(URL + ":8080/" + "rf/graphql", sb.toString(), stringRedisTemplate.opsForValue().get("token"));
        log.info("  新增白名单addWhitelist " + result.toString());
        return result;
    }


    /**
     * 删除白名单,需要token
     *
     * @param droneid 无人机id
     * @return
     */
    public String deleteWhitelist(String droneid) {
        checkURL();
        //非标准JSON, 需要手动拼接，无法通过map  生成
        //删除白名单 {"query":"mutation whitelist{\n        deleteWhitelist(id:\"1231232\")\n    }"}
        StringBuffer sb = new StringBuffer();
        sb.append("{\"query\"").append(":").append("\"mutation whitelist{deleteWhitelist(id:\\").append("\"").append(droneid).append("\\").append("\"").append(")}\"}");
        log.info(" 删除白名单参数为   " + sb.toString());
        String result = HttpClientUtil.doPostWithAuth(URL + ":8080/" + "rf/graphql", sb.toString(), stringRedisTemplate.opsForValue().get("token"));
        log.info("  删除白名单deleteWhitelist结果为 " + result.toString());
        return result;
    }


    /**
     * 控制无人机
     *
     * @param droneid 无人机id
     * @param cmd     无人机的控制参数：不同的机型不同，但基本相同 基本命令包括takeoff, landing, left, right, up, down， forward, backward
     * @return
     */
    public String control(String droneid, String cmd) {
        checkURL();
        //发送无人机控制指令
        //{"query":"mutation control{\n        control(id:\"483e241f\", command:\"down\")\n    }"}
        StringBuffer sb = new StringBuffer();
        sb.append("{\"query\"").append(":").append("\"mutation control{control(id:\\").append("\"").append(droneid).append("\\").append("\",command:\\").append("\"").append(cmd).append("\\").append("\"").append(")}\"}");
        log.info(" 无人机控制指令参数为   " + sb.toString());
        String result = HttpClientUtil.doPostWithAuth(URL + ":8080/" + "rf/graphql", sb.toString(), stringRedisTemplate.opsForValue().get("token"));
        log.info("  无人机控制指令control结果为 " + result.toString());
        return result;
    }


    /**
     * 查询白名单,请求时需要加上 token
     *
     * @return
     */
    public String whiteList() {
        checkURL();
        Map<String, String> params = new HashMap<String, String>();
        params.put("query", "{whitelist {id,dronetype}}");
        String result = HttpClientUtil.doPostWithAuth(URL + ":8080/" + "rf/graphql", JSON.toJSON(params).toString(), stringRedisTemplate.opsForValue().get("token"));
        log.info(" 查询白名单的结果为 " + result);
        JSONObject jsonObj = JSONObject.parseObject(result);
        //log.info(" jsonObj.toString " + jsonObj.toString());
        jsonObj = jsonObj.getJSONObject("data");
        //log.info(" jsonArray.toString " + jsonObj.toString());
        JSONArray jsonArray = jsonObj.getJSONArray("whitelist");
        log.info(" jsonArray.toString " + jsonArray.toString());

        //第三层数据  全部可获取
        List<WhiteTemporary> dtResult = JSON.parseArray(jsonArray.toJSONString(), WhiteTemporary.class);
        log.info(" dtResult.toString " + dtResult);
        return result;
    }

    /**
     * 获取站点信息
     *
     * @return
     */
    public AjaxResponse querySensor() {
        checkURL();
        LzDrone lzDrone = new LzDrone();
        Map<String, String> params = new HashMap<String, String>();
        params.put("query", "{sensor{id,name,state,ttl,config{build_time,df_deflection,engine_ip,engine_port,geo_location{lat,lng},git_hash,id,mqtt_server,name,ttl,version}}}");
        String result = HttpClientUtil.doPostWithAuth(URL + ":8080/" + "rf/graphql", JSON.toJSON(params).toString(), stringRedisTemplate.opsForValue().get("token"));
        log.info(" 查询无人机  结果为 " + result);
        if (StringUtils.trim(result).equals("Unauthorized")) {
            log.error("囧囧囧 ，token  已过期  需要重新登录");
            login();
        }
        //转换为jsonObject
        JSONObject jsonObj = JSONObject.parseObject(result);

        jsonObj = jsonObj.getJSONObject("data");
        JSONArray jsonArray = jsonObj.getJSONArray("sensor");
        log.info(" jsonArray.toString " + jsonArray.toString());
        if (jsonArray.isEmpty()) {
            return AjaxResponse.failed(" 当前没有发现站点 ");
        }

        //取出站点数据，存入数据库
        List<SensorFloor> ll = JSON.parseArray(jsonArray.toJSONString(), SensorFloor.class);
        Map<String, Object> webmap = new HashMap<String, Object>();
        webmap.put("status", "0");
        webmap.put("msg", "alarm drone");
        webmap.put("data", ll);
        //向前端推送
        // WebSocketUserUtils.sendDroneToCust(JSON.toJSONString(webmap));
        //访问信息暂时没有用
       /* for (DroneThirdFloor droneThirdFloor : ll) {
            List<DirectionsTemporary> dList = droneThirdFloor.getDirections();
            log.info(" dList.toString " + dList);
            List<PeerDirectionsTemporary> pdList = droneThirdFloor.getPeer_directions();
            log.info(" pdList.toString " + pdList);
        }*/
        return iSensorService.updSensorByLz(ll);

    }


    /**
     * 判断历正接口是否返回错误
     *
     * @param result
     * @return
     */
    public Boolean isSuccess(String result) {
        if (result.indexOf("errors") == -1) {
            return true;
        }
        return false;

    }

    public static void main(String[] args) {
    }


    /**
     * 从redis中获取站点的自动打击状态，如果缓存中未取到，则向历正重新拉取一次，并存入redis
     *
     * @return
     */
    public Boolean getAtuoattackFromRedis() {
        String temp = stringRedisTemplate.opsForValue().get(DroneConstant.AUTO_ATTACK);
        if (StringUtils.isBlank(temp)) {
            log.info(" 缓存中未获取到，需要重新拉取！ ");
            cheackAutoState();
        }
        temp = stringRedisTemplate.opsForValue().get(DroneConstant.AUTO_ATTACK);
        SenorAtuoAttack senorAtuoAttack = JSONObject.parseObject(temp, SenorAtuoAttack.class);
        return senorAtuoAttack.getAutoAttack();
    }


    public void checkURL() {
        //从缓存中读取  lz  url
        if (StringUtils.isBlank(URL)) {
            String sensors = (String) stringRedisTemplate.opsForHash().get(DroneConstant.RY_CONFIG, "sensorConfig");
            if (StringUtils.isBlank(sensors)) {
                log.error("  囧囧囧   囧 ,  数据库好像不有这个配置  抛个错吧");
                throw new DroneException("囧囧囧   囧 ,  数据库好像不有这个配置 ");
            }
            List<DroneConfigAttr> attrs = JSONObject.parseObject(sensors, DroneConfig.class).getAttrs();
            for (DroneConfigAttr attr : attrs) {
                if (attr.getAttrkey().equals("sensorUrl")) {
                    URL = attr.getAttrvalue();
                }
            }
        }
    }
}
