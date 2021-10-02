package at.wibb.server;

import org.json.JSONArray;
import org.json.JSONException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WibbE2ETest extends AbstractWibbServerTest {

    @Test
    void verifyOffersEmpty() throws JSONException {
        JSONArray offers = httpGetArray("/offers");
        assertThat(offers.length())
                .isEqualTo(0);
    }

    @Test
    void verifyNoOfferWhenNotYetActive() throws JSONException {
        httpPost("/offers", "{" +
                "    \"beer\"     : " + TestData.getRestJsonBeer(1) + ",\n" +
                "    \"store\"    : " + TestData.getRestJsonStore(1) + ",\n" +
                "    \"price\"    : 20,\n" +
                "    \"startDate\": \"2000-01-01\",\n" +
                "    \"endDate\"  : \"2100-01-01\",\n" +
                "    \"expires\"  : \"2100-01-01\",\n" +
                "    \"type\"     : \"BOTTLE_CRATE_20\"" +
                "}");
        JSONArray offers = httpGetArray("/offers?validAtDate=1999-05-05");
        assertThat(offers.length())
                .isEqualTo(0);
    }

    @Test
    void verifyCreateOffer() throws JSONException {
        httpPost("/offers", "{" +
                "    \"beer\"     : " + TestData.getRestJsonBeer(1) + ",\n" +
                "    \"store\"    : " + TestData.getRestJsonStore(1) + ",\n" +
                "    \"price\"    : 20,\n" +
                "    \"startDate\": \"2000-01-01\",\n" +
                "    \"endDate\"  : \"2100-01-01\",\n" +
                "    \"expires\"  : \"2100-01-01\",\n" +
                "    \"type\"     : \"BOTTLE_CRATE_20\"" +
                "}");
        JSONArray offers = httpGetArray("/offers");
        assertThat(offers.length())
                .isEqualTo(1);
    }

    @Test
    void verifyEndDateConsidered() throws JSONException {
        httpPost("/offers", "{" +
                "    \"beer\"     : " + TestData.getRestJsonBeer(1) + ",\n" +
                "    \"store\"    : " + TestData.getRestJsonStore(1) + ",\n" +
                "    \"price\"    : 20,\n" +
                "    \"startDate\": \"2000-01-01\",\n" +
                "    \"endDate\"  : \"2100-01-01\",\n" +
                "    \"type\"     : \"BOTTLE_CRATE_20\"" +
                "}");
        JSONArray offers = httpGetArray("/offers?validAtDate=2100-02-02");
        assertThat(offers.length())
                .isEqualTo(0);
    }

    @Test
    void verifyExpiresConsidered() throws JSONException {
        httpPost("/offers", "{" +
                "    \"beer\"     : " + TestData.getRestJsonBeer(1) + ",\n" +
                "    \"store\"    : " + TestData.getRestJsonStore(1) + ",\n" +
                "    \"price\"    : 20,\n" +
                "    \"startDate\": \"2000-01-01\",\n" +
                "    \"expires\"  : \"2100-01-01\",\n" +
                "    \"type\"     : \"BOTTLE_CRATE_20\"" +
                "}");
        JSONArray offers = httpGetArray("/offers?validAtDate=2100-02-02");
        assertThat(offers.length())
                .isEqualTo(0);
    }

}
