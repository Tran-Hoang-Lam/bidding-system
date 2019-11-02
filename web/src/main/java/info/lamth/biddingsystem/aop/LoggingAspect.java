package info.lamth.biddingsystem.aop;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Log4j2
@Component
public class LoggingAspect {
    @Before("execution(* info.lamth.biddingsystem.service.BiddingServiceImpl.*(..))")
    public void loggingBeforeExecuteBiddingService(JoinPoint joinPoint) {
        log.info("Method " + joinPoint.toString() + " in BiddingService got call with param " + Arrays.toString(joinPoint.getArgs()));
    }

    @After("execution(* info.lamth.biddingsystem.service.BiddingServiceImpl.*(..))")
    public void loggingAfterExecuteBiddingService(JoinPoint joinPoint) {
        log.info("Finish execute method " + joinPoint.toString() + " in BiddingService");
    }

    @Before("execution(* info.lamth.biddingsystem.controller.AuctioneerController.*(..))")
    public void loggingBeforeExecuteAuctioneerController(JoinPoint joinPoint) {
        log.info("Method " + joinPoint.toString() + " in AuctioneerController got call with param " + Arrays.toString(joinPoint.getArgs()));
    }

    @After("execution(* info.lamth.biddingsystem.controller.AuctioneerController.*(..))")
    public void loggingAfterExecuteAuctioneerController(JoinPoint joinPoint) {
        log.info("Finish execute method " + joinPoint.toString() + " in AuctioneerController");
    }

    @Before("execution(* info.lamth.biddingsystem.controller.BidderController.*(..))")
    public void loggingBeforeExecuteBidderController(JoinPoint joinPoint) {
        log.info("Method " + joinPoint.toString() + " in BidderController got call with param " + Arrays.toString(joinPoint.getArgs()));
    }

    @After("execution(* info.lamth.biddingsystem.controller.BidderController.*(..))")
    public void loggingAfterExecuteBidderController(JoinPoint joinPoint) {
        log.info("Finish execute method " + joinPoint.toString() + " in BidderController");
    }

    @Before("execution(* info.lamth.biddingsystem.repository.BiddingItemRepository.save(..))")
    public void loggingBeforeSaveDatabase(JoinPoint joinPoint) {
        log.info("Saving " + Arrays.toString(joinPoint.getArgs()) + " to database");
    }

    @AfterThrowing(value = "execution(* info.lamth.biddingsystem.service.*.*(..))", throwing = "ex")
    public void loggingError(Exception ex) {
        log.error(ex);
    }
}
