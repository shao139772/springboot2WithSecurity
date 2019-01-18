package com.ubisys.drone.modules.base.serviceimpl;

import com.ubisys.drone.common.constant.DroneConstant;
import com.ubisys.drone.modules.base.dao.DictDataDao;
import com.ubisys.drone.modules.base.entity.DictData;
import com.ubisys.drone.modules.base.service.DictDataService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * O(∩_∩)O哈哈~接口实现
 *
 * @author miaomiao
 */
@Slf4j
@Service
@Transactional
public class DictDataServiceImpl implements DictDataService {

    @Autowired
    private DictDataDao dictDataDao;

    @Override
    public DictDataDao getRepository() {
        return dictDataDao;
    }

    @Override
    public void deleteByDictId(Integer id) {
        dictDataDao.deleteByDictId(id);
    }

    @Override
    public Page<DictData> findByCondition(DictData dictData, Pageable pageable) {
        return dictDataDao.findAll(new Specification<DictData>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<DictData> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

                Path<String> titleField = root.get("title");
                Path<Integer> statusField = root.get("status");
                Path<String> dictIdField = root.get("dictId");

                List<Predicate> list = new ArrayList<Predicate>();

                //模糊搜素
                if (StringUtils.isNotBlank(dictData.getTitle())) {
                    list.add(cb.like(titleField, '%' + dictData.getTitle() + '%'));
                }

                //状态
                if (dictData.getStatus() != null) {
                    list.add(cb.equal(statusField, dictData.getStatus()));
                }

                //所属字典
                if (null != dictData.getDictId()) {
                    list.add(cb.equal(dictIdField, dictData.getDictId()));
                }

                Predicate[] arr = new Predicate[list.size()];
                cq.where(list.toArray(arr));
                return null;
            }
        }, pageable);
    }


    @Override
    public List<DictData> findByDictId(Integer id) {
        return dictDataDao.findByDictIdAndStatusOrderBySortOrder(id, DroneConstant.STATUS_NORMAL);
    }
}