package github.FernandoSSI.Munzze.repositories;

import github.FernandoSSI.Munzze.domain.ExpenseCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ExpenseCategoryRepository extends MongoRepository<ExpenseCategory, String> {

    @Query("{ 'accountId': ?0 }")
    Page<ExpenseCategory> getAllByAccount(String accountId, Pageable pageable);

    @Query("{ 'accountId': ?0 }")
    List<ExpenseCategory> listAllByAccount(String accountId);


}


