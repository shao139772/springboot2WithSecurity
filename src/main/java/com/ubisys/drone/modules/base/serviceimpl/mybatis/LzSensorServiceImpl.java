package com.ubisys.drone.modules.base.serviceimpl.mybatis;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ubisys.drone.modules.base.dao.mapper.LzSensorMapper;
import com.ubisys.drone.modules.base.entity.LzSensor;
import com.ubisys.drone.modules.base.service.mybatis.ILzSensorService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author cw
 * @since 2018-11-01
 */
@Service
public class LzSensorServiceImpl extends ServiceImpl<LzSensorMapper, LzSensor> implements ILzSensorService {

}
