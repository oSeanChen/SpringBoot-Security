package com.example.Spring.Security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(customizer -> customizer.disable())
                .authorizeHttpRequests((registry) -> registry //對所有訪問HTTP端點的HttpServletRequest進行限制
                        .requestMatchers(HttpMethod.GET, "/hello").hasAnyAuthority("ADMIN", "USER")   //指定路徑，對應身分才可訪問
                        .requestMatchers(HttpMethod.GET, "/error").permitAll()   //指定路徑允許所有用戶訪問，不需身份驗證
                        .requestMatchers(HttpMethod.POST, "/register", "/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/users/?*").hasAuthority("ADMIN")
                        .anyRequest().authenticated()//其他尚未匹配到的路徑都需要身份驗證
                )
//                .httpBasic(Customizer.withDefaults()) // HTTP Basic 身份驗證作為默認的驗證方式
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//                .formLogin(Customizer.withDefaults());

//        http.authorizeHttpRequests((registry) -> registry //對所有訪問HTTP端點的HttpServletRequest進行限制
//                        .requestMatchers(HttpMethod.GET, "/hello").hasAnyAuthority("ADMIN", "USER")   //指定路徑，對應身分才可訪問
//                        .requestMatchers(HttpMethod.GET, "/error").permitAll()   //指定路徑允許所有用戶訪問，不需身份驗證
//                        .requestMatchers(HttpMethod.POST, "/register", "/login").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/users/?*").hasAuthority("ADMIN")
//                        .anyRequest().authenticated()//其他尚未匹配到的路徑都需要身份驗證
//
//                )
//                .httpBasic(Customizer.withDefaults())
//                .csrf(AbstractHttpConfigurer::disable);
//                .formLogin(Customizer.withDefaults());

        return http.build();
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails user = User.withDefaultPasswordEncoder()
//                .username("user")
//                .password("user")
//                .roles("USER")
//                .build();
//
//        UserDetails admin = User.withDefaultPasswordEncoder()
//                .username("admin")
//                .password("admin")
//                .roles("ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(user, admin);

}
