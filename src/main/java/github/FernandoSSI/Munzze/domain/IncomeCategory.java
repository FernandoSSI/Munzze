package github.FernandoSSI.Munzze.domain;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "incomeCategory")
public class IncomeCategory extends Category{

    private Double totalIncomes;

    public IncomeCategory() {

    }

    public IncomeCategory(String id, String accountId, String title, String description, String icon, Double totalIncomes) {
        super(id, accountId, title, description, icon);
        this.totalIncomes = totalIncomes;
    }

    public Double getTotalIncomes() {
        return totalIncomes;
    }

    public void setTotalIncomes(Double totalIncomes) {
        this.totalIncomes = totalIncomes;
    }
}
