package github.FernandoSSI.Munzze.services;

import github.FernandoSSI.Munzze.domain.Account;
import github.FernandoSSI.Munzze.domain.Income;
import github.FernandoSSI.Munzze.repositories.AccountRepository;
import github.FernandoSSI.Munzze.repositories.IncomeRepository;
import github.FernandoSSI.Munzze.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
public class IncomeService {

    @Autowired
    private IncomeRepository incomeRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountService accountService;


    public Income insert(Income income) {
        income = incomeRepository.save(income);
        Account account = accountService.findById(income.getAccountId());
        account.setTotalEarnings(account.getTotalEarnings() + income.getAmount());
        account.setTotalBalance(account.getTotalBalance() + income.getAmount());
        accountRepository.save(account);
        return income;
    }

    public Income findById(String id) {
        Optional<Income> earning = incomeRepository.findById(id);
        return earning.orElseThrow(() -> new ObjectNotFoundException("Object not found"));
    }

    public Page<Income> getAllByAccount(String accountId, Pageable pageable) {
        return incomeRepository.getAllByAccount(accountId, pageable);
    }

    public Page<Income> getByDateAndAccount(String accountId, Date date, Pageable pageable) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        Date startDate = cal.getTime();

        cal.add(Calendar.DAY_OF_MONTH, 2);
        Date endDate = cal.getTime();

        return incomeRepository.findByDateAndAccountId(startDate, endDate, accountId, pageable);
    }

    public Page<Income> getByYearAndAccount(String accountId, int year, Pageable pageable) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year - 1);
        cal.set(Calendar.MONTH, Calendar.DECEMBER);
        cal.set(Calendar.DAY_OF_MONTH, 31);
        Date startDate = cal.getTime();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, Calendar.DECEMBER);
        cal.set(Calendar.DAY_OF_MONTH, 31);
        Date endDate = cal.getTime();
        return incomeRepository.findByPeriod(startDate, endDate, accountId, pageable);
    }

    public Page<Income> getByMonthAndAccount(String accountId, int year, int month, Pageable pageable) {
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

        return incomeRepository.findByPeriod(startDate, endDate, accountId, pageable);
    }

    public Page<Income> getByPeriodAndAccount(String accountId, Date startDate, Date endDate, Pageable pageable) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.DATE, -1);
        Date adjustedStartDate = calendar.getTime();

        return incomeRepository.findByPeriod(adjustedStartDate, endDate, accountId, pageable);
    }

    public Income update(Income newIncome) {
        Income income = findById(newIncome.getId());

        Account account = accountService.findById(income.getAccountId());
        account.setTotalBalance(account.getTotalBalance()- income.getAmount());
        account.setTotalEarnings(account.getTotalEarnings()- income.getAmount());

        income.setAmount(newIncome.getAmount());

        account.setTotalBalance(account.getTotalBalance()+ newIncome.getAmount());
        account.setTotalEarnings(account.getTotalEarnings()+ newIncome.getAmount());

        income.setDate(newIncome.getDate());
        income.setDescription(newIncome.getDescription());
        accountRepository.save(account);
        return incomeRepository.save(income);
    }

    public void delete(String id) {
        Income income = findById(id);
        if (income != null) {
            Account account = accountService.findById(income.getAccountId());
            account.setTotalEarnings(account.getTotalEarnings()- income.getAmount());
            account.setTotalBalance(account.getTotalBalance()- income.getAmount());
            accountRepository.save(account);
            incomeRepository.deleteById(id);
        }
    }

}
