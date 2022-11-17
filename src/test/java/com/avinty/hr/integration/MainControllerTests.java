package com.avinty.hr.integration;

import com.avinty.hr.config.Constants;
import com.avinty.hr.config.payload.JwtResponseDTO;
import com.avinty.hr.config.payload.LoginRequestDTO;
import com.avinty.hr.modules.department.Department;
import com.avinty.hr.modules.department.DepartmentDTO;
import com.avinty.hr.modules.department.DepartmentRepository;
import com.avinty.hr.modules.department.DepartmentService;
import com.avinty.hr.modules.employee.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static com.avinty.hr.TestUtils.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ComponentScan(basePackageClasses = {
        DepartmentRepository.class,
        DepartmentService.class,
        EmployeeRepository.class,
        EmployeeService.class
})
@EnableAutoConfiguration
@AutoConfigureMockMvc
public class MainControllerTests {

    @Autowired
    private DepartmentRepository departmentRepository;
    private DepartmentService departmentService;

    @Autowired
    private EmployeeRepository employeeRepository;
    private EmployeeService employeeService;

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        this.departmentService = new DepartmentService(departmentRepository);
        this.employeeService = new EmployeeService(employeeRepository);
    }

    @Test
    void loginTest() throws JSONException {
        JSONObject json = login("emp1@mail.hu","test123");
        assertThat(Objects.nonNull(json)).isTrue();
        assertThat(Objects.nonNull(json.getString(JwtResponseDTO.Fields.token))).isTrue();
    }

    @Test
    void authCheck_listEmployees() throws Exception {
        int resultStatus = mvc.perform(MockMvcRequestBuilders.get(Constants.ALL_EMPLOYEES_URL)).andReturn().getResponse().getStatus();
        assertThat(resultStatus).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    void listEmployees() throws Exception {
        JSONObject loginResp = login("emp1@mail.hu","test123");
        assertThat(Objects.nonNull(loginResp)).isTrue();

        String token = loginResp.getString(JwtResponseDTO.Fields.token);
        MockHttpServletResponse response = mvc.perform(
                MockMvcRequestBuilders.get(Constants.ALL_EMPLOYEES_URL)
                        .header(HttpHeaders.AUTHORIZATION, token)
        ).andReturn().getResponse();
        JSONArray json = readResultList(response);

        assertThat(Objects.nonNull(json)).isTrue();
        assertThat(json.length()).isEqualTo(employeeService.filterAll(new EmployeeFilter(null, null)).size());
    }

    @Test
    void authCheck_listDepartmentEmployees() throws Exception {
        int resultStatus = mvc.perform(MockMvcRequestBuilders.get(Constants.ALL_DEPARTMENT_EMPLOYEES_URL))
                .andReturn()
                .getResponse()
                .getStatus();
        assertThat(resultStatus).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    void listDepartmentEmployees() throws Exception {
        JSONObject loginResp = login("emp1@mail.hu","test123");
        assertThat(Objects.nonNull(loginResp)).isTrue();

        String token = loginResp.getString(JwtResponseDTO.Fields.token);

        MockHttpServletResponse response = mvc.perform(
                MockMvcRequestBuilders.get(Constants.ALL_DEPARTMENT_EMPLOYEES_URL)
                        .header(HttpHeaders.AUTHORIZATION, token)
        ).andReturn().getResponse();
        JSONArray json = readResultList(response);

        assertThat(Objects.nonNull(json)).isTrue();
        assertThat(json.length()).isEqualTo(departmentService.findAll().size());
    }

    @Test
    void authCheck_getDepartment() throws Exception {
        MockHttpServletResponse response = mvc.perform(
                MockMvcRequestBuilders.get(Constants.DEPARTMENT_URL)
                        .param(DepartmentDTO.Fields.name, "dep1")
            ).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    void getDepartment_validSearch() throws Exception {
        JSONObject loginResp = login("emp1@mail.hu","test123");
        assertThat(Objects.nonNull(loginResp)).isTrue();

        String token = loginResp.getString(JwtResponseDTO.Fields.token);

        String searchValue = "dep1";
        MockHttpServletResponse response = mvc.perform(
                MockMvcRequestBuilders.get(Constants.DEPARTMENT_URL)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .param(DepartmentDTO.Fields.name, searchValue)
        ).andReturn().getResponse();
        JSONObject json = readSingleResult(response);

        assertThat(Objects.nonNull(json)).isTrue();
        assertThat(json.getString(DepartmentDTO.Fields.name)).isEqualTo(searchValue);
    }

    @Test
    void getDepartment_invalidSearch() throws Exception {
        JSONObject loginResp = login("emp1@mail.hu","test123");
        assertThat(Objects.nonNull(loginResp)).isTrue();

        String token = loginResp.getString(JwtResponseDTO.Fields.token);

        MockHttpServletResponse response = mvc.perform(
                MockMvcRequestBuilders.get(Constants.DEPARTMENT_URL)
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .param(DepartmentDTO.Fields.name, "depUndefined")
        ).andReturn().getResponse();
        JSONObject dep1Json = readSingleResult(response);

        assertThat(Objects.nonNull(dep1Json)).isFalse();
    }

    @Test
    void authCheck_setDepartmentManager() throws Exception {
        int resultStatus = mvc.perform(MockMvcRequestBuilders.patch(Constants.DEPARTMENT_MANAGER_URL+"/1"))
                .andReturn()
                .getResponse()
                .getStatus();
        assertThat(resultStatus).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    void roleCheck_setDepartmentManager() throws Exception {
        String email = "emp2@mail.hu";
        Optional<Employee> employeeOptional = employeeRepository.findByEmail(email);
        assertThat(employeeOptional.isPresent()).isTrue();
        assertThat(employeeOptional.get().getPosition()).isEqualTo(Position.EMPLOYEE);

        JSONObject loginResp = login(email,"test123");
        assertThat(Objects.nonNull(loginResp)).isTrue();

        String token = loginResp.getString(JwtResponseDTO.Fields.token);

        int resultStatus = mvc.perform(
                MockMvcRequestBuilders.patch(Constants.DEPARTMENT_MANAGER_URL+"/1")
                        .header(HttpHeaders.AUTHORIZATION, token)
            ).andReturn().getResponse().getStatus();
        assertThat(resultStatus).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @Test
    void authCheck_deleteDepartment() throws Exception {
        int resultStatus = mvc.perform(MockMvcRequestBuilders.delete(Constants.DEPARTMENT_URL+"/1"))
                .andReturn()
                .getResponse()
                .getStatus();
        assertThat(resultStatus).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }


    @Test
    void roleCheck_deleteDepartment() throws Exception {
        String email = "emp2@mail.hu";
        Optional<Employee> employeeOptional = employeeRepository.findByEmail(email);
        assertThat(employeeOptional.isPresent()).isTrue();
        assertThat(employeeOptional.get().getPosition()).isEqualTo(Position.EMPLOYEE);

        JSONObject loginResp = login(email,"test123");
        assertThat(Objects.nonNull(loginResp)).isTrue();

        String token = loginResp.getString(JwtResponseDTO.Fields.token);

        int resultStatus = mvc.perform(
                MockMvcRequestBuilders.delete(Constants.DEPARTMENT_URL+"/1")
                        .header(HttpHeaders.AUTHORIZATION, token)
        ).andReturn().getResponse().getStatus();
        assertThat(resultStatus).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @Test
    void deleteDepartment() throws Exception {
        JSONObject loginResp = login("emp1@mail.hu","test123");
        assertThat(Objects.nonNull(loginResp)).isTrue();

        String token = loginResp.getString(JwtResponseDTO.Fields.token);
        Integer selectedDepartmentId = 2;

        Optional<Department> selectedDepartment = departmentRepository.findById(selectedDepartmentId);
        assertThat(selectedDepartment.isPresent()).isTrue();

        Set<Employee> originalEmployees = selectedDepartment.get().getEmployees();
        assertThat(originalEmployees.size()).isGreaterThan(0);

        MockHttpServletResponse response = mvc.perform(
                MockMvcRequestBuilders.delete(Constants.DEPARTMENT_URL+"/"+selectedDepartmentId)
                        .header(HttpHeaders.AUTHORIZATION, token)
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        assertThat(departmentRepository.findById(selectedDepartmentId).isPresent()).isFalse();
        for (Employee employee : originalEmployees) {
            Optional<Employee> employeeOptional = employeeRepository.findById(employee.getId());
            assertThat(employeeOptional.isPresent()).isTrue();
            assertThat(Objects.isNull(employeeOptional.get().getDepartment())).isTrue();
        }
    }

    private JSONObject login(String email, String password) {
        try {
            LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
            loginRequestDTO.setEmail(email);
            loginRequestDTO.setPassword(password);
            MockHttpServletResponse response = mvc.perform(
                    MockMvcRequestBuilders.post(Constants.LOGIN_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(toJson(loginRequestDTO))
            ).andReturn().getResponse();
            return readSingleResult(response);
        } catch (Exception e) {
            return null;
        }
    }
}
