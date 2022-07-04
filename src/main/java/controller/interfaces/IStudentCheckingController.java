package controller.interfaces;

import model.StudentChecking;

import java.util.List;
import java.util.Optional;

public interface IStudentCheckingController {

    public List<StudentChecking> showAll();

    public Optional<StudentChecking> find(Long id);

}
