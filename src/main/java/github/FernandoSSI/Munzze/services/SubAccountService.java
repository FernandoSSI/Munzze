package github.FernandoSSI.Munzze.services;

import github.FernandoSSI.Munzze.domain.SubAccount;
import github.FernandoSSI.Munzze.repositories.SubAccountRepository;
import github.FernandoSSI.Munzze.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubAccountService {

    @Autowired
    private SubAccountRepository subAccountRepository;

    public SubAccount insert(SubAccount subAccount){
        return subAccountRepository.save(subAccount);
    }

    public SubAccount findById(String id) {
        Optional<SubAccount> earning = subAccountRepository.findById(id);
        return earning.orElseThrow(() -> new ObjectNotFoundException("Object not found"));
    }

    public Page<SubAccount> getAllByAccount(String accountId, Pageable pageable) {
        return subAccountRepository.getAllByAccount(accountId, pageable);
    }
}
