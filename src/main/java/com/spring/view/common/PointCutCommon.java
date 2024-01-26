package com.spring.view.common;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class PointCutCommon {
	@Pointcut("execution(* com.spring.biz..*Impl.*(..))")
	public void allPointCut() {}
	
	@Pointcut("execution(* com.spring.biz..*Impl.get*(..))")
	public void getPointCut() {}
}
