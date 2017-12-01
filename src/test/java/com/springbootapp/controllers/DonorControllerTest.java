package com.springbootapp.controllers;

import com.springbootapp.models.Donor;
import com.springbootapp.repositories.DonorRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasProperty;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


import java.util.Date;

@RunWith(SpringRunner.class)
@WebMvcTest(DonorController.class)
public class DonorControllerTest {
    public static final Long IdTest = 1L;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    DonorRepository donors;

    @Before
    public void setup() {
        Donor donor = new Donor();
        donor.setId(IdTest);
        donor.setAddress("testAddress");
        donor.setBirthDate(new Date(1999, 10, 10));
        donor.setFirstName("name");
        donor.setLastName("surname");
        given(this.donors.findById(IdTest)).willReturn(donor);
    }

    @Test
    public void initCreationForm() throws Exception {
        mockMvc.perform(get("/donors/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("donor"))
                .andExpect(view().name("donors/DonorForm"));
    }

    @Test
    public void processCreationForm() throws Exception {
        mockMvc.perform(post("/donors/new")
                .param("firstName", "name")
                .param("lastName", "surname")
                .param("address", "testAddress")
                .param("birthDate", "1999-10-10")
        )
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void initFindForm() throws Exception {
        mockMvc.perform(get("/donors"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("donor"))
                .andExpect(view().name("donors/findOwnersForm"));
    }

    @Test
    public void processFindForm() throws Exception {
        mockMvc.perform(post("/donors/find")
                .param("lastName", "surname")
        );
    }

    @Test
    public void initUpdateDonorForm() throws Exception {
        mockMvc.perform(get("/donors/{donorId}/edit", IdTest))
                .andExpect(status().isOk())
                .andExpect(model().attribute("donor", hasProperty("firstName", is("name"))))
                .andExpect(model().attribute("donor", hasProperty("lastName")))
                .andExpect(model().attribute("donor", hasProperty("address")))
                .andExpect(model().attribute("donor", hasProperty("birthDate")))
                .andExpect(view().name("donors/DonorForm"));
    }

    @Test
    public void processUpdateDonorForm() throws Exception {
        mockMvc.perform(post("/donors/{donorId}/edit", IdTest)
                .param("firstName", "name")
                .param("lastName", "surname")
                .param("address", "testAddress")
                .param("birthDate", "1999-10-10")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/donors/{donorId}"));
    }

    @Test
    public void displayDonors() throws Exception {
        mockMvc.perform(get("/donors/{donorId}", IdTest))
                .andExpect(status().isOk())
                .andExpect(model().attribute("donor", hasProperty("firstName", is("name"))))
                .andExpect(model().attribute("donor", hasProperty("lastName", is("surname"))))
                .andExpect(model().attribute("donor", hasProperty("address", is("testAddress"))))
                .andExpect(model().attribute("donor", hasProperty("birthDate")));
    }

}