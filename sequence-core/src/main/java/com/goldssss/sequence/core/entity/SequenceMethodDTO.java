package com.goldssss.sequence.core.entity;

public class SequenceMethodDTO {

    private SequenceMethodStatusEnum sequenceMethodStatusEnum;

    private String className;

    private String methodName;

    private Class[] paramsType;

    public SequenceMethodStatusEnum getSequenceMethodStatusEnum() {
        return sequenceMethodStatusEnum;
    }

    public void setSequenceMethodStatusEnum(SequenceMethodStatusEnum sequenceMethodStatusEnum) {
        this.sequenceMethodStatusEnum = sequenceMethodStatusEnum;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class[] getParamsType() {
        return paramsType;
    }

    public void setParamsType(Class[] paramsType) {
        this.paramsType = paramsType;
    }
}
