package com.a2.a2.Model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    List<Student> findByNameAndHairColor(String name, String hairColor);
    List<Student> findByName(String name);
} 