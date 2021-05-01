package at.wibb.server.persistence.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import at.wibb.server.persistence.templates.OfferTemplate;

public interface OfferRepository extends MongoRepository<OfferTemplate, String> {
    
}
