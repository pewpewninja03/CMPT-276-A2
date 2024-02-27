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
import org.springframework.web.bind.annotation.PathVariable;



@Controller
public class StudentController {
    @Autowired
    private StudentRepository studentRepo;
    
    @GetMapping("/students/home")
    public String getAllStudents(Model model) {
        System.out.println("Getting all students: ");
        // Get all students from database
        List<Student> students = studentRepo.findAll();

        model.addAttribute("students", students);
        
        return "/students/showAll";
    }
    @GetMapping("/students/edit/{uid}")
    public String getStudent(Model model, @PathVariable int uid) {
        Student student = studentRepo.findById(uid).get();
        model.addAttribute("students", student);

        return "students/edit";
    }
    

     @PostMapping("/students/add")
    public String addStudent(@RequestParam Map<String, String> newStudent, HttpServletResponse response) {
        System.out.println("ADD STUDENT");
        String newName = newStudent.get("name");
        int newWeight = Integer.parseInt(newStudent.get("weight"));
        int newHeight = Integer.parseInt(newStudent.get("height"));
        String newHairColor = newStudent.get("hairColor");
        float newGpa = Float.parseFloat(newStudent.get("gpa"));

        studentRepo.save(new Student(newName, newWeight, newHeight, newHairColor, newGpa));
        response.setStatus(201);
        return "redirect:/add.html";
    }

    @PostMapping("/students/edit") 
    public String editStudent(@RequestParam Map<String, String> editStudent) {
        int uid = Integer.parseInt(editStudent.get("uid"));
        Student student = studentRepo.findById(uid).get();

        student.setName(editStudent.get("name"));
        student.setWeight(Integer.parseInt(editStudent.get("weight")));
        student.setHeight(Integer.parseInt(editStudent.get("height")));
        student.setHairColor(editStudent.get("hairColor"));
        student.setGpa(Float.parseFloat(editStudent.get("gpa")));

        studentRepo.save(student);
        
        return "redirect:/students/home";
    }

  
    @PostMapping("/students/{name}/{hairColor}/delete")
    public String deleteStudent(@RequestParam String name, @RequestParam String hairColor){
        Student student = studentRepo.findByNameAndHairColor(name, hairColor).get(0);
        studentRepo.delete(student);
        return "redirect:/delete.html";
    }
    
    
}
