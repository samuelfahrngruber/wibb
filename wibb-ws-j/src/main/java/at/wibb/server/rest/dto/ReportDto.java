package at.wibb.server.rest.dto;

import at.wibb.server.shared.ReportType;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ReportDto {

    private ReportType type;
    private String info;
    private OfferDto offer;
    @JsonProperty("_id")
    private String id;

    public ReportType getType() {
        return type;
    }

    public void setType(ReportType type) {
        this.type = type;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public OfferDto getOffer() {
        return offer;
    }

    public void setOffer(OfferDto offer) {
        this.offer = offer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
