package com.ubisys.drone.modules.base.serviceimpl.mybatis;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ubisys.drone.common.utils.AjaxResponse;
import com.ubisys.drone.modules.base.dao.mapper.LzWhitelistMapper;
import com.ubisys.drone.modules.base.entity.LzWhitelist;
import com.ubisys.drone.modules.base.service.mybatis.ILzWhitelistService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author cw
 * @since 2018-11-13
 */
@Service
public class LzWhitelistServiceImpl extends ServiceImpl<LzWhitelistMapper, LzWhitelist> implements ILzWhitelistService {

    @Resource
    private LzWhitelistMapper lzWhitelistMapper;

    @Override
    public AjaxResponse selectListByPage(String pageNum, String pageSize) {
        Map<String, Object> result = new HashMap<String, Object>();
        Page<LzWhitelist> page = new Page<LzWhitelist>(Integer.valueOf(pageNum), Integer.valueOf(pageSize));
        List<LzWhitelist> lsWhiteList = lzWhitelistMapper.selectPage(page, Condition.create());
        Integer count = lzWhitelistMapper.selectCount(Condition.create());
        AjaxResponse ajaxResponse = AjaxResponse.success(lsWhiteList);
        ajaxResponse.setTotal(count);
        return ajaxResponse;
    }
}
