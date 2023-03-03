INSERT INTO magasin (description, nom, proprietaire, adresse, emplacement, telephone, email, logo)
VALUES ('Grand magasin de vêtements', 'Fashion Store', 'John Doe', '123 rue des magasins', 'Centre commercial', '01 23 45 67 89', 'contact@fashionstore.com', 'https://www.fashionstore.com/logo.jpg');

INSERT INTO `horaire` (`id`, `heure_fermeture`, `heure_ouverture`, `jour`, `id_magasin`) VALUES
(1, '08:30:00', '20:30:00', 'Lundi', '1'),
(2, '08:30:00', '20:30:00', 'Mardi', '1'),
(3, '08:30:00', '20:30:00', 'Mercredi', '1'),
(4, '08:30:00', '20:30:00', 'Jeudi', '1'),
(5, '08:30:00', '20:30:00', 'Vendredi', '1'),
(6, '08:30:00', '20:30:00', 'Samedi', '1'),
(7, '09:00:00', '12:30:00', 'Dimanche', '1');


INSERT INTO rayon (nom, description, image, id_magasin)
VALUES ('Vêtements pour hommes', 'Rayon dédié aux vêtements pour hommes', 'https://www.fashionstore.com/hommes.jpg', 1);
INSERT INTO rayon (nom, description, image, id_magasin)
VALUES ('Vêtements pour hommes', 'Rayon dédié aux vêtements pour hommes', 'https://www.fashionstore.com/hommes.jpg', 1);
INSERT INTO rayon (nom, description, image, id_magasin)
VALUES ('Vêtements pour femmes', 'Rayon dédié aux vêtements pour femmes', 'https://www.fashionstore.com/femmes.jpg', 1);
INSERT INTO rayon (nom, description, image, id_magasin)
VALUES ('Accessoires', 'Rayon dédié aux accessoires', 'https://www.fashionstore.com/accessoires.jpg', 1);

INSERT INTO categorie (nom, description, image, id_rayon)
VALUES ('Chemises', 'Chemises pour hommes', 'https://www.fashionstore.com/chemises.jpg', 1);
INSERT INTO categorie (nom, description, image, id_rayon)
VALUES ('Pantalons', 'Pantalons pour hommes', 'https://www.fashionstore.com/pantalons.jpg', 1);
INSERT INTO categorie (nom, description, image, id_rayon)
VALUES ('Chemises', 'Chemises pour hommes', 'https://www.fashionstore.com/chemises.jpg', 1);
INSERT INTO categorie (nom, description, image, id_rayon)
VALUES ('Pantalons', 'Pantalons pour hommes', 'https://www.fashionstore.com/pantalons.jpg', 1);
INSERT INTO categorie (nom, description, image, id_rayon)
VALUES ('Robes', 'Robes pour femmes', 'https://www.fashionstore.com/robes.jpg', 2);
INSERT INTO categorie (nom, description, image, id_rayon)
VALUES ('Sacs', 'Sacs pour hommes et femmes', 'https://www.fashionstore.com/sacs.jpg', 3);

INSERT INTO article (nom, description, prix, image, stock, id_categorie)
VALUES ('Chemise en coton blanc', 'Chemise en coton blanc pour hommes', 29.99, 'https://www.fashionstore.com/chemise-coton-blanc.jpg', 10, 1);
INSERT INTO article (nom, description, prix, image, stock, id_categorie)
VALUES ('Pantalon chino beige', 'Pantalon chino beige pour hommes', 49.99, 'https://www.fashionstore.com/pantalon-beige.jpg', 5, 2);
INSERT INTO article (nom, description, prix, image, stock, id_categorie)
VALUES ('Chemise en coton blanc', 'Chemise en coton blanc pour hommes', 29.99, 'https://www.fashionstore.com/chemise-coton-blanc.jpg', 10, 1);
INSERT INTO article (nom, description, prix, image, stock, id_categorie)
VALUES ('Pantalon chino beige', 'Pantalon chino beige pour hommes', 49.99, 'https://www.fashionstore.com/pantalon-beige.jpg', 5, 2);
INSERT INTO article (nom, description, prix, image, stock, id_categorie)
VALUES ('T-shirt rouge', 'T-shirt rouge pour homme', 15.99, 'https://www.fashionstore.com/images/tshirt-rouge.jpg', 50, 1);
INSERT INTO article (nom, description, prix, image, stock, id_categorie)
VALUES ('T-shirt bleu', 'T-shirt bleu pour homme', 15.99, 'https://www.fashionstore.com/images/tshirt-bleu.jpg', 50, 1);
INSERT INTO article (nom, description, prix, image, stock, id_categorie)
VALUES ('Pantalon noir', 'Pantalon noir pour homme', 39.99, 'https://www.fashionstore.com/images/pantalon-noir.jpg', 30, 2);
INSERT INTO article (nom, description, prix, image, stock, id_categorie)
VALUES ('Pantalon beige', 'Pantalon beige pour homme', 39.99, 'https://www.fashionstore.com/images/pantalon-beige.jpg', 30, 2);
INSERT INTO article (nom, description, prix, image, stock, id_categorie)
VALUES ('Chemise blanche', 'Chemise blanche pour homme', 29.99, 'https://www.fashionstore.com/images/chemise-blanche.jpg', 40, 3);
INSERT INTO article (nom, description, prix, image, stock, id_categorie)
VALUES ('Chemise bleue', 'Chemise bleue pour homme', 29.99, 'https://www.fashionstore.com/images/chemise-bleue.jpg', 40, 3);

INSERT INTO `utilisateur` (`id`, `email`, `mot_de_passe`, `nom`, `prenom`, `telephone`) VALUES ('1', 'ayhammohammad642@gmail.com', '$2a$10$VjPf7eGRWaQegjCqZPK/NORLpNzu0BDmzuYAC.zKb1NOno4s4bQx2', 'ALHAWAMEDEH', 'Ayham', '0612345677');
INSERT INTO `utilisateur` (`id`, `email`, `mot_de_passe`, `nom`, `prenom`, `telephone`) VALUES ('2', 'a@a.com', '$2a$10$VjPf7eGRWaQegjCqZPK/NORLpNzu0BDmzuYAC.zKb1NOno4s4bQx2', 'ALHAWAMEDEH', 'Amjad', '0613455677');

INSERT INTO `role` (`id`, `denomination`) VALUES ('1', 'ROLE_ADMINISTRATEUR'), ('2', 'ROLE_UTILISATEUR');

INSERT INTO `utilisateur_role` (`id_utilisateur`, `id_role`) VALUES ('1', '1'), ('2', '2');
