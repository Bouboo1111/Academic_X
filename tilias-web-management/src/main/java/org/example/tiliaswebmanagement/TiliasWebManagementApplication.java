package org.example.tiliaswebmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.servlet.context.ServletComponentScan;

@ServletComponentScan//开启了Servlet组件的支持
@SpringBootApplication
public class TiliasWebManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(TiliasWebManagementApplication.class, args);
	}

}
