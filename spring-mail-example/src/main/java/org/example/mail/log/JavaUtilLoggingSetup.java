package org.example.mail.log;

import org.slf4j.bridge.SLF4JBridgeHandler;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class JavaUtilLoggingSetup {
    public static void setup() {
        SLF4JBridgeHandler.removeHandlersForRootLogger();  // (since SLF4J 1.6.5)
        SLF4JBridgeHandler.install();

        LogManager manager = java.util.logging.LogManager.getLogManager();
        Logger javaxMail = Logger.getLogger("javax.mail");
        Logger comSunMail = Logger.getLogger("com.sun.mail");
        javaxMail.setLevel(Level.FINEST);
        comSunMail.setLevel(Level.FINEST);
        manager.addLogger(javaxMail);
        manager.addLogger(comSunMail);
    }
}
