package com.ubisys.drone.modules.base.serviceimpl;

import com.ubisys.drone.modules.base.dao.QuartzJobDao;
import com.ubisys.drone.modules.base.entity.QuartzJob;
import com.ubisys.drone.modules.base.service.QuartzJobService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>Title: 定时任务接口实现</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: http://www.ubisys.com.cn/</p>
 *
 * @Auther: cw
 * @Date: 2018/12/17 17:59
 */
@Slf4j
@Service
@Transactional
public class QuartzJobServiceImpl implements QuartzJobService {

    @Autowired
    private QuartzJobDao quartzJobDao;

    @Override
    public QuartzJobDao getRepository() {
        return quartzJobDao;
    }

    @Override
    public List<QuartzJob> findByJobClassName(String jobClassName) {

        return quartzJobDao.findByJobClassName(jobClassName);
    }
}