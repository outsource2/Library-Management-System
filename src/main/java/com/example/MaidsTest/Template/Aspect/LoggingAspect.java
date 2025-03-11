package com.example.MaidsTest.Template.Aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Aspect class responsible for logging method execution details.
 * <p>
 * This class utilizes Aspect-Oriented Programming (AOP) to log the execution of methods annotated with {@link Loggable}.
 * It logs the method name, arguments, execution time, and the return value of the method.
 * The aspect ensures that any method annotated with {@code @Loggable} will have detailed logging.
 * </p>
 */
@Aspect  // Defines this class as an aspect for Aspect-Oriented Programming.
@Component  // Marks the class as a Spring component to be managed by the Spring container.
public class LoggingAspect {

    // Logger instance for logging method details.
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    /**
     * Around advice for methods annotated with {@code @Loggable}.
     * <p>
     * This advice intercepts method executions and logs method details such as:
     * - Method name
     * - Arguments passed to the method
     * - Time taken for execution
     * - Return value of the method
     * </p>
     *
     * @param joinPoint The join point that provides details about the method being intercepted.
     * @return The result of the method execution.
     * @throws Throwable If the method execution throws an exception, it is propagated.
     */
    @Around("@annotation(com.example.MaidsTest.Template.Aspect.Loggable)")  // Trigger this advice for methods annotated with @Loggable.
    public Object logMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {

        // Get the method name and arguments.
        String methodName = joinPoint.getSignature().toShortString();
        Object[] methodArgs = joinPoint.getArgs();

        // Log the method name and arguments before execution.
        logger.info("Executing method: {} with arguments: {}", methodName, Arrays.toString(methodArgs));

        // Record the start time of the method execution.
        long startTime = System.currentTimeMillis();

        // Proceed with the method execution.
        Object result = joinPoint.proceed();

        // Calculate the time taken for the method execution.
        long elapsedTime = System.currentTimeMillis() - startTime;

        // Log the successful execution and the method's return value.
        logger.info("Method {} executed successfully in {} ms - Return Value: {}", methodName, elapsedTime, result);

        // Return the result of the method execution.
        return result;
    }
}
