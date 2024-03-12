package github.FernandoSSI.Munzze.repositories;

import github.FernandoSSI.Munzze.domain.Income;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface IncomeRepository extends MongoRepository<Income, String> {

    @Query("{ 'accountId': ?0 }")
    Page<Income> getAllByAccount(String accountId, Pageable pageable);

    @Query("{'date': {$gte: ?0, $lt: ?1}, 'accountId': ?2}")
    Page<Income> findByDateAndAccountId(Date startDate, Date endDate, String accountId, Pageable pageable);

    @Query("{'date' : { $gte: ?0, $lte: ?1 }, 'accountId': ?2}")
    Page<Income> findByPeriod(Date startDate, Date endDate, String accountId, Pageable pageable);

    @Query("{ 'SubAccountId': ?0 }")
    Page<Income> getAllBySubAccount(String accountId, Pageable pageable);

    @Query("{ 'SubAccountId': ?0 }")
    List<Income> listAllBySubAccount(String accountId);

    @Query("{'date': {$gte: ?0, $lt: ?1}, 'SubAccountId': ?2}")
    Page<Income> findByDateAndSubAccountId(Date startDate, Date endDate, String accountId, Pageable pageable);

    @Query("{'date' : { $gte: ?0, $lte: ?1 }, 'SubAccountId': ?2}")
    Page<Income> findByPeriodAndSubAccount(Date startDate, Date endDate, String accountId, Pageable pageable);


}
