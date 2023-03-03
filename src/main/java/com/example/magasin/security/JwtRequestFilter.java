package com.example.magasin.security;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*Cette classe JwtRequestFilter est un filtre de requête qui est utilisé
 pour valider les jetons JWT (JSON Web Tokens) envoyés avec les requêtes HTTP.
 Ce filtre permet de sécuriser les API REST de l'application Spring Boot en
 vérifiant si un jeton JWT est présent dans l'en-tête de la requête, en le validant
 et en autorisant ou en refusant l'accès à la ressource protégée.*/
    @Component
    public class JwtRequestFilter extends OncePerRequestFilter {
        private UserDetailsServiceCustom userDetailsServiceCustom;
        private JwtUtil jwtUtil;

        /*JwtRequestFilter:
        constructeur de la classe, qui prend en paramètres une instance de
        UserDetailsServiceCustom et une instance de JwtUtil. Ces deux classes
        sont utilisées pour vérifier le jeton JWT et charger les détails de
        l'utilisateur à partir de la base de données.*/
        @Autowired
        public JwtRequestFilter(UserDetailsServiceCustom userDetailsServiceCustom, JwtUtil jwtUtil) {
            this.userDetailsServiceCustom = userDetailsServiceCustom;
            this.jwtUtil = jwtUtil;
        }




        /*shouldNotFilter:
        cette méthode permet de spécifier les requêtes qui ne doivent pas être filtrées.
        Dans cet exemple, les requêtes qui commencent par /test ou qui sont destinées aux
        URL /authentification et /inscription sont autorisées sans vérification de jeton JWT.*/
        @Override
        protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
            return request.getServletPath().startsWith("/test")
                    || request.getServletPath().equals("/authentification")
                    || request.getServletPath().equals("/inscription");
        }



        /*doFilterInternal :
        cette méthode effectue le traitement principal du filtre. Elle extrait le jeton JWT
         de l'en-tête Authorization de la requête, le valide en utilisant la classe JwtUtil,
         et, si le jeton est valide, crée un UsernamePasswordAuthenticationToken pour l'utilisateur
         identifié par le jeton. Ensuite, l'objet SecurityContextHolder est utilisé pour définir
         l'authentification de l'utilisateur, ce qui permet à l'utilisateur d'accéder aux ressources
         protégées. Si le jeton est invalide ou expiré, une réponse HTTP avec un code d'erreur
         correspondant est renvoyée.*/
        @Override
        protected void doFilterInternal(
                HttpServletRequest httpServletRequest,
                HttpServletResponse httpServletResponse,
                FilterChain filterChain
        ) throws ServletException, IOException {

            String authorizationHeader = httpServletRequest.getHeader("Authorization");

            if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String jwt = authorizationHeader.substring(7);

                try {
                    String email = jwtUtil.getTokenBody(jwt).getSubject();

                    if(email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                        UserDetails userDetails = userDetailsServiceCustom.loadUserByUsername(email);

                        if(jwtUtil.valideToken(jwt,userDetails)){

                            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                                    new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

                            usernamePasswordAuthenticationToken
                                    .setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

                            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                            filterChain.doFilter(httpServletRequest,httpServletResponse);
                        } else {
                            httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            httpServletResponse.setCharacterEncoding("UTF-8");
                            httpServletResponse.getWriter().write("Le token est corrompu ou expiré");
                            httpServletResponse.getWriter().flush();
                        }
                    }

                } catch (ExpiredJwtException e) {
                    httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    httpServletResponse.setCharacterEncoding("UTF-8");
                    httpServletResponse.getWriter().write("Le token est expiré");
                    httpServletResponse.getWriter().flush();
                }
            } else {
                httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
                httpServletResponse.setCharacterEncoding("UTF-8");
                httpServletResponse.getWriter().write("Le token est inexistant ou malformé");
                httpServletResponse.getWriter().flush();
            }

        }
    }

