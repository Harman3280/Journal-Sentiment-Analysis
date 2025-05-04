package org.example.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.example.cache.APICache;
import org.example.entity.JournalEntry;
import org.example.entity.User;
import org.example.enums.Sentiment;
import org.example.model.SentimentData;
import org.example.repository.UserRepositoryImpl;
import org.example.service.EmailService;
import org.example.service.SentimentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class UserScheduler {

    @Autowired
    EmailService emailService;

    @Autowired
    SentimentAnalysisService sentimentAnalysisService;

    @Autowired
    UserRepositoryImpl userRepository;

    @Autowired
    APICache apiCache;

    @Autowired
    KafkaTemplate<String, SentimentData> kafkaTemplate;


    //                 S M  H D Mo Day
    @Scheduled(cron = "0 30 21 * * THU")
    public void fetchUsersAndSendSAMail(){
        List<User> users = userRepository.getUserForSentimentAnalysis();

        for(User user : users){
            List<JournalEntry> journalEntryList = user.getJournalEntryList();
            List<Sentiment> filteredJournalSentimentList = journalEntryList.stream().filter(
                            x -> x.getDate()
                                    .isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS)))
                    .map(x -> x.getSentiment())
                    .collect(Collectors.toList());
            Map<Sentiment, Integer> sentimentCounts = new HashMap<>();

            for(Sentiment sentiment : filteredJournalSentimentList){
                if(sentiment != null){
                    sentimentCounts.put(sentiment, sentimentCounts.getOrDefault(sentiment, 0) +1);
                }
            }
            Sentiment mostFrequentSentiment = null;
            int maxCount = 0;
            for (Map.Entry<Sentiment, Integer> entry : sentimentCounts.entrySet()) {
                if (entry.getValue() > maxCount) {
                    maxCount = entry.getValue();
                    mostFrequentSentiment = entry.getKey();
                }
            }

            if(mostFrequentSentiment != null) {
                // Below task moved to SentimentConsumerService
                //emailService.sendEmail(user.getEmail(), "Sentiment for Last 7 days", mostFrequentSentiment.toString());

                SentimentData sentimentData = SentimentData.builder()
                        .email(user.getEmail()).sentiment(" Sentiment for Last 7 days " + mostFrequentSentiment).build();
                kafkaTemplate.send("weekly-sentiments", sentimentData.getEmail(), sentimentData);
            }
        }
    }

    //Job to clear APPCache every 5 min

    @Scheduled(cron = "0 0/5 * ? * *")
    public void clearAppCache(){
        apiCache.init();
    }

}
