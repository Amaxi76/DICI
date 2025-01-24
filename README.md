# DICI Project

## Prérequis

### 1. **Fichier CSV à télécharger**
Pour utiliser ce projet, vous devez télécharger un fichier CSV contenant les données immobilières.

- Accédez au lien suivant pour télécharger le fichier :
  [https://files.data.gouv.fr/geo-dvf/latest/csv/2021/](https://files.data.gouv.fr/geo-dvf/latest/csv/2021/)

- Placez le fichier téléchargé dans le répertoire suivant du projet :
  ```
  src/main/resources/data/dataRealEstate.csv
  ```

Assurez-vous que le fichier soit nommé **`dataRealEstate.csv`** pour être compatible avec le projet.

### 2. **Installer Maven**
Ce projet utilise Maven pour la gestion des dépendances et de la compilation. Vous devez installer Maven si ce n'est pas déjà fait.

- Documentation officielle de Maven : [https://maven.apache.org/install.html](https://maven.apache.org/install.html)

Pour vérifier que Maven est bien installé, exécutez la commande suivante dans un terminal :
```bash
mvn -v
```
Vous devriez voir une sortie indiquant la version de Maven installée.

## Compilation et Exécution

### 1. **Compiler le projet**
Dans le terminal, placez-vous à la racine du projet (là où se trouve le fichier `pom.xml`) et exécutez :
```bash
mvn compile
```

### 2. **Exécuter la classe principale**
Pour lancer le projet et exécuter la classe principale `DataRealEstateToDatabase`, exécutez la commande suivante :
```bash
mvn exec:java
```