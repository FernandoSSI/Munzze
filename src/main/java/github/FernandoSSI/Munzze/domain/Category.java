package github.FernandoSSI.Munzze.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "category")
public class Category implements Serializable {

    @Id
    private String Id;
    private String AccountId;
    private String title;
    private String description;
    private String icon;
    private Double totalBalance;
    private Double totalIncomes;
    private Double totalExpenses;

    public Category(String id, String accountId, String title, String description, String icon, Double totalBalance, Double totalIncomes, Double totalExpenses) {
        Id = id;
        AccountId = accountId;
        this.title = title;
        this.description = description;
        this.icon = icon;
        this.totalBalance = totalBalance;
        this.totalIncomes = totalIncomes;
        this.totalExpenses = totalExpenses;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getAccountId() {
        return AccountId;
    }

    public void setAccountId(String accountId) {
        AccountId = accountId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Double getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(Double totalBalance) {
        this.totalBalance = totalBalance;
    }

    public Double getTotalIncomes() {
        return totalIncomes;
    }

    public void setTotalIncomes(Double totalIncomes) {
        this.totalIncomes = totalIncomes;
    }

    public Double getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(Double totalExpenses) {
        this.totalExpenses = totalExpenses;
    }
}
