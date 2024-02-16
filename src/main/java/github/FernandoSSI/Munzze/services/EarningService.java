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

import java.util.Calendar;
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


    public Earning insert(Earning earning) {
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

    public Page<Earning> getAllByAccount(String accountId, Pageable pageable) {
        return earningRepository.getAllByAccount(accountId, pageable);
    }

    public Page<Earning> getByDateAndAccount(String accountId, Date date, Pageable pageable) {
        return earningRepository.findByDateAndAccountId(date, accountId, pageable);
    }

    public Page<Earning> getByYearAndAccount(String accountId, int year, Pageable pageable) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date startDate = cal.getTime();
        cal.set(Calendar.MONTH, Calendar.DECEMBER);
        cal.set(Calendar.DAY_OF_MONTH, 31);
        Date endDate = cal.getTime();
        return earningRepository.findByPeriod(startDate, endDate, accountId, pageable);
    }

    public Page<Earning> getByMonthAndAccount(String accountId, int year, int month, Pageable pageable) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date startDate = cal.getTime();
        cal.add(Calendar.MONTH, 1);
        Date endDate = cal.getTime();
        return earningRepository.findByPeriod(startDate, endDate, accountId, pageable);
    }

    public Page<Earning> getByPeriodAndAccount(String accountId, Date startDate, Date endDate, Pageable pageable) {
        return earningRepository.findByPeriod(startDate, endDate, accountId, pageable);
    }

    public Earning update(Earning newEarning) {
        Earning earning = findById(newEarning.getId());
        earning.setAmount(newEarning.getAmount());
        earning.setDate(newEarning.getDate());
        earning.setDescription(newEarning.getDescription());
        return earningRepository.save(earning);
    }

    public void delete(String id) {
        Earning earning = findById(id);
        if (earning != null) {
            earningRepository.deleteById(id);
        }
    }

}
