package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.api.response.WeatherResponse;
import org.example.cache.APICache;
import org.example.constants.Placeholders;
import org.example.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class WeatherService {

    /* This variable should be non-static to work with @Value, bcz Spring don't change static variable as it is common to a class,
       if changed changes may impact value access to other instances of class
    */
    @Value("${weather.api.key}")
    private String apiKey;

    // Moved this config to DB
    //private static final String API = "http://api.weatherstack.com/current?access_key=API_KEY&query=CITY";

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    APICache apiCache;

    @Autowired
    RedisService redisService;

    public WeatherResponse getWeather(String city){
        String key = "weather_of_"+city;
        WeatherResponse weatherResponse = redisService.get(key, WeatherResponse.class);

        if(weatherResponse != null){
            return weatherResponse;
        }else{
            String finalAPI = apiCache.appCacheMap.get(APICache.Keys.WEATHER_API.toString()).replace(Placeholders.CITY,city).replace(Placeholders.API_KEY, apiKey);
            ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class);
            WeatherResponse body = response.getBody();

            if(body != null){
                redisService.set(key, body, 60L);
            }
            return body;
        }
    }

    /*
        Post Call example with RestTemplate

    public WeatherResponse postDummyExample(String city){

        HttpHeaders httpHeaders =new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        // We pass our auth token after Bearer in Authorization token
        httpHeaders.set("Authorization", "Bearer your_token_here");

        User user = User.builder().userName("DummyPostTest").password("Dummy").build();
        HttpEntity<User> httpEntity = new HttpEntity<>(user, httpHeaders);

        String finalAPI = API.replace("CITY",city).replace("API_KEY", apiKey);

        ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.POST, httpEntity, WeatherResponse.class);
        WeatherResponse body = response.getBody();
        return body;
    }

     */


}
