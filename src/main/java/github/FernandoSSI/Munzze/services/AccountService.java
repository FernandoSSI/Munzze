package github.FernandoSSI.Munzze.services;

import github.FernandoSSI.Munzze.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;


}
