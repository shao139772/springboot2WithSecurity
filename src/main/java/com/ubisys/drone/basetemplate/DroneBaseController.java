package com.ubisys.drone.basetemplate;

import com.ubisys.drone.common.utils.AjaxResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.Serializable;

/**
 */
public abstract class DroneBaseController<E, ID extends Serializable> {

    /**
     * 获取service
     *
     * @return
     */
    @Autowired
    public abstract DroneBaseService<E, ID> getService();

    @RequestMapping(value = "/get/{id}", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "通过id获取")
    public AjaxResponse get(@PathVariable ID id) {
        E entity = getService().get(id);
        return AjaxResponse.success(entity);
    }


}
