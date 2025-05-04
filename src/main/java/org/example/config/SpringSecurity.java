package org.example.config;

import org.example.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurity {

    /*Flow -->
        📌 Note: antMatchers() was removed in Spring Security 6, but it still works in 5.7.
    1. Username and Password is fetched from request(Frontend call/Postman), our method will check which userDetailsService we are using.
    2. using userDetailsService, it will fetch UserDetails using it method by passing username argument.
    3. Password matching process : Then using BCryptPasswordEncoder we match the user entered password

    */

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


        return http.authorizeHttpRequests(request -> request

                        //.antMatchers("/public/**").permitAll()  // Allow public access
                        //.antMatchers("/user/**").permitAll()  // Allow user endpoints access
                        .antMatchers("/journal/**","/user/**").authenticated()
                        .antMatchers("/admin/**").hasRole("ADMIN") //RoleBasedAuthentication
                        //.anyRequest().authenticated())
                        .anyRequest().permitAll())
                .httpBasic(Customizer.withDefaults())   // Enable basic authentication
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf(AbstractHttpConfigurer::disable)  // Disable CSRF if using APIs

                .build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }


    // This Encoder will take a string and generate a HASH for randomized string
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
