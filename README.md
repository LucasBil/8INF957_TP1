# lancement de l'application avec Docker

**Pour faire fonctionner docker sur windows, il est requis d'avoir l'application docker desktop ouverte**

Pour lancer les tests, lancer à la racine du projet 

```
docker build --target test -t hotel-tests .
docker run --rm hotel-tests
```
Le rapport de test détaillé est diponible dans [index.html](8INF957_TP1/build/reports/index.html) (lien indisponible sur github, le fichier n'est pas commit)

Pour lancer le build, lancer à la racine du projet 

```
docker build --target runtime -t hotel-app .
docker run --rm hotel-app
```
 Il suffit ensuite de lancer le main.java pour lancer l'application
