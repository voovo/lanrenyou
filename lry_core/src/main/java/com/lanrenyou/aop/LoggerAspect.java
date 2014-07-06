/**
 * com.lanrenyou.aop.LoggerAspect.java
 *
 * date		2013-12-30
 * author	peijin.zhang
 * Copyright (c) 2013, zhangpj All Rights Reserved.
*/

package com.lanrenyou.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

/**
 * ClassName:LoggerAspect
 *
 * @author   peijin.zhang
 * @Date	 2013-12-30		上午10:19:08 
 */
public class LoggerAspect {
	
	private static Logger logger = LoggerFactory.getLogger(LoggerAspect.class);
	private static Gson gson = new Gson();

    public void doBefore(JoinPoint jp) {
    	logger.info(jp.getTarget().getClass().getName() + "." + jp.getSignature().getName()+"("+gson.toJson(jp.getArgs())+")");
    }

//    public void doAfter(JoinPoint jp) {
//    }
    
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        long time = System.currentTimeMillis();
        Object retVal = pjp.proceed();
        time = System.currentTimeMillis() - time;
        logger.info(pjp.getTarget().getClass().getName()+"."+pjp.getSignature().getName()+" cost: " + time + " ms");
        return retVal;
    }
    
//    public void doThrowing(JoinPoint jp, Throwable ex) {
//        System.out.println("method " + jp.getTarget().getClass().getName()
//                + "." + jp.getSignature().getName() + " throw exception");
//        System.out.println(ex.getMessage());
//    }
}

