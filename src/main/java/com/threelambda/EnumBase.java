package com.threelambda;

import java.io.Serializable;

/**
 * @author yangming 2019-02-02
 */
public interface EnumBase extends Serializable {

    /**
     * 获取枚举中定义的值
     * @return 枚举代码
     */
    public String getCode();

    /**
     * 获取枚举中定义的备注信息
     * @return 枚举代码描述
     */
    public String getValue();

}
