package org.example.repository;

import org.example.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.schema.JsonSchemaObject;

import java.util.List;

//Created this class to write complex queries using Criteria unlike using simply UserRepository Interface
public class UserRepositoryImpl {

    /*

    MongoTemplate is a class provided by spring data Mongo db to interact with mongodb.
    It has all the methods to perform op on DB.

     */
    @Autowired
    MongoTemplate mongoTemplate;


    public List<User> getUserForSentimentAnalysis(){
        Query query =new Query();
        //query.addCriteria(Criteria.where("userName").is("ram"));
       Criteria criteria = new Criteria();
       query.addCriteria(criteria.andOperator(
               Criteria.where("email").exists(true),
               Criteria.where("email").ne(null).ne(""),
               //Criteria.where("roles").in("USER", "ADMIN"),
               Criteria.where("sentimentAnalysis").is(true)));

        List<User> users = mongoTemplate.find(query, User.class);
        return users;
    }


    /*
    E.g. for creating criteria
        1. Greater than equal to - gte - query.addCriteria(Criteria.where("age").gte("20"));
        2. Less    than equal to - lte - query.addCriteria(Criteria.where("age").lte("20"));

        3. Greater than  - gt - query.addCriteria(Criteria.where("age").gt("20"));

        4. Greater than  - lt - query.addCriteria(Criteria.where("age").lt("20"));
        5. the Object value which is passed is matched with the datatype stored in your db. E.g. if 20 Integer passed then it looks for Integer value in db or if String "20" is passed ...

        6. we can also match on type
           E.g.                Criteria.where("sentimentAnalysis").type(JsonSchemaObject.Type.JsonType.BOOLEAN),

     */
}
