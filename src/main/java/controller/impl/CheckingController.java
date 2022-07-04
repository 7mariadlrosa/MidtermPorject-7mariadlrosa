package controller.impl;

import controller.dto.accounts.CheckingDto;
import controller.interfaces.ICheckingController;
import model.Account;
import model.Checking;
import service.interfaces.ICheckingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class CheckingController implements ICheckingController {

    @Autowired
    private ICheckingService checkingService;

    @PostMapping("/admin/create-checking")
    @ResponseStatus(HttpStatus.CREATED)
    public Account create(@RequestBody @Valid CheckingDto checkingDto) {
        return checkingService.create(checkingDto);
    }

    @GetMapping("/admin/checking")
    @ResponseStatus(HttpStatus.OK)
    public List<Checking> showAll() {
        return checkingService.showAll();
    }

    @GetMapping("/admin/checking/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Checking> find(@PathVariable Long id) {
        return checkingService.find(id);
    }
}
