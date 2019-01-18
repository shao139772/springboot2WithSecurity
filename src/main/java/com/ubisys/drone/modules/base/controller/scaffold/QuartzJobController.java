package com.ubisys.drone.modules.base.controller.scaffold;

import com.ubisys.drone.common.aop.SystemLogAspect;
import com.ubisys.drone.common.constant.DroneConstant;
import com.ubisys.drone.common.spring.SpringContextUtil;
import com.ubisys.drone.common.utils.AjaxResponse;
import com.ubisys.drone.common.utils.PageUtil;
import com.ubisys.drone.common.vo.PageVo;
import com.ubisys.drone.config.exception.DroneException;
import com.ubisys.drone.modules.base.entity.QuartzJob;
import com.ubisys.drone.modules.base.service.QuartzJobService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * <p>Title: 定时任务管理</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: http://www.ubisys.com.cn/</p>
 *
 * @Auther: cw
 * @Date: 2018/12/17 10:14
 */
@Slf4j
@RestController
@RequestMapping(value = "/drone/quartzJob")
@Transactional
@Api(description = "定时任务管理")
public class QuartzJobController {

    @Autowired
    private QuartzJobService quartzJobService;

    @Autowired
    private Scheduler scheduler;

    @RequestMapping(value = "/getAllByPage", method = RequestMethod.GET)
    @ApiOperation(value = "获取所有定时任务")
    public AjaxResponse getAll(@ModelAttribute PageVo page) {
        Page<QuartzJob> data = quartzJobService.findAll(PageUtil.initPage(page));

        return AjaxResponse.success(data);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "添加定时任务")
    public AjaxResponse addJob(@ModelAttribute QuartzJob job) {

        List<QuartzJob> list = quartzJobService.findByJobClassName(job.getJobClassName());
        if (list != null && list.size() > 0) {
            return AjaxResponse.failed("该定时任务类名已存在");
        }
        add(job.getJobClassName(), job.getCronExpression(), job.getParameter());
        quartzJobService.save(job);
        return AjaxResponse.success("创建定时任务成功");
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ApiOperation(value = "更新定时任务")
    public AjaxResponse editJob(@ModelAttribute QuartzJob job) {

        delete(job.getJobClassName());
        add(job.getJobClassName(), job.getCronExpression(), job.getParameter());
        job.setStatus(DroneConstant.STATUS_NORMAL);
        quartzJobService.update(job);
        return AjaxResponse.success().setMsg("更新定时任务成功");
    }

    @RequestMapping(value = "/pause", method = RequestMethod.POST)
    @ApiOperation(value = "暂停定时任务")
    public AjaxResponse pauseJob(@ModelAttribute QuartzJob job) {

        try {
            scheduler.pauseJob(JobKey.jobKey(job.getJobClassName()));
        } catch (SchedulerException e) {
            throw new DroneException("暂停定时任务失败");
        }
        job.setStatus(DroneConstant.STATUS_DISABLE);
        quartzJobService.update(job);
        return AjaxResponse.success().setMsg("暂停定时任务成功");
    }

    @RequestMapping(value = "/resume", method = RequestMethod.POST)
    @ApiOperation(value = "恢复定时任务")
    public AjaxResponse resumeJob(@ModelAttribute QuartzJob job) {

        try {
            scheduler.resumeJob(JobKey.jobKey(job.getJobClassName()));
        } catch (SchedulerException e) {
            throw new DroneException("恢复定时任务失败");
        }
        job.setStatus(DroneConstant.STATUS_NORMAL);
        quartzJobService.update(job);
        return AjaxResponse.success().setMsg("恢复定时任务成功");
    }

    @RequestMapping(value = "/delByIds/{ids}", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除定时任务")
    public AjaxResponse deleteJob(@PathVariable String[] ids) {

        for (String id : ids) {
            QuartzJob job = quartzJobService.get(Integer.valueOf(id));
            delete(job.getJobClassName());
            quartzJobService.delete(job);
        }
        return AjaxResponse.success().setMsg("删除定时任务成功");
    }

    /**
     * 添加定时任务
     *
     * @param jobClassName
     * @param cronExpression
     * @param parameter
     */
    public void add(String jobClassName, String cronExpression, String parameter) {

        try {
            // 启动调度器
            scheduler.start();

            //构建job信息
            JobDetail jobDetail = JobBuilder.newJob(getClass(jobClassName).getClass())
                    .withIdentity(jobClassName)
                    .usingJobData("parameter", parameter)
                    .build();

            //表达式调度构建器(即任务执行的时间)
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

            //按新的cronExpression表达式构建一个新的trigger
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobClassName)
                    .withSchedule(scheduleBuilder).build();

            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            log.error(e.toString());
            throw new DroneException("创建定时任务失败");
        } catch (Exception e) {
            throw new DroneException("后台找不到该类名任务");
        }
    }

    /**
     * 删除定时任务
     *
     * @param jobClassName
     */
    public void delete(String jobClassName) {

        try {
            scheduler.pauseTrigger(TriggerKey.triggerKey(jobClassName));
            scheduler.unscheduleJob(TriggerKey.triggerKey(jobClassName));
            scheduler.deleteJob(JobKey.jobKey(jobClassName));
        } catch (Exception e) {
            throw new DroneException("删除定时任务失败");
        }
    }

    public static Job getClass(String classname) throws Exception {
        Class<?> class1 = Class.forName(classname);
        return (Job) class1.newInstance();
    }


}
