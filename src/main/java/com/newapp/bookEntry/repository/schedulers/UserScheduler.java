package com.newapp.bookEntry.repository.schedulers;

import com.newapp.bookEntry.Entity.JournalEntry;
import com.newapp.bookEntry.Entity.User;
import com.newapp.bookEntry.cache.AppCache;
import com.newapp.bookEntry.enums.Sentiment;
import com.newapp.bookEntry.repository.UserRepoImpl;
import com.newapp.bookEntry.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserScheduler {
    @Autowired
    private UserRepoImpl userRepoIml;
    @Autowired
    private AppCache appCache;
    @Autowired
    private EmailService emailService;
    private String username;

    public void getEmail(String username){
        this.username=username;

    }
    @Scheduled(cron = "* * * * * *")
    public String fetchUsersAndSaMail(){
        List<User> users=userRepoIml.getUserSA(username);

        for(User user: users){
            List<JournalEntry> journalEntries=user.getJournalEntries();
            List<Sentiment> sentiments=journalEntries.stream().filter(x->x.getDate()
                    .isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS)))
                    .map(x->x.getSentiment())
                    .collect(Collectors.toList());
            Map<Sentiment,Integer> sentimentsCount=new HashMap<>();
            for (Sentiment sentiment: sentiments){
                 if (sentiment!=null){
                     sentimentsCount.put(sentiment, sentimentsCount.getOrDefault(sentiment,0)+1);
                 }
            }
            Sentiment mostFrequetionSentiment=null;
            int maxCount=0;
            for(Map.Entry<Sentiment,Integer> entry: sentimentsCount.entrySet()){
                if(entry.getValue()>maxCount){
                    maxCount=entry.getValue();
                    mostFrequetionSentiment=entry.getKey();
                }
            }
            if(mostFrequetionSentiment!=null ){
                emailService.mailSender(user.getEmail(), "Sentimental analysis for last 7 days", mostFrequetionSentiment.toString());
                return "Email Sent to "+user.getEmail();
            }
        }
        return "Email not sent";
    }
}
