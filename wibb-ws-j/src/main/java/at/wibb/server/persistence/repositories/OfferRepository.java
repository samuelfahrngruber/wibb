package at.wibb.server.persistence.repositories;

import at.wibb.server.persistence.templates.OfferTemplate;
import at.wibb.server.shared.OfferType;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Date;
import java.util.List;

public interface OfferRepository extends MongoRepository<OfferTemplate, String> {

    @Query(
            value = "{\n" +
                    "    $or: [\n" +
                    "        { endDate: { $gte: ?1 } },\n" +
                    "        { expires: { $gte: ?1 } },\n" +
                    "    ],\n" +
                    "    type: { $eq: ?0 },\n" +
                    "}",
            sort = "{" +
                    "    price: 1" +
                    "}"
    )
    List<OfferTemplate> findRelevantOffers(OfferType offerType, Date currentTimestamp);
}
