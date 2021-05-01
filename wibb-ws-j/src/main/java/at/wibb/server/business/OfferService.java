package at.wibb.server.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import at.wibb.server.persistence.repositories.BeerRepository;
import at.wibb.server.persistence.repositories.OfferRepository;
import at.wibb.server.persistence.repositories.StoreRepository;
import at.wibb.server.persistence.templates.OfferTemplate;
import at.wibb.server.shared.Offer;

@Component
public class OfferService {

    @Autowired
    private BeerRepository beerRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private OfferRepository offerRepository;
    
    public void addOffer(Offer offer) {
        OfferTemplate offerTemplate = TransformUtil.transformOffer(offer);
        offerRepository.insert(offerTemplate);
    }

}
