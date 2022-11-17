package com.avinty.hr.unit;

import com.avinty.hr.modules.employee.Employee;
import com.avinty.hr.modules.employee.EmployeeFilter;
import com.avinty.hr.modules.employee.EmployeeRepository;
import com.avinty.hr.modules.employee.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ComponentScan(basePackageClasses = {
        EmployeeRepository.class,
        EmployeeService.class
})
@EnableAutoConfiguration
public class EmployeeTests {

    @Autowired
    private EmployeeRepository repository;
    private EmployeeService service;

    @BeforeEach
    void setUp() {
        this.service = new EmployeeService(repository);
    }

    @Test
    void save_Repository() {
        Employee employee = create();
        assertThat(Objects.nonNull(employee.getId())).isTrue();
        delete(employee);
    }

    @Test
    void findByEmail_Repository() {
        Employee employee = create();
        assertThat(repository.findByEmail("test@mail.hu").isPresent()).isTrue();
        delete(employee);
    }

    @Test
    void filterAll_Service() {
        int invalidNameFilteredSize = service.filterAll(new EmployeeFilter("asd", null)).size();
        assertThat(invalidNameFilteredSize).isEqualTo(0);

        int invalidEmailFilteredSize = service.filterAll(new EmployeeFilter(null, "asd@asd.asd")).size();
        assertThat(invalidEmailFilteredSize).isEqualTo(0);

        int invalidEmailAndNameFilteredSize = service.filterAll(new EmployeeFilter("asd", "asd@asd.asd")).size();
        assertThat(invalidEmailAndNameFilteredSize).isEqualTo(0);

        int defaultSize = service.filterAll(new EmployeeFilter(null, null)).size();
        Employee employee = create();
        int newFullSize = service.filterAll(new EmployeeFilter(null, null)).size();
        assertThat(newFullSize - defaultSize).isEqualTo(1);

        int validNameFilteredSize = service.filterAll(new EmployeeFilter("test", null)).size();
        assertThat(validNameFilteredSize).isEqualTo(1);

        int validEmailFilteredSize = service.filterAll(new EmployeeFilter(null, "test@mail.hu")).size();
        assertThat(validEmailFilteredSize).isEqualTo(1);

        int validEmailAndNameFilteredSize = service.filterAll(new EmployeeFilter("test", "test@mail.hu")).size();
        assertThat(validEmailAndNameFilteredSize).isEqualTo(1);

        delete(employee);
    }

    private Employee create() {
        Employee employee = new Employee();
        employee.setId(101);
        employee.setEmail("test@mail.hu");
        employee.setFullName("test");
        return repository.save(employee);
    }

    void delete(Employee employee) {
        repository.delete(employee);
    }
}
