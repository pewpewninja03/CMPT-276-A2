package com.a2.a2.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.a2.a2.Model.Student;
import com.a2.a2.Model.StudentRepository;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class StudentController {
    @Autowired
    private StudentRepository studentRepo;
    
    @GetMapping("/students/view")
    public String getAllStudents(Model model) {
        System.out.println("Getting all students: ");
        // Get all students from database
        List<Student> students = studentRepo.findAll();

        model.addAttribute("students", students);
        
        return "students/showAll";
    }

     @PostMapping("/students/add")
    public String addStudent(@RequestParam Map<String, String> newStudent, HttpServletResponse response) {
        System.out.println("ADD STUDENT");
        String newName = newStudent.get("name");
        float newWeight = Float.parseFloat(newStudent.get("weight"));
        float newHeight = Float.parseFloat(newStudent.get("height"));
        String newHairColor = newStudent.get("hairColor");
        float newGpa = Float.parseFloat(newStudent.get("gpa"));
        String newMajor = newStudent.get("major");

        studentRepo.save(new Student(newName, newWeight, newHeight, newHairColor, newGpa, newMajor));
        response.setStatus(201);
        return "students/addedStudent";
    }
}
