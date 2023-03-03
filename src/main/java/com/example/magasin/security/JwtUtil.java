package com.example.magasin.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


 /*Cette classe est une implémentation d'un utilitaire pour les JSON Web Tokens (JWT)*/

@Service
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    /*•	getTokenBody(String token) : cette méthode prend en paramètre un JWT
     et renvoie les revendications (claims) stockées dans le corps du token.
     Elle utilise la bibliothèque io.jsonwebtoken pour décoder le JWT en extrayant
      les informations stockées dans le corps.*/
    public Claims getTokenBody(String token) {
        return Jwts
                .parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    /*generateToken(UserDetailsCustom userDetails) : cette méthode prend en paramètre
     un objet UserDetailsCustom qui représente les informations d'un utilisateur authentifié
     et génère un JWT en utilisant les informations de l'utilisateur ainsi qu'un secret
     partagé qui est stocké dans la propriété secret de l'objet. Le JWT généré contient des
     revendications sur l'utilisateur, telles que son nom d'utilisateur, son identifiant et ses rôles.*/
    public String generateToken(UserDetailsCustom userDetails) {

        Map<String, Object> extra = new HashMap<>();
        extra.put("id",userDetails.getId());

        Object[] listeRole = userDetails.getAuthorities().stream()
                .map(ga -> ga.toString())
                .collect(Collectors.toList())
                .toArray();

        extra.put("roles",listeRole);

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .addClaims(extra)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 12))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    /*•	tokenNonExpire(String token) : cette méthode prend en paramètre un JWT et vérifie si
     sa date d'expiration est dépassée ou non. Si la date d'expiration est dans le futur, la
     méthode renvoie true, sinon elle renvoie false.*/
    private boolean tokenNonExpire(String token) {
        return getTokenBody(token)
                .getExpiration()
                .after(new Date());
    }

    /*•	valideToken(String token, UserDetails userDetails) : cette méthode prend en paramètre
     un JWT et les informations d'un utilisateur authentifié, et vérifie si le JWT est valide
     pour cet utilisateur. Elle utilise les informations stockées dans le JWT pour vérifier si
     le nom d'utilisateur correspond à celui de l'utilisateur authentifié, et si le JWT n'a pas expiré.*/
    public boolean valideToken(String token, UserDetails userDetails) {
        String username = getTokenBody(token).getSubject();
        return username.equals(userDetails.getUsername()) && tokenNonExpire(token);
    }
}
