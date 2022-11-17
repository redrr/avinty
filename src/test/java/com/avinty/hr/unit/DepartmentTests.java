package com.avinty.hr.unit;

import com.avinty.hr.config.exception.AvintyException;
import com.avinty.hr.modules.department.Department;
import com.avinty.hr.modules.department.DepartmentDTO;
import com.avinty.hr.modules.department.DepartmentRepository;
import com.avinty.hr.modules.department.DepartmentService;
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
        DepartmentRepository.class,
        DepartmentService.class
})
@EnableAutoConfiguration
public class DepartmentTests {

    @Autowired
    private DepartmentRepository repository;
    private DepartmentService service;

    @BeforeEach
    void setUp() {
        this.service = new DepartmentService(repository);
    }

    @Test
    void save_Repository() {
        Department department = create();
        assertThat(Objects.nonNull(department.getId())).isTrue();
        delete(department);
    }

    @Test
    void findByName_Repository() {
        Department department = create();
        assertThat(repository.findFirstByName(department.getName()).isPresent()).isTrue();
        delete(department);
    }

    @Test
    void findAll_Repository() {
        int defaultLength = repository.findAll().size();
        Department department = create();
        int newLength = repository.findAll().size();
        assertThat(newLength - defaultLength).isEqualTo(1);
        delete(department);
    }

    @Test
    void findByName_Service() throws AvintyException {
        Department department = create();
        DepartmentDTO dto = service.findByName(department.getName());
        assertThat(dto.getName()).isEqualTo(department.getName());
        delete(department);
    }

    @Test
    void findAll_Service() {
        int defaultLength = service.findAll().size();
        Department department = create();
        int newLength = service.findAll().size();
        assertThat(newLength - defaultLength).isEqualTo(1);
        delete(department);
    }

    private Department create() {
        Department department = new Department();
        department.setId(101);
        department.setName("test-dep");
        department.setManager(null);
        return repository.save(department);
    }

    private void delete(Department department) {
        repository.delete(department);
    }
}
