package service.interfaces;

import model.StudentChecking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IStudentCheckingService{

    public List<StudentChecking> showAll();

    public Optional<StudentChecking> find(Long id);
}
