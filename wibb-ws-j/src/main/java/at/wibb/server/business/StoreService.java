package at.wibb.server.business;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import at.wibb.server.persistence.repositories.StoreRepository;
import at.wibb.server.shared.Store;

@Component
public class StoreService {

    @Autowired
    private StoreRepository beerRepository;
    
    @NonNull
    public List<Store> getAllStores() {
        return beerRepository.findAll()
            .stream()
            .map(TransformUtil::transformStore)
            .collect(Collectors.toList());
    }

}
