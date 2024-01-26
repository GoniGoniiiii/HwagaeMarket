package com.spring.view.common;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/*
	Signature
	
	String getName()
		: 클라이언트가 호출한 메소드 이름 리턴
 	Signature getSignature() 
 		: 클라이언트가 호출한 시그니처(리턴타입, 메소드이름, 매개변수) 정보가 저장된 Signature객체 리턴
 	Object	getTarget()
 		: 클라이언트가 호출한 메소드를 포함하는 비즈니스 객체 리턴
 	Object	getArgs()
 		: 클라이언트가 메소드를 호출할 때 넘겨주는 인자 목록을 Object배열로 리턴
*/
@Component
@Aspect
public class BeforeJoinPoint {
	
	@Before("execution(* com.spring.biz..*.get*(..))")
	public void beforeLog(JoinPoint jp) { // 스프링컨테이너가 요청 정보를 담아서 자동 생성하는 객체
		String method = jp.getSignature().getName();
		Object[] args = jp.getArgs();
		
		for(Object arg : args) {
			System.out.println("코딩싸롱 (강문주, 김주언, 유고운, 최현우)");
		}
	}
}
