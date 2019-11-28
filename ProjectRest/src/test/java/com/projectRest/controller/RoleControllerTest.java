package com.projectRest.controller;


import com.projectRest.ProjectRestApplication;
import com.projectRest.helper.Validations;
import com.projectRest.model.Role;
import com.projectRest.repository.RoleRepository;
import com.projectRest.service.RoleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@WebMvcTest(value = RoleController.class)
@ContextConfiguration(classes= ProjectRestApplication.class)
public class RoleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoleService roleService;
    @MockBean
    Validations validation;

    @MockBean
    RoleRepository roleRepository;

    //    String exampleCourseJson = "{\"name\":\"Spring\",\"description\":\"10 Steps\",\"steps\":[\"Learn Maven\",\"Import Project\",\"First Example\",\"Second Example\"]}";
    String exampleCourseJson = "{\n" +
            "    \"id\": 102,\n" +
            "    \"name\": \"ADMIN\",\n" +
            "    \"nameChange\": null,\n" +
            "    \"appArrayList\": []\n" +
            "}";

    @Test
    public void createRole() throws Exception {
        Role mockRole = new Role("ADMIN");

        Mockito.when(roleService.save(Mockito.any(Role.class))).thenReturn(mockRole);

        // Send course as body to /students/Student1/courses
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/addrol")
                .accept(MediaType.APPLICATION_JSON).content(exampleCourseJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());

      /*  assertEquals("http://localhost/students/Student1/courses/1",
                response.getHeader(HttpHeaders.LOCATION));
*/
    }
}
