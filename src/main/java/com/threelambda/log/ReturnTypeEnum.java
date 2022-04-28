package com.threelambda.log;

import com.threelambda.ServiceResult;

/**
 * @author yangming 2018/10/10
 */
public enum ReturnTypeEnum {

    unknown(UnKnown.class),
    service_result(ServiceResult.class),
    ;

    private Class clazz;

    public static ReturnTypeEnum getByClazz(Class clazz) {
        ReturnTypeEnum[] values = ReturnTypeEnum.values();
        for (ReturnTypeEnum value : values) {
            if (value.getClazz().equals(clazz)) {
                return value;
            }
        }
        return unknown;
    }

    ReturnTypeEnum(Class clazz) {
        this.clazz = clazz;
    }

    public Class getClazz() {
        return clazz;
    }

    private static class UnKnown {

    }

}
