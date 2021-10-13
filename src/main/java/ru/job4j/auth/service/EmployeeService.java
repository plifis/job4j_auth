package ru.job4j.auth.service;

import org.springframework.stereotype.Service;
import ru.job4j.auth.model.Employee;
import ru.job4j.auth.repository.EmployeeRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository rep;

    public EmployeeService(EmployeeRepository rep) {
        this.rep = rep;
    }

    public List<Employee> getAllEmployees() {
        List<Employee>  rsl = new ArrayList<>();
        this.rep.findAll().forEach(rsl::add);
        return rsl;
    }
}
