package github.FernandoSSI.Munzze.repositories;

import github.FernandoSSI.Munzze.domain.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ExpenseRepository extends MongoRepository<Expense, String> {

    @Query("{ 'accountId': ?0 }")
    Page<Expense> getAllByAccount(String accountId, Pageable pageable);

    @Query("{ 'accountId': ?0 }")
    List<Expense> listAllByAccount(String accountId);

    @Query("{'date': {$gte: ?0, $lt: ?1}, 'accountId': ?2}")
    Page<Expense> findByDateAndAccountId(Date startDate, Date endDate, String accountId, Pageable pageable);

    @Query("{'date' : { $gte: ?0, $lte: ?1 }, 'accountId': ?2}")
    Page<Expense> findByPeriod(Date startDate, Date endDate, String accountId, Pageable pageable);

    @Query("{ 'SubAccountId': ?0 }")
    Page<Expense> getAllBySubAccount(String accountId, Pageable pageable);

    @Query("{ 'SubAccountId': ?0 }")
    List<Expense> listAllBySubAccount(String accountId);

    @Query("{'date': {$gte: ?0, $lt: ?1}, 'SubAccountId': ?2}")
    Page<Expense> findByDateAndSubAccountId(Date startDate, Date endDate, String subAccountId, Pageable pageable);

    @Query("{'date' : { $gte: ?0, $lte: ?1 }, 'SubAccountId': ?2}")
    Page<Expense> findByPeriodAndSubAccount(Date startDate, Date endDate, String subAccountId, Pageable pageable);


}
