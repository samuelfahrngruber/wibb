package at.wibb.server.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import at.wibb.server.persistence.BeerPersistence;
import at.wibb.server.shared.Beer;

@Component
public class BeerService {

    @Autowired
    private BeerPersistence beerPersistence;
    
    @NonNull
    public List<Beer> getAllBeers() {
        return beerPersistence.fetchBeers();
    }
}
