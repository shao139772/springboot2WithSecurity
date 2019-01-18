package com.ubisys.drone.quartz.jobs;

import cn.hutool.core.date.DateUtil;
import com.ubisys.drone.common.constant.DroneConstant;
import com.ubisys.drone.common.spring.SpringContextUtil;
import com.ubisys.drone.common.utils.LZUtils;
import com.ubisys.drone.modules.base.service.mybatis.ILzWhitelistService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

/**
 * <p>Title: job示例</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: http://www.ubisys.com.cn/</p>
 *
 * @Auther: cw
 * @Date: 2018/12/17 10:31
 */
@Slf4j
public class GetSensorjob implements Job {


    //历正的工具类
    private LZUtils lzUtils;


    //白名单
    private ILzWhitelistService lzWhitelistService;


    public GetSensorjob(LZUtils lzUtils, ILzWhitelistService lzWhitelistService) {
         log.info("    ");
        this.lzUtils = SpringContextUtil.getBean(LZUtils.class);
        this.lzWhitelistService = SpringContextUtil.getBean(ILzWhitelistService.class);
        ;
    }

    public GetSensorjob() {
        this.lzUtils = SpringContextUtil.getBean(LZUtils.class);
        this.lzWhitelistService = SpringContextUtil.getBean(ILzWhitelistService.class);
        ;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("  定时获取站点信息  job  当前时间为" + DateUtil.formatDateTime(new Date()));


        try {
          //  lzUtils.querySensor();//获取站点基础信息
            //lzUtils.version();//站点温度信息等
        } catch (Exception e) {
            e.printStackTrace();
            log.error(DroneConstant.ERR_MSG, e);
        }

    }
}
