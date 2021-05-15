package at.wibb.server.shared;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Date;
import java.util.regex.Pattern;

import static at.wibb.server.shared.TestUtils.mockOffer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class OfferFilterTest {

    private Offer offerMock;

    @BeforeEach
    void setup() {
        offerMock = mockOffer();
    }

    @Test
    void testPriceRangeValid() {
        // given
        OfferFilter filter = defaultFilter()
                .withPriceRange(5, 7);
        when(offerMock.getPrice())
                .thenReturn(6.0);

        // when
        boolean matches = filter.test(offerMock);

        // then
        assertThat(matches)
                .isTrue();
    }

    @Test
    void testPriceInvalidLt() {
        // given
        OfferFilter filter = defaultFilter()
                .withPriceRange(5, 7);
        when(offerMock.getPrice())
                .thenReturn(4.0);

        // when
        boolean matches = filter.test(offerMock);

        // then
        assertThat(matches)
                .isFalse();
    }

    @Test
    void testPriceInvalidGt() {
        // given
        OfferFilter filter = defaultFilter()
                .withPriceRange(5, 7);
        when(offerMock.getPrice())
                .thenReturn(8.0);

        // when
        boolean matches = filter.test(offerMock);

        // then
        assertThat(matches)
                .isFalse();
    }

    @Test
    void testBeerNamePatternMatches() {
        // given
        OfferFilter filter = defaultFilter()
                .withBeerNamePatterns(Collections.singletonList(Pattern.compile("(.*)vil(.*)")));
        when(offerMock.getBeer().getName())
                .thenReturn("villacher");

        // when
        boolean matches = filter.test(offerMock);

        // then
        assertThat(matches)
                .isTrue();
    }

    @Test
    void testStoreNamePatternMatches() {
        // given
        OfferFilter filter = defaultFilter()
                .withStoreNamePatterns(Collections.singletonList(Pattern.compile("(.*)spar(.*)")));
        when(offerMock.getStore().getName())
                .thenReturn("interspar");

        // when
        boolean matches = filter.test(offerMock);

        // then
        assertThat(matches)
                .isTrue();
    }

    @Test
    void shouldMatchWhenEndDateNull() {
        // given
        when(offerMock.getStartDate()).thenReturn(new Date(1));
        when(offerMock.getEndDate()).thenReturn(null);
        when(offerMock.getExpires()).thenReturn(new Date(7));
        OfferFilter filter = defaultFilter()
                .withDesiredValidDate(new Date(5));

        // when
        boolean matches = filter.test(offerMock);

        // then
        assertThat(matches)
                .isTrue();
    }

    @Test
    void shouldNotMatchWhenBeforeStartDate() {
        // given
        when(offerMock.getStartDate()).thenReturn(new Date(5));
        when(offerMock.getEndDate()).thenReturn(null);
        when(offerMock.getExpires()).thenReturn(new Date(7));
        OfferFilter filter = defaultFilter()
                .withDesiredValidDate(new Date(3));

        // when
        boolean matches = filter.test(offerMock);

        // then
        assertThat(matches)
                .isFalse();
    }

    @Test
    void shouldMatchWhenBeforeEndDate() {
        // given
        when(offerMock.getStartDate()).thenReturn(new Date(5));
        when(offerMock.getEndDate()).thenReturn(new Date(9));
        when(offerMock.getExpires()).thenReturn(new Date(9));
        OfferFilter filter = defaultFilter()
                .withDesiredValidDate(new Date(8));

        // when
        boolean matches = filter.test(offerMock);

        // then
        assertThat(matches)
                .isTrue();
    }

    @Test
    void shouldNotMatchWhenAfterEndDate() {
        // given
        when(offerMock.getStartDate()).thenReturn(new Date(5));
        when(offerMock.getEndDate()).thenReturn(new Date(9));
        when(offerMock.getExpires()).thenReturn(new Date(9));
        OfferFilter filter = defaultFilter()
                .withDesiredValidDate(new Date(10));

        // when
        boolean matches = filter.test(offerMock);

        // then
        assertThat(matches)
                .isFalse();
    }

    @Test
    void shouldNotMatchWhenAfterAllDates() {
        // given
        when(offerMock.getStartDate()).thenReturn(new Date(5));
        when(offerMock.getEndDate()).thenReturn(new Date(6));
        when(offerMock.getExpires()).thenReturn(null);
        OfferFilter filter = defaultFilter()
                .withDesiredValidDate(new Date(10));

        // when
        boolean matches = filter.test(offerMock);

        // then
        assertThat(matches)
                .isFalse();
    }

    private static OfferFilter defaultFilter() {
        return OfferFilter.defaultFilter()
                .withDesiredValidDate(new Date(2));
    }

}