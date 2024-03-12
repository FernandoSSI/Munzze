package github.FernandoSSI.Munzze.services;

import github.FernandoSSI.Munzze.domain.Account;
import github.FernandoSSI.Munzze.domain.Expense;
import github.FernandoSSI.Munzze.domain.Income;
import github.FernandoSSI.Munzze.domain.SubAccount;
import github.FernandoSSI.Munzze.repositories.AccountRepository;
import github.FernandoSSI.Munzze.repositories.ExpenseRepository;
import github.FernandoSSI.Munzze.repositories.IncomeRepository;
import github.FernandoSSI.Munzze.repositories.SubAccountRepository;
import github.FernandoSSI.Munzze.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SubAccountService {

    @Autowired
    private SubAccountRepository subAccountRepository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private IncomeRepository incomeRepository;
    @Autowired
    private ExpenseRepository expenseRepository;


    public SubAccount insert(SubAccount subAccount) {
        subAccount = subAccountRepository.save(subAccount);

        if (subAccount.getTotalBalance() != 0) {
            Account account = accountService.findById(subAccount.getAccountId());
            account.setTotalBalance(account.getTotalBalance() + subAccount.getTotalBalance());
            account.setTotalIncomes(account.getTotalIncomes() + subAccount.getTotalIncomes());
            account.setTotalExpenses(account.getTotalExpenses() + subAccount.getTotalExpenses());
            accountRepository.save(account);
        }

        return subAccount;
    }

    public SubAccount findById(String id) {
        Optional<SubAccount> earning = subAccountRepository.findById(id);
        return earning.orElseThrow(() -> new ObjectNotFoundException("Object not found"));
    }

    public Page<SubAccount> getAllByAccount(String accountId, Pageable pageable) {
        return subAccountRepository.getAllByAccount(accountId, pageable);
    }

    public SubAccount update(SubAccount newSubAccount) {
        SubAccount subAccount = findById(newSubAccount.getId());

        if (!Objects.equals(subAccount.getTotalBalance(), newSubAccount.getTotalBalance()) ||
                !Objects.equals(subAccount.getTotalIncomes(), newSubAccount.getTotalIncomes()) ||
                !Objects.equals(subAccount.getTotalExpenses(), newSubAccount.getTotalExpenses())){

            Account account = accountService.findById(subAccount.getAccountId());
            account.setTotalBalance(account.getTotalBalance() - subAccount.getTotalBalance());
            account.setTotalIncomes(account.getTotalIncomes() - subAccount.getTotalIncomes());
            account.setTotalExpenses(account.getTotalExpenses() - subAccount.getTotalExpenses());

            account.setTotalBalance(account.getTotalBalance() + newSubAccount.getTotalBalance());
            account.setTotalIncomes(account.getTotalIncomes() + newSubAccount.getTotalIncomes());
            account.setTotalExpenses(account.getTotalExpenses() + newSubAccount.getTotalExpenses());
            accountRepository.save(account);

            subAccount.setTotalBalance(newSubAccount.getTotalBalance());
            subAccount.setTotalIncomes(newSubAccount.getTotalIncomes());
            subAccount.setTotalExpenses(newSubAccount.getTotalExpenses());
        }

        subAccount.setSubAccountName(newSubAccount.getSubAccountName());
        subAccount.setDescription(newSubAccount.getDescription());
        subAccount.setIcon(newSubAccount.getIcon());

        return subAccountRepository.save(subAccount);
    }

    public void delete(String id) {
        SubAccount subAccount = findById(id);

        if (subAccount.getTotalBalance() != 0) {
            Account account = accountService.findById(subAccount.getAccountId());
            account.setTotalBalance(account.getTotalBalance() - subAccount.getTotalBalance());
            account.setTotalIncomes(account.getTotalIncomes() - subAccount.getTotalBalance());
            account.setTotalExpenses(account.getTotalIncomes() - subAccount.getTotalExpenses());
            accountRepository.save(account);
        }




        List<Income> incomes = incomeRepository.listAllBySubAccount(id);
        double totalIncomes = 0;
        for(Income income : incomes){
            incomeRepository.deleteById(income.getId());
            totalIncomes += income.getAmount();
        }
        List<Expense> expenses = expenseRepository.listAllBySubAccount(id);
        double totalExpenses = 0;
        for(Expense expense : expenses){
            expenseRepository.deleteById(expense.getId());
            totalExpenses += expense.getAmount();
        }

        Account account = accountService.findById(findById(id).getAccountId());
        account.setTotalIncomes(account.getTotalIncomes() - totalIncomes);
        account.setTotalBalance(account.getTotalBalance() - totalIncomes);
        account.setTotalExpenses(account.getTotalExpenses() - totalExpenses);
        account.setTotalBalance(account.getTotalBalance() + totalExpenses);
        accountRepository.save(account);


        subAccountRepository.deleteById(id);
    }

}
