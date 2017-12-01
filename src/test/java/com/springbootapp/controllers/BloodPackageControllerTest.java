package com.springbootapp.controllers;

import com.springbootapp.models.BloodPackage;
import com.springbootapp.models.BloodType;
import com.springbootapp.repositories.BloodPackageRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.persistence.Id;
import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@WebMvcTest(BloodPackageController.class)
public class BloodPackageControllerTest {
    private static final Long IdTest = 1L;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BloodPackageRepository repo;

    @Before
    public void setup() {
        BloodPackage randomPacket =  new BloodPackage();
        randomPacket.setId(IdTest);
        randomPacket.setShelfLife(new Date(1999, 10, 10));
        randomPacket.setAcceptanceDate(new Date(1000, 10, 10));
        randomPacket.setBloodType(BloodType.ABRhMinus);
        randomPacket.setCapacityInLiters(3.6f);
        given(this.repo.findById(IdTest)).willReturn(randomPacket);
    }


    @Test
    public void initNewPackage() throws Exception {
        mockMvc.perform(get("/bloodPackages/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("bloodPackage"))
                .andExpect(view().name("bloodPackages/new"));
    }

    @Test
    public void processNewPackage() throws Exception {
        mockMvc.perform(post("/bloodPackages/new")
                .param("acceptanceDate", "1000-10-10")
                .param("shelfLife", "1999-10-10")
                .param("capacityInLiters", "3.6f"));
    }

    @Test
    public void displayPackages() throws Exception {
        mockMvc.perform(get("/bloodPackages/{bloodPackageId}", IdTest))
                .andExpect(status().isOk())
                .andExpect(model().attribute("bloodPackage", hasProperty("acceptanceDate")))
                .andExpect(model().attribute("bloodPackage", hasProperty("shelfLife")))
                .andExpect(model().attribute("bloodPackage", hasProperty("capacityInLiters", is(3.6f))))
                .andExpect(view().name("bloodPackages/packagesDetails"));
    }

}