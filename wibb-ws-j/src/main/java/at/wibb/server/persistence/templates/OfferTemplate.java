package at.wibb.server.persistence.templates;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import at.wibb.server.shared.OfferType;

@Document(collection = "offers")
public class OfferTemplate {

    @Id
    private String id;
    private BeerTemplate beer;
    private StoreTemplate store;
    private double price;
    private Date startDate;
    private Date endDate;
    private Date expires;
    private OfferType type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BeerTemplate getBeer() {
        return beer;
    }

    public void setBeer(BeerTemplate beer) {
        this.beer = beer;
    }

    public StoreTemplate getStore() {
        return store;
    }

    public void setStore(StoreTemplate store) {
        this.store = store;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
        
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getExpires() {
        return expires;
    }

    public void setExpires(Date expires) {
        this.expires = expires;
    }

    public OfferType getType() {
        return type;
    }

    public void setType(OfferType type) {
        this.type = type;
    }

}
