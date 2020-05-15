package nl.utwente.secrets.aspect;

import net.bytebuddy.implementation.bytecode.Throw;
import nl.utwente.secrets.controllers.ControllerUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.ZonedDateTime;

/**
 * Implementing logging in the AOP way
 */
@Aspect
@Component
public class LoggerAspect {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * Match data controllers in the demonstrator
     */
    @Pointcut("within(nl.utwente.secrets.controllers.SecretController)"+
                " || within(nl.utwente.secrets.controllers.UserInfoController)")
    public void dataControllersPointcut(){}

    /**
     * Match exception handlers in the demonstrator
     */
    @Pointcut("within(nl.utwente.secrets.controllers.*)" +
                "|| within(nl.utwente.secrets.services.*)")
    public void exceptionsPointcut(){}

    /**
     * Match all services in the demonstrator
     */
    @Pointcut("within(nl.utwente.secrets.services.*)")
    public void servicesPointcut(){}

    /**
     * Log controller methods
     * @param joinPoint point of execution
     * @return result of invocation
     * @throws Throwable
     */
    @Around("dataControllersPointcut()")
    public Object logControllers(ProceedingJoinPoint joinPoint) throws Throwable{
        String invokedMethod = joinPoint.getSignature().getName(); // get method name
        log.info("[CNTRL] Received {} query at {}",invokedMethod,ZonedDateTime.now());
        try {
            log.info("[CNTRL/{}] Request processed by {}",invokedMethod,joinPoint.getTarget().getClass());
            log.info("[CNTRL/{}] Query received from IP {}",invokedMethod,
                    ControllerUtils.getCurrentHttpRequest().getRemoteAddr());
            long start = System.currentTimeMillis(); // get start time
            Object result = joinPoint.proceed(); // invoke the method
            long executionTime = System.currentTimeMillis() - start; // measure execution time
            log.info("[CNTRL/{}] Executed in {} ms",invokedMethod,executionTime);
            return result;
        }catch (Exception e){
            throw e;
        }
    }

    /**
     * Log services methods
     * @param joinPoint point of execution
     * @return result of invocation
     * @throws Throwable
     */
    @Around("servicesPointcut()")
    public Object logService(ProceedingJoinPoint joinPoint) throws Throwable{
        String invokedMethod = joinPoint.getSignature().getName();
        log.info("[SRV] Method {} called at {}",invokedMethod,ZonedDateTime.now());
        try {
            Object result = joinPoint.proceed(); // invoke the method
            // error cases that don't throw an exception
            switch (invokedMethod){
                case "checkSecretById":
                    if (!(boolean)result){
                        log.warn("[SRV] Hash check failed: secret does not match!");
                    }
                    break;
                default:
            }
            return result;
        }catch (Exception e){
            throw e;
        }
    }

    /**
     * Log thrown exceptions from controllers or services
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(pointcut = "exceptionsPointcut()", throwing = "e")
    public void logDataExceptions(JoinPoint joinPoint, Throwable e){
        log.error("[XCP] Exception in {}.{}(), cause: {}, message: {}",joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(), e.getCause() != null ? e.getCause() : "NULL", e.getMessage());
    }
}
