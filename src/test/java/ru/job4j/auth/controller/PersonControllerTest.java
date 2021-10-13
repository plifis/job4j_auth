package ru.job4j.auth.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.job4j.auth.Job4jAuthApplication;
import ru.job4j.auth.model.Person;
import ru.job4j.auth.repository.PersonRepository;
import ru.job4j.auth.service.PersonService;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Job4jAuthApplication.class)
@AutoConfigureMockMvc
class PersonControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService service;

    @Test
    public void shouldReturnAllPersons() throws Exception {
        List<Person> list = List.of(
                new Person(1, "Alex", "123"),
                new Person(2, "Bill", "000"),
                new Person(3, "Petr", "abc"));
        Mockito.when(service.getAllPersons()).thenReturn(list);
        mockMvc.perform(get("/person/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].password", is("123")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].login", is("Petr")));
    }

    @Test
    public void shouldReturnPersonById() throws Exception {
        Person person = new Person(1, "Alex", "123");
        Mockito.when(service.findPersonById(1)).thenReturn(Optional.of(person));
        mockMvc.perform(get("/person/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.login", is("Alex")));
    }

    @Test
    public void shouldReturnStatusCreated() throws Exception {
        Person person = new Person(1, "Alex", "123");
        ObjectMapper mapper = new ObjectMapper();
        Mockito.when(service.save(person)).thenReturn(person);
        mockMvc.perform(post("/person/")
                .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(person)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldReturnUpdatedPerson() throws Exception {
        Person person = new Person(1, "Alex", "123");
        ObjectMapper mapper = new ObjectMapper();
        Mockito.when(service.save(person)).thenReturn(person);
        mockMvc.perform(put("/person/")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(person)))
                .andDo(print())
                .andExpect(status().isOk());
        ArgumentCaptor<Person> captor = ArgumentCaptor.forClass(Person.class);
        verify(service).save(captor.capture());
        assertThat(captor.getValue().getLogin(), is("Alex"));
    }

    @Test
    public void shouldReturn() throws Exception {
        Person person = new Person(1, "Alex", "123");
        mockMvc.perform(delete("/person/1")
        .param("id", "1")
        )
        .andExpect(status().isOk());
    }

}