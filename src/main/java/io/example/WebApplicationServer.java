package io.example;


import java.io.File;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebApplicationServer {
    private static final Logger log = LoggerFactory.getLogger(WebApplicationServer.class);

    private static final String ROOT_LOCATION = "webapps";
    private static final int TOMCAT_PORT = 8080;

    public static void main(String[] args) throws LifecycleException {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(TOMCAT_PORT);

        String rootLocationAbsolutePath = new File(ROOT_LOCATION).getAbsolutePath();
        tomcat.addWebapp("/", rootLocationAbsolutePath);

        tomcat.start();
        log.info("Tomcat started on port(s): {}, configuring app with basedir: {}", TOMCAT_PORT, rootLocationAbsolutePath);
        tomcat.getServer().await();
    }
}
