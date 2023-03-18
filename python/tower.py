import syspaths
from ch.aplu.jgamegrid import Actor
from os.path import abspath
from de.wvsberlin.vektor import Vektor


class Tower(Actor):
    def __init__(self, atkSpeed, atkDmg, atkRange, cost, pos, atkSpeedIncrement, atkDmgIncrement, sprite, key):
        Actor.__init__(self, abspath(sprite))
        self.atkSpeed = atkSpeed
        self.atkDmg = atkDmg
        self.cost = cost
        self.pos = pos
        self.atkSpeedIncrement = atkSpeedIncrement
        self.atkDmgIncrement = atkDmgIncrement
        self.atkCooldown = 0
        self.atkRange = atkRange
        self.targetPos = Vektor(480, 270)
        self.key = key

    def tick(self):
        pass

    def upgradeAttackSpeed(self):
        # Attack speed increment as a factor, eg. every level, attack speed is multiplied by 1.15
        self.atkSpeed = self.atkSpeed * self.atkSpeedIncrement

    def upgradeAttackDamage(self):
        self.atkDmg = self.atkDmg * self.atkDmgIncrement

    """def act(self):
            
            self.atkCooldown = self.atkCooldown - 1
            if self.atkCooldown == 0:
                self.atkCooldown = self.atkSpeed
                if sqrt((self.placeX-enemy.placeX)+(self.placeY-enemy.placeY))<= atkRange:
                    projectile = Projectile(self.atkDmg, enemy(feliatkCooldownproblem))"""
