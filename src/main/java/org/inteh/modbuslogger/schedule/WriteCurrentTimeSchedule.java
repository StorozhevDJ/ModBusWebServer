package org.inteh.modbuslogger.schedule;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.inteh.modbuslogger.service.DeviceServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
 

@Component
public class WriteCurrentTimeSchedule {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(WriteCurrentTimeSchedule.class);
 
    private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    //private final int initDelay = 100*1000;
    
    private static boolean myMutex = false;
    
    @Autowired
	private DeviceServiceImpl deviceServiceImpl;
     
    // initialDelay = 3 second.
    // fixedDelay = 2 second.
    @Scheduled( fixedRate = 120 * 1000)
    public synchronized void writeCurrentData() {
    	LOGGER.debug("Run Time Schedule: {}", df.format(new Date()));
    	if (myMutex == false) {
    		myMutex = true;
	        LOGGER.debug("Read data from ModBus Device started in: {}", df.format(new Date()));
	        deviceServiceImpl.mlpDateToDB();
	        LOGGER.debug("Read data from ModBus Device finished in: {}", df.format(new Date()));
	        myMutex = false;
    	} else {
    		LOGGER.warn("Run Time Schedule second: {}", df.format(new Date()));
    	}
    }
 
}