package com.evry.bdd.utils;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
public class LoggerHelper {
	   private LoggerHelper() {
	        // Prevent instantiation
	    }

	    public static Logger getLogger(Class<?> cls) {
	        return LogManager.getLogger(cls);
	    }}
