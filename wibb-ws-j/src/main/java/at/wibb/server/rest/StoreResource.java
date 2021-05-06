package at.wibb.server.rest;

import java.util.List;
import java.util.stream.Collectors;

import at.wibb.server.rest.dto.RestDtoMapper;
import at.wibb.server.rest.dto.StoreDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import at.wibb.server.business.StoreService;

@RestController
public class StoreResource {
    
    @Autowired
    private StoreService storeService;

    @GetMapping("/api/stores")
    public List<StoreDto> getStores() {
        return storeService.getAllStores()
                .stream()
                .map(RestDtoMapper::map)
                .collect(Collectors.toList());
    }

}
