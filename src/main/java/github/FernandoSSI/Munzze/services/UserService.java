package github.FernandoSSI.Munzze.services;

import github.FernandoSSI.Munzze.domain.*;
import github.FernandoSSI.Munzze.domain.Dto.UserDto;
import github.FernandoSSI.Munzze.repositories.*;
import github.FernandoSSI.Munzze.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private SubAccountService subAccountService;
    @Autowired
    private SubAccountRepository subAccountRepository;
    @Autowired
    private IncomeRepository incomeRepository;
    @Autowired
    private ExpenseRepository expenseRepository;

    public User findById(String id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new ObjectNotFoundException("Object not found"));
    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User insert(User user){
        User existingEmail = findByEmail(user.getEmail());

        if(existingEmail==null){
            user = userRepository.save(user);
            Account account = accountRepository.save(new Account(null, user.getId(), 0.0,0.0,0.0));
            user.setAccountId(account.getId());
            user = userRepository.save(user);
            return user;
        } else {
            return null;
        }
    }

    public User update(User newUser){
        User user = findById(newUser.getId());
        user.setName(newUser.getName());
        user.setEmail(newUser.getEmail());
        user.setPassword(newUser.getPassword());

        return userRepository.save(user);
    }

    public void delete(String id){
        User user = findById(id);
        String accountId = user.getAccountId();

        //excluir as subaccounts e os incomes e expenses associados a elas
        List<SubAccount> subAccounts = subAccountRepository.listAllByAccount(accountId);
        for(SubAccount subAccount: subAccounts){
            subAccountService.delete(subAccount.getId());
        }

        //excluir os incomes e expenses que não estão associados a nenhuma subAccount
        List<Income> incomes = incomeRepository.listAllByAccount(accountId);
        for(Income income: incomes){
            incomeRepository.deleteById(income.getId());
        }
        List<Expense> expenses = expenseRepository.listAllByAccount(accountId);
        for(Expense expense: expenses){
            incomeRepository.deleteById(expense.getId());
        }

        accountRepository.deleteById(accountId);
        userRepository.deleteById(user.getId());
    }
}
