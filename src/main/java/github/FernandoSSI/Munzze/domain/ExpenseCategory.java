package github.FernandoSSI.Munzze.domain;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "expenseCategory")
public class ExpenseCategory extends Category{

    private Double totalExpenses;

    public ExpenseCategory() {

    }

    public ExpenseCategory(String id, String accountId, String title, String description, String icon, Double totalExpenses) {
        super(id, accountId, title, description, icon);
        this.totalExpenses = totalExpenses;
    }

    public Double getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(Double totalExpenses) {
        this.totalExpenses = totalExpenses;
    }
}
