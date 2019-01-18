package com.ubisys.drone.modules.base.model;

import java.io.Serializable;

/**
 * <p>Title: 历正站点自动攻击状态</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2018</p>
 * <p>Company: http://www.ubisys.com.cn/</p>
 *
 * @Auther: cw
 * @Date: 2018/12/21 13:51
 */
public class SenorAtuoAttack implements Serializable {

    private Boolean autoAttack;

    public Boolean getAutoAttack() {
        return autoAttack;
    }

    public void setAutoAttack(Boolean autoAttack) {
        this.autoAttack = autoAttack;
    }
}
