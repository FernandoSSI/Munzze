package github.FernandoSSI.Munzze.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

public class Category implements Serializable {

    @Id
    private String id;
    private String accountId;
    private String title;
    private String description;
    private String icon;

    public Category() {
    }

    public Category(String id, String accountId, String title, String description, String icon) {
        this.id = id;
        this.accountId = accountId;
        this.title = title;
        this.description = description;
        this.icon = icon;
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

}
