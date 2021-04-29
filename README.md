# RES - Laboratoire SMTP

> Auteurs : Gwendoline Dössegger et Cassandre Wojciechowski
>
> Date : 29.04.2021
>
> Cours : RES

## Description du projet

Au cours de ce projet, une application client TCP en Java utilisant la Socket API pour communiquer avec un serveur SMTP a été créée. Le but principal est de permettre à un utilisateur d'envoyer une campagne de mails de blagues à des "victimes". 

A partir de fichiers contenant une liste d'adresses e-mail et des modèles de mails à envoyer, l'application va générer des groupes de personnes et leur envoyer des mails standards de la part d'une des personnes membre de ce groupe. 



## Mocker Server

### Qu'est-ce qu'un `Mock Server` ?

Un `mock server` permet de tester des envois de mails sans les envoyer réellement. Le serveur va capturer les mails envoyés et les afficher sur une interface web, sans qu'ils parviennent dans les boîtes mails des destinataires. 

Ces serveurs permettent à des développeurs de tester les envois de mails automatisés sans surcharger une adresse mail existante. 

### Instructions d'installations

Dans le cadre de notre projet, nous avons utilisé `MockMock`, un projet de `mock server` provenant de [Github](https://github.com/tweakers/MockMock). 

L'installation est très facile et très rapide, il faut simplement télécharger le fichier `.jar` via ce [lien](https://github.com/tweakers-dev/MockMock/blob/master/release/MockMock.jar?raw=true). Attention, il y a une modification à apporter au `pom.xml` selon les indications contenues dans cette [PR](https://github.com/tweakers/MockMock/pull/8/commits/fa4bea3079d88d7d7b9a28e3b0864ba6f3d9f7ff). 

`MockMock` va directement écouter sur le port 25 pour le protocole SMTP et l'interface web sera accessible sur le port 8282. Il peut être nécessaire de préciser les ports qu'on veut utiliser avec les options `-p` et `-h`. 

```shell
// Pour lancer MockMock avec des ports personnalisés
$ java -jar MockMock.jar -p 25000 -h 8080

// Pour lancer MockMock avec les valeurs par défaut
$ java -jar MockMock.jar
```

Après avoir lancé l'application, il faut se connecter sur l'interface web en passant par un navigateur et en allant sur `http://localhost:8282` ou sur le port précisé ci-dessus avec l'option `-h`. Sur l'interface web, tous les mails envoyés apparaissent. 

## Utilisation de l'application

// Lancement du Docker

## Description de l'implémentation

### Diagramme de classes

// Diagramme à créer + explications textuelles à fournir sur les classes et leurs rôles

### Exemples

// Ajouter des screenshots pour montrer un exemple de communication entre l'application et le serveur SMTP



