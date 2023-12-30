create table utilisateur (
                             id INT AUTO_INCREMENT PRIMARY KEY,
                             roles varchar(90) not null default "ROLE_USER",
                             nom varchar(120) not null,
                             prenom varchar(120) not null,
                             adresse varchar(255) not null,
                             code_postal int(5) not null,
                             ville varchar(150) not null,
                             telephone varchar(13) not null,
                             password varchar(255) not null,
                             email varchar(255) not null unique,
                             is_verified boolean default false,
                             is_verified_by_admin boolean default false,
                             permis_path varchar(255) not null,
                             age int(2) not null,
                             created_at Datetime default CURRENT_TIMESTAMP,
                             updated_at Datetime default CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

create table categorie (
                           id INT AUTO_INCREMENT PRIMARY KEY,
                           libelle varchar(255) not null,
                           created_at Datetime default CURRENT_TIMESTAMP,
                           updated_at Datetime default CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

create table vehicule (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          modele varchar(100) not null,
                          caution decimal(7,2) not null,
                          nb_km int(100) not null,
                          nb_place int(100) not null,
                          couleur varchar(250) not null,
                          annee int(100) not null,
                          boite_vitesse varchar(255) not null,
                          fuel varchar(255) not null,
                          nb_cheveaux int(100) not null,
                          is_disponible boolean default true,
                          etat varchar(255) not null,
                          description longtext not null,
                          categorie_id int not null,
                          folder varchar(100) not null,
                          created_at Datetime default CURRENT_TIMESTAMP,
                          updated_at Datetime default CURRENT_TIMESTAMP,
                          FOREIGN KEY (categorie_id) REFERENCES categorie(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

create table vehicule_images (
                                 id INT AUTO_INCREMENT PRIMARY key,
                                 vehicule_id int not null,
                                 image_path varchar(255) not null,
                                 created_at Datetime default CURRENT_TIMESTAMP,
                                 FOREIGN KEY (vehicule_id) REFERENCES vehicule(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

create table facture (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         montant_total decimal(7,2) not null,
                         is_paid boolean default false,
                         created_at Datetime default CURRENT_TIMESTAMP,
                         updated_at Datetime default CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

create table reservation (
                             id INT AUTO_INCREMENT PRIMARY KEY,
                             date_debut_reservation Datetime not null,
                             date_fin_reservation Datetime not null,
                             nb_jour_reserve int(100) not null,
                             prix decimal(7,2) not null,
                             utilisateur_id int not null,
                             vehicule_id int not null,
                             facture_id int default NULL,
                             created_at Datetime default CURRENT_TIMESTAMP,
                             updated_at Datetime default CURRENT_TIMESTAMP,
                             foreign key (utilisateur_id) references utilisateur(id),
                             foreign key (vehicule_id) references vehicule(id),
                             foreign key (facture_id) references facture(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

create table image (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       url varchar(255) not null,
                       vehicule_id int not null,
                       created_at Datetime default CURRENT_TIMESTAMP,
                       updated_at Datetime default CURRENT_TIMESTAMP,
                       foreign key (vehicule_id) references vehicule(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

create table tarif (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       calendrier varchar(50) not null,
                       prix decimal(7,2) not null,
                       km int(100) not null,
                       vehicule_id int not null,
                       created_at Datetime default CURRENT_TIMESTAMP,
                       updated_at Datetime default CURRENT_TIMESTAMP,
                       foreign key (vehicule_id) references vehicule(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

create table avis (
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      commentaire longtext not null,
                      note int(100) not null,
                      published_at Datetime default CURRENT_TIMESTAMP,
                      reservation_id int not null,
                      foreign key (reservation_id) REFERENCES reservation(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;