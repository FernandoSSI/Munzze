package github.FernandoSSI.Munzze.repositories;

import github.FernandoSSI.Munzze.domain.Income;
import github.FernandoSSI.Munzze.domain.SubAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface SubAccountRepository extends MongoRepository<SubAccount, String> {

    @Query("{ 'accountId': ?0 }")
    Page<SubAccount> getAllByAccount(String accountId, Pageable pageable);

    @Query("{ 'AccountId': ?0 }")
    List<SubAccount> listAllByAccount(String accountId);
}
