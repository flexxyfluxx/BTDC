from projectiles import TOWER1_PROJ, TOWER2_PROJ
from tower import Tower
from os.path import abspath

class Tower1(Tower):
    cost = 250

    def __init__(self, pos, key, game):
        Tower.__init__(self, attackSpeed=5, attackDamage=2, attackRange=40, pierce=1, pos=pos,
                       attackSpeedIncrement=1.05, attackDamageIncrement=1.15, 
                       costUpgradeAttackSpeed=400, costUpgradeAttackDamage=275, 
                       costUpgrade3=420, upgrade3Text="Attackrange:", projectile=TOWER1_PROJ,
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
                       costUpgradeAttackSpeed=350, costUpgradeAttackDamage=520, 
                       costUpgrade3=400, upgrade3Text="Piercing", projectile=TOWER2_PROJ,
                       sprite=abspath('../assets/sprites/egirlsheesh.png'), key=key, game=game)
        
    def upgradePath3(self):
        if self.costUpgrade3 <= self.game.money:
            self.game.updateMoney(-self.costUpgrade3)
            self.pierce += 1
            self.costUpgrade3 *= 1.2
            self.game.updateCost()

class Tower3(Tower):
    cost = 500
    def __init__(self, pos, key, game):
        Tower.__init__(self, attackSpeed=15, attackDamage=3, attackRange=30, pierce=1, pos=pos,
                       attackSpeedIncrement=1.02, attackDamageIncrement=1.4, 
                       costUpgradeAttackSpeed=250, costUpgradeAttackDamage=300, 
                       costUpgrade3=0, upgrade3Text="None", projectile=TOWER2_PROJ,
                       sprite=abspath('../assets/sprites/sprite2.png'), key=key, game=game)
        
    def upgradePath3(self):
        pass
