package github.FernandoSSI.Munzze.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Objects;

@Document(collection = "subAccounts")
public class SubAccount implements Serializable{

    @Id
    private String id;
    private String accountId;
    private String subAccountName;
    private String description;
    private String icon;
    private Double totalBalance;
    private Double totalIncomes;
    private Double totalExpenses;

    public SubAccount() {
    }

    public SubAccount(String id, String accountId, String subAccountName, String description, String icon, Double totalBalance, Double totalIncomes, Double totalExpenses) {
        this.id = id;
        this.accountId = accountId;
        this.subAccountName = subAccountName;
        this.description = description;
        this.icon = icon;
        this.totalBalance = totalBalance;
        this.totalIncomes = totalIncomes;
        this.totalExpenses = totalExpenses;
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

    public String getSubAccountName() {
        return subAccountName;
    }

    public void setSubAccountName(String subAccountName) {
        this.subAccountName = subAccountName;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubAccount that = (SubAccount) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
