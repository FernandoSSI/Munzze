package github.FernandoSSI.Munzze.domain;

import github.FernandoSSI.Munzze.domain.Dto.UserDto;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Objects;

@Document( collection = "accounts")
public class Account implements Serializable {

    @Id
    private String id;
    private String userId;
    private Double totalBalance;
    private Double totalEarnings;
    private Double totalExpenses;

    public Account() {
    }

    public Account(String id,  String userId, Double totalBalance, Double totalEarnings, Double totalExpenses) {
        id = id;
        this.totalBalance = totalBalance;
        this.totalEarnings = totalEarnings;
        this.totalExpenses = totalExpenses;
        this.userId= userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        id = id;
    }

    public Double getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(Double totalBalance) {
        this.totalBalance = totalBalance;
    }

    public Double getTotalEarnings() {
        return totalEarnings;
    }

    public void setTotalEarnings(Double totalEarnings) {
        this.totalEarnings = totalEarnings;
    }

    public Double getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(Double totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
