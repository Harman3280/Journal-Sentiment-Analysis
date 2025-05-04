package org.example.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

//    @Data removes NoArgsConstructor and All args constructor so we need to add other 2 annotations also

    @Id
    private ObjectId userId;

    @Indexed(unique = true)
    @NonNull
    private String userName;

    private String email;

    //This field is added as to the user who opt for sentimentAnalysis, we will trigger a mail to them every week for sentimentAnalysis
    private boolean sentimentAnalysis;

    @NonNull
    private String password;

    @DBRef
    List<JournalEntry> journalEntryList = new ArrayList<>();

    List<String> roles;
}
