package at.wibb.server.business;

import at.wibb.server.persistence.templates.BeerTemplate;
import at.wibb.server.persistence.templates.StoreTemplate;
import at.wibb.server.shared.Beer;
import at.wibb.server.shared.Icon;
import at.wibb.server.shared.Store;

class TransformUtil {
    
    private TransformUtil() {}

    static Beer transformBeer(BeerTemplate beer) {
        return new Beer(beer.getName(), new Icon(beer.getIcon(), beer.getMeta().getIconBg()));
    }

    static Store transformStore(StoreTemplate store) {
        return new Store(store.getName(), new Icon(store.getIcon(), store.getMeta().getIconBg()));
    }
    
}
