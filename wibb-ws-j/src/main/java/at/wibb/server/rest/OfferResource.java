package at.wibb.server.rest;

import at.wibb.server.business.OfferService;
import at.wibb.server.rest.dto.OfferDto;
import at.wibb.server.rest.dto.ReportDto;
import at.wibb.server.rest.dto.RestDtoMapper;
import at.wibb.server.shared.OfferFilter;
import at.wibb.server.shared.OfferType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static at.wibb.server.rest.dto.RestDtoMapper.map;

@RestController
public class OfferResource {

    @Autowired
    private OfferService offerService;

    @GetMapping("/api/offers")
    public List<OfferDto> getOffers(@RequestParam Map<String,String> allRequestParams) {
        OfferFilter offerFilter = OfferFilterSerializer.fromQueryString(allRequestParams);
        return offerService.getOffers(offerFilter)
                .stream()
                .map(RestDtoMapper::map)
                .collect(Collectors.toList());
    }

    @PostMapping("/api/offers")
    public OfferDto postOffer(@RequestBody OfferDto offer, @RequestParam(defaultValue = "BOTTLE_CRATE_20") OfferType type) {
        offerService.addOffer(map(offer, type));
        return offer;
    }

    @PostMapping("/api/reports")
    public ReportDto postOffer(@RequestBody ReportDto report, @RequestParam(defaultValue = "BOTTLE_CRATE_20") OfferType offerType) {
        String id = offerService.reportOffer(map(report.getOffer(), offerType), report.getType(), report.getInfo());
        report.setId(id);
        return report;
    }
    
}
