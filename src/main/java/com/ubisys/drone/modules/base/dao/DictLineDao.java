package com.ubisys.drone.modules.base.dao;

import com.ubisys.drone.basetemplate.DroneBaseDao;
import com.ubisys.drone.modules.base.entity.DictLine;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * O(∩_∩)O哈哈~数据处理层
 *
 * @author miaomiao
 */
public interface DictLineDao extends DroneBaseDao<DictLine, Integer> {


    /**
     * 通过type获取
     *
     * @param type 类型
     * @return
     */
    List<DictLine> findByType(String type);

    /**
     * 标题和类型模糊搜索
     *
     * @param key 关键字
     * @return
     */
    @Query(value = "select * from sys_dict_line d where d.title like %:key% or d.type like %:key%", nativeQuery = true)
    List<DictLine> findByTitleOrTypeLike(@Param("key") String key);

}