# projet_java
 
# ProjetJava



## Lien vers notre projet 

- [ ] [upload](https://gitlab.com/clelia_s/projetjava.git) files


## Utilisation de l'application 

Lors du lancement du main, soit la classe MainExecution, deux fenêtres graphiques vont s'afficher devant vous: la fenêtre OpenStreetMap ainsi que la fenêtre instruction. Pour une bonne utilisation de l'application, l'utilisateur parcourt la carte à la recherche d'un itinéraire. Une fois celui-ci choisi, il suffit de cliquer sur la carte afin de récupérer dans la console éclipse les coordonnées écrans de celui-ci. Dans le cadre du rendu nous vous proposons des coordonnées pré-remplies adaptées au zoom afin d'éviter différentes erreurs rencontrées. Notamment pour les longs itinéraires, où le grand nombre de candidats traités par le serveur d'OpenStreetMap alourdi le calcul, couplé aux nombreuses requêtes, autres que les nôtres, que le serveur doit traiter; cela nous donne l'erreur " "xmldata" is null ".
 
Nous vous recommandons donc l'itinéraire suivant : 
Start : 478,470
End : 831,730

Une fois les coordonnées rentrées, cliquez sur le bouton "Valider !" afin de lancer l'application. 
Après environ 1 à 2 mins de temps d'attente, pour le court itinéraire proposé, normalement il apparaitra sur la carte. Les points rouges représentent les coordonnées de départ et d'arrivé, les noirs représentent les points de décisions et les bleus, les points d'intérêts. Parallèlement à cela, les instructions s'afficheront sur la fenêtre graphique uniquement si vous agrandissez la fenetre ...


## Name

Application d'itinéraire dans la ville de Lille 

## Description du projet 

L’objectif du projet de programmation informatique est de développer un algorithme similaire à ceux des services de navigation mais à but uniquement piéton. En effet, aujourd’hui ces services ne sont pas adaptés à une utilisation piétonne en raison du manque de visibilité des panneaux de rues ou encore la localisation dynamique permettant de générer ces itinéraires à souvent une marge d’erreur de plusieurs mètres rendant difficile l’estimation des distances. D’autre part, d’après l’article Towards a Landmark-Based Pedestrian Navigation Service Using OSM Data. (Rousell, A. ; Zipf, A.) ISPRS Int. J. Geo-Inf. 2017, 6, 64, utiliser des points de repères visible dans la ville est déjà quelque chose que nous faisons automatiquement pour nous repérer dans un lieu, ce mécanisme est semblable à une carte mentale d’un itinéraire.

L’application que nous devons développé a donc pour but de déterminer un itinéraire adapté à une navigation piétonne, ce qui suppose que les instructions de navigation soient données à partir de points d’intérêts (ou points remarquables) pour chaque point de décision de l’itinéraire (points au niveau desquels le piéton doit faire un choix entre plusieurs possibilités pour sa poursuites d’itinéraire). De plus, il est nécessaire, pour pallier au problème de marge d’erreur due à la localisation GPS du piéton, de générer les instructions précises avant que le piéton entame son itinéraire. L’application ne s’appuie donc pas sur la localisation GPS du piéton mais plutôt sur les coordonnées de départ et d’arrivée de l’itinéraire rentrées par le piéton. 

## Visualisation 

![image](https://gitlab.com/clelia_s/projetjava/-/blob/28ba6feb53d5750d53bc83b40a6495b18fcfaf7b/Capture_d_e%CC%81cran_2022-05-13_a%CC%80_17.49.30.png)

## Amélioration du projet

Dans l'hypothèse où nous aurions pu retravailler notre application, une amélioration possible serait de créer une méthode de choix des candidats selon leurs visibilité. 
D'un côté plus technique, au niveau de l'ergonomique de l'application pour l'utilisateur, il aurait été plus agréable de récupérer les coordonnées directement à partir de la fenêtre graphique ou alors de réduire la taille de la fenêtre instruction et de la laisser au premier plan. D'autre part, la récupération des instructions se fait qu'après agrandissement de la fenêtre, malgré des recherches de notre part nous n'avons pas réussi à régler ce problème, cela serait donc une possible amélioration. 

## Auteurs

Clelia Segouin 
Rouguiatou Ba
Joséphine Bocquet

## Licence


![image](https://www.ensg.eu/-MEP0-/apv/logo_ensg_rwd.png) /n
© ENSG 

