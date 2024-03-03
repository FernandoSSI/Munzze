package github.FernandoSSI.Munzze.repositories;

import github.FernandoSSI.Munzze.domain.SubAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface SubAccountRepository extends MongoRepository<SubAccount, String> {

    @Query("{ 'accountId': ?0 }")
    Page<SubAccount> getAllByAccount(String accountId, Pageable pageable);

    @Query("{ 'subAccountName': { $regex: ?0, $options: 'i'} }")
    Page<SubAccount> getBySubAccountName(String accountId, Pageable pageable);
}
