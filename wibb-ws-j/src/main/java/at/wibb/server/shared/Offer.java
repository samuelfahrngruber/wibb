package at.wibb.server.shared;

import static at.wibb.server.shared.Preconditions.checkArgument;
import static at.wibb.server.shared.Preconditions.checkNotNull;

import java.util.Date;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public class Offer {
    
    private final Beer beer;
    private final Store store;
    private final double price;
    private final Date startDate;
    private final Date endDate;
    private final Date expires;
    private final OfferType type;

    public Offer(
        @NonNull Beer beer,
        @NonNull Store store,
        double price,
        @Nullable Date startDate,
        @Nullable Date endDate,
        @Nullable Date expires,
        @NonNull OfferType type
    ) {
        this.beer = checkNotNull(beer);
        this.store = checkNotNull(store);
        checkArgument(price >= 0);
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.expires = expires;
        this.type = checkNotNull(type);
    }

    @NonNull
    public Beer getBeer() {
        return beer;
    }

    @NonNull
    public Store getStore() {
        return store;
    }

    public double getPrice() {
        return price;
    }

    @Nullable
    public Date getStartDate() {
        return startDate;
    }
    
    @Nullable
    public Date getEndDate() {
        return endDate;
    }
    
    @Nullable
    public Date getExpires() {
        return expires;
    }

    @NonNull
    public OfferType getType() {
        return type;
    }
}
