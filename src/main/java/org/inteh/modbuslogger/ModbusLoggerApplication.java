package org.inteh.modbuslogger;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;



@SpringBootApplication
public class ModbusLoggerApplication extends SpringBootServletInitializer {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ModbusLoggerApplication.class);

    public static void main(String[] args) {
    	LOGGER.info("Start main ModbusLoggerApplication: {}", new Date());
        SpringApplication.run(ModbusLoggerApplication.class, args);
    }

    /*@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
    	LOGGER.info("Start configure ModbusLoggerApplication: {}", new Date());
        return builder.sources(ModbusLoggerApplication.class);
    }*/
}
