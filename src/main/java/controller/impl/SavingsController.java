package controller.impl;

import controller.dto.accounts.SavingsDto;
import controller.interfaces.ISavingsController;
import model.Savings;
import service.interfaces.ISavingsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class SavingsController implements ISavingsController {

    @Autowired
    private ISavingsService savingsService;

    @PostMapping("/admin/create-saving")
    @ResponseStatus(HttpStatus.CREATED)
    public Savings create(@RequestBody @Valid SavingsDto savingsDto) {
        return savingsService.create(savingsDto);
    }

    @GetMapping("/admin/savings")
    @ResponseStatus(HttpStatus.OK)
    public List<Savings> showAll() {
        return savingsService.showAll();
    }

    @GetMapping("/admin/savings/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Savings> find(@PathVariable Long id) {
        return savingsService.find(id);
    }
}