package at.wibb.server.persistence.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import at.wibb.server.persistence.templates.StoreTemplate;

public interface StoreRepository extends MongoRepository<StoreTemplate, String> {
    
}
