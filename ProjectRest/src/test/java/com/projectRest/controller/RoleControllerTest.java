package com.projectRest.controller;


import com.projectRest.ProjectRestApplication;
import com.projectRest.model.Role;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;


@SpringBootTest(classes = ProjectRestApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RoleControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    //    String exampleCourseJson = "{\"name\":\"Spring\",\"description\":\"10 Steps\",\"steps\":[\"Learn Maven\",\"Import Project\",\"First Example\",\"Second Example\"]}";
    String exampleCourseJson = "{\n" +
            "    \"id\": 102,\n" +
            "    \"name\": \"ADMIN\",\n" +
            "    \"nameChange\": null,\n" +
            "    \"appArrayList\": []\n" +
            "}";

    @Test
    public void createRole() throws Exception {
/*
        Mockito.when(roleService.save(Mockito.any(Role.class))).thenReturn(mockRole);

        // Send course as body to /students/Student1/courses
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/addrol")
                .accept(MediaType.APPLICATION_JSON).content(exampleCourseJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());

      *//*  assertEquals("http://localhost/students/Student1/courses/1",
                response.getHeader(HttpHeaders.LOCATION));
*/
    }

    @Test
    public void testAddRole()
    {
        Role role = new Role("Lokesh");
        ResponseEntity<String> responseEntity = this.restTemplate
                .postForEntity("http://localhost:" + port + "/api/addrol", role, String.class);
        Assertions.assertEquals(200, responseEntity.getStatusCodeValue());
    }
}
