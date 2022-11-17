package com.avinty.hr.integration;

import com.avinty.hr.config.Constants;
import com.avinty.hr.config.payload.JwtResponseDTO;
import com.avinty.hr.config.payload.LoginRequestDTO;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Objects;

import static com.avinty.hr.TestUtils.readSingleResult;
import static com.avinty.hr.TestUtils.toJson;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@EnableAutoConfiguration
@AutoConfigureMockMvc
public class AuthControllerTests {

    @Autowired
    private MockMvc mvc;

    @Test
    void loginTest() throws Exception {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setEmail("emp4@mail.hu");
        loginRequestDTO.setPassword("test123");
        MockHttpServletResponse response = mvc.perform(
                MockMvcRequestBuilders.post(Constants.LOGIN_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(loginRequestDTO))
        ).andReturn().getResponse();
        JSONObject json =  readSingleResult(response);
        assertThat(Objects.nonNull(json)).isTrue();
        assertThat(Objects.nonNull(json.getString(JwtResponseDTO.Fields.token))).isTrue();
    }
}
