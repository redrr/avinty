package com.avinty.hr.modules.department;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository repository;

    public DepartmentDTO findByName(String name) {
        return repository.findFirstByName(name).map(DepartmentDTO::new).orElseThrow();
    }

    public List<DepartmentDTO> findAll() {
        return repository.findAll().stream().map(DepartmentDTO::new).collect(Collectors.toList());
    }
}
