package com.example.magasin.controllers;

import com.example.magasin.dao.UtilisateurDao;
import com.example.magasin.models.Role;
import com.example.magasin.models.Utilisateur;
import com.example.magasin.security.*;
import com.example.magasin.view.VueUtilisateur;
import com.fasterxml.jackson.annotation.JsonView;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class UtilisateurController {

    private UtilisateurDao utilisateurDao;
    private JwtUtil jwtUtil;
    private AuthenticationManager authenticationManager;
    private UserDetailsServiceCustom userDetailsServiceCustom;
    private PasswordEncoder passwordEncoder;

    @Autowired
    UtilisateurController(
            UtilisateurDao utilisateurDao,
            JwtUtil jwtUtil,
            AuthenticationManager authenticationManager,
            UserDetailsServiceCustom userDetailsServiceCustom,
            PasswordEncoder passwordEncoder

    ) {
        this.utilisateurDao = utilisateurDao;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.userDetailsServiceCustom = userDetailsServiceCustom;
        this.passwordEncoder = passwordEncoder;

    }

    @PostMapping("/authentification")
    public ResponseEntity<String> authentification(@RequestBody Utilisateur utilisateur) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            utilisateur.getEmail(), utilisateur.getMotDePasse()
                    )
            );
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().body("Mauvais email / mot de passe");
        }
        UserDetailsCustom userDetails = this.userDetailsServiceCustom.loadUserByUsername(utilisateur.getEmail());
        return ResponseEntity.ok(jwtUtil.generateToken(userDetails));
    }


    @PostMapping("/inscription")
    public ResponseEntity<String> inscription(@RequestBody Utilisateur utilisateur) {
        Optional<Utilisateur> utilisateurDoublon = utilisateurDao.findByEmail(utilisateur.getEmail());
        if (utilisateurDoublon.isPresent()) {
            return ResponseEntity.badRequest().body("Ce email est déja utilisé");
        } else {
            utilisateur.setMotDePasse(passwordEncoder.encode(utilisateur.getMotDePasse()));
            Role roleUtilisateur = new Role();
            roleUtilisateur.setId(1);
            utilisateur.getListeRole().add(roleUtilisateur);
            utilisateurDao.saveAndFlush(utilisateur);
            return ResponseEntity.ok(Integer.toString(utilisateur.getId()));
        }
    }

    @PostMapping("/admin/utilisateur")
    public ResponseEntity<String> updateUser(@RequestBody Utilisateur utilisateur) {
        Optional<Utilisateur> utilisateurBddOptional = utilisateurDao.findByEmail(utilisateur.getEmail());
        if (utilisateurBddOptional.isPresent()) {
            Utilisateur utilisateurBdd = utilisateurBddOptional.get();
            utilisateur.setMotDePasse(utilisateurBdd.getMotDePasse());
            utilisateurDao.save(utilisateur);
            return ResponseEntity.ok().body("Utilisateur mis à jour");
        }
        return ResponseEntity.notFound().build();
    }

    @JsonView(VueUtilisateur.class)
    @GetMapping("/user/utilisateur-connecte")
    public ResponseEntity<Utilisateur> getInformationUtilisateurConnecte(
            @RequestHeader(value = "Authorization") String authorization) {
        //la valeur des champs authorization est extrait de l'entête de la requête

        //On supprime la partie "Bearer " de la valeur de l'authorization
        String token = authorization.substring(7);

        //on extrait l'information souhaitée du token
        String username = jwtUtil.getTokenBody(token).getSubject();
        Optional<Utilisateur> utilisateur = utilisateurDao.findByEmail(username);
        if (utilisateur.isPresent()) {
            return ResponseEntity.ok().body(utilisateur.get());
        }
        return ResponseEntity.notFound().build();
    }

    @JsonView(VueUtilisateur.class)
    @GetMapping("/admin/utilisateur/{id}")
    public ResponseEntity<Utilisateur> getUtilisateur(@PathVariable int id) {
        Optional<Utilisateur> utilisateur = utilisateurDao.findById(id);
        if (utilisateur.isPresent()) {
            return ResponseEntity.ok(utilisateur.get());
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @JsonView(VueUtilisateur.class)
    @GetMapping("/admin/utilisateurs")
    public ResponseEntity<List<Utilisateur>> getUtilisateurs() {
        return ResponseEntity.ok(utilisateurDao.findAll());
    }

    @DeleteMapping("/admin/utilisateur/{id}")
    public ResponseEntity<Integer> deleteUtilisateur(@PathVariable int id) {
        if (utilisateurDao.existsById(id)) {
            utilisateurDao.deleteById(id);
            return ResponseEntity.ok(id);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping("/user/changer-mot-de-passe")
    public ResponseEntity<String> changerMotDePasse(@RequestBody ChangerMotDePasseDto changerMotDePasseDto,
                                                    @RequestHeader(value = "Authorization") String authorization) {

        String token = authorization.substring(7);
        String username = jwtUtil.getTokenBody(token).getSubject();
        Optional<Utilisateur> utilisateur = utilisateurDao.findByEmail(username);

        if (utilisateur.isPresent()) {
            if (passwordEncoder.matches(changerMotDePasseDto.getAncienMotDePasse(), utilisateur.get().getMotDePasse())) {
                String nouveauMotDePasseEncode = passwordEncoder.encode(changerMotDePasseDto.getNouveauMotDePasse());
                utilisateur.get().setMotDePasse(nouveauMotDePasseEncode);
                utilisateurDao.save(utilisateur.get());
                return ResponseEntity.ok().body("Mot de passe changé avec succès");
            } else {
                return ResponseEntity.badRequest().body("Ancien mot de passe incorrect");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @Autowired
    private JavaMailSender javaMailSender;

    @PostMapping("/user/oubli-mot-de-passe")
    public ResponseEntity<String> oubliMotDePasse(@RequestBody OubliMotDePasseDto oubliMotDePasseDto) {
        Optional<Utilisateur> utilisateur = utilisateurDao.findByEmail(oubliMotDePasseDto.getEmail());
        if (utilisateur.isPresent()) {
            String nouveauMotDePasse = RandomStringUtils.randomAlphanumeric(10);
            String nouveauMotDePasseEncode = passwordEncoder.encode(nouveauMotDePasse);
            utilisateur.get().setMotDePasse(nouveauMotDePasseEncode);
            utilisateurDao.save(utilisateur.get());

            // Envoi de l'email avec le nouveau mot de passe
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(utilisateur.get().getEmail());
            message.setSubject("Réinitialisation de mot de passe");
            message.setText("Votre nouveau mot de passe est : " + nouveauMotDePasse);
            javaMailSender.send(message);

            return ResponseEntity.ok().body("Nouveau mot de passe envoyé à l'adresse email");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        javaMailSender.send(message);
    }

    @PostMapping("/test-email")
    public ResponseEntity<String> testEmail(@RequestParam("to") String to) {
        String subject = "Test Email";
        String body = "This is a test email sent from my Spring Boot application.";

        try {
            sendEmail(to, subject, body);
            return ResponseEntity.ok().body("Email sent successfully");
        } catch (MailException e) {
            return ResponseEntity.badRequest().body("Error sending email: " + e.getMessage());
        }
    }




}
