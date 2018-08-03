package com.goldssss.sequence.core.entity;

import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SequenceMethodDTO that = (SequenceMethodDTO) o;
        return Objects.equals(className, that.className) &&
                Objects.equals(methodName, that.methodName) &&
                Objects.equals(longName, that.longName) &&
                Objects.equals(shortName, that.shortName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(className, methodName, longName, shortName, paramsType, returnType);
    }
}
