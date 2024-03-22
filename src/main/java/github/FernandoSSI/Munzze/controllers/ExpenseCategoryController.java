package github.FernandoSSI.Munzze.controllers;

import github.FernandoSSI.Munzze.domain.ExpenseCategory;
import github.FernandoSSI.Munzze.services.ExpenseCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/expensecategories")
@CrossOrigin(origins = "*")
public class ExpenseCategoryController {

    @Autowired
    private ExpenseCategoryService expenseCategoryService;

    @PostMapping
    public ResponseEntity<ExpenseCategory> insert(@RequestBody ExpenseCategory expenseCategory){
        expenseCategory = expenseCategoryService.insert(expenseCategory);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(expenseCategory.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseCategory> findById(@PathVariable String id) {
        ExpenseCategory expenseCategory = expenseCategoryService.findById(id);
        return ResponseEntity.ok().body(expenseCategory);
    }

    @GetMapping("/search-all-by-account-id")
    public ResponseEntity<Page<ExpenseCategory>> getAll(
            @RequestParam() String accountId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<ExpenseCategory> expenseCategory = expenseCategoryService.getAllByAccount(accountId, pageable);

        return ResponseEntity.ok().body(expenseCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpenseCategory> update(@PathVariable String id, @RequestBody ExpenseCategory expenseCategory){
        expenseCategory.setId(id);
        expenseCategoryService.update(expenseCategory);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id){
        expenseCategoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
