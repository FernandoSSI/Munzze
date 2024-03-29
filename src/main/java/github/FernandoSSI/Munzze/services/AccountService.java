package github.FernandoSSI.Munzze.services;

import github.FernandoSSI.Munzze.domain.Account;
import github.FernandoSSI.Munzze.repositories.AccountRepository;
import github.FernandoSSI.Munzze.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account findById(String id){
        Optional<Account> account = accountRepository.findById(id);
        return account.orElseThrow(() -> new ObjectNotFoundException("Object not found"));
    }

    public Account update(Account newAccount, String id){
        Account account = findById(id);

        account.setTotalIncomes(newAccount.getTotalIncomes());
        account.setTotalExpenses(newAccount.getTotalExpenses());
        account.setTotalBalance(account.getTotalIncomes() - account.getTotalExpenses());

        account = accountRepository.save(account);
        return account;
    }


}
