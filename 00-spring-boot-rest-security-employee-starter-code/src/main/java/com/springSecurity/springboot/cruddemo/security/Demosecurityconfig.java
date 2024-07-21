package com.luv2code.springboot.cruddemo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class Demosecurityconfig {
    //add support for jdbc .. and no more code using..

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource){

        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        //define query to retrieve a user by username
        jdbcUserDetailsManager.setUsersByUsernameQuery(
          "select user_id, pw, active from members where user_id=?");

        //define query to retrieve the authentuication /roles by username
jdbcUserDetailsManager.setUsersByUsernameQuery(
        "select user_id, role from roles where user_id=? "
);


        return jdbcUserDetailsManager;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)throws Exception{
        http.authorizeHttpRequests(configurer->
                configurer
                        .requestMatchers(HttpMethod.GET,"/api/employees").hasRole("Employee")
                        .requestMatchers(HttpMethod.GET,"/api/employees/**").hasRole("Employee")
                        .requestMatchers(HttpMethod.POST,"/api/employees").hasRole("Manager")
                        .requestMatchers(HttpMethod.PUT,"/api/employees").hasRole("Manager")
                        .requestMatchers(HttpMethod.DELETE,"/api/employees").hasRole("Admin")


        );

        //use http basic authentication
        http.httpBasic(Customizer.withDefaults());

        //disable csrf
        //in generasl , not require rest apis thar use post put ,delete
        http.csrf(csrf->csrf.disable());



        return http.build();

    }


//    @Bean
//   public InMemoryUserDetailsManager userDetailsManager(){
//        UserDetails deek= User.builder()
//                .username("deek")
//                .password("{noop}deek123")
//                .roles("Employee,Manager,Admin")
//                .build();
//
//
//        UserDetails harsha= User.builder()
//                .username("harsha")
//                .password("{noop}harsha123")
//                .roles("Employee")
//                .build();
//
//
//        UserDetails divya = User.builder()
//                .username("divya")
//                .password("{noop}divya123")
//                .roles("Employee,Manager")
//                .build();
//        return new InMemoryUserDetailsManager(deek,harsha,divya);
//
//    }
//





}
