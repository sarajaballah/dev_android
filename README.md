# Projet de développement d'applications mobiles
Le but de ce projet est de développer deux applications mobiles qui s'échangent des données entre elles.

## Contact'em All 
Contact'em All est une application mobile compatible avec les appareils android. 

Cette application permet d'afficher la liste des contacts enregistrés sur le mobile de l'utilisateur. Une permission d'accès aux contacts doit être accordée à l'application. Dans le cas contraire, un message d'erreur est affiché sur l'écran principal. Contact'em All se charge de l'envoi des données (la liste des contacts) à la deuxième application.   

## Rate'em All
Rate'em All est aussi destinée aux appareils android. 

Elle permet à l'utilisateur de visiter des sites web et de leur attribuer une note. Le lien du site doit respecter la norme suivante: 
```
http(s)://www.exemple.com
```
La liste des sites déjà visités ainsi que leurs notes associées est affichée sur l'écran principal de l'application. 

Le bouton 'Update minimum rate' permet à l'utilisateur de définir une note minimale. Lors de la prochaine recherche, selon le positionnement de la note du site par rapport à cette note minimale, un message d'avertissment est affiché sur l'écran . L'utilisateur a alors le choix soit de visiter le site malgré la mauvaise note soit d'annuler sa recherche. 

La liste des contacts envoyée par 'Contact'em All' est reçue dès que 'Rate'em All' est lancée . Rate'em All s'occupe alors d'envoyer cette liste à une adresse mail statique. Les détails de ce compte sont définis dans le code. 


## Équipe
* JABALLAH Sarra
* SLAMA Amenallah
