package com.library.librarymanagementsystem.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("within(com.library.librarymanagementsystem.service..*)")
    public void serviceLayer() {
        // Pointcut for service layer
    }

    @Before("serviceLayer()")
    public void logBeforeServiceMethods() {
        logger.info("A method in the service layer is about to be invoked");
    }

    @After("serviceLayer()")
    public void logAfterServiceMethods() {
        logger.info("A method in the service layer has been invoked");
    }

    @AfterReturning(pointcut = "serviceLayer()", returning = "result")
    public void logAfterReturningServiceMethods(Object result) {
        logger.info("A method in the service layer returned with result: " + result);
    }

    @AfterThrowing(pointcut = "serviceLayer()", throwing = "ex")
    public void logAfterThrowingServiceMethods(Exception ex) {
        logger.error("A method in the service layer threw an exception: " + ex.getMessage(), ex);
    }
}