package github.FernandoSSI.Munzze.services;

import github.FernandoSSI.Munzze.domain.*;
import github.FernandoSSI.Munzze.domain.Expense;
import github.FernandoSSI.Munzze.repositories.AccountRepository;
import github.FernandoSSI.Munzze.repositories.ExpenseRepository;
import github.FernandoSSI.Munzze.repositories.SubAccountRepository;
import github.FernandoSSI.Munzze.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountService accountService;

    @Autowired
    private SubAccountRepository subAccountRepository;
    @Autowired
    private SubAccountService subAccountService;


    public Expense insert(Expense expense) {
        expense = expenseRepository.save(expense);

        Account account = accountService.findById(expense.getAccountId());
        account.setTotalExpenses(account.getTotalExpenses() + expense.getAmount());
        account.setTotalBalance(account.getTotalBalance() - expense.getAmount());
        accountRepository.save(account);

        if (expense.getSubAccountId() != null) {
            SubAccount subAccount = subAccountService.findById(expense.getSubAccountId());
            subAccount.setTotalExpenses(subAccount.getTotalExpenses() + expense.getAmount());
            subAccount.setTotalBalance(subAccount.getTotalBalance() - expense.getAmount());
            subAccountRepository.save(subAccount);
        }
        // fazer a verificação da category depois
        return expense;
    }

    public Expense findById(String id) {
        Optional<Expense> expense = expenseRepository.findById(id);
        return expense.orElseThrow(() -> new ObjectNotFoundException("Object not found"));
    }

    public Page<Expense> getAllByAccount(String accountId, Pageable pageable) {
        return expenseRepository.getAllByAccount(accountId, pageable);
    }

    public Page<Expense> getAllBySubAccount(String subAccountId, Pageable pageable){
        return expenseRepository.getAllBySubAccount(subAccountId, pageable);
    }

    public Page<Expense> getByDateAndAccount(String accountId, Date date, Pageable pageable) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        Date startDate = cal.getTime();

        cal.add(Calendar.DAY_OF_MONTH, 2);
        Date endDate = cal.getTime();

        return expenseRepository.findByDateAndAccountId(startDate, endDate, accountId, pageable);
    }

    public Page<Expense> getByYearAndAccount(String accountId, int year, Pageable pageable) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year - 1);
        cal.set(Calendar.MONTH, Calendar.DECEMBER);
        cal.set(Calendar.DAY_OF_MONTH, 31);
        Date startDate = cal.getTime();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, Calendar.DECEMBER);
        cal.set(Calendar.DAY_OF_MONTH, 31);
        Date endDate = cal.getTime();
        return expenseRepository.findByPeriod(startDate, endDate, accountId, pageable);
    }

    public Page<Expense> getByMonthAndAccount(String accountId, int year, int month, Pageable pageable) {
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

        return expenseRepository.findByPeriod(startDate, endDate, accountId, pageable);
    }

    public Page<Expense> getByPeriodAndAccount(String accountId, Date startDate, Date endDate, Pageable pageable) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.DATE, -1);
        Date adjustedStartDate = calendar.getTime();

        return expenseRepository.findByPeriod(adjustedStartDate, endDate, accountId, pageable);
    }

    public Expense update(Expense newExpense) {
        Expense expense = findById(newExpense.getId());

        if (!Objects.equals(expense.getAmount(), newExpense.getAmount())) {
            Account account = accountService.findById(expense.getAccountId());
            account.setTotalBalance(account.getTotalBalance() + expense.getAmount());
            account.setTotalExpenses(account.getTotalExpenses() - expense.getAmount());

            account.setTotalBalance(account.getTotalBalance() - newExpense.getAmount());
            account.setTotalExpenses(account.getTotalExpenses() + newExpense.getAmount());
            accountRepository.save(account);
        }

        if (!Objects.equals(expense.getSubAccountId(), newExpense.getSubAccountId())) {
            if (expense.getSubAccountId()!=null) {
                SubAccount subAccount = subAccountService.findById(expense.getSubAccountId());
                subAccount.setTotalBalance(subAccount.getTotalBalance() + expense.getAmount());
                subAccount.setTotalExpenses(subAccount.getTotalExpenses() - expense.getAmount());
                subAccountRepository.save(subAccount);
            }
            if (newExpense.getSubAccountId()!= null) {
                SubAccount newSubAccount = subAccountService.findById(newExpense.getSubAccountId());
                newSubAccount.setTotalBalance(newSubAccount.getTotalBalance() - newExpense.getAmount());
                newSubAccount.setTotalExpenses(newSubAccount.getTotalExpenses() + newExpense.getAmount());
                subAccountRepository.save(newSubAccount);
            }
        }

        expense.setAmount(newExpense.getAmount());
        expense.setDate(newExpense.getDate());
        expense.setDescription(newExpense.getDescription());

        return expenseRepository.save(expense);
    }

    public void delete(String id) {
        Expense expense = findById(id);
        if (expense != null) {
            Account account = accountService.findById(expense.getAccountId());
            account.setTotalExpenses(account.getTotalExpenses() - expense.getAmount());
            account.setTotalBalance(account.getTotalBalance() + expense.getAmount());
            accountRepository.save(account);

            if (expense.getSubAccountId() != null) {
                SubAccount subAccount = subAccountService.findById(expense.getSubAccountId());
                subAccount.setTotalExpenses(subAccount.getTotalExpenses() - expense.getAmount());
                subAccount.setTotalBalance(subAccount.getTotalBalance() + expense.getAmount());
                subAccountRepository.save(subAccount);
            }
            expenseRepository.deleteById(id);
        }

    }

    public void deleteAllBySubAccount(String subAccountId){
        List<Expense> expenses = expenseRepository.listAllBySubAccount(subAccountId);
        double total = 0;
        for(Expense expense : expenses){
            expenseRepository.deleteById(expense.getId());
            total += expense.getAmount();
        }

        Account account = accountService.findById(subAccountService.findById(subAccountId).getAccountId());
        account.setTotalExpenses(account.getTotalExpenses() - total);
        account.setTotalBalance(account.getTotalBalance() + total);
        accountRepository.save(account);
    }

}
