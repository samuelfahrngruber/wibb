package at.wibb.server.rest.dto;

import at.wibb.server.shared.*;

import java.util.Date;

public class RestDtoMapper {

    private RestDtoMapper() {}

    public static BeerDto map(Beer beer) {
        BeerDto beerDto = new BeerDto();
        beerDto.setIcon(beer.getIcon().getImgUrl());
        beerDto.setName(beer.getName());
        MetaDto meta = new MetaDto();
        meta.setIconBg(beer.getIcon().getBackgroundColor());
        beerDto.setMeta(meta);
        return beerDto;
    }

    public static Beer map(BeerDto beerDto) {
        return new Beer(beerDto.getName(), new Icon(beerDto.getIcon(), beerDto.getMeta().getIconBg()));
    }

    public static StoreDto map(Store store) {
        StoreDto storeDto = new StoreDto();
        storeDto.setIcon(store.getIcon().getImgUrl());
        storeDto.setName(store.getName());
        MetaDto meta = new MetaDto();
        meta.setIconBg(store.getIcon().getBackgroundColor());
        storeDto.setMeta(meta);
        return storeDto;
    }

    public static Store map(StoreDto storeDto) {
        return new Store(storeDto.getName(), new Icon(storeDto.getIcon(), storeDto.getMeta().getIconBg()));
    }

    public static OfferDto map(Offer offer) {
        OfferDto offerDto = new OfferDto();
        offerDto.setBeer(map(offer.getBeer()));
        offerDto.setStore(map(offer.getStore()));
        offerDto.setPrice(offer.getPrice());
        offerDto.setStartDate(offer.getStartDate());
        offerDto.setEndDate(offer.getEndDate());
        return offerDto;
    }

    public static Offer map(OfferDto offerDto, OfferType offerType) {
        return new Offer(
                map(offerDto.getBeer()),
                map(offerDto.getStore()),
                offerDto.getPrice(),
                offerDto.getStartDate(),
                offerDto.getEndDate(),
                new Date(),
                offerType
        );
    }

}
