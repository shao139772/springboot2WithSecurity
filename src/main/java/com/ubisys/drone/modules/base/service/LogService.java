package com.ubisys.drone.modules.base.service;


import com.ubisys.drone.basetemplate.DroneBaseService;
import com.ubisys.drone.common.vo.SearchVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.ubisys.drone.modules.base.entity.Log;

/**
 * 日志接口
 */
public interface LogService extends DroneBaseService<Log,Integer> {

    /**
     * 分页搜索获取日志
     * @param type
     * @param key
     * @param searchVo
     * @param pageable
     * @return
     */
    Page<Log> findByConfition(Integer type, String key, SearchVo searchVo, Pageable pageable);
    /**
     * 删除所有
     */
    void deleteAll();
}
