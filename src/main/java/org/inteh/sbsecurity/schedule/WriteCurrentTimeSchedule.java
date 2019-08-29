package org.inteh.sbsecurity.schedule;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.inteh.sbsecurity.service.DeviceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
 
@Component
public class WriteCurrentTimeSchedule {
 
    private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    @Autowired
	private DeviceServiceImpl deviceServiceImpl;
     
    // initialDelay = 3 second.
    // fixedDelay = 2 second.
    @Scheduled( initialDelay = 600 * 1000, fixedRate = 10*60 * 1000)
    public void writeCurrentTime() {
         
         
        System.out.println("Read data from ModBus Device started in: "+ df.format(new Date()));
         
        deviceServiceImpl.mlpDateToDB();
    }
 
}