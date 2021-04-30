package at.wibb.server.persistence;

import java.util.Collections;
import java.util.List;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import at.wibb.server.shared.Beer;
import at.wibb.server.shared.Icon;

@Component
public class BeerPersistence {
    
    @NonNull
    public List<Beer> fetchBeers() {
        return Collections.singletonList(new Beer("villacher", new Icon("/res/villacher.png", "#FFFFFF")));
    }
}
