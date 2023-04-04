package de.wvsberlin

import ch.aplu.jgamegrid.GameGrid
import java.awt.*
import java.awt.event.ActionEvent
import javax.swing.*
import javax.swing.border.TitledBorder

/**
 *
 * Description
 *
 * @version 1.0 from 04/03/2023
 * @author
 */
open class JMainFrame : JFrame() {
    // start attributes
    @JvmField
    var bSelectMap = JButton()
    @JvmField
    var bUpgrades = JButton()
    @JvmField
    var bSettings = JButton()
    @JvmField
    var bQuitGame = JButton()
    @JvmField
    var jButtonGroupMaps = JPanel()
    @JvmField
    var jButtonGroupMapsBG = ButtonGroup()
    @JvmField
    var jButtonGroupMapsTB = TitledBorder("Maps")
    @JvmField
    var jButtonGroupMapsRB0 = JRadioButton("Raum 208")
    @JvmField
    var jButtonGroupMapsRB1 = JRadioButton("Raum 208 Legacy")
    @JvmField
    var jButtonGroupMapsRB2 = JRadioButton("Jungel")
    @JvmField
    var jButtonGroupMapsRB3 = JRadioButton("Map not implemented")
    @JvmField
    var jButtonGroupDifficulty = JPanel()
    @JvmField
    var jButtonGroupDifficultyBG = ButtonGroup()
    @JvmField
    var jButtonGroupDifficultyTB = TitledBorder("Difficulty")
    @JvmField
    var jButtonGroupDifficultyRB0 = JRadioButton("Easy")
    @JvmField
    var jButtonGroupDifficultyRB1 = JRadioButton("Normal")
    @JvmField
    var jButtonGroupDifficultyRB2 = JRadioButton("Hard")
    @JvmField
    var bStartGame = JButton()
    @JvmField
    var bBack = JButton()
    @JvmField
    var gamegrid = GameGrid()
    @JvmField
    var jGridPanel = JPanel()
    @JvmField
    var bQuit = JButton()
    @JvmField
    var bConfirm = JButton()
    @JvmField
    var bAbort = JButton()
    @JvmField
    var bStartRound = JButton()
    @JvmField
    var jSeparator1 = JSeparator()
    @JvmField
    var bAutostart = JToggleButton()
    @JvmField
    var bTower1 = JButton()
    @JvmField
    var bTower2 = JButton()
    @JvmField
    var bTower3 = JButton()
    @JvmField
    var bTower4 = JButton()
    @JvmField
    var lTower1 = JLabel()
    @JvmField
    var lTower2 = JLabel()
    @JvmField
    var lTower3 = JLabel()
    @JvmField
    var lTower4 = JLabel()
    @JvmField
    var bUpgrade1 = JButton()
    @JvmField
    var bUpgrade2 = JButton()
    @JvmField
    var bUpgrade3 = JButton()
    @JvmField
    var bSell = JButton()
    @JvmField
    var bDeselect = JButton()
    @JvmField
    var tUpgrade1 = JTextField()
    @JvmField
    var tUpgrade2 = JTextField()
    @JvmField
    var tUpgrade3 = JTextField()
    @JvmField
    var lUpgrade1 = JLabel("Attackspeed:")
    @JvmField
    var lUpgrade2 = JLabel("Attackdamage:")
    @JvmField
    var lUpgrade3 = JLabel("Towerspecific:")
    @JvmField
    var lCurrentRound = JLabel("Round:")
    @JvmField
    var lMoney = JLabel("Money:")
    @JvmField
    var lHealth = JLabel("Health:")
    @JvmField
    var tCurrentRound = JTextField()
    @JvmField
    var tMoney = JTextField()
    @JvmField
    var tHealth = JTextField()
    @JvmField
    var lgooseCondition = JLabel()
    @JvmField
    var lgameOver = JLabel("You Goose!")
    @JvmField
    var lwon = JLabel("You Won!")
    @JvmField
    var bDebug = JToggleButton("Debug")
    private val mapIDs = hashMapOf(
            Pair("Raum 208", 0),
            Pair("Raum 208 Legacy", 1),
            Pair("Jungel", 2),
    )

    // end attributes
    init {
        // Frame-Init
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
        bSelectMap.addActionListener { evt: ActionEvent? -> bSelectMap_ActionPerformed(evt) }
        cp.add(bSelectMap)
        bUpgrades.setBounds(560, 260, 80, 24)
        bUpgrades.text = "Upgrades"
        bUpgrades.margin = Insets(2, 2, 2, 2)
        bUpgrades.addActionListener { evt: ActionEvent? -> bUpgrades_ActionPerformed(evt) }
        cp.add(bUpgrades)
        bSettings.setBounds(560, 460, 80, 24)
        bSettings.text = "Settings"
        bSettings.margin = Insets(2, 2, 2, 2)
        bSettings.addActionListener { evt: ActionEvent? -> bSettings_ActionPerformed(evt) }
        cp.add(bSettings)
        bQuitGame.setBounds(560, 520, 80, 24)
        bQuitGame.text = "Quit Game"
        bQuitGame.margin = Insets(2, 2, 2, 2)
        bQuitGame.addActionListener { evt: ActionEvent? -> bQuitGame_ActionPerformed(evt) }
        cp.add(bQuitGame)

        //Settings components
        bDebug.setBounds(560, 258, 80, 24)
        bDebug.margin = Insets(2, 2, 2, 2)
        bDebug.isVisible = false
        bDebug.addActionListener { evt: ActionEvent? -> bDebug_ActionPerformed(evt) }
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
        bStartGame.addActionListener { evt: ActionEvent? -> bStartGame_ActionPerformed(evt) }
        bStartGame.isVisible = false
        cp.add(bStartGame)

        //used for multiple screens
        bBack.setBounds(895, 420, 80, 24)
        bBack.text = "Back"
        bBack.margin = Insets(2, 2, 2, 2)
        bBack.addActionListener { evt: ActionEvent? -> bBack_ActionPerformed(evt) }
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
        gamegrid.setNbHorzCells(960)
        gamegrid.setNbVertCells(540)
        gamegrid.setCellSize(1)
        gamegrid.setBgColor(Color.BLACK)
        gamegrid.setBounds(40, 120, 960, 540)
        jGridPanel.add(gamegrid)
        bQuit.setBounds(1170, 10, 80, 24)
        bQuit.text = "Quit"
        bQuit.margin = Insets(2, 2, 2, 2)
        bQuit.addActionListener { evt: ActionEvent? -> bQuit_ActionPerformed(evt) }
        bQuit.isVisible = false
        cp.add(bQuit)
        bStartRound.setBounds(1100, 580, 24, 24)
        bStartRound.text = ">"
        bStartRound.margin = Insets(2, 2, 2, 2)
        bStartRound.addActionListener { evt: ActionEvent? -> bStartRound_ActionPerformed(evt) }
        bStartRound.isVisible = false
        cp.add(bStartRound)
        jSeparator1.setBounds(1040, 255, 200, 8)
        jSeparator1.isVisible = false
        cp.add(jSeparator1)
        bAutostart.setBounds(1160, 580, 36, 24)
        bAutostart.text = ">>"
        bAutostart.margin = Insets(2, 2, 2, 2)
        bAutostart.addActionListener { evt: ActionEvent? -> bAutostart_ActionPerformed(evt) }
        bAutostart.isVisible = false
        cp.add(bAutostart)
        bTower1.setBounds(1050, 180, 80, 24)
        bTower1.text = "Tower1"
        bTower1.margin = Insets(2, 2, 2, 2)
        bTower1.addActionListener { evt: ActionEvent? -> bTower1_ActionPerformed(evt) }
        bTower1.isVisible = false
        cp.add(bTower1)
        bTower2.setBounds(1155, 180, 80, 24)
        bTower2.text = "Tower2"
        bTower2.margin = Insets(2, 2, 2, 2)
        bTower2.addActionListener { evt: ActionEvent? -> bTower2_ActionPerformed(evt) }
        bTower2.isVisible = false
        cp.add(bTower2)
        bTower3.setBounds(1050, 300, 80, 24)
        bTower3.text = "Tower3"
        bTower3.margin = Insets(2, 2, 2, 2)
        bTower3.addActionListener { evt: ActionEvent? -> bTower3_ActionPerformed(evt) }
        bTower3.isVisible = false
        cp.add(bTower3)
        bTower4.setBounds(1155, 300, 80, 24)
        bTower4.text = "Tower4"
        bTower4.margin = Insets(2, 2, 2, 2)
        bTower4.addActionListener { evt: ActionEvent? -> bTower4_ActionPerformed(evt) }
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
        bUpgrade1.addActionListener { evt: ActionEvent? -> bUpgrade1_ActionPerformed(evt) }
        bUpgrade1.isVisible = false
        cp.add(bUpgrade1)
        bUpgrade2.setBounds(300, 50, 80, 24)
        bUpgrade2.text = "Upgrade"
        bUpgrade2.margin = Insets(2, 2, 2, 2)
        bUpgrade2.addActionListener { evt: ActionEvent? -> bUpgrade2_ActionPerformed(evt) }
        bUpgrade2.isVisible = false
        cp.add(bUpgrade2)
        bUpgrade3.setBounds(450, 50, 80, 24)
        bUpgrade3.text = "Upgrade"
        bUpgrade3.margin = Insets(2, 2, 2, 2)
        bUpgrade3.addActionListener { evt: ActionEvent? -> bUpgrade3_ActionPerformed(evt) }
        bUpgrade3.isVisible = false
        cp.add(bUpgrade3)
        bSell.setBounds(600, 50, 80, 24)
        bSell.text = "Sell"
        bSell.margin = Insets(2, 2, 2, 2)
        bSell.addActionListener { evt: ActionEvent? -> bSell_ActionPerformed(evt) }
        bSell.isVisible = false
        cp.add(bSell)
        bDeselect.setBounds(600, 80, 80, 24)
        bDeselect.text = "Deselect"
        bDeselect.margin = Insets(2, 2, 2, 2)
        bDeselect.addActionListener { evt: ActionEvent? -> bDeselect_ActionPerformed(evt) }
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
        bAbort.addActionListener { evt: ActionEvent? -> bAbort_ActionPerformed(evt) }
        bAbort.isVisible = false
        cp.add(bAbort)

        //Win and Goose Screens
        lgooseCondition.setBounds(240, 60, 800, 600)
        lgooseCondition.isVisible = false
        cp.add(lgooseCondition)
        lgameOver.setBounds(610, 10, 120, 34)
        lgameOver.font = Font("Dialog", Font.BOLD, 18)
        lgameOver.isVisible = false
        cp.add(lgameOver)
        lwon.setBounds(590, 150, 120, 34)
        lwon.font = Font("Dialog", Font.BOLD, 18)
        lwon.isVisible = false
        cp.add(lwon)
        // end components
    } // end of public JMainFrame

    // start methods

    //Main Menu
    open fun bSelectMap_ActionPerformed(evt: ActionEvent?) {} // end of bSelectMap_ActionPerformed
    open fun bUpgrades_ActionPerformed(evt: ActionEvent?) {} // end of bUpgrades_ActionPerformed
    open fun bSettings_ActionPerformed(evt: ActionEvent?) {} // end of bSettings_ActionPerformed
    open fun bQuitGame_ActionPerformed(evt: ActionEvent?) {} // end of bQuitGame_ActionPerformed

    //Settings
    fun bDebug_ActionPerformed(evt: ActionEvent?) {} // end of bDebug_ActionPerformed
    val selectedMap: Int
        //Maps Selector
        get() {
            val e = jButtonGroupMapsBG.elements
            while (e.hasMoreElements()) {
                val b = e.nextElement()
                if (b.isSelected) return mapIDs[b.text]!!
            }
            return 0
        }
    val selectedDifficulty: Difficulty
        get() {
            val e = jButtonGroupDifficultyBG.elements
            while (e.hasMoreElements()) {
                val b = e.nextElement()
                if (b.isSelected) {
                    when (b.text) {
                        "Easy" -> return Difficulty.EASY
                        "Normal" -> return Difficulty.NORMAL
                        "Hard" -> return Difficulty.HARD
                    }
                }
            }
            throw IllegalComponentStateException("An invalid or no difficulty is selected.")
        }

    open fun bStartGame_ActionPerformed(evt: ActionEvent?) {
        println("bStartGame_actionPerformed")
    } // end of bStartGame_ActionPerformed

    //Multiple Screens
    open fun bBack_ActionPerformed(evt: ActionEvent?) {} // end of bBack_ActionPerformed
    open fun bConfirm_ActionPerformed(evt: ActionEvent?) {} // end of bConfirm_ActionPerformed

    //Quit
    open fun bAbort_ActionPerformed(evt: ActionEvent?) {} // end of bAbort_ActionPerformed

    //Game Screen
    open fun bQuit_ActionPerformed(evt: ActionEvent?) {} // end of bQuit_ActionPerformed
    open fun bStartRound_ActionPerformed(evt: ActionEvent?) {} // end of bStartRound_ActionPerformed
    fun bAutostart_ActionPerformed(evt: ActionEvent?) {} // end of bAutostart_ActionPerformed
    open fun bTower1_ActionPerformed(evt: ActionEvent?) {} // end of bTower1_ActionPerformed
    open fun bTower2_ActionPerformed(evt: ActionEvent?) {} // end of bTower2_ActionPerformed
    open fun bTower3_ActionPerformed(evt: ActionEvent?) {} // end of bTower3_ActionPerformed
    open fun bTower4_ActionPerformed(evt: ActionEvent?) {} // end of bTower4_ActionPerformed
    open fun bUpgrade1_ActionPerformed(evt: ActionEvent?) {} // end of bUpgrade1_ActionPerformed
    open fun bUpgrade2_ActionPerformed(evt: ActionEvent?) {} // end of bUpgrade2_ActionPerformed
    open fun bUpgrade3_ActionPerformed(evt: ActionEvent?) {} // end of bUpgrade3_ActionPerformed
    open fun bSell_ActionPerformed(evt: ActionEvent?) {} // end of bSell_ActionPerformed
    open fun bDeselect_ActionPerformed(evt: ActionEvent?) {} // end of bSell_ActionPerformed
    // end methods
} // end of class JMainFrame
