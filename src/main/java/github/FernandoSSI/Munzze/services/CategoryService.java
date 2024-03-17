package github.FernandoSSI.Munzze.services;

import github.FernandoSSI.Munzze.domain.Account;
import github.FernandoSSI.Munzze.domain.Expense;
import github.FernandoSSI.Munzze.domain.Income;
import github.FernandoSSI.Munzze.domain.Category;
import github.FernandoSSI.Munzze.repositories.AccountRepository;
import github.FernandoSSI.Munzze.repositories.CategoryRepository;
import github.FernandoSSI.Munzze.repositories.ExpenseRepository;
import github.FernandoSSI.Munzze.repositories.IncomeRepository;
import github.FernandoSSI.Munzze.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private IncomeRepository incomeRepository;
    @Autowired
    private ExpenseRepository expenseRepository;

    public Category insert(Category category) {
        category = categoryRepository.save(category);

        return category;
    }

    public Category findById(String id) {
        Optional<Category> earning = categoryRepository.findById(id);
        return earning.orElseThrow(() -> new ObjectNotFoundException("Object not found"));
    }

    public Page<Category> getAllByAccount(String accountId, Pageable pageable) {
        return categoryRepository.getAllByAccount(accountId, pageable);
    }

    public Category update(Category newCategory) {
        Category category = findById(newCategory.getId());

        category.setTitle(newCategory.getTitle());
        category.setDescription(newCategory.getDescription());
        category.setIcon(newCategory.getIcon());

        return categoryRepository.save(category);
    }

    public void delete(String id) {
        Category category = findById(id);
        categoryRepository.deleteById(id);
    }

}
