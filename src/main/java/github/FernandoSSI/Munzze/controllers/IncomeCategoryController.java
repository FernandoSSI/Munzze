package github.FernandoSSI.Munzze.controllers;

import github.FernandoSSI.Munzze.domain.IncomeCategory;
import github.FernandoSSI.Munzze.services.IncomeCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/incomecategories")
@CrossOrigin(origins = "*")
public class IncomeCategoryController {

    @Autowired
    private IncomeCategoryService incomeCategoryService;

    @PostMapping
    public ResponseEntity<IncomeCategory> insert(@RequestBody IncomeCategory incomeCategory){
        incomeCategory = incomeCategoryService.insert(incomeCategory);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(incomeCategory.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<IncomeCategory> findById(@PathVariable String id) {
        IncomeCategory incomeCategory = incomeCategoryService.findById(id);
        return ResponseEntity.ok().body(incomeCategory);
    }

    @GetMapping("/search-all-by-account-id")
    public ResponseEntity<Page<IncomeCategory>> getAll(
            @RequestParam() String accountId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<IncomeCategory> incomeCategory = incomeCategoryService.getAllByAccount(accountId, pageable);

        return ResponseEntity.ok().body(incomeCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<IncomeCategory> update(@PathVariable String id, @RequestBody IncomeCategory incomeCategory){
        incomeCategory.setId(id);
        incomeCategoryService.update(incomeCategory);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id){
        incomeCategoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
