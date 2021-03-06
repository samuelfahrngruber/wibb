package at.wibb.server.business;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import at.wibb.server.persistence.repositories.BeerRepository;
import at.wibb.server.shared.Beer;

@Component
public class BeerService {

    @Autowired
    private BeerRepository beerRepository;
    
    @NonNull
    public List<Beer> getAllBeers() {
        return beerRepository.findAll()
            .stream()
            .map(TransformUtil::transformBeer)
            .collect(Collectors.toList());
    }

}
