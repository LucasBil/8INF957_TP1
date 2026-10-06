# Lancement du projet
## Requirements
Ce projet a été pensé pour être lancé avec Java 26, aussi il est requis d'avoir cette version de Java installée.
Le projet à été développé sous windows avec Intellij, aussi il est plus simple de le lancer avec ces outils. 

## Lancement via Intellij
Il suffit de lancer le fichier main.java avec Intellij, qui s'occupera de compiler tout seul puis de lancer la classe et afficher les résultats dans la console.

## Lancement des tests
Pour lancer les tests, il suffit de se placer à la racine du projet et de lancer
```
./gradlew cleanTest test
```
## Lancement du projet sans Intellij
Pour lancer le projet, il faut d'abord compiler avec
```
./gradlew build
```
Et ensuite, il faut lancer la classe main en se plaçant dans le dossier [org.example](src/main/java/org/example) et en lançant la commande 
```
java main.java
```
## Lancement sous linux
Pour lancer le projet linux, il faut installer gradlew et lancer les mêmes commande que décrite précedemment avec les droits administrateur. Si l'erreur Permission denied ou bad interpreter: /bin/sh^M apparaît, il faut lancer
```
chmod +x gradlew
sudo apt install dos2unix
dos2unix gradlew
```
