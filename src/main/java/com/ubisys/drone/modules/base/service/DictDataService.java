package com.ubisys.drone.modules.base.service;

import com.ubisys.drone.basetemplate.DroneBaseService;
import com.ubisys.drone.modules.base.entity.DictData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * O(∩_∩)O哈哈~接口
 *
 * @author miaomiao
 */
public interface DictDataService extends DroneBaseService<DictData, Integer> {


    /**
     * 根据 字典头删除
     *
     * @param id
     */
    void deleteByDictId(Integer id);


    /**
     * 分页条件查询字典属性
     *
     * @param dictData 字典属性对象
     * @param pageable 分页对象
     * @return
     */
    Page<DictData> findByCondition(DictData dictData, Pageable pageable);


    /**
     * 通过所属字典头id查询
     *
     * @param id 所属字典头id查询
     * @return
     */
    List<DictData> findByDictId(Integer id);
}