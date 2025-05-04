package org.example.cache;

import org.example.entity.ConfigJournalAppEntity;
import org.example.repository.ConfigJournalAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//Here we are not using REDIS or other cache server, we are simply using @PostConstruct to load preAppDetails on startup
@Component
public class APICache {

    public enum Keys{
        WEATHER_API;
    }

    @Autowired
    ConfigJournalAppRepository configJournalAppRepository;

    public Map<String, String> appCacheMap;

    @PostConstruct
    public void init(){
        appCacheMap = new HashMap<>();
        List<ConfigJournalAppEntity> all = configJournalAppRepository.findAll();
        for(ConfigJournalAppEntity configJournalAppEntity : all){
            appCacheMap.put(configJournalAppEntity.getKey(), configJournalAppEntity.getValue());
        }
    }


}
