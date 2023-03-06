package de.wvsberlin;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import ch.aplu.jgamegrid.GameGrid;

/**
 *
 * Description
 *
 * @version 1.0 from 04/03/2023
 * @author till
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
    public JRadioButton jButtonGroupMapsRB0 = new JRadioButton("Map 1");
    public JRadioButton jButtonGroupMapsRB1 = new JRadioButton("Map 2");
    public JRadioButton jButtonGroupMapsRB2 = new JRadioButton("Map 3");
    public JRadioButton jButtonGroupMapsRB3 = new JRadioButton("Map 4");
  public JPanel jButtonGroupDifficulty = new JPanel();
    public ButtonGroup jButtonGroupDifficultyBG = new ButtonGroup();
    public TitledBorder jButtonGroupDifficultyTB = new TitledBorder("Difficulty");
    public JRadioButton jButtonGroupDifficultyRB0 = new JRadioButton("Easy");
    public JRadioButton jButtonGroupDifficultyRB1 = new JRadioButton("Normal");
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
  public JButton bUpgrade1 = new JButton();
  public JButton bUpgrade2 = new JButton();
  public JButton bUpgrade3 = new JButton();
  public JTextField tRound = new JTextField();
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
    
    bSelectMap.setBounds(560, 200, 80, 24);
    bSelectMap.setText("Select Map");
    bSelectMap.setMargin(new Insets(2, 2, 2, 2));
    bSelectMap.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        bSelectMap_ActionPerformed(evt);
      }
    });
    cp.add(bSelectMap);
    bUpgrades.setBounds(560, 260, 80, 24);
    bUpgrades.setText("Upgrades");
    bUpgrades.setMargin(new Insets(2, 2, 2, 2));
    bUpgrades.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        bUpgrades_ActionPerformed(evt);
      }
    });
    cp.add(bUpgrades);
    bSettings.setBounds(560, 460, 80, 24);
    bSettings.setText("Settings");
    bSettings.setMargin(new Insets(2, 2, 2, 2));
    bSettings.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        bSettings_ActionPerformed(evt);
      }
    });
    cp.add(bSettings);
    bQuitGame.setBounds(560, 520, 80, 24);
    bQuitGame.setText("Quit Game");
    bQuitGame.setMargin(new Insets(2, 2, 2, 2));
    bQuitGame.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        bQuitGame_ActionPerformed(evt);
      }
    });
    cp.add(bQuitGame);
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
    jButtonGroupMapsBG.add(jButtonGroupMapsRB3);
    jButtonGroupMaps.add(jButtonGroupMapsRB3);
    jButtonGroupMaps.setBorder(jButtonGroupMapsTB);
    cp.add(jButtonGroupMaps);
    jButtonGroupDifficulty.setLayout(null);
    jButtonGroupDifficulty.setBounds(875, 120, 120, 80);
    jButtonGroupDifficulty.setVisible(false);
    jButtonGroupDifficultyRB0.setBounds(7, 18, 106, 29);
    jButtonGroupDifficultyRB0.setSelected(true);
    jButtonGroupDifficultyBG.add(jButtonGroupDifficultyRB0);
    jButtonGroupDifficulty.add(jButtonGroupDifficultyRB0);
    jButtonGroupDifficultyRB1.setBounds(7, 47, 106, 29);
    jButtonGroupDifficultyBG.add(jButtonGroupDifficultyRB1);
    jButtonGroupDifficulty.add(jButtonGroupDifficultyRB1);
    jButtonGroupDifficulty.setBorder(jButtonGroupDifficultyTB);
    cp.add(jButtonGroupDifficulty);
    bStartGame.setBounds(895, 360, 80, 24);
    bStartGame.setText("Start Game");
    bStartGame.setMargin(new Insets(2, 2, 2, 2));
    bStartGame.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        bStartGame_ActionPerformed(evt);
      }
    });
    bStartGame.setVisible(false);
    cp.add(bStartGame);
    bBack.setBounds(895, 420, 80, 24);
    bBack.setText("Back");
    bBack.setMargin(new Insets(2, 2, 2, 2));
    bBack.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        bBack_ActionPerformed(evt);
      }
    });
    bBack.setVisible(false);
    cp.add(bBack);
    
    jGridPanel.setBounds(40, 120, 890, 500);
    jGridPanel.setVisible(false);
    cp.add(jGridPanel);
    gamegrid.setNbHorzCells(890);
    gamegrid.setNbVertCells(500);
    gamegrid.setCellSize(1);
    gamegrid.setBounds(40, 120, 890, 500);
    jGridPanel.add(gamegrid);
    bQuit.setBounds(1170, 10, 80, 24);
    bQuit.setText("Quit");
    bQuit.setMargin(new Insets(2, 2, 2, 2));
    bQuit.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        bQuit_ActionPerformed(evt);
      }
    });
    bQuit.setVisible(false);
    cp.add(bQuit);
    bConfirm.setBounds(320, 330, 80, 24);
    bConfirm.setText("Confirm");
    bConfirm.setMargin(new Insets(2, 2, 2, 2));
    bConfirm.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        bConfirm_ActionPerformed(evt);
      }
    });
    bConfirm.setVisible(false);
    cp.add(bConfirm);
    bAbort.setBounds(460, 330, 80, 24);
    bAbort.setText("Abort");
    bAbort.setMargin(new Insets(2, 2, 2, 2));
    bAbort.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        bAbort_ActionPerformed(evt);
      }
    });
    bAbort.setVisible(false);
    cp.add(bAbort);
    bStartRound.setBounds(1100, 580, 24, 24);
    bStartRound.setText(">");
    bStartRound.setMargin(new Insets(2, 2, 2, 2));
    bStartRound.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        bStartRound_ActionPerformed(evt);
      }
    });
    bStartRound.setVisible(false);
    cp.add(bStartRound);
    jSeparator1.setBounds(1040, 255, 200, 8);
    jSeparator1.setVisible(false);
    cp.add(jSeparator1);
    bAutostart.setBounds(1160, 580, 36, 24);
    bAutostart.setText(">>");
    bAutostart.setMargin(new Insets(2, 2, 2, 2));
    bAutostart.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        bAutostart_ActionPerformed(evt);
      }
    });
    bAutostart.setVisible(false);
    cp.add(bAutostart);
    bTower1.setBounds(1050, 180, 80, 24);
    bTower1.setText("Tower1");
    bTower1.setMargin(new Insets(2, 2, 2, 2));
    bTower1.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        bTower1_ActionPerformed(evt);
      }
    });
    bTower1.setVisible(false);
    cp.add(bTower1);
    bTower2.setBounds(1155, 180, 80, 24);
    bTower2.setText("Tower2");
    bTower2.setMargin(new Insets(2, 2, 2, 2));
    bTower2.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        bTower2_ActionPerformed(evt);
      }
    });
    bTower2.setVisible(false);
    cp.add(bTower2);
    bTower3.setBounds(1050, 300, 80, 24);
    bTower3.setText("Tower3");
    bTower3.setMargin(new Insets(2, 2, 2, 2));
    bTower3.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        bTower3_ActionPerformed(evt);
      }
    });
    bTower3.setVisible(false);
    cp.add(bTower3);
    bTower4.setBounds(1155, 300, 80, 24);
    bTower4.setText("Tower4");
    bTower4.setMargin(new Insets(2, 2, 2, 2));
    bTower4.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        bTower4_ActionPerformed(evt);
      }
    });
    bTower4.setVisible(false);
    cp.add(bTower4);
    bUpgrade1.setBounds(150, 50, 80, 24);
    bUpgrade1.setText("Upgrade1");
    bUpgrade1.setMargin(new Insets(2, 2, 2, 2));
    bUpgrade1.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        bUpgrade1_ActionPerformed(evt);
      }
    });
    bUpgrade1.setVisible(false);
    cp.add(bUpgrade1);
    bUpgrade2.setBounds(300, 50, 80, 24);
    bUpgrade2.setText("Upgrade2");
    bUpgrade2.setMargin(new Insets(2, 2, 2, 2));
    bUpgrade2.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        bUpgrade2_ActionPerformed(evt);
      }
    });
    bUpgrade2.setVisible(false);
    cp.add(bUpgrade2);
    bUpgrade3.setBounds(450, 50, 80, 24);
    bUpgrade3.setText("Upgrade3");
    bUpgrade3.setMargin(new Insets(2, 2, 2, 2));
    bUpgrade3.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        bUpgrade3_ActionPerformed(evt);
      }
    });
    bUpgrade3.setVisible(false);
    cp.add(bUpgrade3);
    tRound.setBounds(950, 50, 80, 24);
    tRound.setEditable(false);
    tRound.setVisible(false);
    cp.add(tRound);
    // end components
  } // end of public JMainFrame
  
  // start methods
  
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

  public String getSelectedMap() {
    for (java.util.Enumeration<AbstractButton> e = jButtonGroupMapsBG.getElements(); e.hasMoreElements();) {
      AbstractButton b = e.nextElement();
      if (b.isSelected()) return b.getText();
    }
    return "";
  }

  public int getSelectedDifficulty() {
    for (java.util.Enumeration<AbstractButton> e = jButtonGroupDifficultyBG.getElements(); e.hasMoreElements();) {
      AbstractButton b = e.nextElement();
      if (b.isSelected()) {
        if (b.getText() == "Easy") {
          return 0;
        }else if (b.getText() == "Normal") {
          return 1;
        }
        
      }
    }
    return 1;
  }

  public void bStartGame_ActionPerformed(ActionEvent evt) {
    // TODO add your code here
    
  } // end of bStartGame_ActionPerformed

  public void bBack_ActionPerformed(ActionEvent evt) {
    // TODO add your code here
    
  } // end of bBack_ActionPerformed

  public void bQuit_ActionPerformed(ActionEvent evt) {
    // TODO add your code here
    
  } // end of bQuit_ActionPerformed

  public void bConfirm_ActionPerformed(ActionEvent evt) {
    // TODO add your code here
    
  } // end of bConfirm_ActionPerformed

  public void bAbort_ActionPerformed(ActionEvent evt) {
    // TODO add your code here
    
  } // end of bAbort_ActionPerformed

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

  // end methods
  
} // end of class JMainFrame

