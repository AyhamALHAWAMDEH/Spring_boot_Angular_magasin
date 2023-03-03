package com.example.magasin.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;


/*La classe SecurityConfig est une configuration de sécurité qui
utilise JSON Web Tokens (JWT) pour l'authentification et l'autorisation.*/
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    final UserDetailsServiceCustom userDetailsService;

    final JwtRequestFilter jwtRequestFilter;

    public SecurityConfig(
            com.example.magasin.security.UserDetailsServiceCustom userDetailsService,
            com.example.magasin.security.JwtRequestFilter jwtRequestFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }



     /*•	configure(AuthenticationManagerBuilder auth): Cette méthode configure
     l'AuthenticationManager pour l'authentification des utilisateurs. Dans
     cette configuration, elle utilise un UserDetailsServiceCustom pour charger
     les utilisateurs à partir de la base de données.*/
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    /*•	configure(HttpSecurity httpSecurity): Cette méthode configure la sécurité
     des requêtes HTTP en définissant les règles d'autorisation pour les différents
      URL de l'application. Elle utilise également un JwtRequestFilter pour vérifier
      si les requêtes sont accompagnées d'un JWT valide.*/

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        /*•	CorsConfiguration: Cette classe est utilisée pour définir la
        configuration CORS (Cross-Origin Resource Sharing) pour l'application.
        Elle permet à l'application d'accepter des requêtes de domaines différents.*/

        httpSecurity
                .cors().configurationSource(httpServletRequest -> {
                    CorsConfiguration corsConfiguration = new CorsConfiguration();
                    corsConfiguration.applyPermitDefaultValues();
                    corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT"));
                    corsConfiguration.setAllowedHeaders(
                            Arrays.asList("X-Requested-With", "Origin", "Content-Type",
                                    "Accept", "Authorization","Access-Control-Allow-Origin"));
                    return corsConfiguration;
                })
                .and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/test/**").permitAll()
                .antMatchers("/authentification").permitAll()
                .antMatchers("/inscription").permitAll()
                .antMatchers("/admin/**").hasRole("ADMINISTRATEUR")
                .antMatchers("/redact/**").hasAnyRole("ADMINISTRATEUR","REDACTEUR")
                .antMatchers("/user/**").hasAnyRole("ADMINISTRATEUR","UTILISATEUR","REDACTEUR")
                .anyRequest().authenticated()
                .and().exceptionHandling()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

    }


    /*•	getPasswordEncoder(): Cette méthode renvoie un BCryptPasswordEncoder
    qui est utilisé pour encoder les mots de passe des utilisateurs avant de
    les stocker dans la base de données.*/
    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }


    /*•	authenticationManagerBean(): Cette méthode retourne l'AuthenticationManager
     qui est utilisé pour l'authentification des utilisateurs.*/
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
