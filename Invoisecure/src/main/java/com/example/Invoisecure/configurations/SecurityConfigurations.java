package com.example.Invoisecure.configurations;

import com.example.Invoisecure.models.Personnel;
import com.example.Invoisecure.services.GeneralServices;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Arrays;
import java.util.List;

import static com.example.Invoisecure.enum_package.ROLETYPE.ADMIN;
import static com.example.Invoisecure.enum_package.ROLETYPE.USER;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired
    private GeneralServices generalService;

    public List<Personnel> personnel_credentials;

    @PostConstruct
    public void onStartup(){
        personnel_credentials = generalService.LoadIntoConfig();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers("/admin").hasRole("ADMIN")
                                .requestMatchers("/user").hasRole("USER")
                                .requestMatchers("/").permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                        .successHandler((request, response, authentication) -> {
                            String authorityType = authentication.getAuthorities().iterator().next().getAuthority();
                            if(authorityType.equals("ROLE_ADMIN")){
                                response.sendRedirect("/admin");
                            }
                            if(authorityType.equals("ROLE_USER")){
                                response.sendRedirect("/user");
                            }
                        })
                ).logout(logout ->
                        logout
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/login?logout")
                                .permitAll()
                );


        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

        for (Personnel userentity : personnel_credentials){
            UserDetails user = User.withUsername(userentity.getUsername())
                    .password(passwordEncoder().encode(userentity.getPassword()))
                    .roles(userentity.getRole())
                    .build();
            manager.createUser(user);
        }
        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
