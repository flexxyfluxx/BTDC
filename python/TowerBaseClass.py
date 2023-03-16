from ch.aplu.jgamegrid import Actor
from os.path import abspath

class Tower(Actor): 
        def __init__(self, asp, admg, aR, cost, placeX, placeY, upAsp, upAdmg, sprite, key): 
        #asp = attackspeed , admg = atackdamage, cost = price, place = location on map, upAsp/-Admg = upgrades for attackspeed and damage, sprite = variable for sprite datapath, aR = attack Range
            Actor.__init__(self,abspath(sprite)) 
            self.asp = asp 
            self.admg = admg
            self.cost = cost
            self.placeX = placeX
            self.placeY = placeY
            self.upAsp = upAsp
            self.upAdmg = upAdmg
            self.x = self.asp
            self.aR = aR
            self.targetX = 480
            self.targetY = 270
            self.key = key
            
        def upgradeAttackSpeed(self):
            self.asp = self.asp*self.upAsp # upAsp as percentile increase so asp*1.15 ors so every tower has own increase percentage value
            self.x += 10
            
            
        def upgradeAttackDamage(self):
            self.admg = self.admg*self.upAdmg
        
        """def act(self):
            
            self.x = self.x - 1
            if self.x == 0:
                self.x = self.asp
                if sqrt((self.placeX-enemy.placeX)+(self.placeY-enemy.placeY))<= aR:
                    projectile = Projectile(self.admg, enemy(felixproblem))"""