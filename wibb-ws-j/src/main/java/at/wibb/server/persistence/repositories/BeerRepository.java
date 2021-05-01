package at.wibb.server.persistence.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import at.wibb.server.persistence.templates.BeerTemplate;

public interface BeerRepository extends MongoRepository<BeerTemplate, String> {
    
}
