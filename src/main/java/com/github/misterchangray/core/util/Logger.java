package com.github.misterchangray.core.util;


import java.util.logging.LogManager;

/**
 * @description: logeer
 * @author: Ray.chang
 * @create: 2021-12-17 15:05
 **/
public class Logger {
    static java.util.logging.Logger logger = LogManager.getLogManager().getLogger("MAGIC-BYTE");

    public static void info(String msg) {
        logger.info(msg);
    }
}
