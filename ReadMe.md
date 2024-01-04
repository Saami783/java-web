# LOCAR - Symfony 6.2

LOCAR est une application web de réservation de véhicules basée sur Java avec le Framework Springboot 6. <br>
Vidéo de démonstration de Locar 👇 <br><br>
[![Démo de Locar](https://i.postimg.cc/sDcRNqQL/Screenshot-2023-09-30-at-13-05-11.png)](https://youtu.be/3xP6SJ6OmOY)

Ce fichier README vous guide à travers les étapes nécessaires pour installer
et démarrer l'application sur votre machine.

Prérequis :
- JDK 17
- Un IDE tel que IntelliJ ou Eclipse

***

## Importer le projet :

IntelliJ reconnait les projets Maven, et propose une fonctionnalité "Load Maven Project" en plus d'un serveur Tomcat intégré.

Pour ce qui est d'Eclipse,

## Installer les dépendances du projet :

Toutes les dépendances du projets sont contenues dans le fichier `pom.xml` qui se situe à la racine du projet.
Pour installer ces dépendances :  
1. Clique droit sur le fichier `pom.xml`
2. Cliquer sur `Maven`
3. Cliquer sur `Download Sources`
4. Répeter l'étape 1 et 2.
5. Cliquer sur `Generates Sources and Update Folders`
6. Répeter l'étape 1 et 2.
7. Cliquer sur `Reload project`

***

# Importation de la base de données MySQL

## Création de la base de données
Créez une nouvelle base de données nommée `locar_java`. 
Les paramètres d'authentification à la base de données se font depuis le fichier `application.properties`.


## Importation des données

Après avoir crée la base de données, vous devez importer son schéma et les données. <br>
Pour ce faire, rendez-vous sur votre système de gestion de base de données (SGBD) et importez les fichiers d'insertion du dossier `sql` dans l'ordre suivant :

1. tables.sql
2. categorie.sql
3. vehicule.sql
4. tarif.sql
5. vehicule_images.sql
6. utilisateurs.sql

Une fois ces étapes terminées, votre base de données sera prête à être utilisée avec l'application web LOCAR. <br>
L'url de l'application est :`http://localhost:8081`.  <br>
#### Note: Le port 8081 a été choisi car le port 8080 est déjà utilisé pour notre serveur SQL. <br> Vous pouvez changer le port du serveur avec la propriété `server.port` disponible dans le fichier `application.properties`.

