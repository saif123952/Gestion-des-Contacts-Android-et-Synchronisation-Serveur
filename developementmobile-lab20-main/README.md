# LAB 20 — Carnet Contacts Android

**Cours :** Programmation Mobile — Android avec Java

---

## Description

**Carnet Contacts** est une application Android qui lit les contacts enregistrés dans le téléphone, les affiche dans une liste défilante, les synchronise vers un serveur distant via une API REST, puis permet d'effectuer une recherche par nom ou numéro de téléphone directement sur le serveur.

Le backend est développé en PHP avec PDO et stocke les données dans une base MySQL. La communication entre l'application et le serveur est assurée par la bibliothèque Retrofit.

---

## Technologies

| Couche | Outil |
|---|---|
| Application mobile | Android Studio — Java |
| Communication HTTP | Retrofit 2 |
| Sérialisation JSON | Gson |
| Affichage de liste | RecyclerView |
| Backend serveur | PHP 8 / PDO |
| Base de données | MySQL |

---

## Fonctionnalités

- Lecture des contacts du téléphone avec gestion de la permission `READ_CONTACTS`
- Affichage des contacts dans une liste triée alphabétiquement
- Synchronisation des contacts vers le serveur (bilan succès/échec affiché en fin d'envoi)
- Recherche distante par nom ou numéro (minimum 2 caractères)
- Validation des données côté serveur et côté application
- Gestion des erreurs réseau avec messages d'information à l'utilisateur

---

## Structure du projet

```
Application-Number-Book-avec-Android/
├── backend/
│   ├── config/
│   │   └── ConnexionBD.php          — Connexion PDO MySQL
│   ├── modele/
│   │   └── EntreeContact.php        — Objet métier contact
│   ├── service/
│   │   └── GestionnaireContacts.php — Logique CRUD
│   └── api/
│       ├── listerEntrees.php        — GET : tous les contacts
│       ├── ajouterEntree.php        — POST : ajouter un contact
│       └── rechercherEntree.php     — GET : recherche par mot-clé
│
└── android/app/src/main/
    ├── java/com/ennoukra/carnetcontacts/
    │   ├── EntreeContact.java        — Modèle de données
    │   ├── ReponseServeur.java       — Wrapper réponse JSON
    │   ├── ServiceReseau.java        — Interface Retrofit
    │   ├── ClientHttp.java           — Singleton Retrofit
    │   ├── AdaptateurContacts.java   — Adaptateur RecyclerView
    │   └── MainActivity.java         — Activité principale
    └── res/
        └── layout/activity_main.xml  — Interface utilisateur
```

---

## Installation

### Prérequis

- Android Studio (API minimum 24)
- Serveur local : XAMPP ou WAMP avec PHP 8 et MySQL

### Base de données

Créer la base et la table dans phpMyAdmin ou via MySQL :

```sql
CREATE DATABASE carnet_contacts CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE carnet_contacts;

CREATE TABLE entree_contact (
    id_entree    INT AUTO_INCREMENT PRIMARY KEY,
    nom          VARCHAR(150)  NOT NULL,
    telephone    VARCHAR(30)   NOT NULL,
    origine      VARCHAR(20)   DEFAULT 'mobile',
    enregistre_le DATETIME     DEFAULT CURRENT_TIMESTAMP
);
```

### Backend PHP

1. Copier le dossier `backend/` dans le répertoire web de votre serveur :
   - XAMPP : `C:/xampp/htdocs/carnet-contacts-api/`
   - WAMP  : `C:/wamp64/www/carnet-contacts-api/`

2. Vérifier les paramètres dans `config/ConnexionBD.php` (hôte, nom de la base, identifiants).

### Application Android

1. Ouvrir le dossier `android/` dans Android Studio.
2. Dans `ClientHttp.java`, remplacer `192.168.1.10` par l'adresse IP de votre machine sur le réseau local.
3. Compiler et lancer l'application sur un émulateur ou un appareil physique.

---

## Utilisation

1. **Charger les contacts** — l'application demande la permission d'accès aux contacts du téléphone, puis les affiche dans la liste.
2. **Synchroniser vers le serveur** — les contacts chargés sont envoyés un par un à l'API. Un bilan (envoyés / échecs) s'affiche à la fin.
3. **Rechercher** — saisir au moins 2 caractères dans le champ de recherche, puis appuyer sur le bouton. Les résultats proviennent du serveur et remplacent la liste affichée.

---

## Résultats
<img width="444" height="817" alt="image" src="https://github.com/user-attachments/assets/9d90cd4e-f2f7-4f4c-a335-a063ddbe8e0b" />

  
L'application permet de lire les contacts locaux, de les persister dans une base MySQL distante via une API REST, et d'effectuer des recherches en temps réel sur les données stockées. La validation des données est effectuée à la fois côté Android (longueur du mot-clé, champs vides) et côté serveur (format du numéro, champs obligatoires).
