package controller.interfaces;

import controller.dto.accounts.CreditCardDto;
import model.CreditCard;

import java.util.List;
import java.util.Optional;

public interface ICreditCardController {

    public CreditCard create(CreditCardDto creditCardDto);

    public List<CreditCard> showAll();

    public Optional<CreditCard> find(Long id);
}