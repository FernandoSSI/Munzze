package github.FernandoSSI.Munzze.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Objects;

@Document( collection = "accounts")
public class Account implements Serializable {

    @Id
    private String Id;
    private Double totalBalance;
    private Double totalEarnings;
    private Double totalExpenses;

    @DBRef
    private User user;

    public Account() {
    }

    public Account(String id, Double totalBalance, Double totalEarnings, Double totalExpenses, User user) {
        Id = id;
        this.totalBalance = totalBalance;
        this.totalEarnings = totalEarnings;
        this.totalExpenses = totalExpenses;
        this.user = user;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(Id, account.Id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id);
    }
}
