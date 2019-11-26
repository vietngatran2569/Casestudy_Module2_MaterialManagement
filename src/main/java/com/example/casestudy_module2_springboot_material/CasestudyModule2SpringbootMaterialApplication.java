package com.example.casestudy_module2_springboot_material;

import com.codegym.service.MaterialService;
import com.codegym.service.SupplierService;
import com.codegym.service.impl.MaterialServiceImpl;
import com.codegym.service.impl.SupplierServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@Configuration
@EntityScan("com.codegym.model")
@ComponentScan("com.codegym.controller")
@EnableJpaRepositories("com.codegym.repository")
@ComponentScan("com.codegym.config")
@EnableTransactionManagement
@EnableWebMvc
@EnableSpringDataWebSupport

public class CasestudyModule2SpringbootMaterialApplication {

    @Bean
    public MaterialService materialService(){
        return new MaterialServiceImpl();
    }

    @Bean
    public SupplierService supplierService(){
        return new SupplierServiceImpl();
    }

    @Autowired
    Environment env;


    public static void main(String[] args) {
        SpringApplication.run(CasestudyModule2SpringbootMaterialApplication.class, args);
    }

}
