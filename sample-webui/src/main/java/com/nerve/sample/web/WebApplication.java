package com.nerve.sample.web;

import com.nerve.commons.repository.config.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.test.ImportAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by zengxm on 2016/5/18.
 */
@ImportAutoConfiguration({Config.class})
//@ImportAutoConfiguration({Config.class})
@SpringBootApplication
@EnableScheduling
@ComponentScan("com.nerve")
public class WebApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(WebApplication.class);

		application.run(args);
	}
}