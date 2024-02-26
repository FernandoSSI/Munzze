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

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        Date startDate = cal.getTime();

        cal.add(Calendar.DAY_OF_MONTH, 2);
        Date endDate = cal.getTime();

        return earningRepository.findByDateAndAccountId(startDate, endDate, accountId, pageable);
    }

    public Page<Earning> getByYearAndAccount(String accountId, int year, Pageable pageable) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year - 1);
        cal.set(Calendar.MONTH, Calendar.DECEMBER);
        cal.set(Calendar.DAY_OF_MONTH, 31);
        Date startDate = cal.getTime();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, Calendar.DECEMBER);
        cal.set(Calendar.DAY_OF_MONTH, 31);
        Date endDate = cal.getTime();
        return earningRepository.findByPeriod(startDate, endDate, accountId, pageable);
    }

    public Page<Earning> getByMonthAndAccount(String accountId, int year, int month, Pageable pageable) {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 2);
        int lastDayOfMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, lastDayOfMonth);
        Date startDate = cal.getTime();

        cal.add(Calendar.MONTH, 1);
        if (month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
        Date endDate = cal.getTime();

        return earningRepository.findByPeriod(startDate, endDate, accountId, pageable);
    }

    public Page<Earning> getByPeriodAndAccount(String accountId, Date startDate, Date endDate, Pageable pageable) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.DATE, -1);
        Date adjustedStartDate = calendar.getTime();

        return earningRepository.findByPeriod(adjustedStartDate, endDate, accountId, pageable);
    }

    public Earning update(Earning newEarning) {
        Earning earning = findById(newEarning.getId());

        Account account = accountService.findById(earning.getAccountId());
        account.setTotalBalance(account.getTotalBalance()-earning.getAmount());
        account.setTotalEarnings(account.getTotalEarnings()-earning.getAmount());

        earning.setAmount(newEarning.getAmount());

        account.setTotalBalance(account.getTotalBalance()+newEarning.getAmount());
        account.setTotalEarnings(account.getTotalEarnings()+newEarning.getAmount());

        earning.setDate(newEarning.getDate());
        earning.setDescription(newEarning.getDescription());
        accountRepository.save(account);
        return earningRepository.save(earning);
    }

    public void delete(String id) {
        Earning earning = findById(id);
        if (earning != null) {
            Account account = accountService.findById(earning.getAccountId());
            account.setTotalEarnings(account.getTotalEarnings()-earning.getAmount());
            account.setTotalBalance(account.getTotalBalance()-earning.getAmount());
            accountRepository.save(account);
            earningRepository.deleteById(id);
        }
    }

}
