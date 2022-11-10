package com.avinty.hr.modules.employee;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EmployeeSpecification implements Specification<Employee> {

    private final EmployeeFilter filter;

    public EmployeeSpecification(EmployeeFilter criteria) {
        this.filter =criteria;
    }

    @Override
    public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> filters = new ArrayList<>();
        if (Objects.nonNull(filter.getFullName())) {
            filters.add(cb.equal(root.get(Employee.Fields.fullName), filter.getFullName()));
        }
        if (Objects.nonNull(filter.getEmail())) {
            filters.add(cb.equal(root.get(Employee.Fields.email), filter.getEmail()));
        }
        return cb.and(filters.toArray(Predicate[]::new));
    }
}
