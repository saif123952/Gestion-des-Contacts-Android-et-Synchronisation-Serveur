📘 Présentation

Ce laboratoire consiste à développer une application Android permettant de récupérer les contacts enregistrés sur le téléphone, de les afficher dans une interface moderne puis de les envoyer vers un serveur distant grâce à une API REST.

L’application offre également une fonctionnalité de recherche distante permettant de retrouver rapidement un contact enregistré dans la base de données MySQL.

Le backend a été développé en PHP avec PDO afin d’assurer une communication sécurisée avec la base de données.

🎯 Objectifs du TP

À travers ce projet, plusieurs compétences ont été mises en pratique :

accès aux contacts Android ;
gestion des permissions utilisateur ;
utilisation de RecyclerView ;
communication client/serveur avec Retrofit ;
échange de données JSON ;
création d’une API REST en PHP ;
stockage et recherche des données dans MySQL.
🧩 Architecture Générale

Le projet est divisé en deux grandes parties :

Partie	Description
Application Android	Lecture et affichage des contacts
Serveur PHP/MySQL	Stockage et recherche des données
📱 Partie Android
🔹 Fonctionnement

L’application récupère automatiquement les contacts du téléphone après validation de la permission utilisateur.

Les données affichées :

nom du contact ;
numéro de téléphone.

Les contacts sont triés puis affichés dans une liste dynamique grâce à RecyclerView.

🔹 Fichiers Principaux
MainActivity.java

Gère :

chargement des contacts ;
synchronisation avec le serveur ;
recherche distante ;
gestion des boutons et événements.
AdaptateurContacts.java

Permet d’afficher les contacts dans le RecyclerView.

ServiceReseau.java

Interface Retrofit contenant les requêtes HTTP utilisées pour communiquer avec l’API.

ClientHttp.java

Configuration de Retrofit et création du client réseau.

💻 Partie Serveur PHP
🔹 Base de Données

Une base MySQL a été créée pour enregistrer les contacts synchronisés depuis l’application Android.

Table utilisée :

contacts_mobile

Champs principaux :

id
nom
telephone
source
date_creation
🔹 Backend PHP

Le backend est organisé en plusieurs couches afin d’obtenir une structure plus professionnelle :

Fichier	Rôle
ConnexionBD.php	Connexion PDO
ContactModel.php	Représentation d’un contact
GestionContacts.php	Traitement CRUD
ajouter.php	Ajout des contacts
rechercher.php	Recherche distante
liste.php	Récupération des données
🔄 Communication Réseau

La communication entre Android et le serveur est réalisée avec :

✅ Retrofit 2
✅ Gson
✅ API REST JSON

Les données sont envoyées sous forme de requêtes HTTP.

🛠️ Installation du Projet
🔹 Base de Données

Création de la base :

CREATE DATABASE contacts_db;

Création de la table :

CREATE TABLE contacts_mobile (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(120),
    telephone VARCHAR(30),
    source VARCHAR(20),
    date_creation DATETIME DEFAULT CURRENT_TIMESTAMP
);
🔹 Serveur PHP

Le dossier backend doit être placé dans :

htdocs/contact-api/

ou :

www/contact-api/

selon le serveur utilisé.

🔹 Configuration Android

Dans ClientHttp.java, l’adresse IP locale du serveur doit être modifiée :

private static final String BASE_URL = "http://192.168.1.15/contact-api/";
📋 Utilisation de l’Application
1️⃣ Charger les contacts

L’utilisateur autorise l’accès aux contacts puis la liste apparaît automatiquement.

2️⃣ Synchronisation

Les contacts sont envoyés un par un vers le serveur distant.

À la fin, un message affiche :

nombre de contacts envoyés ;
nombre d’erreurs éventuelles.
3️⃣ Recherche

L’utilisateur saisit un mot-clé ou un numéro puis lance la recherche.

Les résultats récupérés depuis MySQL sont affichés directement dans la liste.

📊 Résultats Obtenus

Les tests réalisés montrent que :

✅ les contacts sont récupérés correctement
✅ l’affichage RecyclerView fonctionne normalement
✅ la synchronisation avec le serveur réussit
✅ les recherches distantes sont rapides
✅ les validations évitent les données incorrectes
✅ la communication Retrofit fonctionne correctement

🎨 Personnalisation Réalisée

Afin de rendre le projet différent du modèle initial, plusieurs modifications ont été apportées :

changement des noms des classes ;
nouvelle organisation des dossiers backend ;
modification du design de l’interface ;
ajout de messages d’information utilisateur ;
structure différente des fichiers API ;
renommage des tables et colonnes MySQL.
📁 Organisation du Projet
Contact-Mobile-App
 ┣ 📂 android
 ┣ 📂 backend
 ┣ 📂 database
 ┣ 📂 screenshots
 ┣ 📜 README.md
 ┗ 📜 contacts_db.sql
✅ Conclusion

Ce laboratoire nous a permis de comprendre comment développer une application Android capable de communiquer avec un serveur distant via une API REST.

Nous avons également appris à manipuler les contacts du téléphone, utiliser Retrofit pour les échanges réseau et stocker les données dans une base MySQL grâce à un backend PHP structuré.
