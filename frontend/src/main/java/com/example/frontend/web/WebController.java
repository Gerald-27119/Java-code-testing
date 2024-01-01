//package com.example.frontend.web;
//
//import com.example.backend.student.Student;
//import com.example.backend.student.StudentService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//@Controller
//public class WebController {
//    private final StudentService studentService;
//
//    @Autowired
//    WebController(StudentService studentService) {
//        this.studentService = studentService;
//    }
//
//    @PostMapping(path = "/create")
//    public String create(Student student) {
//        studentService.create(student);
//        return "redirect:/index";
//    }
//
//    @PostMapping(path = "/update")
//    public String put(Student updatedStudent) {
//        Student existingStudent = studentService.read(updatedStudent.getId());
//        existingStudent.setName(updatedStudent.getName());
//        existingStudent.setSurname(updatedStudent.getSurname());
//        existingStudent.setEmail(updatedStudent.getEmail());
//        existingStudent.setAge(updatedStudent.getAge());
//        studentService.put(existingStudent);
//        return "redirect:/index";
//    }
//
//    @PostMapping(path = "/delete")
//    public String delete(Student student) {
//        Long id = student.getId();
//        studentService.delete(id);
//        return "redirect:/index";
//    }
//
//    @GetMapping(path = "/welcome")
//    public String showAllStudents(Model model) {
//        model.addAttribute("students", studentService.readAll());
//        return "index";
//    }
//
//    @GetMapping(path = "/index")
//    public String getIndexView(Model model) {
//        model.addAttribute("students", studentService.readAll());
//        return "index";
//    }
//
//    @GetMapping(path = "/edit-student")
//    public String getEditStudentView(@RequestParam("id") Long id, Model model) {
//        Student student = studentService.read(id);
//        model.addAttribute("student", student);
//        return "edit-student";
//    }
//}