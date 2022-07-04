package controller.impl;

import controller.dto.accounts.CreditCardDto;
import controller.interfaces.ICreditCardController;
import model.CreditCard;
import service.interfaces.ICreditCardService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class CreditCardController implements ICreditCardController {

    @Autowired
    private ICreditCardService creditCardService;

    @PostMapping("/admin/create-creditCard")
    @ResponseStatus(HttpStatus.CREATED)
    public CreditCard create(@RequestBody @Valid CreditCardDto creditCardDto) {
        return creditCardService.create(creditCardDto);
    }

    @GetMapping("/admin/creditCard")
    @ResponseStatus(HttpStatus.OK)
    public List<CreditCard> showAll() {
        return creditCardService.showAll();
    }

    @GetMapping("/admin/creditCar/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<CreditCard> find(@PathVariable Long id) {
        return creditCardService.find(id);
    }
}