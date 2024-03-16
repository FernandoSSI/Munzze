package github.FernandoSSI.Munzze.repositories;

import github.FernandoSSI.Munzze.domain.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface CategoryRepository extends MongoRepository<Category, String> {

    @Query("{ 'accountId': ?0 }")
    Page<Category> getAllByCategory(String accountId, Pageable pageable);

    @Query("{ 'accountId': ?0 }")
    List<Category> listAllByCategory(String accountId);


}
