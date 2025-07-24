# 📊 Application de Suivi & Contrôle – Managem CMG

Cette application est un système web développé avec **Spring Boot** (backend) et **HTML/JavaScript** (frontend), destiné à remplacer les fichiers Excel de suivi logistique pour les différentes opérations des mines (DS, KA, Chaux, Expéditions…).

---

## 🧱 Fonctionnalités principales

### ✅ Tables de saisie
- **DS Classique / DS Nord** : arrivages par poste, transporteur, BL, etc.
- **Chaux Lafarge / Cosumar** : avec calculs automatiques (Net CMG, ECART…)
- **Expéditions CC** : zinc, plomb, cuivre, bulk…
- **Contrôle Bascule HJ/DS** : vérification des écarts entre valeurs saisies et attendues

### 📆 Rapport dynamique
- Page `rapportHajjar.html` avec :
  - Sélection de date
  - Tableaux synthétiques horizontaux (P1, P2, P3, Global)
  - Export Excel stylé avec totaux

### 📈 Synthèses automatiques
- Journalière / Mensuelle / Annuelle
- Totaux globaux et par table

### 🛠 Page admin dynamique
- Gérer les **valeurs par défaut** utilisées dans tous les formulaires :
  - Transporteurs
  - Postes
  - Lieux de décharge / chargement
- Ajouter / Modifier / Supprimer sans changer le code

---

## 🧑‍💻 Technologies

- **Spring Boot 3** (REST API)
- **JPA / Hibernate** (MySQL ou H2)
- **Thymeleaf** (optionnel)
- **HTML5, CSS3, JavaScript**
- **Apache POI** (pour export Excel)
- **Spring Security** (authentification et accès sécurisé)

---

## 🚀 Lancement du projet

### 1. Configuration
- Base de données configurée dans `application.properties`
- Importer le projet dans **IntelliJ IDEA**

### 2. Lancer le backend
```bash
./mvnw spring-boot:run
