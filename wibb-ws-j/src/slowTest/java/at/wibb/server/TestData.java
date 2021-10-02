package at.wibb.server;

import at.wibb.server.persistence.templates.BeerTemplate;
import at.wibb.server.persistence.templates.Metadata;
import at.wibb.server.persistence.templates.StoreTemplate;

public class TestData {

    private static final String TEMPLATE_BEER_NAME = "beer%s";
    private static final String TEMPLATE_STORE_NAME = "store%s";
    private static final String TEMPLATE_ICON = "icon%s";
    private static final String TEMPLATE_COLOR = "#ffffff";

    static String getRestJsonStore(int seed) {
        return createRestJsonStore(
                String.format(TEMPLATE_STORE_NAME, seed),
                String.format(TEMPLATE_ICON, seed),
                TEMPLATE_COLOR
        );
    }

    static StoreTemplate getDbTemplateStore(int seed) {
        StoreTemplate storeTemplate = new StoreTemplate();
        storeTemplate.setName(String.format(TEMPLATE_STORE_NAME, seed));
        storeTemplate.setIcon(String.format(TEMPLATE_ICON, seed));
        Metadata metadata = new Metadata();
        metadata.setIconBg(TEMPLATE_COLOR);
        storeTemplate.setMeta(metadata);
        return storeTemplate;
    }

    static String getRestJsonBeer(int seed) {
        return createRestJsonBeer(
                String.format(TEMPLATE_BEER_NAME, seed),
                String.format(TEMPLATE_ICON, seed),
                TEMPLATE_COLOR
        );
    }

    static BeerTemplate getDbTemplateBeer(int seed) {
        BeerTemplate beerTemplate = new BeerTemplate();
        beerTemplate.setName(String.format(TEMPLATE_BEER_NAME, seed));
        beerTemplate.setIcon( String.format(TEMPLATE_ICON, seed));
        Metadata metadata = new Metadata();
        metadata.setIconBg(TEMPLATE_COLOR);
        beerTemplate.setMeta(metadata);
        return beerTemplate;
    }

    private static String createRestJsonStore(String name, String icon, String backgroundColor) {
        return String.format("{\"name\":\"%s\",\"icon\":\"%s\",\"meta\":{\"iconBg\":\"%s\"}}", name, icon, backgroundColor);
    }

    private static String createRestJsonBeer(String name, String icon, String backgroundColor) {
        return String.format("{\"name\":\"%s\",\"icon\":\"%s\",\"meta\":{\"iconBg\":\"%s\"}}", name, icon, backgroundColor);
    }

}
