package org.springblade;

import org.springblade.common.constant.CommonConstant;
import org.springblade.core.launch.BladeApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

/**
 * @author bond
 * @date 2021/5/22 10:10
 * @desc
 */
@EnableScheduling
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        BladeApplication.run("car", Application.class, args);
    }

    @Bean
    public RestTemplate restTemplate(){
        return new  RestTemplate();
    }

}
