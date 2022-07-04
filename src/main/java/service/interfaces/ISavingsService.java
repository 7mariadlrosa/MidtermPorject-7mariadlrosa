package service.interfaces;

import controller.dto.accounts.SavingsDto;
import model.Savings;

import java.util.List;
import java.util.Optional;

public interface ISavingsService {

    public Savings create(SavingsDto savingsDto);

    public List<Savings> showAll();

    public Optional<Savings> find(Long id);
}
