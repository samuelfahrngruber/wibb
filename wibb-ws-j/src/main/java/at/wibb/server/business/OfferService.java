package at.wibb.server.business;

import static at.wibb.server.business.TransformUtil.transformOffer;
import static at.wibb.server.shared.Preconditions.checkNotNull;

import at.wibb.server.shared.ReportType;
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

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Business logic service for managing offers and reporting invalid offers.
 */
@Component
public class OfferService {

    private static final SimpleDateFormat DAY_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");

    @Autowired
    private BeerRepository beerRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private OfferRepository offerRepository;

    /**
     * Stores a new offer.
     *
     * @param offer The offer to be stored;
     */
    public void addOffer(@NonNull Offer offer) {
        checkNotNull(offer);
        OfferTemplate offerTemplate = transformOffer(offer);

        if (!beerRepository.exists(createExample(offerTemplate.getBeer()))) {
            throw new UnknownEntityException("The beer " + offerTemplate.getBeer().getName() + " does not exist!");
        }
        if (!storeRepository.exists(createExample(offerTemplate.getStore()))) {
            throw new UnknownEntityException("The store " + offerTemplate.getStore().getName() + " does not exist!");
        }

        offerTemplate.setId(calculateOfferId(offer));
        offerRepository.insert(offerTemplate);
    }

    /**
     * Gets all offers that are currently active.
     * Outdated offers and auto-expired offers will not be contained.
     *
     * @return Currently active offers.
     */
    @NonNull
    public List<Offer> getOffers() {
        return offerRepository.findAll()
                .stream()
                .map(TransformUtil::transformOffer)
                .collect(Collectors.toList());
    }

    /**
     * This will report an offer that is under suspect to be invalid or fake.
     * The offer will be distinguished based on its following properties:
     * <li>beer
     * <li>store
     * <li>price
     * <li>start date
     * <li>end date
     *
     * @param offer The offer to be reported.
     * @param reportType Why this offer was reported.
     * @param message The message why this offer is suspected to be invalid.
     * @return An identifier of the report for later tracking.
     */
    @NonNull
    public String reportOffer(Offer offer, ReportType reportType, String message) {
        // TODO: this should be changed to actually report offers
        String offerId = calculateOfferId(offer);
        if (!offerRepository.existsById(offerId)) {
            throw new UnknownEntityException("The reported offer with id " + offerId + "does not exist!");
        }
        offerRepository.deleteById(offerId);
        return createReportId(offerId);
    }

    private static <T> Example<T> createExample(T probe) {
        return Example.of(probe, UntypedExampleMatcher.matching());
    }

    private static String calculateOfferId(Offer offer) {
        String startDate = offer.getStartDate() == null ? null : DAY_DATE_FORMAT.format(offer.getStartDate());
        String endDate = offer.getEndDate() == null ? null : DAY_DATE_FORMAT.format(offer.getEndDate());
        int hashId = Objects.hash(
                offer.getBeer(),
                offer.getStore(),
                offer.getPrice(),
                startDate,
                endDate
        );
        return Integer.toUnsignedString(hashId, 16);
    }

    private static String createReportId(String offerId) {
        return "r-" + offerId;
    }
}
