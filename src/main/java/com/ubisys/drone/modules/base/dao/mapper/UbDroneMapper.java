package com.ubisys.drone.modules.base.dao.mapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ubisys.drone.modules.base.entity.UbisysDrone;

import java.util.List;
import java.util.Map;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: http://www.ubisys.com.cn/</p>
 *
 * @Auther: Jhon Davis
 * @Date: 2018/12/19 19:03
 */
public interface UbDroneMapper extends BaseMapper<UbisysDrone> {

    List<UbisysDrone> allAlarmInformation(@Param("param") Map<String, Object> dfMap);

    long countAlarmInformation(@Param(value = "param") Map<String, Object> dfMap);


}
