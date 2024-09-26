package com.example.demo.controller;

import com.example.demo.controller.EmployeeController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class ContextControllerTest {

    @Autowired
    private EmployeeController controller;

    /*
    Test application context is creating controller
    */
    @Test
    void contextLoads() throws Exception{
        assertThat(controller).isNotNull();
    }



}
