package com.csw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.csw.dao")
public class CloudApplication {
/*单文件*/
    public static void main(String[] args) {
        SpringApplication.run(CloudApplication.class, args);
    }

}
