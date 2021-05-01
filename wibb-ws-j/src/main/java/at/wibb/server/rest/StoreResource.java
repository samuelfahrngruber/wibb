package at.wibb.server.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import at.wibb.server.business.StoreService;
import at.wibb.server.shared.Store;

@RestController
public class StoreResource {
    
    @Autowired
    private StoreService storeService;

    @GetMapping("/api/stores")
    public List<Store> getStores() {
        return storeService.getAllStores();
    }

}
