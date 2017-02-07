/**
 * Copyright (c) 2015 Resolto. All Rights Reserved.
 */
package by.intexsoft.ccm;

import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.apache.commons.lang3.CharEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Provides a quick way to start up embedded Tomcat server for testing purposes.
 */
public class Bootstrap
{
	private static final Logger logger = LoggerFactory.getLogger(Bootstrap.class);
	private static final String CATALINA_HOME = ".";
	private static final String WEB_APP_DIR_LOCATION = "src/main/webapp";
	private static final String BASE_DIR = ".";
	private static final String CONTEXT_PATH = "/";
	private static final int PORT = 8080;

	public static void main(String[] args) throws Exception
	{
		System.setProperty("catalina.home", new File(CATALINA_HOME).getAbsolutePath());
		File webappDir = new File(WEB_APP_DIR_LOCATION);
		if (logger.isDebugEnabled())
		{
			logger.debug("Configuring web app with basedir: " + webappDir.getAbsolutePath());
		}
		Tomcat tomcat = new Tomcat();
		tomcat.setBaseDir(BASE_DIR);
		tomcat.setPort(PORT);
		Connector connector = tomcat.getConnector();
		connector.setURIEncoding(CharEncoding.UTF_8);
		tomcat.addWebapp(CONTEXT_PATH, webappDir.getAbsolutePath());
		tomcat.start();
		tomcat.getServer().await();
	}
}
