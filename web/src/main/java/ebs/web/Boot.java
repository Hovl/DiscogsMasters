package ebs.web;

import org.apache.catalina.startup.Tomcat;

import java.io.File;
import java.util.logging.Logger;

/**
 * Created by Aleksey Dubov
 * Date: 14/11/28
 * Time: 20:35
 * Copyright (c) 2014
 */
public class Boot {
	private static Logger logger = Logger.getLogger(BasicJSONServlet.class.getName());

	public static void main(String[] args) throws Exception {

		String webappDirLocation = "src/main/webapp/";
		Tomcat tomcat = new Tomcat();

		//The port that we should run on can be set into an environment variable
		//Look for that variable and default to 8080 if it isn't there.
		String webPort = System.getenv("PORT");
		if (webPort == null || webPort.isEmpty()) {
			webPort = "8080";
		}

		tomcat.setPort(Integer.valueOf(webPort));

		tomcat.addWebapp("/", new File(webappDirLocation).getAbsolutePath());
		logger.info("configuring app with basedir: " + new File("./" + webappDirLocation).getAbsolutePath());

		tomcat.start();
		tomcat.getServer().await();
	}
}
