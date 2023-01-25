package com.erdemnayin.jpaauthentication.config;

import com.erdemnayin.jpaauthentication.model.Post;
import com.erdemnayin.jpaauthentication.model.User;
import com.erdemnayin.jpaauthentication.repository.PostRepository;
import com.erdemnayin.jpaauthentication.repository.UserRepository;
import com.erdemnayin.jpaauthentication.service.JpaUserDetailsService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JpaUserDetailsService jpaUserDetailsService;

    public SecurityConfig(JpaUserDetailsService jpaUserDetailsService) {
        this.jpaUserDetailsService = jpaUserDetailsService;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .csrf(csrf -> csrf.ignoringAntMatchers("/h2-console/**"))
                .authorizeHttpRequests(auth -> auth
                        .antMatchers("/h2-console/**").permitAll()
                        .mvcMatchers("/api/posts/**").permitAll()
                        .anyRequest().authenticated()
                )
                .userDetailsService(jpaUserDetailsService)
                .headers(headers -> headers.frameOptions().sameOrigin())
                .httpBasic(withDefaults())
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner commandLineRunner(PostRepository postRepository,
                                        UserRepository userRepository,
                                        PasswordEncoder passwordEncoder){
        return args -> {
            userRepository.save(new User("user",passwordEncoder.encode("password"),"ROLE_USER"));
            userRepository.save(new User("admin",passwordEncoder.encode("password"),"ROLE_USER,ROLE_ADMIN"));
            postRepository.save(new Post("Hello, World!","hello-world","Welcome to my new blog!","Dan Vega"));

        };
    }
}
