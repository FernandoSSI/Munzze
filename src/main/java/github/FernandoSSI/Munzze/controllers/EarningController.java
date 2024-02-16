package github.FernandoSSI.Munzze.controllers;

import github.FernandoSSI.Munzze.domain.Earning;
import github.FernandoSSI.Munzze.services.EarningService;
import github.FernandoSSI.Munzze.services.util.URL;
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

@RestController
@RequestMapping(value = "/earnings")
@CrossOrigin(origins = "*")
public class EarningController {

    @Autowired
    private EarningService earningService;

    @PostMapping
    public ResponseEntity<Earning> insert(@RequestBody Earning earning) {
        earning = earningService.insert(earning);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(earning.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping
    public ResponseEntity<Page<Earning>> getAll(
            @RequestParam() String accountId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Earning> earnings = earningService.getAllByAccount(accountId, pageable);

        return ResponseEntity.ok().body(earnings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Earning> findById(@PathVariable String id) {
        Earning earning = earningService.findById(id);
        return ResponseEntity.ok().body(earning);
    }

    @GetMapping("/search-by-period")
    public ResponseEntity<Page<Earning>> findByPeriod(
            @RequestParam() String accountId,
            @RequestParam() @DateTimeFormat(pattern = "dd-MM-yyyy") String startDate,
            @RequestParam() @DateTimeFormat(pattern = "dd-MM-yyyy") String endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Date parseStartDate;
        Date parseEndDate;
        try{
            parseEndDate = new SimpleDateFormat("dd-MM-yyyy").parse(endDate);
            parseStartDate = new SimpleDateFormat("dd-MM-yyyy").parse(startDate);
        } catch (ParseException e) {
            return ResponseEntity.badRequest().build();
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<Earning> earnings = earningService.getByPeriodAndAccount(accountId, parseStartDate, parseEndDate, pageable);

        return ResponseEntity.ok().body(earnings);
    }

    @GetMapping("/search-by-year")
    public ResponseEntity<Page<Earning>> findByYear(
            @RequestParam() String accountId,
            @RequestParam() int year,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Earning> earnings = earningService.getByYearAndAccount(accountId, year, pageable);

        return ResponseEntity.ok().body(earnings);
    }

    @GetMapping("/search-by-month")
    public ResponseEntity<Page<Earning>> findByMonth(
            @RequestParam() String accountId,
            @RequestParam() int month,
            @RequestParam() int year,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Earning> earnings = earningService.getByMonthAndAccount(accountId, year, month, pageable);

        return ResponseEntity.ok().body(earnings);
    }

    @GetMapping("/search-by-date")
    public ResponseEntity<Page<Earning>> findByDate(
            @RequestParam() String accountId,
            @RequestParam() @DateTimeFormat(pattern = "dd-MM-yyyy") Date date,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Earning> earnings = earningService.getByDateAndAccount(accountId, date, pageable);

        return ResponseEntity.ok().body(earnings);
    }


}
