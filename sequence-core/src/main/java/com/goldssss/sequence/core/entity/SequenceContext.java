package com.goldssss.sequence.core.entity;

/**
 * @author majinxin1
 * @date 2018/8/4
 */
public class SequenceContext {

    private String title;

    private String clazzName;

    private String methodName;

    private String messageContext;

    private String paramsTypes;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessageContext() {
        return messageContext;
    }

    public void setMessageContext(String messageContext) {
        this.messageContext = messageContext;
    }

    public String getClazzName() {
        return clazzName;
    }

    public void setClazzName(String clazzName) {
        this.clazzName = clazzName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getParamsTypes() {
        return paramsTypes;
    }

    public void setParamsTypes(String paramsTypes) {
        this.paramsTypes = paramsTypes;
    }
}
