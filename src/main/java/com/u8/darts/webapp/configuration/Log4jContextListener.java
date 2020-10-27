package com.u8.darts.webapp.configuration;

import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.helpers.FileWatchdog;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class Log4jContextListener implements ServletContextListener {

    private static final String LOG4J_CONFIG_FILE = "C:\\Users\\batal\\OneDrive\\Documents\\Projects\\U8\\darts-api\\DartsAPI\\src\\main\\resources\\log4j.properties";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        final ServletContext sc = sce.getServletContext();

        sc.log("[LOG4J_CONTEXT_LISTENER] Initializing log4j configuration from file '" +
                LOG4J_CONFIG_FILE + "'. Will check for changes every " + FileWatchdog.DEFAULT_DELAY + " ms.");
        PropertyConfigurator.configureAndWatch(LOG4J_CONFIG_FILE);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        sce.getServletContext().log("[LOG4J_CONTEXT_LISTENER] Shutting down log4j.");
        LogManager.shutdown();
    }
}
