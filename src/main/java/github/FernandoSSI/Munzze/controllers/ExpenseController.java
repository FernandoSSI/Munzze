package github.FernandoSSI.Munzze.controllers;

import github.FernandoSSI.Munzze.domain.Expense;
import github.FernandoSSI.Munzze.services.ExpenseService;
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
@RequestMapping(value = "/expenses")
@CrossOrigin(origins = "*")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<Expense> insert(@RequestBody Expense expense) {
        expense = expenseService.insert(expense);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(expense.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping
    public ResponseEntity<Page<Expense>> getAll(
            @RequestParam() String accountId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Expense> expenses = expenseService.getAllByAccount(accountId, pageable);

        return ResponseEntity.ok().body(expenses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Expense> findById(@PathVariable String id) {
        Expense expense = expenseService.findById(id);
        return ResponseEntity.ok().body(expense);
    }
    @GetMapping("/search-by-period")
    public ResponseEntity<Page<Expense>> findByPeriod(
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

        Page<Expense> earnings;
        Pageable pageable = PageRequest.of(page, size);
        if (Objects.equals(subAccountId, "0")) {
            earnings = expenseService.getByPeriodAndAccount(accountId, parseStartDate, parseEndDate, pageable);
        } else {
            earnings = expenseService.getByPeriodAndSubAccount(subAccountId, parseStartDate, parseEndDate, pageable);
        }


        return ResponseEntity.ok().body(earnings);
    }

    @GetMapping("/search-by-year")
    public ResponseEntity<Page<Expense>> findByYear(
            @RequestParam() String accountId,
            @RequestParam(defaultValue = "0") String subAccountId,
            @RequestParam() int year,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<Expense> earnings;
        Pageable pageable = PageRequest.of(page, size);
        if (Objects.equals(subAccountId, "0")) {
            earnings = expenseService.getByYearAndAccount(accountId, year, pageable);
        } else {
            earnings = expenseService.getByYearAndSubAccount(subAccountId, year, pageable);
        }


        return ResponseEntity.ok().body(earnings);
    }

    @GetMapping("/search-by-month")
    public ResponseEntity<Page<Expense>> findByMonth(
            @RequestParam() String accountId,
            @RequestParam(defaultValue = "0") String subAccountId,
            @RequestParam() int month,
            @RequestParam() int year,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Expense> earnings;
        if(Objects.equals(subAccountId, "0")){
            earnings = expenseService.getByMonthAndAccount(accountId, year, month, pageable);
        } else {
            earnings = expenseService.getByMonthAndSubAccount(subAccountId, year, month, pageable);
        }

        return ResponseEntity.ok().body(earnings);
    }

    @GetMapping("/search-by-date")
    public ResponseEntity<Page<Expense>> findByDate(
            @RequestParam() String accountId,
            @RequestParam(defaultValue = "0") String subAccountId,
            @RequestParam() @DateTimeFormat(pattern = "dd-MM-yyyy") Date date,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Expense> earnings;
        if (Objects.equals(subAccountId, "0")){
            earnings = expenseService.getByDateAndAccount(accountId, date, pageable);
        } else {
            earnings = expenseService.getByDateAndSubAccount(subAccountId, date, pageable);
        }

        return ResponseEntity.ok().body(earnings);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Expense> update(@PathVariable String id, @RequestBody Expense expense){
        expense.setId(id);
        expenseService.update(expense);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id){
        expenseService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
