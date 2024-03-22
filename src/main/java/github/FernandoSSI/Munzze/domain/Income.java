package github.FernandoSSI.Munzze.domain;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "income")
public class Income extends Transaction{
    public Income() {
    }

    public Income(String id, String accountId, String subAccountId, String categoryId, Date date, String description, Double amount) {
       super(id, accountId, subAccountId, categoryId, date, description, amount);
    }

}
