package com.example.wibb.data;

import java.time.LocalDate;

public class Offer {
    private Store store;
    private Brand brand;
    private int price;
    private LocalDate start;
    private LocalDate end;

    public Offer() {

    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public boolean isValid(){
        return price >= 0 && store != null && brand != null && end != null;
    }
}
