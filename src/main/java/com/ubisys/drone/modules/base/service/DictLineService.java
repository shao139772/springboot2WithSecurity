package com.ubisys.drone.modules.base.service;

import cn.hutool.core.lang.Dict;
import com.ubisys.drone.basetemplate.DroneBaseService;
import com.ubisys.drone.modules.base.entity.DictLine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * O(∩_∩)O哈哈~接口
 * @author miaomiao
 */
public interface DictLineService extends DroneBaseService<DictLine,Integer> {


    /**
     * 根据字典标题搜索
     * @param key
     * @return
     */
    List<DictLine> findByTitleOrTypeLike(String key);

    /**
     * 根据字典type搜索
     * @param type 类型
     * @return
     */
    DictLine findByType(String type);
}