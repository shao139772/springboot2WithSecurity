package com.ubisys.drone.modules.base.serviceimpl.mybatis;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ubisys.drone.modules.base.dao.mapper.LzSensorConfigMapper;
import com.ubisys.drone.modules.base.entity.LzSensorConfig;
import com.ubisys.drone.modules.base.service.mybatis.ILzSensorConfigService;
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
public class LzSensorConfigServiceImpl extends ServiceImpl<LzSensorConfigMapper, LzSensorConfig> implements ILzSensorConfigService {

}
