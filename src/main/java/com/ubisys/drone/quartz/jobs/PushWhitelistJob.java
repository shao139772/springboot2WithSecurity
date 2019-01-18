package com.ubisys.drone.quartz.jobs;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.mapper.Condition;
import com.ubisys.drone.common.spring.SpringContextUtil;
import com.ubisys.drone.common.utils.LZUtils;
import com.ubisys.drone.modules.base.entity.LzWhitelist;
import com.ubisys.drone.modules.base.service.mybatis.ILzWhitelistService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;
import java.util.List;

/**
 * <p>Title: 带参数的job示例</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: http://www.ubisys.com.cn/</p>
 *
 * @Auther: cw
 * @Date: 2018/12/17 10:57
 */
@Slf4j
public class PushWhitelistJob implements Job {

    private String parameter;

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }


    //历正的工具类
    private LZUtils lzUtils;


    //白名单
    private ILzWhitelistService lzWhitelistService;


    public PushWhitelistJob(LZUtils lzUtils, ILzWhitelistService lzWhitelistService) {
        this.lzUtils = SpringContextUtil.getBean(LZUtils.class);
        this.lzWhitelistService = SpringContextUtil.getBean(ILzWhitelistService.class);
    }


    public PushWhitelistJob() {
        this.lzUtils = SpringContextUtil.getBean(LZUtils.class);
        this.lzWhitelistService = SpringContextUtil.getBean(ILzWhitelistService.class);
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("  PushWhitelistJob    当前时间为" + DateUtil.formatDateTime(new Date()));
       /* List<LzWhitelist> lzWhitelistList = lzWhitelistService.selectList(Condition.create().eq("is_white", "1").eq("is_lz", "1"));//查询未和立正同步的白名单
        for (LzWhitelist lzWhitelist : lzWhitelistList) {//逐一推送
            String result = lzUtils.addWhitelist(lzWhitelist.getDroneId(), lzWhitelist.getDronetype());
            if (result.indexOf("error") == -1) {//字符串中无error字样即为推送成功
                lzWhitelist.setIsLz("2");//修改为已同步
                lzWhitelistService.update(lzWhitelist, Condition.create().eq("id", lzWhitelist.getId()));//推送成功更新数据库
            }
        }*/
    }
}
