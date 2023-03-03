package com.example.magasin.security;

import com.example.magasin.dao.UtilisateurDao;
import com.example.magasin.models.Utilisateur;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceCustom implements UserDetailsService {

    private final UtilisateurDao utilisateurDao;

    public UserDetailsServiceCustom(UtilisateurDao utilisateurDao) {
        this.utilisateurDao = utilisateurDao;
    }

    @Override
    public UserDetailsCustom loadUserByUsername(String emialSaisi) throws UsernameNotFoundException {

        Utilisateur utilisateur = utilisateurDao
                .findByEmail(emialSaisi)
                .orElseThrow(() -> new UsernameNotFoundException(emialSaisi + " inconnu"));

        return new UserDetailsCustom(utilisateur);
    }
}
