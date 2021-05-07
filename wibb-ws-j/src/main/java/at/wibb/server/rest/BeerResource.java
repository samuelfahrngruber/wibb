package at.wibb.server.rest;

import java.util.List;
import java.util.stream.Collectors;

import at.wibb.server.rest.dto.BeerDto;
import at.wibb.server.rest.dto.RestDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import at.wibb.server.business.BeerService;

@RestController
public class BeerResource {

    @Autowired
    private BeerService beerService;

    @GetMapping("/api/beers")
    public List<BeerDto> getBeers() {
        return beerService.getAllBeers()
                .stream()
                .map(RestDtoMapper::map)
                .collect(Collectors.toList());
    }

}
