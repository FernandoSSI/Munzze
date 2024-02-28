package github.FernandoSSI.Munzze.services;

import github.FernandoSSI.Munzze.domain.SubAccount;
import github.FernandoSSI.Munzze.repositories.SubAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubAccountService {

    @Autowired
    private SubAccountRepository subAccountRepository;

    public SubAccount insert(SubAccount subAccount){
        subAccount = subAccountRepository.save(subAccount);


    }
}
