package com.ubisys.drone.modules.base.serviceimpl;

import com.alibaba.fastjson.JSONObject;
import com.ubisys.drone.common.constant.DroneConstant;
import com.ubisys.drone.common.utils.AjaxResponse;
import com.ubisys.drone.config.exception.DroneException;
import com.ubisys.drone.modules.base.dao.DroneConfigDao;
import com.ubisys.drone.modules.base.entity.DroneConfig;
import com.ubisys.drone.modules.base.entity.DroneConfigAttr;
import com.ubisys.drone.modules.base.service.DroneConfigAttrService;
import com.ubisys.drone.modules.base.service.DroneConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;

/**
 * O(∩_∩)O哈哈~接口实现
 *
 * @author miaomiao
 */
@Slf4j
@Service
@Transactional
public class DroneConfigServiceImpl implements DroneConfigService {

    @Autowired
    private DroneConfigDao droneConfigDao;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private DroneConfigAttrService droneConfigAttrService;

    @Override
    public DroneConfigDao getRepository() {
        return droneConfigDao;
    }

    /**
     * 新增配置信息
     *
     * @param droneConfig
     * @return
     */
    @Override
    public AjaxResponse saveConfig(DroneConfig droneConfig) {
        if (StringUtils.isBlank(droneConfig.getConfigCode()) || StringUtils.isBlank(droneConfig.getConfigName())) {
            return AjaxResponse.build(501, "配置的基本信息不能为空!");
        }
        if (CollectionUtils.isEmpty(droneConfig.getAttrs())) {
            return AjaxResponse.build(501, "请输入属性值!");
        }
        List<DroneConfigAttr> ll = droneConfig.getAttrs();
        DroneConfig save = save(droneConfig);
        for (Iterator<DroneConfigAttr> iterator = ll.iterator(); iterator.hasNext(); ) {
            DroneConfigAttr next = iterator.next();
            droneConfigAttrService.save(next);
        }
        stringRedisTemplate.opsForHash().put(DroneConstant.RY_CONFIG, droneConfig.getConfigCode(), JSONObject.toJSONString(droneConfig));
        return AjaxResponse.success("添加成功！");
    }

    /**
     * 更新配置信息
     *
     * @param droneConfig
     * @return
     */
    @Override
    public AjaxResponse updateConfig(DroneConfig droneConfig) {
        if (StringUtils.isBlank(droneConfig.getConfigCode()) || StringUtils.isBlank(droneConfig.getConfigName())) {
            return AjaxResponse.build(501, "配置的基本信息不能为空!");
        }
        if (CollectionUtils.isEmpty(droneConfig.getAttrs())) {
            return AjaxResponse.build(501, "请输入属性值!");
        }
        if (droneConfig.getId() == null) {
            return AjaxResponse.build(501, "id 不能为空！");
        }
        List<DroneConfigAttr> ll = droneConfig.getAttrs();
        DroneConfig save = update(droneConfig);
        for (Iterator<DroneConfigAttr> iterator = ll.iterator(); iterator.hasNext(); ) {

            DroneConfigAttr next = iterator.next();
            if (next.getId() == null) {
                throw new DroneException("属性值的 id  为空！");
            }
            droneConfigAttrService.update(next);
        }
        stringRedisTemplate.opsForHash().put(DroneConstant.RY_CONFIG, droneConfig.getConfigCode(), JSONObject.toJSONString(droneConfig));
        return AjaxResponse.success("修改成功！");
    }


    /**
     * 获取全部系统配置信息
     *
     * @return
     */
    @Override
    public AjaxResponse getALL() {
        log.info("  getALL ");

        List<DroneConfig> configs = getAll();
        //取配置属性
        for (DroneConfig config : configs) {
            List<DroneConfigAttr> attrs = droneConfigAttrService.findByConfCode(config.getConfigCode());
            config.setAttrs(attrs);
        }
        return AjaxResponse.success(configs);
    }


    /**
     * 加载配置文件到redis
     *
     * @return
     */
    @Override
    public AjaxResponse loadConfigToRedis() {

        log.info("   ready to load config to Redis   ");
        List<DroneConfig> configs = getAll();
        //取配置属性
        for (DroneConfig config : configs) {
            List<DroneConfigAttr> attrs = droneConfigAttrService.findByConfCode(config.getConfigCode());
            config.setAttrs(attrs);

            stringRedisTemplate.opsForHash().put(DroneConstant.RY_CONFIG, config.getConfigCode(), JSONObject.toJSONString(config));
        }
        log.info("  load config to Redis  Finished   ");
        return AjaxResponse.success();
    }


    /**
     * 清空redis缓存
     *
     * @return
     */
    @Override
    public AjaxResponse clearRedis() {
        log.info("  容器启动  准备清空  缓存   ");
        stringRedisTemplate.opsForValue().getOperations().execute(new RedisCallback<Object>() {
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                connection.flushDb();
                return "ok";
            }
        });
        log.info("  容器启动  缓存   清空完成  ");
        return AjaxResponse.success();
    }
}