from projectiles import TOWER1_PROJ, TOWER2_PROJ, TOWER3_PROJ
from tower import Tower
from os.path import abspath

class Tower1(Tower):
    cost = 250

    def __init__(self, pos, key, game):
        Tower.__init__(self, attackSpeed=5, attackDamage=2, attackRange=40, pierce=1, pos=pos,
                       attackSpeedIncrement=1.05, attackDamageIncrement=1.15, 
                       costUpgradeAttackSpeed=200, costUpgradeAttackDamage=75, 
                       costUpgrade3=220, upgrade3Text="Attackrange:", projectile=TOWER1_PROJ,
                       sprite=abspath('../assets/sprites/crTower.png'), key=key, game=game)

    def upgradePath3(self):
        if self.costUpgrade3 <= self.game.money:
            self.game.updateMoney(-self.costUpgrade3)
            self.attackRange += 5
            self.costUpgrade3 *= 1.1
            self.game.updateCost()

class Tower2(Tower):
    cost = 300
    
    def __init__(self, pos, key, game):
        Tower.__init__(self, attackSpeed=10, attackDamage=1, attackRange=20, pierce=1, pos=pos,
                       attackSpeedIncrement=1.01, attackDamageIncrement=1.3, 
                       costUpgradeAttackSpeed=150, costUpgradeAttackDamage=320, 
                       costUpgrade3=200, upgrade3Text="Piercing", projectile=TOWER2_PROJ,
                       sprite=abspath('../assets/sprites/egirlsheesh.png'), key=key, game=game)
        
    def upgradePath3(self):
        if self.costUpgrade3 <= self.game.money:
            self.game.updateMoney(-self.costUpgrade3)
            self.pierce += 1
            self.costUpgrade3 *= 1.2
            self.game.updateCost()

class Tower3(Tower):
    cost = 425

    def __init__(self, pos, key, game):
        Tower.__init__(self, attackSpeed=7, attackDamage=3, attackRange=25, pierce=1, pos=pos,
                       attackSpeedIncrement=1.02, attackDamageIncrement=1.4, 
                       costUpgradeAttackSpeed=50, costUpgradeAttackDamage=100, 
                       costUpgrade3=0, upgrade3Text="None", projectile=TOWER3_PROJ,
                       sprite=abspath('../assets/sprites/picasso.png'), key=key, game=game)
        
    def upgradePath3(self):
        pass

class Tower4(Tower):
    cost = 100
    def __init__(self, pos, key, game):
        Tower.__init__(self, attackSpeed=2, attackDamage=10, attackRange=1, pierce=1, pos=pos,
                       attackSpeedIncrement=1.02, attackDamageIncrement=1.4, 
                       costUpgradeAttackSpeed=50, costUpgradeAttackDamage=100, 
                       costUpgrade3=0, upgrade3Text="None", projectile = None,
                       sprite=abspath('../assets/sprites/tower4.png'), key=key, game=game)
                       
    def attack(self):
        self.game.updateMoney(self.attackDamage)
    
    def upgradePath4(self):
        pass