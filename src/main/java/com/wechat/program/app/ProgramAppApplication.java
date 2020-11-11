package com.wechat.program.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan({"com.wechat.program.app.mapper"})
@SpringBootApplication
@EnableSwagger2
public class ProgramAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProgramAppApplication.class, args);
	}

}
