package org.example;

import org.apache.logging.log4j.*;


public class Main {
    public static void main(String[] args) {

        Logger log = LogManager.getLogger(Main.class);
        log.info("Info");
        log.error("Error");
        log.trace("Trace");
        log.debug("Debug");
        log.error("Error");
        System.out.println("Hello world!");
    }
}