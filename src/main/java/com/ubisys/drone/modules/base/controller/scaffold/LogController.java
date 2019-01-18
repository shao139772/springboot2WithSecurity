package com.ubisys.drone.modules.base.controller.scaffold;

import com.ubisys.drone.common.utils.AjaxResponse;
import com.ubisys.drone.common.utils.PageUtil;
import com.ubisys.drone.common.vo.PageVo;
import com.ubisys.drone.common.vo.SearchVo;
import com.ubisys.drone.modules.base.entity.Log;
import com.ubisys.drone.modules.base.service.LogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * <p>Title: 日志管理</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: http://www.ubisys.com.cn/</p>
 *
 * @Auther: cw
 * @Date: 2018/12/24 14:36
 */
@Slf4j
@RestController
@Api(description = "日志管理接口")
@RequestMapping("/drone/log")
@Transactional
public class LogController {


    @Autowired
    private LogService logService;

    @RequestMapping(value = "/getAllByPage", method = RequestMethod.POST)
    @ApiOperation(value = "分页获取全部日志")
    public AjaxResponse getAllByPage(@RequestParam(required = false) Integer type,
                                     @RequestParam String key,
                                     @ModelAttribute SearchVo searchVo,
                                     @ModelAttribute PageVo pageVo) {
        Page<Log> page = logService.findByConfition(type, key, searchVo, PageUtil.initPage(pageVo));
        return AjaxResponse.success(page);
    }

    @RequestMapping(value = "/delByIds/{ids}", method = RequestMethod.DELETE)
    @ApiOperation(value = "批量删除日志")
    public AjaxResponse delByIds(@PathVariable String[] ids) {

        for (String id : ids) {
            logService.delete(Integer.valueOf(id));
        }
        return AjaxResponse.success("删除成功");
    }

    @RequestMapping(value = "/delAll", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除全部日志，慎用！")
    public AjaxResponse delAll() {
        logService.deleteAll();
        return AjaxResponse.success("删除成功");
    }

}
