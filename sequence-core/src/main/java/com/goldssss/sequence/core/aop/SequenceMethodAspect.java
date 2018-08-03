package com.goldssss.sequence.core.aop;

import com.goldssss.sequence.core.entity.SequenceMethodDTO;
import com.goldssss.sequence.core.entity.SequenceMethodStatusEnum;
import org.apache.commons.collections4.CollectionUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component("sequenceMethodAspect")
@Aspect
public class SequenceMethodAspect {

    private ThreadLocal<List<SequenceMethodDTO>> listThreadLocal = new ThreadLocal<>();
    /**
     * 方法栈
     */
    private Stack<SequenceMethodDTO> currentInvokeStack = new Stack();
    private Stack<SequenceMethodDTO> methodInvokeStack = new Stack<>();
    private Integer returnCount = 0;

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
        methodInvokeStackPeek(sequenceMethodDTOList.get(0));
        for (int index=0;index<sequenceMethodDTOList.size();index++){
            //当前方法
            SequenceMethodDTO sequenceMethodDTO = sequenceMethodDTOList.get(index);
            //当前方法执行后执行的方法
            SequenceMethodDTO nextSequenceMethodDTO = (index==sequenceMethodDTOList.size()-1)?null:sequenceMethodDTOList.get(index+1);
            if (nextSequenceMethodDTO==null){ break;}
            this.push(sequenceMethodDTO);
            mkString.append(this.peek().getClassName()).append("->");
            this.push(methodInvokeStackPeek(nextSequenceMethodDTO));
            mkString.append(this.peek().getClassName()).append(":").append(this.peek().getMethodName()).append("\n");
            this.pop();
        }
        listThreadLocal.remove();
        currentInvokeStack.clear();
        return mkString.append("```\n").toString();
    }

    /**
     * 当前方法
     * @return
     */
    private SequenceMethodDTO pop(){
       return currentInvokeStack.pop();
    }

    /**
     * 入栈
     * @param sequenceMethodDTO
     */
    private void push(SequenceMethodDTO sequenceMethodDTO){
        currentInvokeStack.push(sequenceMethodDTO);
    }

    /**
     * 栈顶方法
     * @return
     */
    private SequenceMethodDTO peek(){
        return currentInvokeStack.peek();
    }

    private SequenceMethodDTO currentMethod(SequenceMethodDTO sequenceMethodDTO){
        return methodInvokeStack.pop();
    }

    /**
     * 方法调用链，未执行完的方法栈,返回栈顶方法
     * @param sequenceMethodDTO
     * @return
     */
    private SequenceMethodDTO methodInvokeStackPeek(SequenceMethodDTO sequenceMethodDTO){
        if (SequenceMethodStatusEnum.RETURN.equals(sequenceMethodDTO.getSequenceMethodStatusEnum())){
            return methodInvokeStack.pop();
        }else {
            return methodInvokeStack.push(sequenceMethodDTO);
        }
    }
}
