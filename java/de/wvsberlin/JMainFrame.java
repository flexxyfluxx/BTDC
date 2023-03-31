package de.wvsberlin;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import ch.aplu.jgamegrid.GameGrid;
import java.util.HashMap;
import java.util.Objects;
import de.wvsberlin.Difficulty;

/**
 *
 * Description
 *
 * @version 1.0 from 04/03/2023
 * @author 
 */

public class JMainFrame extends JFrame {
  // start attributes
  public JButton bSelectMap = new JButton();
  public JButton bUpgrades = new JButton();
  public JButton bSettings = new JButton();
  public JButton bQuitGame = new JButton();
  public JPanel jButtonGroupMaps = new JPanel();
    public ButtonGroup jButtonGroupMapsBG = new ButtonGroup();
    public TitledBorder jButtonGroupMapsTB = new TitledBorder("Maps");
    public JRadioButton jButtonGroupMapsRB0 = new JRadioButton("Raum 208");
    public JRadioButton jButtonGroupMapsRB1 = new JRadioButton("Raum 208 Legacy");
    public JRadioButton jButtonGroupMapsRB2 = new JRadioButton("Jungel");
    public JRadioButton jButtonGroupMapsRB3 = new JRadioButton("de.wvsberlin.Map not implemented");
  public JPanel jButtonGroupDifficulty = new JPanel();
    public ButtonGroup jButtonGroupDifficultyBG = new ButtonGroup();
    public TitledBorder jButtonGroupDifficultyTB = new TitledBorder("Difficulty");
    public JRadioButton jButtonGroupDifficultyRB0 = new JRadioButton("Easy");
    public JRadioButton jButtonGroupDifficultyRB1 = new JRadioButton("Normal");
    public JRadioButton jButtonGroupDifficultyRB2 = new JRadioButton("Hard");
  public JButton bStartGame = new JButton();
  public JButton bBack = new JButton();
  public GameGrid gamegrid = new GameGrid();
  public JPanel jGridPanel = new JPanel();
  public JButton bQuit = new JButton();
  public JButton bConfirm = new JButton();
  public JButton bAbort = new JButton();
  public JButton bStartRound = new JButton();
  public JSeparator jSeparator1 = new JSeparator();
  public JToggleButton bAutostart = new JToggleButton();
  public JButton bTower1 = new JButton();
  public JButton bTower2 = new JButton();
  public JButton bTower3 = new JButton();
  public JButton bTower4 = new JButton();
  public JLabel lTower1 = new JLabel();
  public JLabel lTower2 = new JLabel();
  public JLabel lTower3 = new JLabel();
  public JLabel lTower4 = new JLabel();
  public JButton bUpgrade1 = new JButton();
  public JButton bUpgrade2 = new JButton();
  public JButton bUpgrade3 = new JButton();
  public JButton bSell = new JButton();
  public JButton bDeselect = new JButton();
  public JTextField tUpgrade1 = new JTextField();
  public JTextField tUpgrade2 = new JTextField();
  public JTextField tUpgrade3 = new JTextField();
  public JLabel lUpgrade1 = new JLabel("Attackspeed:");
  public JLabel lUpgrade2 = new JLabel("Attackdamage:");
  public JLabel lUpgrade3 = new JLabel("Towerspecific:");
  public JLabel lCurrentRound = new JLabel("Round:");
  public JLabel lMoney = new JLabel("Money:");
  public JLabel lHealth = new JLabel("Health:");
  public JTextField tCurrentRound = new JTextField();
  public JTextField tMoney = new JTextField();
  public JTextField tHealth = new JTextField();
  public JLabel lgooseCondition = new JLabel();
  public JLabel lgameOver = new JLabel("You Goose!");
  public JLabel lwon = new JLabel("You Won!");
  public JToggleButton bDebug = new JToggleButton("Debug");

  private final HashMap<String, Integer> mapIDs = new HashMap<>();
  // end attributes
  
  public JMainFrame() { 
    // Frame-Init
    super();
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    int frameWidth = 1280; 
    int frameHeight = 720;
    setSize(frameWidth, frameHeight);
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    int x = (d.width - getSize().width) / 2;
    int y = (d.height - getSize().height) / 2;
    setLocation(x, y);
    setTitle("BTDC");
    setResizable(false);
    Container cp = getContentPane();
    cp.setLayout(null);
    // start components
    
    //Main Menu components
    bSelectMap.setBounds(560, 200, 80, 24);
    bSelectMap.setText("Select de.wvsberlin.Map");
    bSelectMap.setMargin(new Insets(2, 2, 2, 2));
    bSelectMap.addActionListener(this::bSelectMap_ActionPerformed);
    cp.add(bSelectMap);
    bUpgrades.setBounds(560, 260, 80, 24);
    bUpgrades.setText("Upgrades");
    bUpgrades.setMargin(new Insets(2, 2, 2, 2));
    bUpgrades.addActionListener(this::bUpgrades_ActionPerformed);
    cp.add(bUpgrades);
    bSettings.setBounds(560, 460, 80, 24);
    bSettings.setText("Settings");
    bSettings.setMargin(new Insets(2, 2, 2, 2));
    bSettings.addActionListener(this::bSettings_ActionPerformed);
    cp.add(bSettings);
    bQuitGame.setBounds(560, 520, 80, 24);
    bQuitGame.setText("Quit Game");
    bQuitGame.setMargin(new Insets(2, 2, 2, 2));
    bQuitGame.addActionListener(this::bQuitGame_ActionPerformed);
    cp.add(bQuitGame);

    //Settings components
    bDebug.setBounds(560, 258, 80, 24);
    bDebug.setMargin(new Insets(2, 2, 2, 2));
    bDebug.setVisible(false);
    bDebug.addActionListener(this::bDebug_ActionPerformed);
    cp.add(bDebug);

    //Maps Selector Components
    jButtonGroupMaps.setLayout(null);
    jButtonGroupMaps.setBounds(40, 120, 800, 500);
    jButtonGroupMaps.setVisible(false);
    jButtonGroupMapsRB0.setBounds(7, 18, 393, 239);
    jButtonGroupMapsRB0.setSelected(true);
    jButtonGroupMapsBG.add(jButtonGroupMapsRB0);
    jButtonGroupMaps.add(jButtonGroupMapsRB0);
    jButtonGroupMapsRB1.setBounds(400, 18, 393, 239);
    jButtonGroupMapsBG.add(jButtonGroupMapsRB1);
    jButtonGroupMaps.add(jButtonGroupMapsRB1);
    jButtonGroupMapsRB2.setBounds(7, 257, 393, 239);
    jButtonGroupMapsBG.add(jButtonGroupMapsRB2);
    jButtonGroupMaps.add(jButtonGroupMapsRB2);
    jButtonGroupMapsRB3.setBounds(400, 257, 393, 239);
    jButtonGroupMapsRB3.setEnabled(false);
    jButtonGroupMapsBG.add(jButtonGroupMapsRB3);
    jButtonGroupMaps.add(jButtonGroupMapsRB3);
    jButtonGroupMaps.setBorder(jButtonGroupMapsTB);
    cp.add(jButtonGroupMaps);
    jButtonGroupDifficulty.setLayout(null);
    jButtonGroupDifficulty.setBounds(875, 120, 120, 120);
    jButtonGroupDifficulty.setVisible(false);
    jButtonGroupDifficultyRB0.setBounds(7, 18, 106, 29);
    jButtonGroupDifficultyRB0.setSelected(true);
    jButtonGroupDifficultyBG.add(jButtonGroupDifficultyRB0);
    jButtonGroupDifficulty.add(jButtonGroupDifficultyRB0);
    jButtonGroupDifficultyRB1.setBounds(7, 47, 106, 29);
    jButtonGroupDifficultyBG.add(jButtonGroupDifficultyRB1);
    jButtonGroupDifficulty.add(jButtonGroupDifficultyRB1);
    jButtonGroupDifficultyRB2.setBounds(7, 76, 106, 29);
    jButtonGroupDifficultyBG.add(jButtonGroupDifficultyRB2);
    jButtonGroupDifficulty.add(jButtonGroupDifficultyRB2);
    jButtonGroupDifficulty.setBorder(jButtonGroupDifficultyTB);
    cp.add(jButtonGroupDifficulty);
    bStartGame.setBounds(895, 360, 80, 24);
    bStartGame.setText("Start Game");
    bStartGame.setMargin(new Insets(2, 2, 2, 2));
    bStartGame.addActionListener(this::bStartGame_ActionPerformed);
    bStartGame.setVisible(false);
    cp.add(bStartGame);

    //used for multiple screens
    bBack.setBounds(895, 420, 80, 24);
    bBack.setText("Back");
    bBack.setMargin(new Insets(2, 2, 2, 2));
    bBack.addActionListener(this::bBack_ActionPerformed);
    bBack.setVisible(false);
    cp.add(bBack);
    bConfirm.setBounds(320, 330, 80, 24);
    bConfirm.setText("Confirm");
    bConfirm.setMargin(new Insets(2, 2, 2, 2));
    bConfirm.addActionListener(this::bConfirm_ActionPerformed);
    bConfirm.setVisible(false);
    cp.add(bConfirm);
    
    //Game components
    jGridPanel.setBounds(40, 120, 960, 540);
    jGridPanel.setVisible(false);
    cp.add(jGridPanel);
    gamegrid.setNbHorzCells(960);
    gamegrid.setNbVertCells(540);
    gamegrid.setCellSize(1);
    gamegrid.setBgColor(Color.BLACK);
    gamegrid.setBounds(40, 120, 960, 540);
    jGridPanel.add(gamegrid);
    bQuit.setBounds(1170, 10, 80, 24);
    bQuit.setText("Quit");
    bQuit.setMargin(new Insets(2, 2, 2, 2));
    bQuit.addActionListener(this::bQuit_ActionPerformed);
    bQuit.setVisible(false);
    cp.add(bQuit);
    bStartRound.setBounds(1100, 580, 24, 24);
    bStartRound.setText(">");
    bStartRound.setMargin(new Insets(2, 2, 2, 2));
    bStartRound.addActionListener(this::bStartRound_ActionPerformed);
    bStartRound.setVisible(false);
    cp.add(bStartRound);
    jSeparator1.setBounds(1040, 255, 200, 8);
    jSeparator1.setVisible(false);
    cp.add(jSeparator1);
    bAutostart.setBounds(1160, 580, 36, 24);
    bAutostart.setText(">>");
    bAutostart.setMargin(new Insets(2, 2, 2, 2));
    bAutostart.addActionListener(this::bAutostart_ActionPerformed);
    bAutostart.setVisible(false);
    cp.add(bAutostart);
    bTower1.setBounds(1050, 180, 80, 24);
    bTower1.setText("Tower1");
    bTower1.setMargin(new Insets(2, 2, 2, 2));
    bTower1.addActionListener(this::bTower1_ActionPerformed);
    bTower1.setVisible(false);
    cp.add(bTower1);
    bTower2.setBounds(1155, 180, 80, 24);
    bTower2.setText("Tower2");
    bTower2.setMargin(new Insets(2, 2, 2, 2));
    bTower2.addActionListener(this::bTower2_ActionPerformed);
    bTower2.setVisible(false);
    cp.add(bTower2);
    bTower3.setBounds(1050, 300, 80, 24);
    bTower3.setText("Tower3");
    bTower3.setMargin(new Insets(2, 2, 2, 2));
    bTower3.addActionListener(this::bTower3_ActionPerformed);
    bTower3.setVisible(false);
    cp.add(bTower3);
    bTower4.setBounds(1155, 300, 80, 24);
    bTower4.setText("Tower4");
    bTower4.setMargin(new Insets(2, 2, 2, 2));
    bTower4.addActionListener(this::bTower4_ActionPerformed);
    bTower4.setVisible(false);
    cp.add(bTower4);
    lTower1.setBounds(1050, 210, 80, 24);
    lTower1.setVisible(false);
    cp.add(lTower1);
    lTower2.setBounds(1155, 210, 80, 24);
    lTower2.setVisible(false);
    cp.add(lTower2);
    lTower3.setBounds(1050, 330, 80, 24);
    lTower3.setVisible(false);
    cp.add(lTower3);
    lTower4.setBounds(1155, 330, 80, 24);
    lTower4.setVisible(false);
    cp.add(lTower4);
    bUpgrade1.setBounds(150, 50, 80, 24);
    bUpgrade1.setText("Upgrade");
    bUpgrade1.setMargin(new Insets(2, 2, 2, 2));
    bUpgrade1.addActionListener(this::bUpgrade1_ActionPerformed);
    bUpgrade1.setVisible(false);
    cp.add(bUpgrade1);
    bUpgrade2.setBounds(300, 50, 80, 24);
    bUpgrade2.setText("Upgrade");
    bUpgrade2.setMargin(new Insets(2, 2, 2, 2));
    bUpgrade2.addActionListener(this::bUpgrade2_ActionPerformed);
    bUpgrade2.setVisible(false);
    cp.add(bUpgrade2);
    bUpgrade3.setBounds(450, 50, 80, 24);
    bUpgrade3.setText("Upgrade");
    bUpgrade3.setMargin(new Insets(2, 2, 2, 2));
    bUpgrade3.addActionListener(this::bUpgrade3_ActionPerformed);
    bUpgrade3.setVisible(false);
    cp.add(bUpgrade3);
    bSell.setBounds(600, 50, 80, 24);
    bSell.setText("Sell");
    bSell.setMargin(new Insets(2, 2, 2, 2));
    bSell.addActionListener(this::bSell_ActionPerformed);
    bSell.setVisible(false);
    cp.add(bSell);
    bDeselect.setBounds(600, 80, 80, 24);
    bDeselect.setText("Deselect");
    bDeselect.setMargin(new Insets(2, 2, 2, 2));
    bDeselect.addActionListener(this::bDeselect_ActionPerformed);
    bDeselect.setVisible(false);
    cp.add(bDeselect);
    lUpgrade1.setBounds(150, 20, 80, 24);
    lUpgrade1.setVisible(false);
    cp.add(lUpgrade1);
    lUpgrade2.setBounds(300, 20, 80, 24);
    lUpgrade2.setVisible(false);
    cp.add(lUpgrade2);
    lUpgrade3.setBounds(450, 20, 80, 24);
    lUpgrade3.setVisible(false);
    cp.add(lUpgrade3);
    tUpgrade1.setBounds(150, 80, 80, 24);
    tUpgrade1.setEditable(false);
    tUpgrade1.setVisible(false);
    cp.add(tUpgrade1);
    tUpgrade2.setBounds(300, 80, 80, 24);
    tUpgrade2.setEditable(false);
    tUpgrade2.setVisible(false);
    cp.add(tUpgrade2);
    tUpgrade3.setBounds(450, 80, 80, 24);
    tUpgrade3.setEditable(false);
    tUpgrade3.setVisible(false);
    cp.add(tUpgrade3);
    tCurrentRound.setBounds(950, 20, 80, 24);
    tCurrentRound.setEditable(false);
    tCurrentRound.setVisible(false);
    cp.add(tCurrentRound);
    tMoney.setBounds(950, 50, 80, 24);
    tMoney.setEditable(false);
    tMoney.setVisible(false);
    cp.add(tMoney);
    tHealth.setBounds(950, 80, 80, 24);
    tHealth.setEditable(false);
    tHealth.setVisible(false);
    cp.add(tHealth);
    lCurrentRound.setBounds(900, 20, 80, 24);
    lCurrentRound.setVisible(false);
    cp.add(lCurrentRound);
    lMoney.setBounds(900, 50, 80, 24);
    lMoney.setVisible(false);
    cp.add(lMoney);
    lHealth.setBounds(900, 80, 80, 24);
    lHealth.setVisible(false);
    cp.add(lHealth);

    //Quit Screen components
    bAbort.setBounds(460, 330, 80, 24);
    bAbort.setText("Abort");
    bAbort.setMargin(new Insets(2, 2, 2, 2));
    bAbort.addActionListener(this::bAbort_ActionPerformed);
    bAbort.setVisible(false);
    cp.add(bAbort);

    //Win and Goose Screens
    lgooseCondition.setBounds(240, 60, 800, 600);
    lgooseCondition.setVisible(false);
    cp.add(lgooseCondition);
    lgameOver.setBounds(610, 10, 120, 34);
    lgameOver.setFont(new Font("Dialog", Font.BOLD, 18));
    lgameOver.setVisible(false);
    cp.add(lgameOver);
    lwon.setBounds(590, 150, 120, 34);
    lwon.setFont(new Font("Dialog", Font.BOLD, 18));
    lwon.setVisible(false);
    cp.add(lwon);
    // end components

    //map namen -> map ids
    mapIDs.put("Raum 208", 0);
    mapIDs.put("Raum 208 Legacy", 1);
    mapIDs.put("Jungel", 2);
    mapIDs.put("de.wvsberlin.Map not implemented", 3);
  } // end of public JMainFrame


  public static void main(String[] args) {
    new JMainFrame().setVisible(true);
  }
  
  // start methods
  
  //Main Menu
  public void bSelectMap_ActionPerformed(ActionEvent evt) {
    // TODO add your code here
    
  } // end of bSelectMap_ActionPerformed

  public void bUpgrades_ActionPerformed(ActionEvent evt) {
    // TODO add your code here
    
  } // end of bUpgrades_ActionPerformed

  public void bSettings_ActionPerformed(ActionEvent evt) {
    // TODO add your code here
    
  } // end of bSettings_ActionPerformed

  public void bQuitGame_ActionPerformed(ActionEvent evt) {
    // TODO add your code here
    
  } // end of bQuitGame_ActionPerformed
  
  //Settings
  public void bDebug_ActionPerformed(ActionEvent evt) {
    // TODO add your code here
    
  } // end of bDebug_ActionPerformed

  //Maps Selector
  public int getSelectedMap() {
    for (java.util.Enumeration<AbstractButton> e = jButtonGroupMapsBG.getElements(); e.hasMoreElements();) {
      AbstractButton b = e.nextElement();
      if (b.isSelected()) return mapIDs.get(b.getText());
    }
    return 0;
  }

  public Difficulty getSelectedDifficulty() {
    for (java.util.Enumeration<AbstractButton> e = jButtonGroupDifficultyBG.getElements(); e.hasMoreElements();) {
      AbstractButton b = e.nextElement();
      if (b.isSelected()) {
        String btnText = b.getText();
        switch (btnText) {
          case "Easy":
            return Difficulty.EASY;
          case "Normal":
            return Difficulty.NORMAL;
          case "Hard":
            return Difficulty.HARD;
        }
      }
    }
    throw new IllegalComponentStateException("No difficulty is selected. This should not regularly be able to happen!");
  }

  public void bStartGame_ActionPerformed(ActionEvent evt) {
    System.out.println("Java bStartGame_actionPerformed called");
  } // end of bStartGame_ActionPerformed

  //Multiple Screens
  public void bBack_ActionPerformed(ActionEvent evt) {
    // TODO add your code here
    
  } // end of bBack_ActionPerformed

  public void bConfirm_ActionPerformed(ActionEvent evt) {
    // TODO add your code here
    
  } // end of bConfirm_ActionPerformed

  //Quit
  public void bAbort_ActionPerformed(ActionEvent evt) {
    // TODO add your code here
    
  } // end of bAbort_ActionPerformed
  
  //Game Screen
  public void bQuit_ActionPerformed(ActionEvent evt) {
    // TODO add your code here
    
  } // end of bQuit_ActionPerformed

  public void bStartRound_ActionPerformed(ActionEvent evt) {
    // TODO add your code here
    
  } // end of bStartRound_ActionPerformed

  public void bAutostart_ActionPerformed(ActionEvent evt) {
    // TODO add your code here
    
  } // end of bAutostart_ActionPerformed

  public void bTower1_ActionPerformed(ActionEvent evt) {
    // TODO add your code here
    
  } // end of bTower1_ActionPerformed

  public void bTower2_ActionPerformed(ActionEvent evt) {
    // TODO add your code here
    
  } // end of bTower2_ActionPerformed

  public void bTower3_ActionPerformed(ActionEvent evt) {
    // TODO add your code here
    
  } // end of bTower3_ActionPerformed

  public void bTower4_ActionPerformed(ActionEvent evt) {
    // TODO add your code here
    
  } // end of bTower4_ActionPerformed

  public void bUpgrade1_ActionPerformed(ActionEvent evt) {
    // TODO add your code here
    
  } // end of bUpgrade1_ActionPerformed

  public void bUpgrade2_ActionPerformed(ActionEvent evt) {
    // TODO add your code here
    
  } // end of bUpgrade2_ActionPerformed

  public void bUpgrade3_ActionPerformed(ActionEvent evt) {
    // TODO add your code here
    
  } // end of bUpgrade3_ActionPerformed

  public void bSell_ActionPerformed(ActionEvent evt) {
    // TODO add your code here
    
  } // end of bSell_ActionPerformed

  public void bDeselect_ActionPerformed(ActionEvent evt) {
    // TODO add your code here
    
  } // end of bSell_ActionPerformed

  // end methods
  
} // end of class JMainFrame
