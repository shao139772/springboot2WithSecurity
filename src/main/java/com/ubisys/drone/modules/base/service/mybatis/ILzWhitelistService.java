package com.ubisys.drone.modules.base.service.mybatis;

import com.baomidou.mybatisplus.service.IService;
import com.ubisys.drone.common.utils.AjaxResponse;
import com.ubisys.drone.modules.base.entity.LzWhitelist;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author cw
 * @since 2018-11-13
 */
public interface ILzWhitelistService extends IService<LzWhitelist> {

    AjaxResponse selectListByPage(String pageNum, String pageSize);
}
