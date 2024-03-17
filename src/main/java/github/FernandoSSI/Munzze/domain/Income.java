package github.FernandoSSI.Munzze.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Document(collection = "income")
public class Income extends Transaction{
    public Income() {
    }

    public Income(String id, String accountId, String subAccountId, String categoryId, Date date, String description, Double amount) {
       super(id, accountId, subAccountId, categoryId, date, description, amount);
    }

}
