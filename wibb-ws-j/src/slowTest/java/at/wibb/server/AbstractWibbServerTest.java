package at.wibb.server;

import at.wibb.server.persistence.repositories.BeerRepository;
import at.wibb.server.persistence.repositories.OfferRepository;
import at.wibb.server.persistence.repositories.StoreRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractWibbServerTest {

    protected static final Logger LOGGER = LoggerFactory.getLogger(WibbE2ETest.class);

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private BeerRepository beerRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private OfferRepository offerRepository;

    @BeforeEach
    void setupTestData() {
        beerRepository.deleteAll();
        beerRepository.insert(TestData.getDbTemplateBeer(1));
        beerRepository.insert(TestData.getDbTemplateBeer(2));
        beerRepository.insert(TestData.getDbTemplateBeer(3));
        storeRepository.deleteAll();
        storeRepository.insert(TestData.getDbTemplateStore(1));
        storeRepository.insert(TestData.getDbTemplateStore(2));
        storeRepository.insert(TestData.getDbTemplateStore(3));
        offerRepository.deleteAll();
    }

    private String getApiUri(String subPath) {
        return "http://localhost:" + port + "/api" + subPath;
    }

    protected void httpPost(String subPath, String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(json, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(getApiUri(subPath), httpEntity, String.class);
        LOGGER.info("POSTed JSON to \"{}\":\n{}", subPath, json);
    }

    protected JSONObject httpGet(String subPath) throws JSONException {
        ResponseEntity<String> response = restTemplate.getForEntity(getApiUri(subPath), String.class);
        String jsonResponse = response.getBody();
        LOGGER.info("Received JSON for \"{}\":\n{}", subPath, jsonResponse);
        return new JSONObject(jsonResponse);
    }

    protected JSONArray httpGetArray(String subPath) throws JSONException {
        ResponseEntity<String> response = restTemplate.getForEntity(getApiUri(subPath), String.class);
        String jsonResponse = response.getBody();
        LOGGER.info("Received JSON for \"{}\":\n{}", subPath, jsonResponse);
        return new JSONArray(jsonResponse);
    }
}
