package github.FernandoSSI.Munzze.repositories;

import github.FernandoSSI.Munzze.domain.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountRepository extends MongoRepository<Account, String> {
}
