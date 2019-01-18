package com.ubisys.drone.common.constant;

/**
 * <p>Title: 无人机常量类</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: http://www.ubisys.com.cn/</p>
 *
 * @Auther: cw
 * @Date: 2018/12/17 10:23
 */
public interface DroneConstant {
    //https://avatar.csdn.net/2/3/3/1_qq_29308413.jpg?1545132390
    String USER_DEFAULT_AVATAR = "https://avatar.csdn.net/2/3/3/1_qq_29308413.jpg?1545132390";
    /**
     * 用户正常状态
     */
    Integer USER_STATUS_NORMAL = 0;

    /**
     * 用户禁用状态
     */
    Integer USER_STATUS_LOCK = -1;

    /**
     * 普通用户
     */
    Integer USER_TYPE_NORMAL = 0;

    /**
     * 管理员
     */
    Integer USER_TYPE_ADMIN = 1;

    /**
     * 全部数据权限
     */
    Integer DATA_TYPE_ALL = 0;

    /**
     * 自定义数据权限
     */
    Integer DATA_TYPE_CUSTOM = 1;

    /**
     * 正常状态
     */
    Integer STATUS_NORMAL = 0;

    /**
     * 禁用状态
     */
    Integer STATUS_DISABLE = -1;

    /**
     * 删除标志
     */
    Integer DEL_FLAG = 1;

    /**
     * 限流标识
     */
    String LIMIT_ALL = "DRONE_LIMIT_ALL";

    /**
     * 页面类型权限
     */
    Integer PERMISSION_PAGE = 0;

    /**
     * 操作类型权限
     */
    Integer PERMISSION_OPERATION = 1;

    /**
     * 1级菜单
     */
    String PARENT_ID = "0";

    /**
     * 1级菜单
     */
    Integer LEVEL_ONE = 1;

    /**
     * 2级菜单
     */
    Integer LEVEL_TWO = 2;

    /**
     * 3级菜单
     */
    Integer LEVEL_THREE = 3;


    String ERR_MSG = "好像发生了不可描述的事情，囧囧囧o(╯□╰)o";


    String LZ_DRONE = "LZ:DRONE";

    String AUTO_ATTACK = "AUTO:ATTACK";


    //sms
    String SMS = "SMS:";


    //无人机白名单操作   添加
    String DRONE_WHITE_ADD = "1";

    //无人机白名单操作   删除
    String DRONE_WHITE_DEL = "2";


    String RY_CONFIG = "RY:CONFIG";

    String DEFAULT_PASS = "123456";



    String PERMISSON_TREE = "permisson_tree";

}
