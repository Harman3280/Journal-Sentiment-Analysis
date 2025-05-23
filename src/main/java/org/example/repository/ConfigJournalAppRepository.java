package org.example.repository;

import org.bson.types.ObjectId;
import org.example.entity.ConfigJournalAppEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  ConfigJournalAppRepository extends MongoRepository<ConfigJournalAppEntity, ObjectId> {
}
