package github.FernandoSSI.Munzze.services;

import github.FernandoSSI.Munzze.domain.Account;
import github.FernandoSSI.Munzze.domain.Earning;
import github.FernandoSSI.Munzze.repositories.AccountRepository;
import github.FernandoSSI.Munzze.repositories.EarningRepository;
import github.FernandoSSI.Munzze.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class EarningService {

    @Autowired
    private EarningRepository earningRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountService accountService;


    public Earning insert(Earning earning){
        earning = earningRepository.save(earning);
        Account account = accountService.findById(earning.getAccountId());
        account.setTotalEarnings(account.getTotalEarnings() + earning.getAmount());
        account.setTotalBalance(account.getTotalBalance() + earning.getAmount());
        accountRepository.save(account);
        return earning;
    }

    public Earning findById(String id) {
        Optional<Earning> earning = earningRepository.findById(id);
        return earning.orElseThrow(() -> new ObjectNotFoundException("Object not found"));
    }

    public Page<Earning> getAllByAccount(String accountId, Pageable pageable){
        return earningRepository.getAllByAccount(accountId, pageable);
    }

    public Page<Earning> getByDateAndAccount(String accountId, Date date, Pageable pageable){
        return earningRepository.findByDateAndAccountId(date, accountId, pageable );
    }

    //public Page<Earning> getByYearAndAccount(){}
    //public Page<Earning> getByMonthAndAccount(){}
    //public Page<Earning> getByWeekAndAccount(){}
    //public Page<Earning> getByPeriodAndAccount(){}


}
