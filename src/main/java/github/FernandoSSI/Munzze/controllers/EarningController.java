package github.FernandoSSI.Munzze.controllers;

import github.FernandoSSI.Munzze.domain.Earning;
import github.FernandoSSI.Munzze.services.EarningService;
import github.FernandoSSI.Munzze.services.util.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/earnings")
@CrossOrigin(origins = "*")
public class EarningController {

    @Autowired
    private EarningService earningService;

    @PostMapping
    public ResponseEntity<Earning> insert(@RequestBody Earning earning){
        earning = earningService.insert(earning);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(earning.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping
    public ResponseEntity<Page<Earning>> getAll(
            @RequestParam() String accountId,
            @RequestParam(defaultValue= "0") int page,
            @RequestParam(defaultValue= "10") int size){

        Pageable pageable = PageRequest.of(page, size);
        Page<Earning> earnings = earningService.getAllByAccount(accountId, pageable);

        return ResponseEntity.ok().body(earnings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Earning> findById(@PathVariable String id){
        Earning earning = earningService.findById(id);
        return ResponseEntity.ok().body(earning);
    }

    
}
