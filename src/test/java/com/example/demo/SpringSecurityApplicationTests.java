package com.example.demo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.example.demo.dto.Course;
import com.example.demo.dto.Student;
import com.example.demo.util.BasicAuthGenerator;
import com.example.demo.util.StringContainMatcher;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc
public class SpringSecurityApplicationTests {
    ObjectMapper mapper;

    @Autowired
    private MockMvc mockMvc;
    private Course course ;
    private Student student;

    @Before
    public void contextLoads() {
        course = new Course(123,"Basic MatheMatics","Tom","22B",null);
        student = new Student(1000,"Jerry","Mouse");
        mapper = new ObjectMapper();
    }

    @Test
    public void testAddCourseWithAdminCredentials() throws Exception{

        mockMvc.perform(post("/course")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(course))
//                .with(HttpBasicConfigurer)
                .header("Authorization", BasicAuthGenerator.generate("admin", "123")))
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value(Matchers.containsStringIgnoringCase("Added")));
    }

    @Test
    public void testAddCourseWithStudentCredentials() throws Exception{
        mockMvc.perform(post("/course")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(course))
                .header("Authorization", BasicAuthGenerator.generate("student", "123")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.FORBIDDEN.value()))
                .andExpect(jsonPath("$.message").value(Matchers.containsStringIgnoringCase("Authorization Failure")));
    }


    @Test
    public void testAddStudentToCourseWithStudentCredentials() throws Exception{
        mockMvc.perform(post("/student")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(student))
                .header("Authorization", BasicAuthGenerator.generate("student", "123")))
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value(Matchers.containsStringIgnoringCase("added")))
                ;
    }

    @Test
    public void testAddStudentToCourseWithAdminCredentials() throws Exception{
        mockMvc.perform(post("/student")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(student))
                .header("Authorization", BasicAuthGenerator.generate("admin", "123")))
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value(Matchers.containsStringIgnoringCase("Added")));
    }

    @Test
    public void testAddStudentToCourseWithIncorrectCredentials() throws Exception{
        mockMvc.perform(post("/student")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(student))
                .header("Authorization", BasicAuthGenerator.generate("student", "wrong-123")))
//                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.UNAUTHORIZED.value()))
                .andExpect(jsonPath("$.message").value(Matchers.containsStringIgnoringCase("Authentication Failure")));
    }

    @Test
    public void testAddCourseWithIncorrectCredentials() throws Exception{
        mockMvc.perform(post("/course")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(course))
                .header("Authorization", BasicAuthGenerator.generate("admin", "123+wrong")))
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.UNAUTHORIZED.value()))
                .andExpect(jsonPath("$.message").value(Matchers.containsStringIgnoringCase("Authentication Failure")));

    }


    @Test
    public void testGetCourseDetailsWithNoCredentials() throws Exception{
        mockMvc.perform(get("/hello"))
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value(Matchers.containsStringIgnoringCase("hello")));
    }


}
