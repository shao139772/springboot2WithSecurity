package com.ubisys.drone.modules.base.serviceimpl.mybatis;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ubisys.drone.modules.base.dao.mapper.UbPendingCertificationMapper;
import com.ubisys.drone.modules.base.entity.UbisysDroneInfo;
import com.ubisys.drone.modules.base.entity.UbisysPendingCertification;
import com.ubisys.drone.modules.base.service.mybatis.UbDroneInfoService;
import com.ubisys.drone.modules.base.service.mybatis.UbPendingCertificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>Title: 无人机接口实现</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: http://www.ubisys.com.cn/</p>
 *
 * @Auther: Jhon Davis
 * @Date: 2018/12/17 17:59
 */
@Service
@Slf4j
public class UbPendingCertificationServiceImpl extends ServiceImpl<UbPendingCertificationMapper, UbisysPendingCertification> implements UbPendingCertificationService {

}
