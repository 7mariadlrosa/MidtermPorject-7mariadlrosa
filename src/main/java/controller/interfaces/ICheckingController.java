package controller.interfaces;

import controller.dto.accounts.CheckingDto;
import model.Account;
import model.Checking;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

public interface ICheckingController {

    public Account create(CheckingDto checkingDto);

    public List<Checking> showAll();

    public Optional<Checking> find(Long id);
}
