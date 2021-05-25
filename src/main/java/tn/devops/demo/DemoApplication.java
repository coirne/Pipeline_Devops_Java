package tn.devops.demo;

import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// we initialize the Servlet context required by Tomcat by implementing the SpringBootServletInitializer interface
public class DemoApplication extends SpringBootServletInitializer  {
 /**
  * La méthode main ...
  * @param args les arguments 
  * de la méthode main
  */
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
