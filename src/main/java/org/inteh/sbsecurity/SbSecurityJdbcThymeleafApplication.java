package org.inteh.sbsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/*@SpringBootApplication
public class SbSecurityJdbcThymeleafApplication {

	public static void main(String[] args) {
		SpringApplication.run(SbSecurityJdbcThymeleafApplication.class, args);
	}

}*/

@SpringBootApplication
public class SbSecurityJdbcThymeleafApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(SbSecurityJdbcThymeleafApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SbSecurityJdbcThymeleafApplication.class);
    }
}
