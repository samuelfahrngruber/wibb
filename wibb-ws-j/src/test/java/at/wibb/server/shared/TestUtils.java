package at.wibb.server.shared;

import java.util.Date;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestUtils {

    private static final String NAME = "name";
    private static final String IMG_URL = "/res/img.png";
    private static final String BACKGROUND_COLOR = "#FFFFFF";
    private static final double PRICE = 15;
    private static final OfferType OFFER_TYPE = OfferType.BOTTLE_CRATE_20;
    private static final Date START_DATE = new Date(1);
    private static final Date END_DATE = new Date(3);
    private static final Date EXPIRES_DATE = new Date(3);


    public static Icon mockIcon() {
        Icon icon = mock(Icon.class);
        when(icon.getImgUrl()).thenReturn(IMG_URL);
        when(icon.getBackgroundColor()).thenReturn(BACKGROUND_COLOR);
        return icon;
    }

    public static Beer mockBeer() {
        Beer beer = mock(Beer.class);
        when(beer.getName()).thenReturn(NAME);
        Icon icon = mockIcon();
        when(beer.getIcon()).thenReturn(icon);
        return beer;
    }

    public static Store mockStore() {
        Store store = mock(Store.class);
        when(store.getName()).thenReturn(NAME);
        Icon icon = mockIcon();
        when(store.getIcon()).thenReturn(icon);
        return store;
    }

    public static Offer mockOffer() {
        Offer offer = mock(Offer.class);
        Beer beer = mockBeer();
        Store store = mockStore();
        when(offer.getBeer()).thenReturn(beer);
        when(offer.getStore()).thenReturn(store);
        when(offer.getPrice()).thenReturn(PRICE);
        when(offer.getStartDate()).thenReturn(START_DATE);
        when(offer.getEndDate()).thenReturn(END_DATE);
        when(offer.getExpires()).thenReturn(EXPIRES_DATE);
        when(offer.getType()).thenReturn(OFFER_TYPE);
        return offer;
    }
}
