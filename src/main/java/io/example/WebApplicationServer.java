package io.example;


import java.io.File;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebApplicationServer {
    private static final Logger log = LoggerFactory.getLogger(WebApplicationServer.class);
    private static final String WEB_APPLICATION_SERVER_ROOT_LOCATION = "webapps";
    private static final int TOMCAT_PORT = 8080;

    public static void main(String[] args) throws LifecycleException {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(TOMCAT_PORT);

        String absolutePath = new File(WEB_APPLICATION_SERVER_ROOT_LOCATION).getAbsolutePath();
        tomcat.addWebapp("/", absolutePath);
        log.info("configuring app with basedir: {}", new File("./" + WEB_APPLICATION_SERVER_ROOT_LOCATION).getAbsolutePath());

        tomcat.start();
        tomcat.getServer().await();
    }
}
