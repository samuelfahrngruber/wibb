package at.wibb.server.business;

import at.wibb.server.persistence.templates.BeerTemplate;
import at.wibb.server.persistence.templates.Metadata;
import at.wibb.server.persistence.templates.OfferTemplate;
import at.wibb.server.persistence.templates.StoreTemplate;
import at.wibb.server.shared.Beer;
import at.wibb.server.shared.Icon;
import at.wibb.server.shared.Offer;
import at.wibb.server.shared.Store;

class TransformUtil {
    
    private TransformUtil() {}

    static Beer transformBeer(BeerTemplate beer) {
        return new Beer(beer.getName(), new Icon(beer.getIcon(), beer.getMeta().getIconBg()));
    }

    static BeerTemplate transformBeer(Beer beer) {
        BeerTemplate beerTemplate = new BeerTemplate();
        beerTemplate.setName(beer.getName());
        beerTemplate.setIcon(beer.getIcon().getImgUrl());

        Metadata meta = new Metadata();
        meta.setIconBg(beer.getIcon().getBackgroundColor());
        beerTemplate.setMeta(meta);

        return beerTemplate;
    }

    static Store transformStore(StoreTemplate store) {
        return new Store(store.getName(), new Icon(store.getIcon(), store.getMeta().getIconBg()));
    }

    static StoreTemplate transformStore(Store store) {
        StoreTemplate storeTemplate = new StoreTemplate();
        storeTemplate.setName(store.getName());
        storeTemplate.setIcon(store.getIcon().getImgUrl());

        Metadata meta = new Metadata();
        meta.setIconBg(store.getIcon().getBackgroundColor());
        storeTemplate.setMeta(meta);

        return storeTemplate;
    }
    
    static OfferTemplate transformOffer(Offer offer) {
        OfferTemplate offerTemplate = new OfferTemplate();
        offerTemplate.setBeer(transformBeer(offer.getBeer()));
        offerTemplate.setStore(transformStore(offer.getStore()));
        offerTemplate.setPrice(offer.getPrice());
        offerTemplate.setStartDate(offer.getStartDate());
        offerTemplate.setEndDate(offer.getEndDate());
        offerTemplate.setExpires(offer.getExpires());
        offerTemplate.setType(offer.getType());
        return offerTemplate;
    }
}
