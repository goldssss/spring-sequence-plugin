package com.goldssss.sequence.core.aop;

import com.goldssss.sequence.core.entity.SequenceMethodDTO;
import com.goldssss.sequence.core.entity.SequenceMethodStatusEnum;
import org.apache.commons.collections.CollectionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.*;

@Component("sequenceMethodAspect")
@Aspect
public class SequenceMethodAspect {

    /**
     * aop获取方法调用关系
     */
    private ThreadLocal<List<SequenceMethodDTO>> listThreadLocal = new ThreadLocal<>();
    /**
     * 方法栈
     */
    private Stack<SequenceMethodDTO> methodInvokeStack = new Stack<>();

    @Pointcut("execution(* com.goldssss.sequence..*(..)) && !bean(sequenceMethodAspect)")
    private void pointCut() {}

    @Around("pointCut()")
    public void methdoAround(ProceedingJoinPoint joinPoint) throws Throwable {
        List<SequenceMethodDTO> sequenceMethodDTOList= listThreadLocal.get();
        if (CollectionUtils.isEmpty(sequenceMethodDTOList)){
            sequenceMethodDTOList = new ArrayList<>();
        }
        this.methodBefore(joinPoint,sequenceMethodDTOList);
        Object result = joinPoint.proceed();
        this.methodAfter(joinPoint,sequenceMethodDTOList,result);
        if(isMethodReturn(sequenceMethodDTOList)){
            System.out.println(this.convermethodStackToMarkDown(sequenceMethodDTOList));
        }
    }

    private void methodBefore(ProceedingJoinPoint joinPoint,List<SequenceMethodDTO> sequenceMethodDTOList){
        SequenceMethodDTO methodBefore = new SequenceMethodDTO();
        methodBefore.setSequenceMethodStatusEnum(SequenceMethodStatusEnum.INVOKE);
        methodBefore.setClassName(joinPoint.getSignature().getDeclaringTypeName());
        methodBefore.setMethodName(joinPoint.getSignature().getName());
        methodBefore.setLongName(joinPoint.getSignature().toLongString());
        //截取className
        methodBefore.setShortName(joinPoint.getSignature().getDeclaringTypeName().substring(joinPoint.getSignature().getDeclaringTypeName().lastIndexOf(".")+1));
        List<Class> paramsTypeList = new ArrayList<>();
        for (Object o :joinPoint.getArgs()){
            paramsTypeList.add(o.getClass());
        }
        methodBefore.setParamsType(paramsTypeList);
        sequenceMethodDTOList.add(methodBefore);
        listThreadLocal.set(sequenceMethodDTOList);
    }

    private void methodAfter(ProceedingJoinPoint joinPoint,List<SequenceMethodDTO> sequenceMethodDTOList,Object result){
        SequenceMethodDTO methodAfter = new SequenceMethodDTO();
        methodAfter.setSequenceMethodStatusEnum(SequenceMethodStatusEnum.RETURN);
        methodAfter.setClassName(joinPoint.getSignature().getDeclaringTypeName());
        methodAfter.setMethodName(joinPoint.getSignature().getName());
        methodAfter.setLongName(joinPoint.getSignature().toLongString());
        methodAfter.setShortName(joinPoint.getSignature().getDeclaringTypeName().substring(joinPoint.getSignature().getDeclaringTypeName().lastIndexOf(".")+1));
        methodAfter.setReturnType(result==null?null:result.getClass());
        sequenceMethodDTOList.add(methodAfter);
        listThreadLocal.set(sequenceMethodDTOList);
    }

    private Boolean isMethodReturn(List<SequenceMethodDTO> sequenceMethodDTOList){
        return  sequenceMethodDTOList.get(0).getSequenceMethodStatusEnum().equals(SequenceMethodStatusEnum.INVOKE)
                && sequenceMethodDTOList.get(sequenceMethodDTOList.size()-1).getSequenceMethodStatusEnum().equals(SequenceMethodStatusEnum.RETURN)
                &&sequenceMethodDTOList.get(0).getLongName().equals(sequenceMethodDTOList.get(sequenceMethodDTOList.size()-1).getLongName());
    }

    private String convermethodStackToMarkDown(List<SequenceMethodDTO> sequenceMethodDTOList){
        StringBuilder mkString = new StringBuilder("```sequence\n");
        methodInvokeStack.push(sequenceMethodDTOList.get(0));
        for (int index=1;index<sequenceMethodDTOList.size();index++){
            //将要执行的方法
            SequenceMethodDTO sequenceMethodDTO = sequenceMethodDTOList.get(index);
            //用于判断后续还有没有方法执行，结束循环
            SequenceMethodDTO nextSequenceMethodDTO = (index==sequenceMethodDTOList.size()-1)?null:sequenceMethodDTOList.get(index+1);
            if (nextSequenceMethodDTO==null){ break;}
            if (methodInvokeStack.contains(sequenceMethodDTO)){
                //获取调用链返回值类型
                methodInvokeStack.push(sequenceMethodDTO);
                String returnType =this.handleResult(methodInvokeStack.peek().getReturnType());
                methodInvokeStack.pop();
                mkString.append(methodInvokeStack.peek().getShortName()).append("-->");
                methodInvokeStack.pop();
                mkString.append(methodInvokeStack.peek().getShortName()).append(":")
                        .append(returnType).append("\n");
            }else{
                mkString.append(methodInvokeStack.peek().getShortName()).append("->");
                methodInvokeStack.push(sequenceMethodDTO);
                mkString.append(methodInvokeStack.peek().getShortName()).append(":")
                        .append(methodInvokeStack.peek().getMethodName())
                        .append("(").append(this.handleParams(methodInvokeStack.peek().getParamsType()))
                        .append(")").append("\n");
            }
        }
        listThreadLocal.remove();
        methodInvokeStack.clear();
        return mkString.append("```\n").toString();
    }

    private String handleParams(List<Class> classList){
        StringBuilder params = new StringBuilder("");
        if (CollectionUtils.isNotEmpty(classList)){
            for (int i=0;i<classList.size();i++){
                Class clazz = classList.get(i);
                params.append(clazz.getName().substring(clazz.getName().lastIndexOf(".")+1));
                if (classList.size()!=1 && i!=classList.size()-1){
                    params.append(",");
                }
            }
        }
        return params.toString();
    }

    private String handleResult(Class clazz){
        StringBuilder result = new StringBuilder("");
        if (clazz!=null){
            result.append(clazz.getName().substring(clazz.getName().lastIndexOf(".")+1));
        }
        return result.toString();
    }
}
