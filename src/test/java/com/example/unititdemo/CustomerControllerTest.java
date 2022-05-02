package com.example.unititdemo;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebMvcTest
class CustomerControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @BeforeEach
    void setUp() {

    }

    @Test
    void testGetCustomers() throws Exception {
        Customer customer1 = new Customer("Jim","Smith","CEO");
        Customer customer2 = new Customer("Bill","Johnson","COO");
        Customer customer3 = new Customer("Kim","Nully","CIO");

        when(customerService.findAll())
                .thenReturn(Arrays.asList(customer1,customer2,customer3));

        mockMvc.perform(MockMvcRequestBuilders.get("/customers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName", is("Jim")));
    }


    @Test
    void testCustomerById() throws Exception {
        Customer customer = new Customer(102L,"Bill","Smith","CEO");

        when(customerService.findById(102)).thenReturn(customer);

        mockMvc.perform(MockMvcRequestBuilders.get("/customer/{id}", 102)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testCustomerById404() throws Exception {
        Customer customer = new Customer(102L,"Bill","Smith","CEO");

        when(customerService.findById(102)).thenReturn(customer);

        mockMvc.perform(MockMvcRequestBuilders.get("/customer/{id}", 101)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

}