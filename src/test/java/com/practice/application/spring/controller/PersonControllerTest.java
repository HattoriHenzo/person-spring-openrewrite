package com.practice.application.spring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.application.spring.generic.GenericService;
import com.practice.application.spring.model.Person;
import com.practice.application.spring.service.exception.ServiceException;
import com.practice.application.spring.utils.TestData;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PersonController.class)
class PersonControllerTest implements WithAssertions {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    private GenericService<Person> service;

    @Test
    void givenController_whenFindingPersonById_thenReturns_200_OK() throws Exception {
        when(service.findById(anyLong())).thenReturn(TestData.createPerson());
        mockMvc.perform(MockMvcRequestBuilders.get("/persons/{id}", TestData.ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void givenController_whenPersonByIdNotFound_thenReturns_404_NOT_FOUND() throws Exception {
        when(service.findById(anyLong())).thenThrow(ServiceException.class);
        mockMvc.perform(MockMvcRequestBuilders.get("/persons/{id}", TestData.ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenController_whenFindingPersons_thenReturns_200_OK() throws Exception {
        when(service.findAll()).thenReturn(TestData.createPersons());
        mockMvc.perform(get("/persons")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void givenController_whenCreatingPerson_thenReturns_200_OK() throws Exception {
        var person = TestData.createPerson();
        when(service.createOrUpdate(person)).thenReturn(person);

        mockMvc.perform(post("/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(person)))
                .andExpect(status().isOk());
    }

    @Test
    void givenController_whenCreatingPerson_thenReturns_400_BAD_REQUEST() throws Exception {
        var person = TestData.createPerson();
        when(service.createOrUpdate(person)).thenThrow(ServiceException.class);

        mockMvc.perform(post("/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(person)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenController_whenUpdatingPerson_thenReturns_200_OK() throws Exception {
        var person = TestData.createPerson();
        when(service.createOrUpdate(person)).thenReturn(person);

        mockMvc.perform(put("/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(person)))
                .andExpect(status().isOk());
    }

    @Test
    void givenController_whenUpdatingPerson_thenReturns_400_BAD_REQUEST() throws Exception {
        var person = TestData.createPerson();
        when(service.createOrUpdate(person)).thenThrow(ServiceException.class);

        mockMvc.perform(put("/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(person)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenController_whenDeletingPerson_thenReturns_200_OK() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/persons/{id}", TestData.ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(service).delete(TestData.ID);
    }

    @Test
    void givenController_whenDeletingPerson_thenReturns_404_NOT_FOUND() throws Exception {
        doThrow(ServiceException.class).when(service).delete(anyLong());
        mockMvc.perform(MockMvcRequestBuilders.delete("/persons/{id}", TestData.INVALID_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(service).delete(TestData.INVALID_ID);
    }
}