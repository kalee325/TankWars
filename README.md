# TankWars


This project is a two-player tank game. Players are able to control their tanks to fight
against each other in the arena for victory. Each tank has its own screen on the same
window, and there is a mini-map on the center. There are breakable and unbreakable
walls on the map as defensive, and there are also special items generated on the map for
players to pick up, rising the tension of the game.



Main Class: Game.TRE


Jar File: TankGame.jar


P.S. To launch the jar file, you need to put the Resource file and the jar file in the same directory; Otherwise, it will not launch.


Tank Control:

Player 1
- UP: Forward
- DOWN: Backward
- LEFT: Rotate Left
- RIGHT: Rotate Right
- ENTER: Shoot Bullet
    
Player 2
- W: Forward
- S: Backward
- A: Rotate Left
- D: Rotate Right
- SPACE: Shoot Bullet

Instruction:
- Each tank has 3 lives and 200 health points.
- Each bullet hit cost 50 health points.
- If a tank's health point is below zero, the tank will respawn at its original position.
- If a breakable wall is destroyed, it will respawn again after 10 seconds.
- In every 7 seconds, there would be a special item and a health item generated at a random spot on the map.
- If a tank picks up a special item, the opponent tank would instantly lose 50 health points.
- If a tank picks up a health item, it would heal itself with 50 health points.
- The program will exit automatically after the game is ended.
