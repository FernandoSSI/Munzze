package github.FernandoSSI.Munzze.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Document(collection = "expense")
public class Expense implements Serializable {

    @Id
    private String id;
    private String accountId;
    private String subAccountId;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date date;
    private String description;
    private Double amount;

    public Expense() {
    }

    public Expense(String id, String accountId,String subAccountId, Date date, String description, Double amount) {
        this.id = id;
        this.accountId = accountId;
        this.subAccountId = subAccountId;
        this.date = date;
        this.description = description;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getSubAccountId() {
        return subAccountId;
    }

    public void setSubAccountId(String subAccountId) {
        this.subAccountId = subAccountId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Expense earnings = (Expense) o;
        return Objects.equals(id, earnings.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
