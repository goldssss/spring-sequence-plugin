package com.goldssss.sequence.core.entity;

import java.util.List;

public class SequenceMethodDTO {

    private SequenceMethodStatusEnum sequenceMethodStatusEnum;

    private String className;

    private String methodName;

    private String longName;

    private String shortName;

    private List<Class> paramsType;

    private Class returnType;

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

    public List<Class> getParamsType() {
        return paramsType;
    }

    public void setParamsType(List<Class> paramsType) {
        this.paramsType = paramsType;
    }

    public Class getReturnType() {
        return returnType;
    }

    public void setReturnType(Class returnType) {
        this.returnType = returnType;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
}
