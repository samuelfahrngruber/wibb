package at.wibb.server.rest.dto;

import java.util.Date;

public class OfferDto {

    private StoreDto store;
    private BeerDto beer;
    private Double price;
    private Date startDate;
    private Date endDate;

    public StoreDto getStore() {
        return store;
    }

    public void setStore(StoreDto store) {
        this.store = store;
    }

    public BeerDto getBeer() {
        return beer;
    }

    public void setBeer(BeerDto beer) {
        this.beer = beer;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
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
}
