package github.FernandoSSI.Munzze.services;

import github.FernandoSSI.Munzze.domain.ExpenseCategory;
import github.FernandoSSI.Munzze.repositories.ExpenseCategoryRepository;
import github.FernandoSSI.Munzze.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExpenseCategoryService {

    @Autowired
    private ExpenseCategoryRepository expenseCategoryRepository;

    public ExpenseCategory insert(ExpenseCategory expenseCategory) {
        expenseCategory = expenseCategoryRepository.save(expenseCategory);

        return expenseCategory;
    }

    public ExpenseCategory findById(String id) {
        Optional<ExpenseCategory> earning = expenseCategoryRepository.findById(id);
        return earning.orElseThrow(() -> new ObjectNotFoundException("Object not found"));
    }

    public Page<ExpenseCategory> getAllByAccount(String accountId, Pageable pageable) {
        return expenseCategoryRepository.getAllByAccount(accountId, pageable);
    }

    public ExpenseCategory update(ExpenseCategory newExpenseCategory) {
        ExpenseCategory expenseCategory = findById(newExpenseCategory.getId());

        expenseCategory.setTitle(newExpenseCategory.getTitle());
        expenseCategory.setDescription(newExpenseCategory.getDescription());
        expenseCategory.setIcon(newExpenseCategory.getIcon());

        return expenseCategoryRepository.save(expenseCategory);
    }

    public void delete(String id) {
        ExpenseCategory expenseCategory = findById(id);
        expenseCategoryRepository.deleteById(id);
    }

}
