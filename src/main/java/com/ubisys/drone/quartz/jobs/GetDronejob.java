package com.ubisys.drone.quartz.jobs;

import cn.hutool.core.date.DateUtil;
import com.ubisys.drone.common.constant.DroneConstant;
import com.ubisys.drone.common.spring.SpringContextUtil;
import com.ubisys.drone.common.utils.LZUtils;
import com.ubisys.drone.modules.base.service.mybatis.ILzWhitelistService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;

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
//控制并发
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
@Slf4j
public class GetDronejob implements Job {


    //历正的工具类
    private LZUtils lzUtils;


    //白名单
    private ILzWhitelistService lzWhitelistService;


    public GetDronejob() {
        this.lzUtils = SpringContextUtil.getBean(LZUtils.class);
        this.lzWhitelistService = SpringContextUtil.getBean(ILzWhitelistService.class);
        ;
    }


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("  定时获取无人机  job  当前时间为" + DateUtil.formatDateTime(new Date()));


        try {
         //   lzUtils.queryDrones();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(DroneConstant.ERR_MSG, e);
        }

    }
}
