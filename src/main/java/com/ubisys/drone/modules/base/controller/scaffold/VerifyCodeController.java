package com.ubisys.drone.modules.base.controller.scaffold;

import com.ubisys.drone.common.utils.AjaxResponse;
import com.ubisys.drone.common.utils.VerifyCodeUtils;
import com.ubisys.drone.common.vo.VerifyCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * <p>Title: 验证码控制层</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: http://www.ubisys.com.cn/</p>
 *
 * @Auther: cw
 * @Date: 2018/12/24 14:55
 */
@Api(description = "验证码接口")
@RequestMapping("/drone/verifyCode")
@RestController
@Transactional
public class VerifyCodeController {


    @Autowired
    private StringRedisTemplate redisTemplate;

    @RequestMapping(value = "/init", method = RequestMethod.POST)
    @ApiOperation(value = "初始化验证码")
    public AjaxResponse initCaptcha() {

        String verifyCodeId = UUID.randomUUID().toString().replace("-", "");
        String code = new VerifyCodeUtils().randomStr(4);
        VerifyCode verifyCode = new VerifyCode();
        verifyCode.setVerifyCodeId(verifyCodeId);
        //缓存验证码  有效期3分钟
        redisTemplate.opsForValue().set(verifyCodeId, code, 3L, TimeUnit.MINUTES);
        return AjaxResponse.success(verifyCode);
    }



    @RequestMapping(value = "/draw/{verifyCode}", method = RequestMethod.POST)
    @ApiOperation(value = "根据验证码ID获取图片")
    public void drawCaptcha(@PathVariable("verifyCode") String verifyCode, HttpServletResponse response) throws IOException {
        //得到验证码 生成指定验证码
        String code = redisTemplate.opsForValue().get(verifyCode);
        VerifyCodeUtils vCode = new VerifyCodeUtils(116, 36, 4, 10, code);
        vCode.write(response.getOutputStream());
    }

}
