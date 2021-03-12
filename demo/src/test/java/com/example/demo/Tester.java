package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.*;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = Controller.class)
public class Tester {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PersonRepository personRepository;

    @Test
    void whenValidInput_thenReturns200() throws Exception {
      mockMvc.perform(get("/persons")
              .contentType(MediaType.APPLICATION_JSON))
              .andExpect(status().isOk());
    }

    @Test
    void whenValidInput_thenReturns20() throws Exception {
      when( personRepository.findById(1L))
              .thenReturn(Optional.of(new Person(1,"leo","mail")));

      mockMvc.perform(get("/persons/1")
              .contentType(MediaType.APPLICATION_JSON))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.name", is("leo")));
    }

    @Test
    void testingPost() throws Exception {
      Person person = new Person(0,"leo","mail");
      mockMvc.perform(post("/persons")
              .contentType(MediaType.APPLICATION_JSON)
              .content(objectMapper.writeValueAsBytes(person)))
              .andExpect(status().isCreated());
    }
}
