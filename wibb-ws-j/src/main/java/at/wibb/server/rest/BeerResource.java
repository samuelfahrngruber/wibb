package at.wibb.server.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import at.wibb.server.business.BeerService;
import at.wibb.server.shared.Beer;

@RestController
public class BeerResource {

    @Autowired
    private BeerService beerService;

    @GetMapping("/api/beers")
    public List<Beer> getBeers() {
        return beerService.getAllBeers();
    }

}
