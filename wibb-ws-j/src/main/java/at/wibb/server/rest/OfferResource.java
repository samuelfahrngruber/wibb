package at.wibb.server.rest;

import at.wibb.server.rest.dto.RestDtoMapper;
import at.wibb.server.rest.dto.OfferDto;
import at.wibb.server.shared.OfferType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import at.wibb.server.business.OfferService;

import java.util.List;
import java.util.stream.Collectors;

import static at.wibb.server.rest.dto.RestDtoMapper.map;

@RestController
public class OfferResource {

    @Autowired
    private OfferService offerService;

    @GetMapping("/api/offers")
    public List<OfferDto> getOffers() {
        return offerService.getOffers()
                .stream()
                .map(RestDtoMapper::map)
                .collect(Collectors.toList());
    }

    @PostMapping("/api/offers")
    public OfferDto postOffer(@RequestBody OfferDto offer) {
        offerService.addOffer(map(offer, OfferType.BOTTLE_CRATE_20));
        return offer;
    }
    
}
