# CAHIER DES CHARGES - APPLICATION BANCAIRE MULTI-DEVISES

## 📋 TABLE DES MATIÈRES
1. [Introduction](#introduction)
2. [Problématique](#problématique)
3. [Solution Proposée](#solution-proposée)
4. [Description du Projet](#description-du-projet)
5. [Interfaces et Fonctionnalités](#interfaces-et-fonctionnalités)
6. [Architecture Technique](#architecture-technique)
7. [Spécifications Fonctionnelles](#spécifications-fonctionnelles)
8. [Spécifications Techniques](#spécifications-techniques)

---

## 🎯 INTRODUCTION

### Contexte du Projet
Dans un monde de plus en plus globalisé, les transactions financières internationales sont devenues une nécessité quotidienne pour les particuliers et les entreprises. Les banques traditionnelles peinent à offrir des solutions flexibles et transparentes pour la gestion de comptes multi-devises.

### Objectif Général
Développer une application bancaire moderne permettant la gestion de comptes en différentes devises avec conversion automatique en temps réel lors des transferts internationaux.

### Public Cible
- Particuliers effectuant des transactions internationales
- Entreprises gérant des opérations multi-devises
- Voyageurs fréquents
- Investisseurs internationaux

---

## ⚠️ PROBLÉMATIQUE

### Problèmes Identifiés

#### 1. **Complexité des Transactions Multi-Devises**
Les utilisateurs rencontrent des difficultés majeures lors de transferts entre comptes de devises différentes :
- Calculs manuels fastidieux des taux de change
- Risque d'erreurs dans les conversions
- Manque de transparence sur les taux appliqués
- Délais importants dans le traitement

#### 2. **Absence de Visibilité en Temps Réel**
- Impossibilité de consulter les taux de change actuels
- Manque d'historique des transactions
- Difficulté à suivre l'évolution des soldes multi-devises

#### 3. **Limitations des Systèmes Bancaires Traditionnels**
- Interfaces utilisateur obsolètes
- Processus de transfert complexes
- Frais cachés et peu transparents
- Support limité des devises internationales

#### 4. **Besoins Non Satisfaits**
- Gestion centralisée de plusieurs comptes en différentes devises
- Conversion automatique et transparente
- Accès instantané aux informations de compte
- Traçabilité complète des opérations

---

## ✅ SOLUTION PROPOSÉE

### Vue d'Ensemble
Une application bancaire web moderne basée sur une architecture microservices, offrant une gestion complète de comptes multi-devises avec conversion automatique en temps réel.

### Fonctionnalités Clés

#### 1. **Gestion Multi-Devises**
- Support de 8 devises internationales (USD, EUR, GBP, JPY, MAD, CAD, AUD, CHF)
- Création illimitée de comptes par devise
- Consultation en temps réel des soldes

#### 2. **Conversion Automatique**
- Détection automatique des devises source et destination
- Application des taux de change en temps réel
- Calcul transparent des montants convertis
- Validation des fonds disponibles avant transaction

#### 3. **Transferts Intelligents**
- Transferts instantanés entre comptes
- Conversion automatique si devises différentes
- Validation des soldes et montants
- Enregistrement complet de l'historique

#### 4. **Consultation des Taux de Change**
- Affichage en temps réel des taux pour toutes les devises
- Interface intuitive et claire
- Mise à jour automatique

#### 5. **Historique des Transactions**
- Liste complète de toutes les transactions
- Filtrage par compte
- Détails complets (montant, date, comptes source/destination)

### Avantages de la Solution

#### Pour les Utilisateurs
- **Simplicité** : Interface intuitive et moderne
- **Rapidité** : Transactions instantanées
- **Transparence** : Taux de change visibles en temps réel
- **Flexibilité** : Gestion de multiples devises

#### Pour l'Entreprise
- **Scalabilité** : Architecture microservices extensible
- **Maintenabilité** : Services indépendants et modulaires
- **Performance** : Traitement distribué des requêtes
- **Évolutivité** : Ajout facile de nouvelles fonctionnalités

---

## 📖 DESCRIPTION DU PROJET

### Architecture Globale

L'application est construite sur une architecture microservices moderne composée de :

#### Backend (Spring Boot)
- **EurekaDiscoveryService** (Port 8761) : Service de découverte et registre des microservices
- **Gateway** (Port 8098) : Point d'entrée unique pour l'API
- **CompteService** (Port 8095) : Gestion des comptes bancaires
- **TransactionService** (Port 8096) : Gestion des transferts et transactions
- **ReportingService** (Port 8097) : Fourniture des taux de change

#### Frontend (React TypeScript)
- Application web moderne (Port 3000)
- Interface utilisateur responsive avec Tailwind CSS
- Communication REST avec les microservices

#### Base de Données (MySQL)
- **CompteBDD** : Stockage des comptes
- **TransactionBDD** : Stockage des transactions

### Technologies Utilisées

#### Backend
- **Spring Boot 4.0** : Framework principal
- **Spring Cloud Netflix Eureka** : Service discovery
- **Spring Cloud Gateway** : API Gateway
- **Spring Cloud OpenFeign** : Communication inter-services
- **Spring Data JPA** : Persistance des données
- **MySQL** : Base de données relationnelle
- **Lombok** : Réduction du code boilerplate
- **Maven** : Gestion des dépendances

#### Frontend
- **React 18** : Bibliothèque UI
- **TypeScript** : Typage statique
- **Tailwind CSS** : Framework CSS utility-first
- **Axios** : Client HTTP
- **Vite** : Build tool moderne

---

## 🖥️ INTERFACES ET FONCTIONNALITÉS

### 1. Interface de Gestion des Comptes

#### Description
Interface principale permettant de visualiser et gérer tous les comptes bancaires de l'utilisateur.

#### Fonctionnalités
- **Affichage de la liste des comptes** : Tableau présentant tous les comptes avec leurs informations
- **Informations affichées** :
  - ID du compte
  - Devise du compte (USD, EUR, GBP, etc.)
  - Solde actuel
  - Date de création
- **Actions disponibles** :
  - Suppression de compte
  - Rafraîchissement de la liste

#### Éléments Visuels
- Tableau responsive avec en-têtes clairs
- Badges colorés pour les devises
- Boutons d'action avec icônes
- Design moderne avec Tailwind CSS

#### Cas d'Usage
```
Utilisateur : Consulte ses comptes
Système : Affiche la liste complète avec soldes actualisés
Utilisateur : Identifie rapidement ses avoirs par devise
```

---

### 2. Interface de Création de Compte

#### Description
Formulaire permettant la création de nouveaux comptes bancaires dans différentes devises.

#### Fonctionnalités
- **Sélection de la devise** : Menu déroulant avec 8 devises disponibles
  - USD (Dollar Américain)
  - EUR (Euro)
  - GBP (Livre Sterling)
  - JPY (Yen Japonais)
  - MAD (Dirham Marocain)
  - CAD (Dollar Canadien)
  - AUD (Dollar Australien)
  - CHF (Franc Suisse)
- **Définition du solde initial** : Champ numérique pour le montant de départ
- **Validation** : Vérification des données avant création
- **Feedback** : Messages de succès ou d'erreur

#### Règles de Validation
- Solde initial ≥ 0
- Devise obligatoire
- Format numérique respecté

#### Cas d'Usage
```
Utilisateur : Clique sur "Créer un compte"
Utilisateur : Sélectionne "EUR" et entre "1000"
Système : Valide les données
Système : Crée le compte et affiche un message de succès
Système : Actualise la liste des comptes
```

---

### 3. Interface de Transfert d'Argent

#### Description
Interface sophistiquée permettant d'effectuer des transferts entre comptes avec conversion automatique de devises.

#### Fonctionnalités
- **Sélection du compte source** : Menu déroulant listant tous les comptes disponibles
- **Sélection du compte destination** : Menu déroulant excluant le compte source
- **Saisie du montant** : Champ numérique pour le montant à transférer
- **Conversion automatique** : Le système détecte si les devises diffèrent et applique le taux de change
- **Validation en temps réel** : Vérification du solde suffisant
- **Confirmation** : Message de succès avec détails de la transaction

#### Processus de Transfert

##### Cas 1 : Même Devise
```
Compte Source : EUR - Solde 1000€
Compte Destination : EUR - Solde 500€
Montant : 200€

Résultat :
- Compte Source : 800€ (-200€)
- Compte Destination : 700€ (+200€)
```

##### Cas 2 : Devises Différentes (avec conversion)
```
Compte Source : USD - Solde $1000
Compte Destination : EUR - Solde 500€
Montant : $200
Taux USD→EUR : 0.92

Processus :
1. Vérification : $1000 ≥ $200 ✓
2. Déduction : $1000 - $200 = $800
3. Conversion : $200 × 0.92 = 184€
4. Ajout : 500€ + 184€ = 684€

Résultat :
- Compte Source : $800 (-$200)
- Compte Destination : 684€ (+184€)
```

#### Règles de Validation
- Montant > 0
- Solde source suffisant
- Comptes source et destination différents
- Taux de change disponible (si devises différentes)

#### Messages d'Erreur
- "Solde insuffisant"
- "Échec de récupération du taux de change"
- "Montant invalide"
- "Veuillez sélectionner des comptes différents"

---

### 4. Interface des Taux de Change

#### Description
Tableau de bord affichant en temps réel tous les taux de change disponibles pour les conversions.

#### Fonctionnalités
- **Affichage matriciel** : Tableau croisé montrant tous les taux de conversion
- **Mise à jour en temps réel** : Taux actualisés depuis le ReportingService
- **Lecture intuitive** : 
  - Ligne = Devise source (FROM)
  - Colonne = Devise destination (TO)
  - Cellule = Taux de conversion

#### Exemple de Lecture
```
Ligne USD, Colonne EUR : 0.92
Signification : 1 USD = 0.92 EUR
Pour convertir : Montant_USD × 0.92 = Montant_EUR
```

#### Devises Supportées
| Code | Devise | Symbole |
|------|--------|---------|
| USD | Dollar Américain | $ |
| EUR | Euro | € |
| GBP | Livre Sterling | £ |
| JPY | Yen Japonais | ¥ |
| MAD | Dirham Marocain | DH |
| CAD | Dollar Canadien | C$ |
| AUD | Dollar Australien | A$ |
| CHF | Franc Suisse | CHF |

#### Cas d'Usage
```
Utilisateur : Consulte les taux de change
Système : Affiche la matrice complète des taux
Utilisateur : Vérifie le taux USD→EUR avant un transfert
Utilisateur : Constate que 1 USD = 0.92 EUR
Utilisateur : Décide d'effectuer le transfert
```

---

### 5. Interface d'Historique des Transactions

#### Description
Vue complète de toutes les transactions effectuées avec possibilité de filtrage par compte.

#### Fonctionnalités
- **Liste complète** : Affichage de toutes les transactions
- **Filtrage par compte** : Sélection d'un compte spécifique pour voir ses transactions
- **Informations détaillées** :
  - ID de la transaction
  - ID du compte source
  - ID du compte destination
  - Montant transféré (dans la devise source)
  - Date et heure de la transaction
- **Tri chronologique** : Transactions les plus récentes en premier

#### Éléments Affichés
```
┌─────────────────────────────────────────────────────────────┐
│ ID | Source | Destination | Montant | Date                 │
├─────────────────────────────────────────────────────────────┤
│ 15 | 3      | 5          | 200.00  | 2025-12-17 14:30:25  │
│ 14 | 7      | 3          | 150.50  | 2025-12-17 13:15:10  │
│ 13 | 5      | 7          | 500.00  | 2025-12-17 12:00:00  │
└─────────────────────────────────────────────────────────────┘
```

#### Filtrage
- **Toutes les transactions** : Vue globale de l'activité
- **Par compte spécifique** : Transactions où le compte est source OU destination

#### Cas d'Usage
```
Utilisateur : Accède à l'historique
Système : Affiche toutes les transactions
Utilisateur : Sélectionne "Compte #3"
Système : Filtre et affiche uniquement les transactions du compte #3
Utilisateur : Vérifie un transfert effectué ce matin
```

---

## 🏗️ ARCHITECTURE TECHNIQUE

### Diagramme de l'Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                    FRONTEND (React)                         │
│                    Port 3000                                │
│  ┌──────────┐ ┌──────────┐ ┌──────────┐ ┌──────────┐      │
│  │ Comptes  │ │Transfert │ │  Taux    │ │Historique│      │
│  └──────────┘ └──────────┘ └──────────┘ └──────────┘      │
└─────────────────────────────────────────────────────────────┘
                          │ HTTP/REST
                          ▼
┌─────────────────────────────────────────────────────────────┐
│              EUREKA DISCOVERY SERVICE                       │
│                    Port 8761                                │
│         (Registre des microservices)                        │
└─────────────────────────────────────────────────────────────┘
                          │
        ┌─────────────────┼─────────────────┐
        ▼                 ▼                 ▼
┌──────────────┐  ┌──────────────┐  ┌──────────────┐
│   COMPTE     │  │ TRANSACTION  │  │  REPORTING   │
│   SERVICE    │  │   SERVICE    │  │   SERVICE    │
│  Port 8095   │  │  Port 8096   │  │  Port 8097   │
│              │  │              │  │              │
│ - Créer      │  │ - Transférer │  │ - Taux de    │
│ - Lister     │  │ - Historique │  │   change     │
│ - Supprimer  │  │ - Valider    │  │              │
│ - Mettre à   │  │ - Convertir  │  │              │
│   jour       │  │              │  │              │
└──────────────┘  └──────────────┘  └──────────────┘
        │                 │
        ▼                 ▼
┌──────────────┐  ┌──────────────┐
│  CompteBDD   │  │TransactionBDD│
│   (MySQL)    │  │   (MySQL)    │
└──────────────┘  └──────────────┘
```

### Communication Inter-Services

#### Feign Clients
Le TransactionService communique avec les autres services via OpenFeign :

1. **CompteRestClient** : Communication avec CompteService
   - `getCompte(id)` : Récupérer un compte
   - `updateCompte(id, compte)` : Mettre à jour un compte

2. **ReportingRestClient** : Communication avec ReportingService
   - `getExchangeRate(from, to)` : Obtenir le taux de change

---

## 📋 SPÉCIFICATIONS FONCTIONNELLES

### RF1 : Gestion des Comptes

#### RF1.1 : Créer un Compte
- **Acteur** : Utilisateur
- **Préconditions** : Aucune
- **Données** : Devise, Solde initial
- **Traitement** : 
  1. Validation des données
  2. Création du compte en base
  3. Attribution d'un ID unique
  4. Date de création automatique
- **Résultat** : Compte créé et visible dans la liste

#### RF1.2 : Consulter les Comptes
- **Acteur** : Utilisateur
- **Préconditions** : Aucune
- **Traitement** : Récupération de tous les comptes
- **Résultat** : Liste complète affichée

#### RF1.3 : Supprimer un Compte
- **Acteur** : Utilisateur
- **Préconditions** : Compte existe
- **Traitement** : Suppression définitive
- **Résultat** : Compte retiré de la liste

### RF2 : Transferts d'Argent

#### RF2.1 : Transfert Même Devise
- **Acteur** : Utilisateur
- **Préconditions** : 
  - 2 comptes existent
  - Solde source suffisant
- **Données** : ID source, ID destination, Montant
- **Traitement** :
  1. Validation du solde
  2. Déduction du compte source
  3. Ajout au compte destination
  4. Enregistrement de la transaction
- **Résultat** : Soldes mis à jour, transaction enregistrée

#### RF2.2 : Transfert Multi-Devises
- **Acteur** : Utilisateur
- **Préconditions** : 
  - 2 comptes de devises différentes
  - Solde source suffisant
  - Taux de change disponible
- **Données** : ID source, ID destination, Montant
- **Traitement** :
  1. Détection des devises différentes
  2. Récupération du taux de change
  3. Validation du solde
  4. Déduction du compte source
  5. Conversion du montant
  6. Ajout au compte destination
  7. Enregistrement de la transaction
- **Résultat** : Soldes mis à jour avec conversion, transaction enregistrée

### RF3 : Consultation des Taux

#### RF3.1 : Afficher les Taux de Change
- **Acteur** : Utilisateur
- **Préconditions** : Aucune
- **Traitement** : Récupération des taux depuis ReportingService
- **Résultat** : Matrice des taux affichée

### RF4 : Historique

#### RF4.1 : Consulter Toutes les Transactions
- **Acteur** : Utilisateur
- **Préconditions** : Aucune
- **Traitement** : Récupération de toutes les transactions
- **Résultat** : Liste complète affichée

#### RF4.2 : Filtrer par Compte
- **Acteur** : Utilisateur
- **Préconditions** : Compte existe
- **Données** : ID du compte
- **Traitement** : Filtrage des transactions
- **Résultat** : Transactions du compte affichées

---

## 🔧 SPÉCIFICATIONS TECHNIQUES

### API Endpoints

#### CompteService (Port 8095)

```
GET    /comptes              - Liste tous les comptes
POST   /comptes              - Crée un nouveau compte
GET    /comptes/{id}         - Récupère un compte par ID
PUT    /comptes/{id}         - Met à jour un compte
DELETE /comptes/{id}         - Supprime un compte
```

#### TransactionService (Port 8096)

```
POST   /transactions/transfer           - Effectue un transfert
       Params: src, dest, amount
       
GET    /transactions                    - Liste toutes les transactions

GET    /transactions/account/{id}       - Transactions d'un compte
```

#### ReportingService (Port 8097)

```
GET    /api/rate                        - Obtient un taux de change
       Params: from, to
       
GET    /api/rates                       - Liste tous les taux
```

### Modèles de Données

#### Compte
```java
{
  "id": Long,
  "type": String,        // Code devise (USD, EUR, etc.)
  "solde": Double,
  "dateCreation": Date
}
```

#### Transaction
```java
{
  "id": Long,
  "sourceId": Long,
  "destinationId": Long,
  "montant": Double,     // Montant dans la devise source
  "dateTransaction": Instant
}
```

#### ExchangeRate
```java
{
  "from": String,
  "to": String,
  "rate": Double
}
```

### Configuration des Services

#### application.properties (CompteService)
```properties
server.port=8095
spring.application.name=COMPTE-SERVICE
spring.datasource.url=jdbc:mysql://localhost:3306/CompteBDD
spring.datasource.username=root
spring.datasource.password=
eureka.client.service-url.defaultZone=http://localhost:8761/eureka
```

#### application.properties (TransactionService)
```properties
server.port=8096
spring.application.name=TRANSACTION-SERVICE
spring.datasource.url=jdbc:mysql://localhost:3306/TransactionBDD
spring.datasource.username=root
spring.datasource.password=
eureka.client.service-url.defaultZone=http://localhost:8761/eureka
```

### Sécurité et Validation

#### Validations Métier
- Montant > 0
- Solde suffisant avant transfert
- Comptes source et destination différents
- Devises valides

#### Gestion des Erreurs
- Exceptions métier avec messages clairs
- Codes HTTP appropriés (200, 400, 404, 500)
- Messages d'erreur en français côté frontend

---

## 📊 CONCLUSION

Cette application bancaire multi-devises répond aux besoins modernes de gestion financière internationale en offrant :

✅ **Simplicité d'utilisation** : Interface intuitive et moderne
✅ **Conversion automatique** : Pas de calculs manuels nécessaires
✅ **Transparence totale** : Taux de change visibles en temps réel
✅ **Traçabilité complète** : Historique détaillé de toutes les opérations
✅ **Architecture robuste** : Microservices scalables et maintenables
✅ **Technologies modernes** : Stack technique à jour et performant

Le projet démontre une maîtrise complète du développement full-stack avec une architecture microservices professionnelle.
