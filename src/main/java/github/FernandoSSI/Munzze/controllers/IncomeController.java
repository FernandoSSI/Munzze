package github.FernandoSSI.Munzze.controllers;

import github.FernandoSSI.Munzze.domain.Income;
import github.FernandoSSI.Munzze.services.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@RestController
@RequestMapping(value = "/incomes")
@CrossOrigin(origins = "*")
public class IncomeController {

    @Autowired
    private IncomeService incomeService;

    @PostMapping
    public ResponseEntity<Income> insert(@RequestBody Income income) {
        income = incomeService.insert(income);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(income.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping
    public ResponseEntity<Page<Income>> getAll(
            @RequestParam() String accountId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Income> earnings = incomeService.getAllByAccount(accountId, pageable);

        return ResponseEntity.ok().body(earnings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Income> findById(@PathVariable String id) {
        Income income = incomeService.findById(id);
        return ResponseEntity.ok().body(income);
    }

    @GetMapping("/search-by-period")
    public ResponseEntity<Page<Income>> findByPeriod(
            @RequestParam() String accountId,
            @RequestParam(defaultValue = "0") String subAccountId,
            @RequestParam() @DateTimeFormat(pattern = "dd-MM-yyyy") String startDate,
            @RequestParam() @DateTimeFormat(pattern = "dd-MM-yyyy") String endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Date parseStartDate;
        Date parseEndDate;
        try {
            parseStartDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(startDate + " 00:00:00");
            parseEndDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(endDate + " 23:59:59");
        } catch (ParseException e) {
            return ResponseEntity.badRequest().build();
        }

        Page<Income> earnings;
        Pageable pageable = PageRequest.of(page, size);
        if (Objects.equals(subAccountId, "0")) {
            earnings = incomeService.getByPeriodAndAccount(accountId, parseStartDate, parseEndDate, pageable);
        } else {
            earnings = incomeService.getByPeriodAndSubAccount(subAccountId, parseStartDate, parseEndDate, pageable);
        }


        return ResponseEntity.ok().body(earnings);
    }

    @GetMapping("/search-by-year")
    public ResponseEntity<Page<Income>> findByYear(
            @RequestParam() String accountId,
            @RequestParam(defaultValue = "0") String subAccountId,
            @RequestParam() int year,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<Income> earnings;
        Pageable pageable = PageRequest.of(page, size);
        if (Objects.equals(subAccountId, "0")) {
            earnings = incomeService.getByYearAndAccount(accountId, year, pageable);
        } else {
            earnings = incomeService.getByYearAndSubAccount(subAccountId, year, pageable);
        }


        return ResponseEntity.ok().body(earnings);
    }

    @GetMapping("/search-by-month")
    public ResponseEntity<Page<Income>> findByMonth(
            @RequestParam() String accountId,
            @RequestParam(defaultValue = "0") String subAccountId,
            @RequestParam() int month,
            @RequestParam() int year,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Income> earnings;
        if(Objects.equals(subAccountId, "0")){
            earnings = incomeService.getByMonthAndAccount(accountId, year, month, pageable);
        } else {
            earnings = incomeService.getByMonthAndSubAccount(subAccountId, year, month, pageable);
        }

        return ResponseEntity.ok().body(earnings);
    }

    @GetMapping("/search-by-date")
    public ResponseEntity<Page<Income>> findByDate(
            @RequestParam() String accountId,
            @RequestParam(defaultValue = "0") String subAccountId,
            @RequestParam() @DateTimeFormat(pattern = "dd-MM-yyyy") Date date,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Income> earnings;
        if (Objects.equals(subAccountId, "0")){
            earnings = incomeService.getByDateAndAccount(accountId, date, pageable);
        } else {
            earnings = incomeService.getByDateAndSubAccount(subAccountId, date, pageable);
        }

        return ResponseEntity.ok().body(earnings);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Income> update(@PathVariable String id, @RequestBody Income income) {
        income.setId(id);
        incomeService.update(income);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        incomeService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
