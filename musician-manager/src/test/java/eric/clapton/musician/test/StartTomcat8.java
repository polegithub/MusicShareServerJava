package eric.clapton.musician.test;

import java.io.File;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;

public class StartTomcat8 {
	public static void main(String[] args) throws Throwable {
		System.setProperty("catalina.base", System.getProperty("user.dir") + "/server");

		String webappDirLocation = "src/main/webapp/";
		Tomcat tomcat = new Tomcat();
		tomcat.setPort(9090);

		Context webapp = tomcat.addWebapp("/", new File(webappDirLocation).getAbsolutePath());
		//  
		webapp.setReloadable(true); 
		
		System.out.println("configuring app with basedir: " + new File("./" + webappDirLocation).getAbsolutePath());

		tomcat.start();
		tomcat.getServer().await();
	}
}
