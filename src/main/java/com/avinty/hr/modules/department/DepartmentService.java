package com.avinty.hr.modules.department;

import com.avinty.hr.config.exception.AvintyException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Department service for database operations
 * @author mredly
 */
@Getter
@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository repository;

    public DepartmentDTO findByName(String name) throws AvintyException {
        return repository.findFirstByName(name).map(DepartmentDTO::new).orElseThrow(() -> new AvintyException("Department not found"));
    }

    public List<DepartmentDTO> findAll() {
        return repository.findAll().stream().map(DepartmentDTO::new).collect(Collectors.toList());
    }
}
