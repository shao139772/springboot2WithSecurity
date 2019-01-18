package com.ubisys.drone.modules.base.serviceimpl;

import com.ubisys.drone.modules.base.dao.DictLineDao;
import com.ubisys.drone.modules.base.entity.DictLine;
import com.ubisys.drone.modules.base.service.DictLineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * O(∩_∩)O哈哈~接口实现
 *
 * @author miaomiao
 */
@Slf4j
@Service
@Transactional
public class DictLineServiceImpl implements DictLineService {

    @Autowired
    private DictLineDao dictLineDao;

    @Override
    public DictLineDao getRepository() {
        return dictLineDao;
    }

    /**
     * 根据字典标题和类型搜索
     *
     * @param key 模糊搜索
     * @return
     */
    @Override
    public List<DictLine> findByTitleOrTypeLike(String key) {
        return dictLineDao.findByTitleOrTypeLike(key);
    }


    /**
     * 根据字典type搜索
     *
     * @param type 类型
     * @return
     */
    @Override
    public DictLine findByType(String type) {
        List<DictLine> list = dictLineDao.findByType(type);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
}