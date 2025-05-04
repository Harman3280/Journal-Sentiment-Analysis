package org.example.repository;

import org.bson.types.ObjectId;
import org.example.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User,ObjectId> {

    User findByUserName(String username);

    void deleteByUserName(String username);
}
