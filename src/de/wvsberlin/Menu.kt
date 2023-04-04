package de.wvsberlin

import de.wvsberlin.vektor.Vektor
import java.awt.Component
import java.awt.event.ActionEvent
import java.lang.IllegalArgumentException
import java.lang.IndexOutOfBoundsException
import javax.swing.ImageIcon
import kotlin.system.exitProcess

class Menu : JMainFrame() {
    var game: Game? = null
    val mainMenu: Array<out Component> = arrayOf(
            bSelectMap,
            bUpgrades,
            bSettings,
            bQuitGame
    )
    val mapSelector: Array<out Component> = arrayOf(
            jButtonGroupMaps,
            jButtonGroupDifficulty,
            bStartGame,
            bBack
    )
    val gameScreen: Array<out Component> = arrayOf(
            jGridPanel,
            bQuit,
            bTower1,
            bTower2,
            bTower3,
            bTower4,
            lTower1,
            lTower2,
            lTower3,
            lTower4,
            bUpgrade1,
            bUpgrade2,
            bUpgrade3,
            bSell,
            bDeselect,
            jSeparator1,
            bStartRound,
            bAutostart,
            tCurrentRound,
            tMoney,
            tHealth,
            lUpgrade1,
            lUpgrade2,
            lUpgrade3,
            tUpgrade1,
            tUpgrade2,
            tUpgrade3,
            lCurrentRound,
            lMoney,
            lHealth
    )
    val confirmScreen: Array<out Component> = arrayOf(
            bConfirm,
            bAbort
    )
    val gameOverScreen: Array<out Component> = arrayOf(
            lgooseCondition,
            bConfirm,
            lgameOver
    )
    val winScreen: Array<out Component> = arrayOf(
            bConfirm,
            lwon
    )
    val settingsScreen: Array<out Component> = arrayOf(
            bDebug,
            bBack
    )

    init {
        lgooseCondition.icon = ImageIcon(Sprite.GOOSE)
    }

    fun startGame() {
        if (bDebug.isSelected) {
            println("Menu.startGame")
            println("map = $selectedMap")
            println("difficulty = $selectedDifficulty")
        }

        try {
            game = Game(this, selectedDifficulty, GameMap.theMaps[selectedMap])
        } catch (err: IndexOutOfBoundsException) {
            setCurrentScreen(1)
            if (bDebug.isSelected) println("Illegal map ID; returning to map select.")
            return
        }
        gamegrid.doRun()
    }

    override fun bSelectMap_ActionPerformed(evt: ActionEvent?) = setCurrentScreen(1)

    override fun bBack_ActionPerformed(evt: ActionEvent?) = setCurrentScreen(0)

    override fun bQuitGame_ActionPerformed(evt: ActionEvent?) {
        dispose()
        exitProcess(0)
    }

    override fun bQuit_ActionPerformed(evt: ActionEvent?) {
        gamegrid.doPause()
        setCurrentScreen(3)
    }

    override fun bAbort_ActionPerformed(evt: ActionEvent?) {
        gamegrid.doRun()
        setCurrentScreen(2)
    }

    override fun bConfirm_ActionPerformed(evt: ActionEvent?) {
        game?.removeAllActors()
        game = null
        setCurrentScreen(0)
    }

    override fun bStartGame_ActionPerformed(evt: ActionEvent?) {
        if (bDebug.isSelected) println("Menu.bStartGame_ActionPerformed")

        setCurrentScreen(2)
        startGame()
    }

    override fun bStartRound_ActionPerformed(evt: ActionEvent?) {
        game?.startNextRound()
    }

    override fun bTower1_ActionPerformed(evt: ActionEvent?) {
        game ?: return
        if ((game!!.money < Tower1.cost) || (game!!.heldTower != null)) return

        val newHeldTower = HeldTower(0)
        game!!.heldTower = newHeldTower

        gamegrid.addActor(newHeldTower, newHeldTower.pos.toLocation())
        game!!.updateMoney(-Tower1.cost)
    }

    override fun bTower2_ActionPerformed(evt: ActionEvent?) {
        game ?: return
        val game = game!!

        if ((game.money < Tower2.cost) || (game.heldTower != null)) return

        val newHeldTower = HeldTower(1)
        game.heldTower = newHeldTower

        gamegrid.addActor(newHeldTower, newHeldTower.pos.toLocation())
        game.updateMoney(-Tower2.cost)
    }

    override fun bTower3_ActionPerformed(evt: ActionEvent?) {
        game ?: return
        val game = game!!

        if (game.debug) {
            for (x in 0 until 96) {
                for (y in 0 until 54) {
                    game.heldTower = HeldTower(3)
                    game.placeHeldTower(Vektor(x * 10, y * 10))
                }
            }
            return
        }

        if ((game.money < Tower3.cost) || (game.heldTower != null)) return

        val newHeldTower = HeldTower(2)
        game.heldTower = newHeldTower

        gamegrid.addActor(newHeldTower, newHeldTower.pos.toLocation())
        game.updateMoney(-Tower3.cost)
    }

    override fun bTower4_ActionPerformed(evt: ActionEvent?) {
        game ?: return
        val game = game!!

        if (game.debug) {
            game.currentRound++
            tCurrentRound.text = (game.currentRound + 1).toString()
        } else throw NotImplementedError("This tower has not been implemented!")
    }

    override fun bUpgrade1_ActionPerformed(evt: ActionEvent?) {
        game?.selectedTower?.upgradeAttackSpeed()
    }

    override fun bUpgrade2_ActionPerformed(evt: ActionEvent?) {
        game?.selectedTower?.upgradeAttackDamage()
    }

    override fun bUpgrade3_ActionPerformed(evt: ActionEvent?) {
        game?.selectedTower?.upgradePath3()
    }

    override fun bSell_ActionPerformed(evt: ActionEvent?) {
        game?.sellSelectedTower()
    }

    override fun bDeselect_ActionPerformed(evt: ActionEvent?) {
        game ?: return
        val game = game!!

        game.selectedTower = null
        game.updateCost()

        if (game.heldTower != null) {
            when (game.heldTower!!.towerID) {
                0 -> game.updateMoney(Tower1.cost)
                1 -> game.updateMoney(Tower2.cost)
                2 -> game.updateMoney(Tower3.cost)
                else -> throw IllegalArgumentException("Illegal tower ID")
            }
            game.grid.removeActor(game.heldTower)
        }
    }

    override fun bSettings_ActionPerformed(evt: ActionEvent?) = setCurrentScreen(6)

    fun setCurrentScreen(screenID: Int) {
        toggleMainMenu(false)
        toggleMapSelector(false)
        toggleGameScreen(false)
        toggleConfirmScreen(false)
        toggleGameOverScreen(false)
        toggleWinScreen(false)
        toggleSettingsScreen(false)

        when (screenID) {
            0 -> toggleMainMenu(true)
            1 -> toggleMapSelector(true)
            2 -> toggleGameScreen(true)
            3 -> toggleConfirmScreen(true)
            4 -> toggleGameOverScreen(true)
            5 -> toggleWinScreen(true)
            6 -> toggleSettingsScreen(true)
            else -> throw IllegalArgumentException("Illegal screen ID!")
        }
    }

    fun toggleMainMenu(toggle: Boolean) = toggleElementGroup(mainMenu, toggle)
    fun toggleMapSelector(toggle: Boolean) = toggleElementGroup(mapSelector, toggle)
    fun toggleGameScreen(toggle: Boolean) {
        toggleElementGroup(gameScreen, toggle)
        bTower4.isEnabled = bDebug.isSelected
    }

    fun toggleConfirmScreen(toggle: Boolean) = toggleElementGroup(confirmScreen, toggle)
    fun toggleGameOverScreen(toggle: Boolean) {
        toggleElementGroup(gameOverScreen, toggle)
        if (toggle) bConfirm.setBounds(620, 200, 80, 24)
        else bConfirm.setBounds(320, 330, 80, 24)
    }

    fun toggleWinScreen(toggle: Boolean) {
        toggleElementGroup(winScreen, toggle)
        if (toggle) bConfirm.setBounds(590, 180, 80, 24)
        else bConfirm.setBounds(320, 330, 80, 24)
    }

    fun toggleSettingsScreen(toggle: Boolean) {
        toggleElementGroup(settingsScreen, toggle)
        if (toggle) bBack.setBounds(560, 300, 80, 24)
        else bBack.setBounds(895, 420, 80, 24)
    }

    fun toggleElementGroup(group: Array<out Component>, toggle: Boolean) = group.map { it.isVisible = toggle }
}