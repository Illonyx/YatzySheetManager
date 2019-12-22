# YatzySheetManager

YatzyProject - Module d'analyse et de statistiques de joueurs de Yatzee

Description du module

A partir d'un fichier Excel contenant les résultats de parties de yatzée, ce logiciel permet
d'afficher un certain nombre de données statistiques selon deux modes : 
- Mode Statistiques : pour chaque joueur : score moyen, pourcentage de yatzées...
- Mode Confrontations : Ce mode permet de comparer les statistiques de deux joueurs en les opposant

**Captures d'écran à mettre*

Pour utiliser le logiciel, il faudra deux fichiers : 
- Obligatoire : Le fichier Excel contenant les résultats qui peut par exemple avoir le format suivant. 
Deux formats sont pris en compte nativement : 
-- le format de fichier du yatzée scandinave (Yatzy) 
-- le format de fichier du yathzee traditionnel

- Optionnel : On peut également ajouter un fichier de règles pour que le logiciel puisse 
analyser son propre format de yathzee. Le fichier à intégrer dans l'application sera un fichier 
sous la forme suivante : **fichier.json**
Si vous voulez intégrer un format par défaut dans l'application, faites un sujet sur Github et on 
regardera pour le faire

Installation du projet

Java 11 et Maven seront nécéssaires pour lancer le projet. 
IntelliJ (dans sa version gratuite) est recommandé pour le développement du projet. 

**A remplir**

Commande pour lancer le projet en local : maven compile javafx:run
Générer un jar executable : 

En cours de developpement

YatzyCore

- Etre plus générique sur les données à introduire (Joueurs, parties à importer..)
- Lecture de fichiers au format JSON
- Mise en place de tests unitaires

- (En etude) Pouvoir générer une feuille de yatz' quelconque à partir de règles
- Installateur pour Windows à créer

YatzyUI

- Mise en place de l'IHM Confrontations
- La liste des erreurs de chargement doit pouvoir être accessible depuis l'interface

Choses à regarder
- Tester avec un fichier xls ou csv

