package com.threelambda.log;

/**
 * @author yangming 2018/10/25
 */
public enum AnnotationOrderEnum {
    CatchMe(100),
    LogMe(50);
    private Integer order;

    AnnotationOrderEnum(Integer order) {
        this.order = order;
    }

    public Integer getOrder() {
        return order;
    }

}
