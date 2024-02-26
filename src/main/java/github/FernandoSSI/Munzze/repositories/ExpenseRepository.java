package github.FernandoSSI.Munzze.repositories;

import github.FernandoSSI.Munzze.domain.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface ExpenseRepository extends MongoRepository<Expense, String> {

    @Query("{ 'accountId': ?0 }")
    Page<Expense> getAllByAccount(String accountId, Pageable pageable);

    @Query("{'date': {$gte: ?0, $lt: ?1}, 'accountId': ?2}")
    Page<Expense> findByDateAndAccountId(Date startDate, Date endDate, String accountId, Pageable pageable);

    @Query("{'date' : { $gte: ?0, $lte: ?1 }, 'accountId': ?2}")
    Page<Expense> findByPeriod(Date startDate, Date endDate, String accountId, Pageable pageable);


}
