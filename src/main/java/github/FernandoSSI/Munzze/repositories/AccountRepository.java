package github.FernandoSSI.Munzze.repositories;

import github.FernandoSSI.Munzze.domain.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends MongoRepository<Account, String> {
}
