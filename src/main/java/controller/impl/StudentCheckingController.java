package controller.impl;

import controller.interfaces.IStudentCheckingController;
import model.StudentChecking;
import service.interfaces.IStudentCheckingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class StudentCheckingController implements IStudentCheckingController {

    @Autowired
    private IStudentCheckingService studentCheckingService;

    @GetMapping("/admin/studentChecking")
    @ResponseStatus(HttpStatus.OK)
    public List<StudentChecking> showAll() {
        return studentCheckingService.showAll();
    }

    @GetMapping("/admin/studentChecking/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<StudentChecking> find(@PathVariable Long id) {
        return studentCheckingService.find(id);
    }
}
