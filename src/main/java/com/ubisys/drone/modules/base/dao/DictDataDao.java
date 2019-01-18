package com.ubisys.drone.modules.base.dao;

import com.ubisys.drone.basetemplate.DroneBaseDao;
import com.ubisys.drone.modules.base.entity.DictData;

import java.util.List;

/**
 * O(∩_∩)O哈哈~数据处理层
 *
 * @author miaomiao
 */
public interface DictDataDao extends DroneBaseDao<DictData, Integer> {


    /**
     * 通过dictId和状态获取
     *
     * @param dictId 所属字典id
     * @param status 状态
     * @return
     */
    List<DictData> findByDictIdAndStatusOrderBySortOrder(Integer dictId, Integer status);

    /**
     * 通过dictId删除
     *
     * @param dictId
     */
    void deleteByDictId(Integer dictId);

}