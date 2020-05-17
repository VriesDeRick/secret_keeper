package nl.utwente.secrets;

import nl.utwente.secrets.controllers.ControllerUtils;
import org.slf4j.LoggerFactory;

import java.time.ZonedDateTime;

public class Logger {

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(Logger.class);

    public void cntrlLog(String mName, long execTime) {
        logger.info("[CNTRL/{}] Received query at {}", mName, ZonedDateTime.now());
        logger.info("[CNTRL/{}] Query received from IP {}", mName, ControllerUtils.getCurrentHttpRequest().getRemoteAddr());
        logger.info("[CNTRL/{}] Executed in {} ms", mName, execTime);
    }

    public void srvLog(String mName) {
        logger.info("[SRV/{}] called at {}", mName, ZonedDateTime.now());
    }

    public void errLog(String mName, Exception e) {
        logger.error("[XCP] Exception in {}(), cause: {}, message: {}", mName, e.getCause() != null ? e.getCause() : "NULL", e.getMessage());
    }
}
