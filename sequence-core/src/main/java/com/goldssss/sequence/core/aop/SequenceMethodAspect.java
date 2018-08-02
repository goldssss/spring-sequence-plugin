package com.goldssss.sequence.core.aop;

import com.goldssss.sequence.core.entity.SequenceMethodDTO;
import com.goldssss.sequence.core.entity.SequenceMethodStatusEnum;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component("sequenceMethodAspect")
@Aspect
public class SequenceMethodAspect {

    private static ThreadLocal<List<SequenceMethodDTO>> listThreadLocal = new ThreadLocal<>();

    public static ConcurrentLinkedQueue<SequenceMethodDTO> sequenceMethodDTOList = new ConcurrentLinkedQueue<SequenceMethodDTO>();

    @Pointcut("execution(* com.goldssss.sequence..*(..)) && !bean(sequenceMethodAspect)")
    private void pointCut() {}

    @Before("pointCut()")
    public void methodBefore(JoinPoint joinPoint){
        SequenceMethodDTO sequenceMethodDTO = new SequenceMethodDTO();
        sequenceMethodDTO.setSequenceMethodStatusEnum(SequenceMethodStatusEnum.INVOKE);
        sequenceMethodDTO.setClassName(joinPoint.getTarget().getClass().getCanonicalName());
        sequenceMethodDTO.setMethodName(joinPoint.getSignature().getName());
        sequenceMethodDTOList.add(sequenceMethodDTO);

    }

    @AfterReturning("pointCut()")
    public void methodAfterReturning(JoinPoint joinPoint){
        SequenceMethodDTO sequenceMethodDTO = new SequenceMethodDTO();
        sequenceMethodDTO.setSequenceMethodStatusEnum(SequenceMethodStatusEnum.RETURN);
        sequenceMethodDTO.setClassName(joinPoint.getTarget().getClass().getCanonicalName());
        sequenceMethodDTO.setMethodName(joinPoint.getSignature().getName());
        sequenceMethodDTOList.add(sequenceMethodDTO);
    }

    public void afterReturning(){

    }
}
