 package com.iflytek.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerUtils {

	private Logger logger; 
	
	private static LoggerUtils instance;

	private LoggerUtils() {
		logger=LogManager.getRootLogger();
	}

	public static synchronized LoggerUtils getInstance() {
		if (instance == null) {
			instance = new LoggerUtils();
		}
		return instance;
	}
	
	public void writeLog(String logInfo) {
		logger.info(logInfo);
	}

}
