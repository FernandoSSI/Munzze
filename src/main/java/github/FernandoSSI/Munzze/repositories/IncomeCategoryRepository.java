package github.FernandoSSI.Munzze.repositories;

import github.FernandoSSI.Munzze.domain.Category;
import github.FernandoSSI.Munzze.domain.IncomeCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface IncomeCategoryRepository extends MongoRepository<IncomeCategory, String> {

    @Query("{ 'accountId': ?0 }")
    Page<IncomeCategory> getAllByAccount(String accountId, Pageable pageable);

    @Query("{ 'accountId': ?0 }")
    List<IncomeCategory> listAllByAccount(String accountId);


}


