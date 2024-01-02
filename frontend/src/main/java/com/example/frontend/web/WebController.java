package com.example.frontend.web;

import com.example.frontend.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WebController {
    private final WebService webService;

    @Autowired
    WebController(WebService webService) {
        this.webService = webService;
    }

    @PostMapping(path = "/create")
    public String create(Student student) {
        webService.createStudent(student);
        return "redirect:/index";
    }

    @PostMapping(path = "/update")
    public String put(Student updatedStudent) {
        webService.updateStudent(updatedStudent);
        return "redirect:/index";
    }

    @PostMapping(path = "/delete")
    public String delete(Student student) {
        webService.deleteStudent(student.getId());
        return "redirect:/index";
    }

    @GetMapping(path = "/welcome")
    public String showAllStudents(Model model) {
        model.addAttribute("students", webService.getAllStudents());
        return "index";
    }

    @GetMapping(path = "/index")
    public String getIndexView(Model model) {
        model.addAttribute("students", webService.getAllStudents());
        return "index";
    }
    @GetMapping(path = "/create-student")
    public String getEditStudent() {
        return "/create-student";
    }

    @GetMapping(path = "/edit-student")
    public String getEditStudentView(@RequestParam("id") Long id, Model model) {
        Student student = webService.getStudentById(id);
        model.addAttribute("student", student);
        return "edit-student";
    }
}