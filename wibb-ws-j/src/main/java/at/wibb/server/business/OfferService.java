package at.wibb.server.business;

import static at.wibb.server.shared.Preconditions.checkNotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.mongodb.core.query.UntypedExampleMatcher;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import at.wibb.server.persistence.repositories.BeerRepository;
import at.wibb.server.persistence.repositories.OfferRepository;
import at.wibb.server.persistence.repositories.StoreRepository;
import at.wibb.server.persistence.templates.OfferTemplate;
import at.wibb.server.shared.Offer;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OfferService {

    @Autowired
    private BeerRepository beerRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private OfferRepository offerRepository;
    
    public void addOffer(@NonNull Offer offer) {
        checkNotNull(offer);
        OfferTemplate offerTemplate = TransformUtil.transformOffer(offer);

        if (!beerRepository.exists(createExample(offerTemplate.getBeer()))) {
            throw new UnknownEntityException("The beer " + offerTemplate.getBeer().getName() + " does not exist!");
        }
        if (!storeRepository.exists(createExample(offerTemplate.getStore()))) {
            throw new UnknownEntityException("The store " + offerTemplate.getStore().getName() + " does not exist!");
        }

        offerRepository.insert(offerTemplate);
    }

    public List<Offer> getOffers() {
        return offerRepository.findAll()
                .stream()
                .map(TransformUtil::transformOffer)
                .collect(Collectors.toList());
    }

    private static <T> Example<T> createExample(T probe) {
        return Example.of(probe, UntypedExampleMatcher.matching());
    }

}
