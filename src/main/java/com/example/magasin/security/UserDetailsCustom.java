package com.example.magasin.security;

import com.example.magasin.models.Role;
import com.example.magasin.models.Utilisateur;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/*La classe UserDetailsCustom contient les informations de l'utilisateur
 nécessaires pour l'authentification, à savoir son email, son mot de passe
 et la liste de ses rôles. */
public class UserDetailsCustom implements UserDetails {

    private int id;
    private String email;
    private String password;
    private boolean active;
    private List<GrantedAuthority> authorities;

    /*•	Le constructeur : Il initialise les champs de la classe à partir
     d'un objet Utilisateur, qui est une entité de la base de données
     représentant l'utilisateur. Il crée également une liste de GrantedAuthority
      à partir de la liste des rôles de l'utilisateur.*/
    public UserDetailsCustom(Utilisateur utilisateur) {
        this.id = utilisateur.getId();
        this.email = utilisateur.getEmail();
        this.password = utilisateur.getMotDePasse();
        this.active = true;
        authorities = new ArrayList<>();
        for(Role role : utilisateur.getListeRole()){
            authorities.add(new SimpleGrantedAuthority(role.getDenomination()));
        }
    }

    //Quand on fait (implements UserDetails) on obteint les méthodes suivantes :

    /*•	getAuthorities() : Cette méthode retourne la liste des rôles
     de l'utilisateur sous forme de GrantedAuthority. Les GrantedAuthority
      sont des objets qui représentent l'autorisation d'un utilisateur à
      effectuer une action ou accéder à une ressource spécifique.*/
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }


    /*getPassword() : Cette méthode retourne le mot de passe de l'utilisateur.*/
    @Override
    public String getPassword() {
        return password;
    }

    /*getUsername() : Cette méthode retourne l'email de l'utilisateur.*/
    @Override
    public String getUsername() {
        return email;
    } // ill retourne l'email

    /*•	isAccountNonExpired() : Cette méthode indique si le compte de
    l'utilisateur a expiré. Dans cet exemple, elle retourne toujours true.*/
    @Override
    public boolean isAccountNonExpired() {
        return true;
    } //normalment il me donne (false) et il faut modifier


    /*•	isAccountNonLocked() : Cette méthode indique si le compte de
     l'utilisateur est verrouillé. Dans cet exemple, elle retourne toujours true.*/
    @Override
    public boolean isAccountNonLocked() {
        return true;
    } //normalment il me donne (false) et il faut modifier


    /*•	isCredentialsNonExpired() : Cette méthode indique si les informations
     d'authentification de l'utilisateur ont expiré. Dans cet exemple, elle
     retourne toujours true.*/
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    } //normalment il me donne (false) et il faut modifier

    /*•	isEnabled() : Cette méthode indique si le compte de l'utilisateur est activé.
    Dans cet exemple, elle retourne toujours true.*/
    @Override
    public boolean isEnabled() {
        return true;
    } //normalment il me donne (false) et il faut modifier



    /*•	getId() et setId(int id) : Ces méthodes sont des accesseurs pour le champ id qui
     contient l'identifiant de l'utilisateur.*/
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
