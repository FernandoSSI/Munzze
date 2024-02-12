package github.FernandoSSI.Munzze.controllers;

import github.FernandoSSI.Munzze.domain.Account;
import github.FernandoSSI.Munzze.domain.User;
import github.FernandoSSI.Munzze.services.AccountService;
import github.FernandoSSI.Munzze.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/accounts")
@CrossOrigin(origins = "*")
public class AccountController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<Account> getById(@PathVariable String id){
        Account account = accountService.findById(id);

        return ResponseEntity.ok().body(account);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Account> update(@PathVariable String id, @RequestBody Account account){

        account.setId(id);
        account = accountService.update(account, id);

        return ResponseEntity.noContent().build();
    }

}
