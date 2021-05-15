package at.wibb.server.rest;

import at.wibb.server.shared.OfferFilter;
import at.wibb.server.shared.OfferType;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class OfferFilterSerializerTest {

    @Test
    void shouldMapPriceRange() {
        // given
        Map<String, String> queryParams = Map.of(
                "priceGte", "9",
                "priceLte", "16"
        );

        // when
        OfferFilter filter = OfferFilterSerializer.fromQueryString(queryParams);

        // then
        assertThat(filter.getMinPrice())
                .isEqualTo(9);
        assertThat(filter.getMaxPrice())
                .isEqualTo(16);
    }

    @Test
    void shouldMapOfferType() {
        // given
        Map<String, String> queryParams = Map.of(
                "offerType", "BOTTLE_CRATE_20"
        );

        // when
        OfferFilter filter = OfferFilterSerializer.fromQueryString(queryParams);

        // then
        assertThat(filter.getOfferType())
                .isEqualTo(OfferType.BOTTLE_CRATE_20);
    }

    @Test
    void shouldMapValidAt() {
        // given
        Map<String, String> queryParams = Map.of(
                "validAtDate", "2020-01-01"
        );

        // when
        OfferFilter filter = OfferFilterSerializer.fromQueryString(queryParams);

        // then
        assertThat(filter.getValidAt().getTime())
                .isEqualTo(1577833200000L);
    }

    @Test
    void shouldMapOfferNameMatcher() {
        // given
        Map<String, String> queryParams = Map.of(
                "storeNameMatching", "(.*)spar,billa(.*)"
        );

        // when
        OfferFilter filter = OfferFilterSerializer.fromQueryString(queryParams);

        // then
        assertThat(filter.getStoreNamePatterns())
                .hasSize(2);
        assertThat(filter.getStoreNamePatterns().get(0).toString())
                .isEqualTo("(.*)spar");
        assertThat(filter.getStoreNamePatterns().get(1).toString())
                .isEqualTo("billa(.*)");

    }
}