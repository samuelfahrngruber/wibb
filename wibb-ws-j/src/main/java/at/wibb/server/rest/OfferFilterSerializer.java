package at.wibb.server.rest;

import at.wibb.server.shared.OfferFilter;
import at.wibb.server.shared.OfferType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class OfferFilterSerializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(OfferFilterSerializer.class);

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private static final String FILTER_QUERY_PARAM_OFFER_TYPE = "offerType";
    private static final String FILTER_QUERY_PARAM_BEER_NAME = "beerNameMatching";
    private static final String FILTER_QUERY_PARAM_STORE_NAME = "storeNameMatching";
    private static final String FILTER_QUERY_PARAM_PRICE_GTE = "priceGte";
    private static final String FILTER_QUERY_PARAM_PRICE_LTE = "priceLte";
    private static final String FILTER_QUERY_PARAM_VALID_AT_DATE = "validAtDate";

    public static OfferFilter fromQueryString(Map<String, String> queryParameters) {
        OfferFilter offerFilter = OfferFilter.defaultFilter();
        String queryParamValue;

        queryParamValue = queryParameters.get(FILTER_QUERY_PARAM_OFFER_TYPE);
        if (queryParamValue != null) {
            offerFilter.withOfferType(OfferType.valueOf(queryParamValue));
        }

        queryParamValue = queryParameters.get(FILTER_QUERY_PARAM_BEER_NAME);
        if (queryParamValue != null) {
            offerFilter.withBeerNamePatterns(parsePatternsQueryParam(queryParamValue));
        }

        queryParamValue = queryParameters.get(FILTER_QUERY_PARAM_STORE_NAME);
        if (queryParamValue != null) {
            offerFilter.withStoreNamePatterns(parsePatternsQueryParam(queryParamValue));
        }

        queryParamValue = queryParameters.get(FILTER_QUERY_PARAM_PRICE_GTE);
        if (queryParamValue != null) {
           offerFilter.withMinPrice(Double.parseDouble(queryParamValue));
        }

        queryParamValue = queryParameters.get(FILTER_QUERY_PARAM_PRICE_LTE);
        if (queryParamValue != null) {
            offerFilter.withMaxPrice(Double.parseDouble(queryParamValue));
        }

        queryParamValue = queryParameters.get(FILTER_QUERY_PARAM_VALID_AT_DATE);
        if (queryParamValue != null) {
            try {
                offerFilter.withDesiredValidDate(DATE_FORMAT.parse(queryParamValue));
            } catch (ParseException e) {
                LOGGER.info("Could not parse date " + queryParamValue + ". Not adding date filter...");
            }
        }

        return offerFilter;
    }

    private static List<Pattern> parsePatternsQueryParam(String patternList) {
        List<Pattern> patterns = new ArrayList<>();
        for (String pattern : patternList.split(",")) {
            patterns.add(Pattern.compile(pattern));
        }
        return patterns;
    }
}
