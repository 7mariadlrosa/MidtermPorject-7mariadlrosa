package service.impl;

import model.StudentChecking;
import repository.StudentCheckingRepository;
import service.interfaces.IStudentCheckingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class StudentCheckingService implements IStudentCheckingService {

    @Autowired
    private StudentCheckingRepository studentCheckingRepository;

    /** Method to find all savings accounts (you have to be and ADMIN) **/
    public List<StudentChecking> showAll(){
        if (studentCheckingRepository.findAll().size()==0){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No checking accounts found");
        }
        return studentCheckingRepository.findAll();
    }

    /** Method to find one savings account by id (you have to be and ADMIN) **/
    public Optional<StudentChecking> find(Long id){
        if (studentCheckingRepository.findById(id).isPresent()){
            return studentCheckingRepository.findById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no account with the provided id");
        }
    }
}