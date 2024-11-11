package com.pinguela.yourpc.config;

import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConfigManager {

	private static final String CONFIG_FILE = "yourpc-cfg.properties";
	
	private static Logger logger = LogManager.getLogger(ConfigManager.class);
	private static Properties propertiesCfg;
	
	static {
		try {
			Class<ConfigManager> configurationParametersManagerClass = ConfigManager.class;
			ClassLoader classLoader = configurationParametersManagerClass.getClassLoader();
			InputStream inputStream = classLoader.getResourceAsStream(CONFIG_FILE);
			propertiesCfg = new Properties();
			propertiesCfg.load(inputStream);
			inputStream.close();
			// parameters = Collections.synchronizedMap(properties)
		} catch (Throwable t) {
			logger.fatal("Unable to load system configuration: "+t.getMessage());
		}
	}
	
	public static final String DELIMITER = getParameter("delimiter");
	
	private ConfigManager() {
	}

	public static final String getParameter(String parameterName) {
		return propertiesCfg.getProperty(parameterName);
	}
	
	public static final String[] getParameters(String parameterName) {
		return propertiesCfg.getProperty(parameterName).split(DELIMITER);
	}

}
