package at.wibb.server.shared;

import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import static at.wibb.server.shared.Preconditions.checkArgument;
import static at.wibb.server.shared.Preconditions.checkNotNull;
import static java.util.Collections.emptyList;

public class OfferFilter implements Predicate<Offer> {

    private static final double MAX_FILTERABLE_PRICE = 100;

    private OfferType offerType;
    private List<Pattern> beerNamePatterns;
    private List<Pattern> storeNamePatterns;
    private double minPrice;
    private double maxPrice;
    private Date validAt;

    private OfferFilter() {
    }

    public static OfferFilter defaultFilter() {
        return new OfferFilter()
                .withOfferType(OfferType.BOTTLE_CRATE_20)
                .withBeerNamePatterns(emptyList())
                .withStoreNamePatterns(emptyList())
                .withPriceRange(0, MAX_FILTERABLE_PRICE)
                .withDesiredValidDate(new Date());
    }

    @Override
    public boolean test(Offer offer) {
        return offer.getType() == offerType
                && allPatternsMatch(offer.getBeer().getName(), beerNamePatterns)
                && allPatternsMatch(offer.getStore().getName(), storeNamePatterns)
                && offer.getPrice() >= minPrice && offer.getPrice() <= maxPrice
                && offerIsValidAt(offer, validAt);
    }

    public OfferType getOfferType() {
        return offerType;
    }

    public List<Pattern> getBeerNamePatterns() {
        return beerNamePatterns;
    }

    public List<Pattern> getStoreNamePatterns() {
        return storeNamePatterns;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public Date getValidAt() {
        return validAt;
    }

    public OfferFilter withOfferType(OfferType offerType) {
        this.offerType = checkNotNull(offerType);
        return this;
    }

    public OfferFilter withBeerNamePatterns(List<Pattern> beerNamePatterns) {
        this.beerNamePatterns = checkNotNull(beerNamePatterns);
        return this;
    }

    public OfferFilter withStoreNamePatterns(List<Pattern> storeNamePatterns) {
        this.storeNamePatterns = checkNotNull(storeNamePatterns);
        return this;
    }

    public OfferFilter withMinPrice(double minPrice) {
        checkArgument(minPrice >= 0);
        checkArgument(minPrice <= maxPrice);
        this.minPrice = minPrice;
        return this;
    }

    public OfferFilter withMaxPrice(double maxPrice) {
        checkArgument(maxPrice <= MAX_FILTERABLE_PRICE);
        checkArgument(minPrice <= maxPrice);
        this.maxPrice = maxPrice;
        return this;
    }

    public OfferFilter withPriceRange(double minPrice, double maxPrice) {
        return this
                .withMinPrice(minPrice)
                .withMaxPrice(maxPrice);
    }

    public OfferFilter withDesiredValidDate(Date validAt) {
        this.validAt = validAt;
        return this;
    }

    private static boolean allPatternsMatch(String testString, List<Pattern> patterns) {
        boolean allMatch = true;
        for (Pattern pattern : patterns) {
            allMatch = allMatch && pattern.asPredicate().test(testString);
        }
        return allMatch;
    }

    private static boolean offerIsValidAt(Offer offer, Date validAt) {
        if (offer.getStartDate() != null && validAt.before(offer.getStartDate())) {
            return false;
        }
        if (offer.getEndDate() != null && validAt.after(offer.getEndDate())) {
            return false;
        }
        if (offer.getEndDate() == null && offer.getExpires() != null && validAt.after(offer.getExpires())) {
            return false;
        }
        return true;
    }

}
