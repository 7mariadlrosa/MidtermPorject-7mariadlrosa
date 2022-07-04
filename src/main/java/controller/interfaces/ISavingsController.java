package controller.interfaces;

import controller.dto.accounts.SavingsDto;
import model.Savings;

import java.util.List;
import java.util.Optional;

public interface ISavingsController {

    public Savings create(SavingsDto savingsDto);

    public List<Savings> showAll();

    public Optional<Savings> find(Long id);
}
