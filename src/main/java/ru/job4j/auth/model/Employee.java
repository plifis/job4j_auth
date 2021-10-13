package ru.job4j.auth.model;

import javax.persistence.*;
import java.util.*;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String surname;
    private int inn;
    private Date hiringDate;
    @OneToMany
    private Set<Person> personSet;

    public Employee() {
    }

    public Employee(String name, String surname, int inn) {
        this.name = name;
        this.surname = surname;
        this.inn = inn;
        this.hiringDate = new Date();
        this.personSet = new HashSet<>();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getInn() {
        return inn;
    }

    public void setInn(int inn) {
        this.inn = inn;
    }

    public Date getHiringDate() {
        return hiringDate;
    }

    public void setHiringDate(Date hiringDate) {
        this.hiringDate = hiringDate;
    }

    public Set<Person> getPersonSet() {
        return personSet;
    }

    public void setPersonList(List<Person> personList) {
        this.personSet = personSet;
    }

    public void addPerson(Person person) {
        this.personSet.add(person);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id == employee.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
