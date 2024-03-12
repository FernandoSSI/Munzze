package github.FernandoSSI.Munzze.controllers;

import github.FernandoSSI.Munzze.domain.SubAccount;
import github.FernandoSSI.Munzze.services.SubAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/subaccounts")
@CrossOrigin(origins = "*")
public class SubAccountController {

    @Autowired
    private SubAccountService subAccountService;

    @PostMapping
    public ResponseEntity<SubAccount> insert(@RequestBody SubAccount subAccount){
        subAccount = subAccountService.insert(subAccount);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(subAccount.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubAccount> findById(@PathVariable String id) {
        SubAccount subAccount = subAccountService.findById(id);
        return ResponseEntity.ok().body(subAccount);
    }

    @GetMapping("/search-all-by-account-id")
    public ResponseEntity<Page<SubAccount>> getAll(
            @RequestParam() String accountId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<SubAccount> subAccount = subAccountService.getAllByAccount(accountId, pageable);

        return ResponseEntity.ok().body(subAccount);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubAccount> update(@PathVariable String id, @RequestBody SubAccount subAccount){
        subAccount.setId(id);
        subAccountService.update(subAccount);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id){
        subAccountService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
