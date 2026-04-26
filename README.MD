# Jeu de labyrinthe en console — Java

Jeu de labyrinthe en ligne de commande développé en Java. Le joueur se déplace dans des niveaux générés à partir de fichiers de configuration, collecte des pièces, évite des pièges et des ennemis pour progresser jusqu'à la victoire.

---

## Fonctionnalités

- **Plusieurs niveaux** : plusieurs niveaux inclus (`level1.txt`, `level2.txt`, `level3.txt`), joués à la suite.
- **Déplacement libre** : le joueur se déplace avec les touches `Z`, `Q`, `S`, `D`.
- **Carte torique** : les bords de la carte sont connectés — sortir d'un côté fait réapparaître le personnage du côté opposé.
- **Collecte de pièces** : chaque pièce (`.`) rapporte **+10 points**. Collecter toutes les pièces d'un niveau permet de passer au suivant.
- **Pièges** : marcher sur un piège (`*`) fait perdre **2 vies** et ramène le joueur à sa position de départ.
- **Portes verrouillées** : les portes (`D`) bloquent le passage.
- **Ennemis Zombies** : des ennemis (`R`) se déplacent aléatoirement. Une collision fait perdre **1 vie** et réinitialise les ennemis.
- **Ennemis Fantômes** : des fantômes (`G`) se déplacent en ligne droite, ignorent les obstacles, et changent de direction pour intercepter le joueur lorsqu'ils sont alignés sur la même ligne ou colonne.
- **Ennemis Chasseurs** : des chasseurs (`C`) poursuivent activement le joueur via un algorithme de pathfinding BFS. Une collision fait perdre **2 vies** et réinitialise les ennemis.
- **Vies** : le joueur démarre avec **5 vies**. À 0 vies, c'est « Game Over » — une option de recommencer est proposée.
- **Score et vies persistants** : le score et les vies sont conservés lors du passage au niveau suivant.
- **Spawn aléatoire** : le joueur apparaît à une position libre aléatoire à chaque début de niveau.

---

## Structure du projet

```
game/
├── src/
│   ├── module-info.java
│   ├── game/
│   │   └── Game.java                        # Point d'entrée — gestion de la boucle de jeu
│   └── generation/
│       ├── Level.java                        # Chargement et logique des niveaux
│       ├── characters/
│       │   ├── Character.java               # Classe abstraite de base pour tous les personnages
│       │   ├── Player.java                  # Joueur (position, score, vies, saisie clavier)
│       │   ├── Enemy.java                   # Ennemi de base — déplacement aléatoire (Zombie)
│       │   ├── Ghost.java                   # Fantôme — ligne droite, intercepte le joueur
│       │   └── Hunter.java                  # Chasseur — pathfinding BFS vers le joueur
│       └── map/
│           ├── Cell.java                    # Case de la grille (vide, piège, pièce)
│           ├── Wall.java                    # Mur infranchissable
│           ├── LockedDoor.java              # Porte verrouillée
│           └── Movement.java               # Enum des directions de déplacement
├── conf/
│   ├── level1.txt                           # Fichier du niveau 1
│   ├── level2.txt                           # Fichier du niveau 2
│   └── level3.txt                           # Fichier du niveau 3
├── bin/                                     # Dossier de sortie des classes compilées
└── doc/                                     # Javadoc générée
```

---

## Légende de la carte

| Symbole | Signification                                    |
|---------|--------------------------------------------------|
| `#`     | Mur (infranchissable)                            |
| `.`     | Pièce à collecter (+10 pts)                      |
| `*`     | Piège (-2 vies, retour au départ)                |
| `D`     | Porte verrouillée                                |
| `R`     | Zombie — se déplace aléatoirement                |
| `G`     | Fantôme — ignore les obstacles, intercepte       |
| `C`     | Chasseur — poursuit le joueur par BFS (-2 vies)  |
| ` `     | Case vide (déplacement libre)                    |
| `X`     | Position du joueur (1ère lettre du nom)          |

---

## Contrôles

| Touche | Action         |
|--------|----------------|
| `Z`    | Monter         |
| `S`    | Descendre      |
| `Q`    | Aller à gauche |
| `D`    | Aller à droite |

---

## Compilation et lancement

### Prérequis

- **Java 11+** (module system requis)

### 1. Compiler

Depuis la racine du projet :

```bash
javac -d bin src/module-info.java src/generation/map/Movement.java src/generation/map/Cell.java src/generation/map/Wall.java src/generation/map/LockedDoor.java src/generation/characters/Character.java src/generation/characters/Enemy.java src/generation/characters/Ghost.java src/generation/characters/Hunter.java src/generation/characters/Player.java src/generation/Level.java src/game/Game.java
```

### 2. Lancer le jeu

```bash
java --module-path bin -m game/game.Game conf/level1.txt conf/level2.txt conf/level3.txt
```

Le programme demande ensuite votre nom, puis la partie commence.

---

## Auteur

**Abdelhamid AKHATAR** — [abdelhamid.akhatar@etu.cyu.fr](mailto:abdelhamid.akhatar@etu.cyu.fr)
