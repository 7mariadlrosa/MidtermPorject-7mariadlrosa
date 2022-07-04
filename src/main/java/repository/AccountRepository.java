package repository;

import jdk.dynalink.Operation;
import model.Account;
import model.AccountHolder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByReceivedMoney(List<Operation> receivedMoney);
    Account findBySentMoney(List<Operation> sentMoney);

    public Account findByPrimaryOwner(AccountHolder accountHolder);
}
