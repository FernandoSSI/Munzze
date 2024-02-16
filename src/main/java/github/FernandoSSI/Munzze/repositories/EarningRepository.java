package github.FernandoSSI.Munzze.repositories;

import github.FernandoSSI.Munzze.domain.Earning;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface EarningRepository extends MongoRepository<Earning, String> {

    @Query("{ 'accountId': ?0 }")
    Page<Earning> getAllByAccount(String accountId, Pageable pageable);

    @Query("{'date': ?0, 'accountId': ?1}")
    Page<Earning> findByDateAndAccountId(Date date, String accountId, Pageable pageable);

    @Query("{'date': {$gte: ?0, $lte: ?1}, 'accountId': ?2}")
    Page<Earning> findByPeriod(Date startDate, Date endDate, String accountId, Pageable pageable);


}
