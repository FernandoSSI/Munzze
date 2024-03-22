package github.FernandoSSI.Munzze.domain;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "expense")
public class Expense extends Transaction {

    public Expense() {
    }

    public Expense(String id, String accountId,String subAccountId, String categoryId, Date date, String description, Double amount) {
        super(id, accountId, subAccountId, categoryId, date, description, amount);
    }

}
