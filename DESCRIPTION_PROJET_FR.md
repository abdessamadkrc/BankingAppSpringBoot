# Application Bancaire Multi-Devises - Description Complète

## 📋 Vue d'Ensemble

Application bancaire complète basée sur une architecture microservices avec Spring Boot et React TypeScript, permettant la gestion de comptes multi-devises avec conversion automatique en temps réel lors des transferts.

---

## 🏗️ Architecture Technique

### Architecture Microservices (Backend)

L'application utilise une architecture microservices moderne avec les composants suivants :

#### 1. **EurekaDiscoveryService** (Port 8761)
- **Rôle** : Serveur de découverte et registre de services
- **Technologie** : Netflix Eureka Server
- **Fonction** : 
  - Enregistrement automatique de tous les microservices
  - Découverte dynamique des services
  - Équilibrage de charge
  - Surveillance de la santé des services

#### 2. **Gateway** (Port 8098)
- **Rôle** : Passerelle API unifiée
- **Technologie** : Spring Cloud Gateway Server WebMVC
- **Fonction** :
  - Point d'entrée unique pour toutes les requêtes clients
  - Routage intelligent vers les microservices appropriés
  - Gestion CORS
  - Équilibrage de charge avec découverte de services

#### 3. **CompteService** (Port 8095)
- **Rôle** : Service de gestion des comptes bancaires
- **Base de données** : MySQL (`CompteBDD`)
- **Technologie** : Spring Boot, Spring Data JPA, Spring Data REST
- **Fonctionnalités** :
  - Création de comptes multi-devises (USD, EUR, MAD, etc.)
  - Consultation des comptes et soldes
  - Mise à jour des informations de compte
  - Suppression de comptes
  - API REST complète (CRUD)

**Schéma de la base de données :**
```sql
Table: comptes
- id (BIGINT, PRIMARY KEY, AUTO_INCREMENT)
- type (VARCHAR) -- Code devise: USD, EUR, MAD, GBP, JPY, CAD, AUD, CHF
- solde (DOUBLE) -- Solde du compte dans sa devise
```

#### 4. **TransactionService** (Port 8096)
- **Rôle** : Service de traitement des transactions et transferts
- **Base de données** : MySQL (`TransactionBDD`)
- **Technologie** : Spring Boot, Spring Data JPA, OpenFeign
- **Fonctionnalités** :
  - Transferts d'argent entre comptes
  - Conversion automatique de devises en temps réel
  - Validation des soldes
  - Communication inter-services avec CompteService et ReportingService
  - Historique des transactions

**Schéma de la base de données :**
```sql
Table: transaction
- id (BIGINT, PRIMARY KEY, AUTO_INCREMENT)
- sourceId (BIGINT) -- ID du compte source
- destinationId (BIGINT) -- ID du compte destination
- montant (DOUBLE) -- Montant dans la devise source
- dateTransaction (TIMESTAMP) -- Date et heure de la transaction
```

**Logique de conversion de devises :**
1. Récupération des comptes source et destination
2. Vérification du solde suffisant dans la devise source
3. Si les devises diffèrent :
   - Appel au ReportingService pour obtenir le taux de change
   - Calcul du montant converti : `montant_converti = montant × taux_change`
4. Débit du compte source (devise source)
5. Crédit du compte destination (devise convertie)
6. Enregistrement de la transaction

#### 5. **ReportingService** (Port 8097)
- **Rôle** : Service de reporting et taux de change
- **Technologie** : Spring Boot, Spring WebFlux (réactif)
- **Fonctionnalités** :
  - Récupération des taux de change en temps réel
  - Intégration avec API externe de devises
  - Support de 8+ devises majeures
  - Réponses réactives et performantes

**Devises supportées :**
- USD (Dollar américain)
- EUR (Euro)
- GBP (Livre sterling)
- JPY (Yen japonais)
- MAD (Dirham marocain) 🇲🇦
- CAD (Dollar canadien)
- AUD (Dollar australien)
- CHF (Franc suisse)

---

### Frontend (React TypeScript)

#### Technologies Utilisées
- **React 19** : Framework UI moderne
- **TypeScript** : Typage statique pour la sécurité du code
- **Tailwind CSS** : Framework CSS utilitaire pour un design moderne
- **Axios** : Client HTTP pour les appels API
- **Create React App** : Configuration et build

#### Composants Principaux

**1. AccountForm.tsx**
- Création et modification de comptes
- Sélection de devise (USD, EUR, MAD, etc.)
- Saisie du solde initial
- Validation des formulaires

**2. AccountList.tsx**
- Affichage de tous les comptes
- Visualisation des soldes avec codes devises
- Actions : Éditer, Supprimer
- Rafraîchissement automatique

**3. TransferForm.tsx**
- Sélection du compte source
- Sélection du compte destination
- Saisie du montant (dans la devise source)
- Indication de conversion automatique
- Validation et traitement des transferts

**4. ExchangeRate.tsx**
- Consultation des taux de change
- Sélection de devises (de/vers)
- Affichage du taux en temps réel
- Interface intuitive

---

## 🔄 Flux de Données et Communication

### Flux de Création de Compte
```
Frontend (React) 
    ↓ POST /comptes {type: "USD", solde: 1000}
CompteService 
    ↓ Enregistrement dans MySQL
    ↓ Enregistrement avec Eureka
EurekaDiscoveryService
```

### Flux de Transfert Multi-Devises
```
Frontend (React)
    ↓ POST /transactions/transfer?src=1&dest=2&amount=100
TransactionService
    ↓ GET /comptes/1 (Feign Client)
CompteService → Retourne compte USD avec solde 1000
    ↓ GET /comptes/2 (Feign Client)
CompteService → Retourne compte EUR avec solde 500
    ↓ GET /api/rate?from=USD&to=EUR (Feign Client)
ReportingService → Retourne taux 0.92
    ↓ Calcul: 100 USD × 0.92 = 92 EUR
    ↓ PUT /comptes/1 {solde: 900} (Débit USD)
CompteService → Mise à jour
    ↓ PUT /comptes/2 {solde: 592} (Crédit EUR)
CompteService → Mise à jour
    ↓ Enregistrement transaction dans MySQL
TransactionService → Transaction sauvegardée
    ↓ Réponse 200 OK
Frontend → Affichage succès + rafraîchissement
```

---

## 💾 Bases de Données

### Configuration MySQL (XAMPP)

**CompteBDD** - Base de données des comptes
```sql
CREATE DATABASE CompteBDD;
USE CompteBDD;

-- Table générée automatiquement par Hibernate
CREATE TABLE comptes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(10) NOT NULL,  -- Code devise
    solde DOUBLE NOT NULL
);
```

**TransactionBDD** - Base de données des transactions
```sql
CREATE DATABASE TransactionBDD;
USE TransactionBDD;

-- Table générée automatiquement par Hibernate
CREATE TABLE transaction (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    source_id BIGINT NOT NULL,
    destination_id BIGINT NOT NULL,
    montant DOUBLE NOT NULL,
    date_transaction TIMESTAMP NOT NULL
);
```

**Configuration de connexion :**
- Hôte : `localhost:3306`
- Utilisateur : `root`
- Mot de passe : (vide)
- Mode Hibernate : `update` (création/mise à jour automatique des tables)

---

## 🚀 Déploiement et Exécution

### Prérequis
- **Java 17** ou supérieur
- **Maven 3.6+**
- **Node.js 16+** et npm
- **MySQL** (via XAMPP ou installation standalone)

### Démarrage des Services Backend

**1. Démarrer Eureka Discovery Service (en premier)**
```bash
cd EurekaDiscoveryService
./mvnw.cmd spring-boot:run
# Attendre 30 secondes pour l'initialisation complète
```

**2. Démarrer les autres services (ordre flexible)**
```bash
# Terminal 2 - CompteService
cd CompteService
./mvnw.cmd spring-boot:run

# Terminal 3 - TransactionService
cd TransactionService
./mvnw.cmd spring-boot:run

# Terminal 4 - ReportingService
cd ReportingService
./mvnw.cmd spring-boot:run

# Terminal 5 - Gateway (optionnel)
cd Gateway
./mvnw.cmd spring-boot:run
```

### Démarrage du Frontend

```bash
cd banking-frontend
npm install  # Première fois seulement
npm start
```

L'application s'ouvre automatiquement sur `http://localhost:3000`

---

## 📡 Points d'Accès API

### Via Frontend
- **Application principale** : http://localhost:3000

### Services Backend Directs

**Eureka Dashboard**
```
http://localhost:8761
```
Visualisation de tous les services enregistrés

**CompteService API**
```
GET    http://localhost:8095/comptes           # Liste tous les comptes
GET    http://localhost:8095/comptes/{id}      # Détails d'un compte
POST   http://localhost:8095/comptes           # Créer un compte
PUT    http://localhost:8095/comptes/{id}      # Modifier un compte
DELETE http://localhost:8095/comptes/{id}      # Supprimer un compte
```

**TransactionService API**
```
POST http://localhost:8096/transactions/transfer?src={id}&dest={id}&amount={montant}
```

**ReportingService API**
```
GET http://localhost:8097/api/rate?from={devise}&to={devise}
```

---

## 🎯 Fonctionnalités Principales

### 1. Gestion Multi-Devises
- ✅ Création de comptes dans 8 devises différentes
- ✅ Affichage des soldes avec codes devises
- ✅ Support complet : USD, EUR, GBP, JPY, MAD, CAD, AUD, CHF

### 2. Transferts avec Conversion Automatique
- ✅ Transferts entre comptes de même devise (sans conversion)
- ✅ Transferts entre devises différentes (conversion automatique)
- ✅ Taux de change en temps réel
- ✅ Validation des soldes avant transfert
- ✅ Historique complet des transactions

### 3. Consultation des Taux de Change
- ✅ Visualisation des taux entre toutes les devises
- ✅ Données en temps réel via API externe
- ✅ Interface intuitive avec sélection de devises

### 4. Architecture Microservices
- ✅ Découverte automatique des services (Eureka)
- ✅ Communication inter-services (OpenFeign)
- ✅ Isolation des bases de données (pattern database-per-service)
- ✅ Scalabilité horizontale
- ✅ Résilience et tolérance aux pannes

---

## 🔒 Sécurité et Bonnes Pratiques

### Backend
- ✅ **CORS configuré** pour autoriser les requêtes du frontend
- ✅ **Transactions atomiques** (@Transactional) pour la cohérence des données
- ✅ **Validation des entrées** (montants positifs, soldes suffisants)
- ✅ **Gestion des erreurs** avec messages explicites
- ✅ **Isolation des services** (chaque service a sa propre base de données)

### Frontend
- ✅ **TypeScript** pour la sécurité des types
- ✅ **Validation des formulaires** côté client
- ✅ **Gestion des états de chargement**
- ✅ **Messages d'erreur utilisateur** clairs
- ✅ **Rafraîchissement automatique** après les opérations

---

## 📊 Exemples d'Utilisation

### Scénario 1 : Transfert International (USD → EUR)
```
Compte Source : #1 (USD) - Solde : 1000 USD
Compte Destination : #2 (EUR) - Solde : 500 EUR
Montant à transférer : 100 USD

Processus :
1. Vérification : 1000 USD ≥ 100 USD ✓
2. Récupération taux : 1 USD = 0.92 EUR
3. Conversion : 100 USD × 0.92 = 92 EUR
4. Débit : 1000 - 100 = 900 USD
5. Crédit : 500 + 92 = 592 EUR

Résultat :
- Compte #1 : 900 USD
- Compte #2 : 592 EUR
- Transaction enregistrée : 100 USD (montant source)
```

### Scénario 2 : Transfert Dirham Marocain (MAD → USD)
```
Compte Source : #3 (MAD) - Solde : 10000 MAD
Compte Destination : #1 (USD) - Solde : 900 USD
Montant à transférer : 1000 MAD

Processus :
1. Vérification : 10000 MAD ≥ 1000 MAD ✓
2. Récupération taux : 1 MAD = 0.10 USD
3. Conversion : 1000 MAD × 0.10 = 100 USD
4. Débit : 10000 - 1000 = 9000 MAD
5. Crédit : 900 + 100 = 1000 USD

Résultat :
- Compte #3 : 9000 MAD
- Compte #1 : 1000 USD
- Transaction enregistrée : 1000 MAD
```

### Scénario 3 : Transfert Domestique (EUR → EUR)
```
Compte Source : #2 (EUR) - Solde : 592 EUR
Compte Destination : #4 (EUR) - Solde : 200 EUR
Montant à transférer : 50 EUR

Processus :
1. Vérification : 592 EUR ≥ 50 EUR ✓
2. Détection : Même devise (pas de conversion)
3. Débit : 592 - 50 = 542 EUR
4. Crédit : 200 + 50 = 250 EUR

Résultat :
- Compte #2 : 542 EUR
- Compte #4 : 250 EUR
- Transaction enregistrée : 50 EUR
```

---

## 🛠️ Stack Technologique Complète

### Backend
| Technologie | Version | Utilisation |
|-------------|---------|-------------|
| Spring Boot | 4.0.0 | Framework principal |
| Spring Cloud | 2025.1.0 | Microservices patterns |
| Spring Data JPA | 4.0.0 | Accès aux données |
| MySQL | 8.0+ | Base de données |
| Netflix Eureka | 2025.1.0 | Service discovery |
| OpenFeign | 2025.1.0 | Client REST déclaratif |
| Spring WebFlux | 4.0.0 | Programmation réactive |
| Lombok | 1.18.42 | Réduction du code boilerplate |
| Hibernate | 7.1.8 | ORM |
| Maven | 3.6+ | Gestion des dépendances |
| Java | 17+ | Langage de programmation |

### Frontend
| Technologie | Version | Utilisation |
|-------------|---------|-------------|
| React | 19.2.3 | Framework UI |
| TypeScript | 4.9.5 | Langage typé |
| Tailwind CSS | 3.x | Framework CSS |
| Axios | 1.x | Client HTTP |
| Create React App | 5.0.1 | Configuration et build |

### Infrastructure
| Outil | Utilisation |
|-------|-------------|
| XAMPP | Serveur MySQL local |
| Tomcat | Serveur d'applications (embarqué) |
| npm | Gestionnaire de paquets frontend |

---

## 📈 Avantages de l'Architecture

### Scalabilité
- Chaque microservice peut être déployé et scalé indépendamment
- Équilibrage de charge automatique via Eureka
- Ajout facile de nouvelles instances

### Maintenabilité
- Code organisé par domaine métier
- Séparation claire des responsabilités
- Tests unitaires et d'intégration par service

### Résilience
- Isolation des pannes (un service défaillant n'affecte pas les autres)
- Circuit breakers possibles avec Resilience4j
- Retry automatique des requêtes

### Flexibilité
- Ajout facile de nouvelles devises
- Extension simple avec de nouveaux microservices
- Changement de technologie par service sans impact global

---

## 🎓 Concepts Techniques Implémentés

### Patterns Microservices
- ✅ **Service Discovery** (Eureka)
- ✅ **API Gateway** (Spring Cloud Gateway)
- ✅ **Database per Service**
- ✅ **Inter-Service Communication** (Feign)
- ✅ **Distributed Transactions** (Saga pattern simplifié)

### Patterns Backend
- ✅ **Repository Pattern** (Spring Data JPA)
- ✅ **Service Layer Pattern**
- ✅ **DTO Pattern** (Data Transfer Objects)
- ✅ **Builder Pattern** (Lombok)
- ✅ **Dependency Injection** (Spring)

### Patterns Frontend
- ✅ **Component-Based Architecture**
- ✅ **Hooks Pattern** (useState, useEffect)
- ✅ **Service Layer** (API abstraction)
- ✅ **Controlled Components**
- ✅ **Conditional Rendering**

---

## 🔍 Monitoring et Observabilité

### Eureka Dashboard
- Visualisation de tous les services enregistrés
- Statut de santé en temps réel
- Informations sur les instances

### Spring Boot Actuator
Endpoints disponibles sur chaque service :
```
/actuator/health    # État de santé du service
/actuator/info      # Informations sur l'application
/actuator/metrics   # Métriques de performance
```

### Logs
- Logs structurés avec SLF4J
- Niveaux de log configurables
- Traçabilité des requêtes

---

## 🚧 Évolutions Possibles

### Court Terme
- [ ] Authentification et autorisation (Spring Security + JWT)
- [ ] Pagination des listes de comptes
- [ ] Filtres et recherche avancée
- [ ] Export des transactions (PDF, CSV)
- [ ] Notifications en temps réel (WebSocket)

### Moyen Terme
- [ ] Dashboard administrateur
- [ ] Gestion des utilisateurs et rôles
- [ ] Limites de transfert configurables
- [ ] Frais de transaction
- [ ] Historique détaillé des transactions

### Long Terme
- [ ] Application mobile (React Native)
- [ ] Intégration avec systèmes de paiement externes
- [ ] Machine Learning pour détection de fraude
- [ ] Blockchain pour traçabilité
- [ ] Support de crypto-monnaies

---

## 📝 Conclusion

Cette application bancaire multi-devises démontre une implémentation complète et professionnelle d'une architecture microservices moderne. Elle combine les meilleures pratiques du développement backend (Spring Boot, microservices) avec une interface utilisateur moderne et réactive (React, TypeScript, Tailwind CSS).

Le système de conversion automatique de devises en temps réel, couplé à une architecture scalable et maintenable, en fait une solution robuste et évolutive pour des besoins bancaires réels.

---

## 👨‍💻 Informations Techniques

**Développé avec :**
- Architecture Microservices
- Spring Boot 4.0.0
- React 19 + TypeScript
- MySQL 8.0
- Tailwind CSS

**Patterns implémentés :**
- Service Discovery
- API Gateway
- Database per Service
- CQRS (simplifié)
- Repository Pattern
- Component-Based Architecture

**Année :** 2025
**Licence :** MIT

---

*Application bancaire multi-devises avec conversion automatique en temps réel* 🌍💱🏦
