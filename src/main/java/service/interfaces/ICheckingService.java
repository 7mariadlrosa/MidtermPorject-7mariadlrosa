package service.interfaces;

import controller.dto.accounts.CheckingDto;
import model.Account;
import model.Checking;

import java.util.List;
import java.util.Optional;

public interface ICheckingService {


    public Account create(CheckingDto checkingDto);

    public List<Checking> showAll();

    public Optional<Checking> find(Long id);
}
