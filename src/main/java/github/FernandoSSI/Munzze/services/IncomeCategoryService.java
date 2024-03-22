package github.FernandoSSI.Munzze.services;

import github.FernandoSSI.Munzze.domain.Category;
import github.FernandoSSI.Munzze.domain.IncomeCategory;
import github.FernandoSSI.Munzze.repositories.AccountRepository;
import github.FernandoSSI.Munzze.repositories.IncomeCategoryRepository;
import github.FernandoSSI.Munzze.repositories.ExpenseRepository;
import github.FernandoSSI.Munzze.repositories.IncomeRepository;
import github.FernandoSSI.Munzze.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IncomeCategoryService {

    @Autowired
    private IncomeCategoryRepository incomeCategoryRepository;

    public IncomeCategory insert(IncomeCategory incomeCategory) {
        incomeCategory = incomeCategoryRepository.save(incomeCategory);

        return incomeCategory;
    }

    public IncomeCategory findById(String id) {
        Optional<IncomeCategory> earning = incomeCategoryRepository.findById(id);
        return earning.orElseThrow(() -> new ObjectNotFoundException("Object not found"));
    }

    public Page<IncomeCategory> getAllByAccount(String accountId, Pageable pageable) {
        return incomeCategoryRepository.getAllByAccount(accountId, pageable);
    }

    public IncomeCategory update(IncomeCategory newIncomeCategory) {
        IncomeCategory incomeCategory = findById(newIncomeCategory.getId());

        incomeCategory.setTitle(newIncomeCategory.getTitle());
        incomeCategory.setDescription(newIncomeCategory.getDescription());
        incomeCategory.setIcon(newIncomeCategory.getIcon());

        return incomeCategoryRepository.save(incomeCategory);
    }

    public void delete(String id) {
        IncomeCategory incomeCategory = findById(id);
        incomeCategoryRepository.deleteById(id);
    }

}
