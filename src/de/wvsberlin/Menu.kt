package de.wvsberlin

import ch.aplu.jgamegrid.GameGrid
import de.wvsberlin.vektor.Vektor
import java.awt.*
import java.awt.event.ActionEvent
import java.lang.IllegalArgumentException
import java.lang.IndexOutOfBoundsException
import javax.swing.*
import javax.swing.border.TitledBorder
import kotlin.system.exitProcess

class Menu : JFrame() {
    var bSelectMap = JButton()
    var bUpgrades = JButton()
    var bSettings = JButton()
    var bQuitGame = JButton()
    var jButtonGroupMaps = JPanel()
    var jButtonGroupMapsBG = ButtonGroup()
    var jButtonGroupMapsTB = TitledBorder("Maps")
    var jButtonGroupMapsRB0 = JRadioButton("Raum 208")
    var jButtonGroupMapsRB1 = JRadioButton("Raum 208 Legacy")
    var jButtonGroupMapsRB2 = JRadioButton("Jungel")
    var jButtonGroupMapsRB3 = JRadioButton("Map not implemented")
    var jButtonGroupDifficulty = JPanel()
    var jButtonGroupDifficultyBG = ButtonGroup()
    var jButtonGroupDifficultyTB = TitledBorder("Difficulty")
    var jButtonGroupDifficultyRB0 = JRadioButton("Easy")
    var jButtonGroupDifficultyRB1 = JRadioButton("Normal")
    var jButtonGroupDifficultyRB2 = JRadioButton("Hard")
    var bStartGame = JButton()
    var bBack = JButton()
    var gamegrid = GameGrid()
    var jGridPanel = JPanel()
    var bQuit = JButton()
    var bConfirm = JButton()
    var bAbort = JButton()
    var bStartRound = JButton()
    var jSeparator1 = JSeparator()
    var bAutostart = JToggleButton()
    var bTower1 = JButton()
    var bTower2 = JButton()
    var bTower3 = JButton()
    var bTower4 = JButton()
    var lTower1 = JLabel()
    var lTower2 = JLabel()
    var lTower3 = JLabel()
    var lTower4 = JLabel()
    var bUpgrade1 = JButton()
    var bUpgrade2 = JButton()
    var bUpgrade3 = JButton()
    var bSell = JButton()
    var bDeselect = JButton()
    var tUpgrade1 = JTextField()
    var tUpgrade2 = JTextField()
    var tUpgrade3 = JTextField()
    var lUpgrade1 = JLabel("Attackspeed:")
    var lUpgrade2 = JLabel("Attackdamage:")
    var lUpgrade3 = JLabel("Towerspecific:")
    var lCurrentRound = JLabel("Round:")
    var lMoney = JLabel("Money:")
    var lHealth = JLabel("Health:")
    var tCurrentRound = JTextField()
    var tMoney = JTextField()
    var tHealth = JTextField()
    var lGooseCondition = JLabel()
    var lgameOver = JLabel("You Goose!")
    var lWon = JLabel("You Won!")
    var bDebug = JToggleButton("Debug")

    var game: Game? = null
    val mainMenu = arrayOf<Component>(
            bSelectMap,
            bUpgrades,
            bSettings,
            bQuitGame
    )
    val mapSelector = arrayOf<Component>(
            jButtonGroupMaps,
            jButtonGroupDifficulty,
            bStartGame,
            bBack
    )
    val gameScreen = arrayOf<Component>(
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
    val confirmScreen = arrayOf<Component>(
            bConfirm,
            bAbort
    )
    val gameOverScreen = arrayOf<Component>(
            lGooseCondition,
            bConfirm,
            lgameOver
    )
    val winScreen = arrayOf<Component>(
            bConfirm,
            lWon
    )
    val settingsScreen = arrayOf<Component>(
            bDebug,
            bBack
    )

    init {
        /*
        jframe components incoming
         */
        defaultCloseOperation = DISPOSE_ON_CLOSE
        val frameWidth = 1280
        val frameHeight = 720
        super.setSize(frameWidth, frameHeight)
        val d = Toolkit.getDefaultToolkit().screenSize
        val x = (d.width - size.width) / 2
        val y = (d.height - size.height) / 2
        super.setLocation(x, y)
        title = "BTDC"
        isResizable = false
        val cp = contentPane
        cp.layout = null
        // start components

        //Main Menu components
        bSelectMap.setBounds(560, 200, 80, 24)
        bSelectMap.text = "Select map"
        bSelectMap.margin = Insets(2, 2, 2, 2)
        bSelectMap.addActionListener(this::bSelectMap_ActionPerformed)
        cp.add(bSelectMap)
        bUpgrades.setBounds(560, 260, 80, 24)
        bUpgrades.text = "Upgrades"
        bUpgrades.margin = Insets(2, 2, 2, 2)
        bUpgrades.addActionListener(this::bUpgrades_ActionPerformed)
        cp.add(bUpgrades)
        bSettings.setBounds(560, 460, 80, 24)
        bSettings.text = "Settings"
        bSettings.margin = Insets(2, 2, 2, 2)
        bSettings.addActionListener(this::bSettings_ActionPerformed)
        cp.add(bSettings)
        bQuitGame.setBounds(560, 520, 80, 24)
        bQuitGame.text = "Quit Game"
        bQuitGame.margin = Insets(2, 2, 2, 2)
        bQuitGame.addActionListener(this::bQuitGame_ActionPerformed)
        cp.add(bQuitGame)

        //Settings components
        bDebug.setBounds(560, 258, 80, 24)
        bDebug.margin = Insets(2, 2, 2, 2)
        bDebug.isVisible = false
        bDebug.addActionListener(this::bDebug_ActionPerformed)
        cp.add(bDebug)

        //Maps Selector Components
        jButtonGroupMaps.layout = null
        jButtonGroupMaps.setBounds(40, 120, 800, 500)
        jButtonGroupMaps.isVisible = false
        jButtonGroupMapsRB0.setBounds(7, 18, 393, 239)
        jButtonGroupMapsRB0.isSelected = true
        jButtonGroupMapsBG.add(jButtonGroupMapsRB0)
        jButtonGroupMaps.add(jButtonGroupMapsRB0)
        jButtonGroupMapsRB1.setBounds(400, 18, 393, 239)
        jButtonGroupMapsBG.add(jButtonGroupMapsRB1)
        jButtonGroupMaps.add(jButtonGroupMapsRB1)
        jButtonGroupMapsRB2.setBounds(7, 257, 393, 239)
        jButtonGroupMapsBG.add(jButtonGroupMapsRB2)
        jButtonGroupMaps.add(jButtonGroupMapsRB2)
        jButtonGroupMapsRB3.setBounds(400, 257, 393, 239)
        jButtonGroupMapsRB3.isEnabled = false
        jButtonGroupMapsBG.add(jButtonGroupMapsRB3)
        jButtonGroupMaps.add(jButtonGroupMapsRB3)
        jButtonGroupMaps.border = jButtonGroupMapsTB
        cp.add(jButtonGroupMaps)
        jButtonGroupDifficulty.layout = null
        jButtonGroupDifficulty.setBounds(875, 120, 120, 120)
        jButtonGroupDifficulty.isVisible = false
        jButtonGroupDifficultyRB0.setBounds(7, 18, 106, 29)
        jButtonGroupDifficultyRB0.isSelected = true
        jButtonGroupDifficultyBG.add(jButtonGroupDifficultyRB0)
        jButtonGroupDifficulty.add(jButtonGroupDifficultyRB0)
        jButtonGroupDifficultyRB1.setBounds(7, 47, 106, 29)
        jButtonGroupDifficultyBG.add(jButtonGroupDifficultyRB1)
        jButtonGroupDifficulty.add(jButtonGroupDifficultyRB1)
        jButtonGroupDifficultyRB2.setBounds(7, 76, 106, 29)
        jButtonGroupDifficultyBG.add(jButtonGroupDifficultyRB2)
        jButtonGroupDifficulty.add(jButtonGroupDifficultyRB2)
        jButtonGroupDifficulty.border = jButtonGroupDifficultyTB
        cp.add(jButtonGroupDifficulty)
        bStartGame.setBounds(895, 360, 80, 24)
        bStartGame.text = "Start Game"
        bStartGame.margin = Insets(2, 2, 2, 2)
        bStartGame.addActionListener(this::bStartGame_ActionPerformed)
        bStartGame.isVisible = false
        cp.add(bStartGame)

        //used for multiple screens
        bBack.setBounds(895, 420, 80, 24)
        bBack.text = "Back"
        bBack.margin = Insets(2, 2, 2, 2)
        bBack.addActionListener(this::bBack_ActionPerformed)
        bBack.isVisible = false
        cp.add(bBack)
        bConfirm.setBounds(320, 330, 80, 24)
        bConfirm.text = "Confirm"
        bConfirm.margin = Insets(2, 2, 2, 2)
        bConfirm.addActionListener { evt: ActionEvent? -> bConfirm_ActionPerformed(evt) }
        bConfirm.isVisible = false
        cp.add(bConfirm)

        //Game components
        jGridPanel.setBounds(40, 120, 960, 540)
        jGridPanel.isVisible = false
        cp.add(jGridPanel)

        // for some reason, multiple calls to the dimension-setting methods causes a memleak.
        // I suspect this is because of repeated calls to dispose() the current gPanel.
        // Maybe Swing is then trying to dispose() the same gPanel multiple times, and dispose()-ing something that no
        // longer exists causes a memleak..? I do not know. It is retarded either way.
        // For some reason, this shit does not happen on Jython.
        gamegrid.nbHorzCells = 960
        gamegrid.nbVertCells = 540

        // the reason we do this as a regular method call is because the only way for us to make sure that the grid's
        // gPanel is correct, because FOR SOME FUCKING REASON, EVERYTHJING IS PRIVATE.
        // I hate the way Java does things sometimes.
        // The dimension-setting methods, aside from causing memleaks, also call some very handy methods to update other
        // related attributes.
        // However, these methods are private. And since there is no way I'm doing all that by hand, we call a
        // dimension-setter the normal way to update shit.
        //
        // here's the setCellSize src code to show what I'm talking about:
        /*
            public void setCellSize(int cellSize) {
                this.cellSize = cellSize;
                adjustDimensions();
                setPreferredSize(new Dimension(this.nbHorzPix, this.nbVertPix));
                if (this.gPanel != null) {
                    gPanel.dispose();
                }

                gPanel = new GGPanel(this);
            }
         */
        // notice the adjustDimensions and setPreferredSize methods.
        gamegrid.setCellSize(1)

        gamegrid.bgColor = Color.BLACK
        // Speaking of bgColor. Let me tell you about my favorite method of all: setBgColor.
        // Here's the implementation:
        /*
            public void setBgColor(Color color) {
                this.bgColor = new Color(color.getRed(), color.getGreen(), color.getBlue());
                this.gPanel.clear();
            }

            public void setBgColor(int r, int g, int b) {
                this.bgColor = new Color(r, g, b);
                this.gPanel.clear();
            }
         */
        // The second one is aight. Just making a color from the given component values.
        // But the first cracks me up.
        // Why on God's green earth is it necessary to CREATE an ENTIRE NEW FUCKING INSTANCE that is IDENTICAL IN EVERY
        // WAY to the given Color? Just assign the fukign color??????
        // One possible reason that I can think of is that maybe this is to protect against the color, after being
        // passed by reference, changing after assignment to whatever the fuck.
        // But this is entirely stupid, because you could also just ...not allow the color to be modified???????
        // Which seems to be the case. Color.value cannot be externally modified.
        // Maybe with subclassing, it might be possible to modify the Color?
        // I have come to the conclusion that the only reasonable explanation is that subclass instances could be passed
        // and then modified..? I hate it. Why isn't java.awt.Color final?

        gamegrid.setBounds(40, 120, 960, 540)
        jGridPanel.add(gamegrid)

        bQuit.setBounds(1170, 10, 80, 24)
        bQuit.text = "Quit"
        bQuit.margin = Insets(2, 2, 2, 2)
        bQuit.addActionListener(this::bQuit_ActionPerformed)
        bQuit.isVisible = false
        cp.add(bQuit)
        bStartRound.setBounds(1100, 580, 24, 24)
        bStartRound.text = ">"
        bStartRound.margin = Insets(2, 2, 2, 2)
        bStartRound.addActionListener(this::bStartRound_ActionPerformed)
        bStartRound.isVisible = false
        cp.add(bStartRound)
        jSeparator1.setBounds(1040, 255, 200, 8)
        jSeparator1.isVisible = false
        cp.add(jSeparator1)
        bAutostart.setBounds(1160, 580, 36, 24)
        bAutostart.text = ">>"
        bAutostart.margin = Insets(2, 2, 2, 2)
        bAutostart.isVisible = false
        cp.add(bAutostart)
        bTower1.setBounds(1050, 180, 80, 24)
        bTower1.text = "Tower1"
        bTower1.margin = Insets(2, 2, 2, 2)
        bTower1.addActionListener(this::bTower1_ActionPerformed)
        bTower1.isVisible = false
        cp.add(bTower1)
        bTower2.setBounds(1155, 180, 80, 24)
        bTower2.text = "Tower2"
        bTower2.margin = Insets(2, 2, 2, 2)
        bTower2.addActionListener(this::bTower2_ActionPerformed)
        bTower2.isVisible = false
        cp.add(bTower2)
        bTower3.setBounds(1050, 300, 80, 24)
        bTower3.text = "Tower3"
        bTower3.margin = Insets(2, 2, 2, 2)
        bTower3.addActionListener(this::bTower3_ActionPerformed)
        bTower3.isVisible = false
        cp.add(bTower3)
        bTower4.setBounds(1155, 300, 80, 24)
        bTower4.text = "Tower4"
        bTower4.margin = Insets(2, 2, 2, 2)
        bTower4.addActionListener(this::bTower4_ActionPerformed)
        bTower4.isVisible = false
        cp.add(bTower4)
        lTower1.setBounds(1050, 210, 80, 24)
        lTower1.isVisible = false
        cp.add(lTower1)
        lTower2.setBounds(1155, 210, 80, 24)
        lTower2.isVisible = false
        cp.add(lTower2)
        lTower3.setBounds(1050, 330, 80, 24)
        lTower3.isVisible = false
        cp.add(lTower3)
        lTower4.setBounds(1155, 330, 80, 24)
        lTower4.isVisible = false
        cp.add(lTower4)
        bUpgrade1.setBounds(150, 50, 80, 24)
        bUpgrade1.text = "Upgrade"
        bUpgrade1.margin = Insets(2, 2, 2, 2)
        bUpgrade1.addActionListener(this::bUpgrade1_ActionPerformed)
        bUpgrade1.isVisible = false
        cp.add(bUpgrade1)
        bUpgrade2.setBounds(300, 50, 80, 24)
        bUpgrade2.text = "Upgrade"
        bUpgrade2.margin = Insets(2, 2, 2, 2)
        bUpgrade2.addActionListener(this::bUpgrade2_ActionPerformed)
        bUpgrade2.isVisible = false
        cp.add(bUpgrade2)
        bUpgrade3.setBounds(450, 50, 80, 24)
        bUpgrade3.text = "Upgrade"
        bUpgrade3.margin = Insets(2, 2, 2, 2)
        bUpgrade3.addActionListener(this::bUpgrade3_ActionPerformed)
        bUpgrade3.isVisible = false
        cp.add(bUpgrade3)
        bSell.setBounds(600, 50, 80, 24)
        bSell.text = "Sell"
        bSell.margin = Insets(2, 2, 2, 2)
        bSell.addActionListener(this::bSell_ActionPerformed)
        bSell.isVisible = false
        cp.add(bSell)
        bDeselect.setBounds(600, 80, 80, 24)
        bDeselect.text = "Deselect"
        bDeselect.margin = Insets(2, 2, 2, 2)
        bDeselect.addActionListener(this::bDeselect_ActionPerformed)
        bDeselect.isVisible = false
        cp.add(bDeselect)
        lUpgrade1.setBounds(150, 20, 80, 24)
        lUpgrade1.isVisible = false
        cp.add(lUpgrade1)
        lUpgrade2.setBounds(300, 20, 80, 24)
        lUpgrade2.isVisible = false
        cp.add(lUpgrade2)
        lUpgrade3.setBounds(450, 20, 80, 24)
        lUpgrade3.isVisible = false
        cp.add(lUpgrade3)
        tUpgrade1.setBounds(150, 80, 80, 24)
        tUpgrade1.isEditable = false
        tUpgrade1.isVisible = false
        cp.add(tUpgrade1)
        tUpgrade2.setBounds(300, 80, 80, 24)
        tUpgrade2.isEditable = false
        tUpgrade2.isVisible = false
        cp.add(tUpgrade2)
        tUpgrade3.setBounds(450, 80, 80, 24)
        tUpgrade3.isEditable = false
        tUpgrade3.isVisible = false
        cp.add(tUpgrade3)
        tCurrentRound.setBounds(950, 20, 80, 24)
        tCurrentRound.isEditable = false
        tCurrentRound.isVisible = false
        cp.add(tCurrentRound)
        tMoney.setBounds(950, 50, 80, 24)
        tMoney.isEditable = false
        tMoney.isVisible = false
        cp.add(tMoney)
        tHealth.setBounds(950, 80, 80, 24)
        tHealth.isEditable = false
        tHealth.isVisible = false
        cp.add(tHealth)
        lCurrentRound.setBounds(900, 20, 80, 24)
        lCurrentRound.isVisible = false
        cp.add(lCurrentRound)
        lMoney.setBounds(900, 50, 80, 24)
        lMoney.isVisible = false
        cp.add(lMoney)
        lHealth.setBounds(900, 80, 80, 24)
        lHealth.isVisible = false
        cp.add(lHealth)

        //Quit Screen components
        bAbort.setBounds(460, 330, 80, 24)
        bAbort.text = "Abort"
        bAbort.margin = Insets(2, 2, 2, 2)
        bAbort.addActionListener(this::bAbort_ActionPerformed)
        bAbort.isVisible = false
        cp.add(bAbort)

        //Win and Goose Screens
        lGooseCondition.setBounds(240, 60, 800, 600)
        lGooseCondition.isVisible = false
        cp.add(lGooseCondition)
        lgameOver.setBounds(610, 10, 120, 34)
        lgameOver.font = Font("Dialog", Font.BOLD, 18)
        lgameOver.isVisible = false
        cp.add(lgameOver)
        lWon.setBounds(590, 150, 120, 34)
        lWon.font = Font("Dialog", Font.BOLD, 18)
        lWon.isVisible = false
        cp.add(lWon)



        lGooseCondition.icon = ImageIcon(Sprite.GOOSE)
    }

    fun startGame() {
        if (bDebug.isSelected) {
            println("Menu.startGame")
            println("map = $selectedMap")
            println("difficulty = $selectedDifficulty")
        }

        try {
            game = Game(this, selectedDifficulty, selectedMap)
        } catch (err: IndexOutOfBoundsException) {
            setCurrentScreen(1)
            if (bDebug.isSelected) println("Illegal map ID; returning to map select.")
            return
        }
        gamegrid.doRun()
    }

    fun bSelectMap_ActionPerformed(evt: ActionEvent?) = setCurrentScreen(1)

    fun bBack_ActionPerformed(evt: ActionEvent?) = setCurrentScreen(0)

    fun bQuitGame_ActionPerformed(evt: ActionEvent?) {
        dispose()
        exitProcess(0)
    }

    fun bQuit_ActionPerformed(evt: ActionEvent?) {
        gamegrid.doPause()
        setCurrentScreen(3)
    }

    fun bAbort_ActionPerformed(evt: ActionEvent?) {
        gamegrid.doRun()
        setCurrentScreen(2)
    }

    fun bConfirm_ActionPerformed(evt: ActionEvent?) {
        game?.removeAllActors()
        game = null
        setCurrentScreen(0)
    }

    fun bStartGame_ActionPerformed(evt: ActionEvent?) {
        if (bDebug.isSelected) println("Menu.bStartGame_ActionPerformed")

        setCurrentScreen(2)
        startGame()
    }

    fun bStartRound_ActionPerformed(evt: ActionEvent?) {
        game ?: return
        if (!game!!.roundActive)
            game!!.startNextRound()
    }

    fun bTower1_ActionPerformed(evt: ActionEvent?) {
        game ?: return
        val game = game!!

        if ((game.money < Tower1.cost) or (game.heldTower != null)) return

        val newHeldTower = HeldTower(0)
        game.heldTower = newHeldTower

        gamegrid.addActor(newHeldTower, newHeldTower.pos.toLocation())
        game.updateMoney(-Tower1.cost)
    }

    fun bTower2_ActionPerformed(evt: ActionEvent?) {
        game ?: return
        val game = game!!

        if ((game.money < Tower2.cost) || (game.heldTower != null)) return

        val newHeldTower = HeldTower(1)
        game.heldTower = newHeldTower

        gamegrid.addActor(newHeldTower, newHeldTower.pos.toLocation())
        game.updateMoney(-Tower2.cost)
    }

    fun bTower3_ActionPerformed(evt: ActionEvent?) {
        game ?: return
        val game = game!!

        if ((game.money < Tower3.cost) or (game.heldTower != null)) return

        val newHeldTower = HeldTower(2)
        game.heldTower = newHeldTower

        gamegrid.addActor(newHeldTower, newHeldTower.pos.toLocation())
        game.updateMoney(-Tower3.cost)
    }

    fun bTower4_ActionPerformed(evt: ActionEvent?) {
        game ?: return
        val game = game!!

        if (game.debug) {
            game.currentRound++
            tCurrentRound.text = (game.currentRound + 1).toString()
        } else throw NotImplementedError("This tower has not been implemented!")
    }

    fun bUpgrade1_ActionPerformed(evt: ActionEvent?) {
        game?.selectedTower?.upgradeAttackSpeed()
    }

    fun bUpgrade2_ActionPerformed(evt: ActionEvent?) {
        game?.selectedTower?.upgradeAttackDamage()
    }

    fun bUpgrade3_ActionPerformed(evt: ActionEvent?) {
        game?.selectedTower?.upgradePath3()
    }

    fun bSell_ActionPerformed(evt: ActionEvent?) {
        game?.sellSelectedTower()
    }

    fun bDeselect_ActionPerformed(evt: ActionEvent?) {
        game ?: return
        val game = game!!

        game.selectedTower = null
        game.updateCost()

        if (game.heldTower == null)
            return

        when (game.heldTower!!.towerID) {
            0 -> game.updateMoney(Tower1.cost)
            1 -> game.updateMoney(Tower2.cost)
            2 -> game.updateMoney(Tower3.cost)
            else -> throw IllegalArgumentException("Illegal tower ID")
        }
        game.grid.removeActor(game.heldTower)
        game.heldTower = null
    }

    fun bSettings_ActionPerformed(evt: ActionEvent?) = setCurrentScreen(6)

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

    private fun toggleElementGroup(group: Array<Component>, toggle: Boolean) = group.map { it.isVisible = toggle }

    fun bUpgrades_ActionPerformed(evt: ActionEvent?) {} // end of bUpgrades_ActionPerformed

    //Settings
    fun bDebug_ActionPerformed(evt: ActionEvent?) {} // end of bDebug_ActionPerformed
    val selectedMap: GameMap
        //Maps Selector
        get() {
            val e = jButtonGroupMapsBG.elements
            var b: AbstractButton
            while (e.hasMoreElements()) {  // why is this like this
                b = e.nextElement()
                if (!b.isSelected)
                    continue

                return when(b.text) {
                    "Raum 208" -> GameMap.RAUM208_V2
                    "Raum 208 Legacy" -> GameMap.RAUM208_LEGACY
                    "Jungel" -> GameMap.JUNGLE
                    else -> throw IllegalStateException("Illegal map selected.")
                }
            }
            throw IllegalStateException("Somehow, no map is selected. That should not be the case.")
        }

    val selectedDifficulty: Difficulty
        get() {
            val e = jButtonGroupDifficultyBG.elements
            var b: AbstractButton
            while (e.hasMoreElements()) {
                b = e.nextElement()
                if (!b.isSelected)
                    continue

                return when (b.text) {
                    "Easy" -> Difficulty.EASY
                    "Normal" -> Difficulty.NORMAL
                    "Hard" -> Difficulty.HARD
                    else -> throw IllegalStateException("Illegal difficulty selected.")
                }
            }
            throw IllegalStateException("No difficulty selected.")
        }
}