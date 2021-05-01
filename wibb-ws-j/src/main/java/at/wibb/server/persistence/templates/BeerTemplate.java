package at.wibb.server.persistence.templates;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "beers")
public class BeerTemplate {

    private String name;
    private String icon;
    private Metadata meta;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Metadata getMeta() {
        return meta;
    }

    public void setMeta(Metadata meta) {
        this.meta = meta;
    }

}
