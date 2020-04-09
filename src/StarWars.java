
 /*
   File Name:         StarWars.java
   Programmer:         Andrew St Germain
   Date Last Modified: May. 16, 2013

   Problem Statement: Make a Star Wars Game
   
   
   
   Overall Plan (step-by-step, how you want the code to make it happen):
   1. Create a gui 
   2. Create the opening menu
   3. Use Actionlisteners to check for enter to go on to the next screen
   4. Next Screen is cinamatic, skit with enter
   5. Choose side, up or down arrows, it will take you to your character selection
   6. After you choose your character, you will be promoted to choose your weapon
   7. After that you will be directed to the map
   8. At the map you can choose your stage or access the menu
   9. Use sequencer to play midi files for background music
   10. Create a sound effect class to play sound effects, use HashMap and InputStream
   11. Create classes for general character then use inheritance and make playable
   *  character classes, and npc classes
   12. If time, make a store class that allows you to buy and upgrade weapons
   13. Create a battle class to have rng in battles and figure out weakness and strength.
   14. Create a menu class that will lead into a sub menu classes
   

   Classes needed and Purpose 
   main class – StarWars.java
   * MidiPlayer.java
   * JukeBox.java
   * Character.java
   * Playable.java
   * NPC.java
   * Store.java
   * Battle.java
   * Menu.java
   

*/











import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
                
 
public class StarWars extends JPanel implements KeyListener, ActionListener{
        public static JFrame jf;
        public Toolkit tk = jf.getToolkit();      
        //Game Timer
        private javax.swing.Timer gameTimer;
        //menu
        JMenuBar menubar;
        JMenu game, other;
        JMenuItem exit, save, load, newGame, about;       
        //Theme Music
 	private MidiPlayer title = new MidiPlayer("Audio/BGM/MainTitle.mid",true);
        private MidiPlayer openingBGM = new MidiPlayer("Audio/BGM/opening.mid",true);
        private MidiPlayer fightBGM = new MidiPlayer("Audio/BGM/fight.mid",true);
        private MidiPlayer cantinaBGM = new MidiPlayer("Audio/BGM/Cantina.mid",true);
	public MidiPlayer currentBGM;    
        //Battle Interface
        private Image battleText = tk.createImage(getClass().getResource("Graphics/Titles/Start.png"));
        //Title Images
        private Image titlescreen = tk.createImage(getClass().getResource("Graphics/Titles/Title.gif"));
	private Image start_symbol = tk.createImage(getClass().getResource("Graphics/Titles/Start.png"));
        private Image title2 = tk.createImage(getClass().getResource("Graphics/Titles/title2.png"));
        //Menu Images
        private Image sithMenu = tk.createImage(getClass().getResource("Graphics/Interface/sithMenu.png"));
        //Scene Images
        private Image firstScene = tk.createImage(getClass().getResource("Graphics/Scenes/Naboo.gif"));
        private Image firstScene2 = tk.createImage(getClass().getResource("Graphics/Scenes/NabooPlanet.png"));
        private Image chooseSide = tk.createImage(getClass().getResource("Graphics/Scenes/jediorsith.png"));
        private Image chooseSaberSith = tk.createImage(getClass().getResource("Graphics/Scenes/chooseSaberSith.png"));
        private Image gameMap = tk.createImage(getClass().getResource("Graphics/Interface/StarWarsMap.png"));
        private Image duel = tk.createImage(getClass().getResource("Graphics/Scenes/Duel.gif"));

    //Playable Character Images
        
        //Playable Charater Menus
        private Image charSelectionSith = tk.createImage(getClass().getResource("Graphics/Interface/SithSelection.png"));
        private Image charSelectionJedi = tk.createImage(getClass().getResource("Graphics/Interface/SithSelection.png"));
//edit
            //Anakin
        private Image anPlayerUp = tk.createImage(getClass().getResource("Graphics/Characters/Playable/Anakin/ANUp.png"));
	private Image anPlayerUp1 = tk.createImage(getClass().getResource("Graphics/Characters/Playable/Anakin/ANUp1.png"));
	private Image anPlayerUp2 = tk.createImage(getClass().getResource("Graphics/Characters/Playable/Anakin/ANUp3.png"));
	private Image anPlayerDown = tk.createImage(getClass().getResource("Graphics/Characters/Playable/Anakin/ANDown.png"));
	private Image anPlayerDown1 = tk.createImage(getClass().getResource("Graphics/Characters/Playable/Anakin/ANDown1.png"));
	private Image anPlayerDown2 = tk.createImage(getClass().getResource("Graphics/Characters/Playable/Anakin/ANDown3.png"));
	private Image anPlayerLeft = tk.createImage(getClass().getResource("Graphics/Characters/Playable/Anakin/ANLeft.png"));
	private Image anPlayerLeft1 = tk.createImage(getClass().getResource("Graphics/Characters/Playable/Anakin/ANLeft1.png"));
	private Image anPlayerLeft2 = tk.createImage(getClass().getResource("Graphics/Characters/Playable/Anakin/ANLeft3.png"));
	private Image anPlayerRight = tk.createImage(getClass().getResource("Graphics/Characters/Playable/Anakin/ANRight.png"));
	private Image anPlayerRight1 = tk.createImage(getClass().getResource("Graphics/Characters/Playable/Anakin/ANRight1.png"));
	private Image anPlayerRight2 = tk.createImage(getClass().getResource("Graphics/Characters/Playable/Anakin/ANRight3.png"));
        
            //Obi-Wan
        
            //Qui-Gon
        
            //Darth Maul
        private Image DMGif = tk.createImage(getClass().getResource("Graphics/Characters/DMGif.gif"));
        private Image DMMenu = tk.createImage(getClass().getResource("Graphics/Interface/DMMenu.png"));
        private Image DMMenuGif = tk.createImage(getClass().getResource("Graphics/Interface/DMMenu.gif"));

        private Image DMMenuChar = tk.createImage(getClass().getResource("Graphics/Interface/DMMenuChar.png"));
            //Darth Vader
        private Image DVGif = tk.createImage(getClass().getResource("Graphics/Characters/DVGif.gif"));
        private Image DVMenu = tk.createImage(getClass().getResource("Graphics/Interface/DVMenu.png"));

            //Darth Sidious
        private Image DSGif = tk.createImage(getClass().getResource("Graphics/Characters/DSGif.gif"));
        private Image DSMenu = tk.createImage(getClass().getResource("Graphics/Interface/DSMenu.png"));
	//Menu Images
	private Image arrow = Toolkit.getDefaultToolkit().getImage(getClass().getResource("Graphics/Interface/Arrow.png"));
        private Image arrow2 = Toolkit.getDefaultToolkit().getImage(getClass().getResource("Graphics/Interface/Arrow2.png"));
        private Image arrow3 = Toolkit.getDefaultToolkit().getImage(getClass().getResource("Graphics/Interface/Arrow3.png"));

        private Image arrowUpSith = Toolkit.getDefaultToolkit().getImage(getClass().getResource("Graphics/Interface/ArrowUpSith.png"));
        private Image arrowUpJedi = Toolkit.getDefaultToolkit().getImage(getClass().getResource("Graphics/Interface/ArrowUpJedi.png"));
        private Image arrowUp = Toolkit.getDefaultToolkit().getImage(getClass().getResource("Graphics/Interface/ArrowUp.png"));


        //Variables
        private boolean atTitle = true;
        private boolean atFirstScene = false;
	private boolean atChooseSide = false;
        private boolean atChooseCharJedi = false;
        private boolean atChooseCharSith = false;
        private boolean atChooseSaberJedi = false;
        private boolean atChooseSaberSith = false;
	private boolean start_visible = true;
	private boolean gamestarted = false;
        private boolean menuOpen = false;
        private boolean charDM = false;
        private boolean charDV = false;
        private boolean charDS = false;
        private boolean charMenu = false;
        private boolean map1, map2, map3, map4, map5, map6, map7, map8, map9;
        private long currentTime;
        private int offsetX = 0, offsetY = 0;
	private int TILE_WIDTH_PIXELS = 32;
	private int TILE_HEIGHT_PIXELS = 32;
	private int concurrentMenuItem = 0;
        private int charSelc = 0;
        private int saberSelc = 0;
        private int mapSelc = 0;
        private int menuSelc = 0;
        private int charMenuSelc = 0;
        private JukeBox col = new JukeBox();

        
        //Constuctor
        public StarWars() {
            //Start title music
            currentBGM = title;
            currentBGM.start();  
            //SE Music         
	   col.loadClip("/Audio/SE/Select.wav", "Select", 1);
	   col.loadClip("/Audio/SE/Menu.wav", "Menu", 1);
            //create and add menu/items
            menubar = new JMenuBar();
            jf.setJMenuBar(menubar);  
            game = new JMenu("Game");
            menubar.add(game);
            other = new JMenu("Other");
            menubar.add(other);
            about = new JMenuItem("About");
            other.add(about);
            newGame = new JMenuItem("New Game");
            game.add(newGame);
            save = new JMenuItem("Save Game");
            game.add(save);
            load = new JMenuItem("Load Game");
            game.add(load);
            exit = new JMenuItem("Exit");
            game.add(exit);
            //frame
            setBackground(Color.BLACK);
            setPreferredSize(new Dimension(480,320));
            addKeyListener(this);
            gameTimer = new javax.swing.Timer(5, this);
            gameTimer.start();
            StarWars.event e = new StarWars.event();
            //Menu Action Listeners
            exit.addActionListener(new StarWars.exit());
            about.addActionListener(new StarWars.about());
            newGame.addActionListener(new StarWars.newGame());
 
        }
        

    

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
     	if(atTitle == true)
        {
     		if(keyCode == KeyEvent.VK_ENTER)
                {
     			
	        	atTitle = false;
	        	                      
	        	atFirstScene = true;                       
	        }
     	}      
        else if(atFirstScene == true)
        {
            if(keyCode == KeyEvent.VK_ENTER)
                {
     			
	        	atFirstScene = false;	                              
	        	atChooseSide = true;                       
	        }
        }
        else if(atChooseSide == true)
        {
            
            gameTimer.setDelay(200);
            if(keyCode == KeyEvent.VK_UP)
            {
                button_pressed();
		if(concurrentMenuItem > 0)
                {
                    
                    concurrentMenuItem--;
                    
		}
	    }
	    if(keyCode == KeyEvent.VK_DOWN)
            {
                                

		button_pressed();
		if(concurrentMenuItem < 1)
                {
                                   

                    concurrentMenuItem = concurrentMenuItem + 1;
                                    

		}
	    }
            if(keyCode == KeyEvent.VK_ENTER)
            {
                
                
		button_pressed();
		if(concurrentMenuItem == 0)
                {
                   //Jedi Path
                    atChooseSide = false;
                    atChooseCharJedi = true;
		}
		else if(concurrentMenuItem == 1)
                {
                    //Sith Path
                    atChooseSide = false;
                    atChooseCharSith = true;
		}			
	    }
        } 
        else if(atChooseCharSith == true)
        {
            
            
            gameTimer.setDelay(200);
            if(keyCode == KeyEvent.VK_LEFT)
            {               
                button_pressed();
		if(charSelc > 0)
                {
                    charSelc--;
		}
	    }
	    else if(keyCode == KeyEvent.VK_RIGHT)
            {
		button_pressed();
		if(charSelc < 2)
                {
                    charSelc++;
		}
	    }
            if(keyCode == KeyEvent.VK_ENTER)
            {
		button_pressed();
		if(charSelc == 0)
                {
                   //Darth Maul
                    atChooseCharSith = false;
                    charDM = true;
                    atChooseSaberSith = true;
		}
		else if(charSelc == 1)
                {
                    //Darth Vader
                    atChooseCharSith = false;
                    charDV = true;
                    atChooseSaberSith = true;
		}
                else if(charSelc == 2)
                {
                    //Darth Sidious
                    atChooseCharSith = false;
                    charDS = true;
                    atChooseSaberSith = true;
		}
                
	    }
        }
        else if(atChooseCharJedi == true)
        {
            
            
            gameTimer.setDelay(200);
            if(keyCode == KeyEvent.VK_LEFT)
            {               
                button_pressed();
		if(charSelc > 0)
                {
                    charSelc--;
		}
	    }
	    else if(keyCode == KeyEvent.VK_RIGHT)
            {
		button_pressed();
		if(charSelc < 2)
                {
                    charSelc++;
		}
	    }
            if(keyCode == KeyEvent.VK_ENTER)
            {
		button_pressed();
		if(charSelc == 0)
                {
                   //Darth Maul
                    atChooseCharJedi = false;
                    atChooseSaberJedi = true;
		}
		else if(charSelc == 1)
                {
                    //Darth Vader
                    atChooseCharJedi = false;
                    atChooseSaberJedi = true;
		}
                else if(charSelc == 2)
                {
                    //Darth Sidious
                    atChooseCharJedi = false;
                    atChooseSaberJedi = true;
		}
                
	    }
        }
        
        else if(atChooseSaberSith == true)
        {        
            gameTimer.setDelay(200);
            if(keyCode == KeyEvent.VK_LEFT)
            {               
                button_pressed();
		if(saberSelc > 0)
                {
                    saberSelc--;
		}
	    }
	    else if(keyCode == KeyEvent.VK_RIGHT)
            {
		button_pressed();
		if(saberSelc < 2)
                {                   
                    saberSelc++;
		}
	    }
            if(keyCode == KeyEvent.VK_ENTER)
            {
		button_pressed();
                
		if(saberSelc == 0)
                {
                   //Single                   
                    atChooseSaberSith = false;
                    gamestarted = true;
                    currentBGM.stop();
                    currentBGM = openingBGM;
                    currentBGM.start(); 
		}
		else if(saberSelc == 1)
                {
                    //Double sided        
                    atChooseSaberSith = false;
                    gamestarted = true;
                    currentBGM.stop();
                    currentBGM = openingBGM;
                    currentBGM.start(); 
		}
                else if(saberSelc == 2)
                {
                    //Dual                   
                    atChooseSaberSith = false;
                    gamestarted = true;
                    currentBGM.stop();
                    currentBGM = openingBGM;
                    currentBGM.start(); 
		}
                
	    }
        }
        else if(gamestarted == true)
        {
            gameTimer.setDelay(200);
            if(keyCode == KeyEvent.VK_LEFT)
            {               
                button_pressed();
		if(mapSelc > 0)
                {
                    mapSelc--;
		}
	    }
	    else if(keyCode == KeyEvent.VK_RIGHT)
            {
		button_pressed();
		if(mapSelc < 8)
                {
                    mapSelc++;
		}
	    }
            else if(keyCode == KeyEvent.VK_DOWN)
            {
		button_pressed();
		if(mapSelc < 6)
                {
                    mapSelc = mapSelc + 3;
		}
	    }
            else if(keyCode == KeyEvent.VK_UP)
            {
		button_pressed();
		if(mapSelc > 2)
                {
                    mapSelc = mapSelc - 3;
		}
	    }
            else if(keyCode == KeyEvent.VK_SPACE)
            {
		menu_opened();
		gamestarted = false;
                menuOpen = true;
                currentBGM.stop();
                currentBGM = cantinaBGM;
                currentBGM.start();
	    }
            else if(keyCode == KeyEvent.VK_ENTER)
            {
		button_pressed();
		if(mapSelc == 0)
                {
                    gamestarted = false;
                    map1 = true;
		}
                else if(mapSelc == 1)
                {
                    gamestarted = false;
                    map2 = true;
		}
                else if(mapSelc == 2)
                {
                    gamestarted = false;
                    map3 = true;
		}
                else if(mapSelc == 3)
                {
                    
                    gamestarted = false;
                    map4 = true;
                    currentBGM.stop();
                    currentBGM = fightBGM;
                    currentBGM.start(); 
		}
                else if(mapSelc == 4)
                {
                    gamestarted = false;
                    map5 = true;
		}
                else if(mapSelc == 5)
                {
                    gamestarted = false;
                    map6 = true;
		}
                else if(mapSelc == 6)
                {
                    gamestarted = false;
                    map7 = true;
		}
                else if(mapSelc == 7)
                {
                    gamestarted = false;
                    map8 = true;
		}
                else if(mapSelc == 8)
                {
                    gamestarted = false;
                    map9 = true;
		}
	    }
        }
        else if(map4 == true)
        {
            gameTimer.setDelay(10);    
        }
        else if(menuOpen == true)
        {
            gameTimer.setDelay(20);
            if(keyCode == KeyEvent.VK_DOWN)
            {
		button_pressed();
		if(menuSelc < 7)
                {
                    menuSelc++;
		}
	    }
            else if(keyCode == KeyEvent.VK_UP)
            {
		button_pressed();
		if(menuSelc > 0)
                {
                    menuSelc--;
		}
	    }
            else if(keyCode == KeyEvent.VK_ENTER)
            {
                button_pressed();
                if(menuSelc == 0)
                {          
                    menuOpen = false;
                    charMenu = true;
                }
            }
            
            else if(keyCode == KeyEvent.VK_SPACE)
            {
		menu_opened();
		menuOpen = false;
                gamestarted= true;
                currentBGM.stop();
                currentBGM = openingBGM;
                currentBGM.start();
	    }
            
        }
        else if(charMenu == true)
        {
            gameTimer.setDelay(20);
            if(keyCode == KeyEvent.VK_RIGHT)
            {
		button_pressed();
		if(charMenuSelc < 2)
                {
                    charMenuSelc++;
		}
	    }
            if(keyCode == KeyEvent.VK_LEFT)
            {
		button_pressed();
		if(charMenuSelc > 0)
                {
                    charMenuSelc--;
		}
	    }
            if(keyCode == KeyEvent.VK_ENTER)
            {
		button_pressed();
		charMenu = false;
                menuOpen = true;
	    }
        }
    }

    public void button_pressed() {
		 col.playClip("Select");
    }
    public void menu_opened() {
		 col.playClip("Menu");
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        
        int keyCode = e.getKeyCode();
        
    }

    public void paintComponent(Graphics g)
    {
    	super.paintComponent(g);
    	Graphics2D g2 = (Graphics2D)g;
		AffineTransform at = new AffineTransform();
		g2.setTransform(at);
    	if(atTitle == true)
        {
    		g.drawImage(titlescreen,0,0,null);
                g.drawImage(title2,0,111,null);
                
    		if(start_visible == true)
                {
                    gameTimer.setDelay(350);
                    g.drawImage(start_symbol,0,260,null);
    		}
    	}
        else if(atFirstScene == true)
        {
            
            g.drawImage(firstScene,0,0,null);
            
            
        }
    	else if(atChooseSide == true)
        {
            g.drawImage(chooseSide,0,0,null);
            if(concurrentMenuItem == 0)
            {
                g.drawImage(arrow, 328, 45, null);
            }
	    else if (concurrentMenuItem == 1)
            {
                g.drawImage(arrow2, 50, 275, null);
	    }
	    
    	}
        else if(atChooseCharSith == true)
        {
            g.drawImage(charSelectionSith,0,0,null);
            g.drawImage(DVGif,235,200,null);
            g.drawImage(DMGif,125,200,null);
            g.drawImage(DSGif,345,200,null);
            if(charSelc == 0)
            {
                g.drawImage(arrowUpSith, 135, 275, null);
            }
	    else if (charSelc == 1)
            {
                g.drawImage(arrowUpSith, 245, 275, null);
	    }
            else if (charSelc == 2)
            {
                g.drawImage(arrowUpSith, 350, 275, null);
	    }
        }
        
        else if(atChooseCharJedi == true)
        {
            g.drawImage(charSelectionJedi,0,0,null);
            if(charSelc == 0)
            {
                g.drawImage(arrowUpJedi, 135, 275, null);
            }
	    else if (charSelc == 1)
            {
                g.drawImage(arrowUpJedi, 245, 275, null);
	    }
            else if (charSelc == 2)
            {
                g.drawImage(arrowUpJedi, 350, 275, null);
	    }
        }
        else if(atChooseSaberSith == true)
        {
            g.drawImage(chooseSaberSith,0,0,null);
            if(saberSelc == 0)
            {
                g.drawImage(arrowUpSith, 215, 325, null);
            }
	    else if (saberSelc == 1)
            {
                g.drawImage(arrowUpSith, 320, 325, null);
	    }
            else if (saberSelc == 2)
            {
                g.drawImage(arrowUpSith, 435, 325, null);
	    }
        }
        else if(gamestarted == true)
        {
            g.drawImage(gameMap, 0, 0, null);
            if(mapSelc == 0)
            {
                g.drawImage(arrowUpJedi, 0, 0, null);
            }
	    else if (mapSelc == 1)
            {
                g.drawImage(arrowUpJedi, 150, 25, null);
	    }
            else if (mapSelc == 2)
            {
                g.drawImage(arrowUpJedi, 350, 25, null);
	    }
            else if (mapSelc == 3)
            {
                g.drawImage(arrowUpJedi, 0, 125, null);
	    }
            else if (mapSelc == 4)
            {
                g.drawImage(arrowUpJedi, 150, 150, null);
	    }
            else if (mapSelc == 5)
            {
                g.drawImage(arrowUpJedi, 350, 155, null);
	    }
            else if (mapSelc == 6)
            {
                g.drawImage(arrowUpJedi, 30, 270, null);
	    }
            else if (mapSelc == 7)
            {
                g.drawImage(arrowUpJedi, 160, 300, null);
	    }
            else if (mapSelc == 8)
            {
                g.drawImage(arrowUpJedi, 350, 325, null);
	    }              
        }
        else if(map4 == true)
        {
            g.drawImage(duel, 0, 0, null);
            
        }
        else if(menuOpen == true)
        {
            JTextField lv = new JTextField("hi");
            jf.add(lv);
            g.drawImage(sithMenu, 0, 0, null);
            if(charDM == true)
            {
                g.drawImage(DMMenuGif, 126, 0, null);
            }
            else if(charDV == true)
            {
                g.drawImage(DVMenu, 88, 0, null);
            }
            else if(charDS == true)
            {
                g.drawImage(DSMenu, 88, 0, null);
            }
            if(menuSelc == 0)
            {
                g.drawImage(arrowUp, 0, 62, null);
            }
	    else if (menuSelc == 1)
            {
                g.drawImage(arrowUp, 0, 86, null);
	    }
            else if (menuSelc == 2)
            {
                g.drawImage(arrowUp, 0, 110, null);
	    }
            else if (menuSelc == 3)
            {
                g.drawImage(arrowUp, 0, 134, null);
	    }
            else if (menuSelc == 4)
            {
                g.drawImage(arrowUp, 0, 158, null);
	    }
            else if (menuSelc == 5)
            {
                g.drawImage(arrowUp, 0, 182, null);
	    }
            else if (menuSelc == 6)
            {
                g.drawImage(arrowUp, 0, 204, null);
	    }
            else if (menuSelc == 7)
            {
                g.drawImage(arrowUp, 0, 228, null);
	    }
        }
        else if(charMenu == true)
        {
            
            g.drawImage(sithMenu, 0, 0, null);
            g.drawImage(DMMenuChar, 90, 0, null);
           
        }
    	
	              
	              //Reset to 0,0
	              g2.translate(-offsetX, -offsetY);
	              //Player Sprites
	              g2.setTransform(at);	    	 
	    	
     	 }

    @Override
    public void keyTyped(KeyEvent e) {
        if(atChooseCharSith == true)
        {
            //do nothing
        }
    }
     
    
    
    private static class event implements ActionListener
    {
        public void actionPerformed(ActionEvent e){
            System.exit(0);
        }
    }
    
    public void actionPerformed(ActionEvent e)
    {
            currentTime = java.lang.System.currentTimeMillis();
            if(gamestarted == true)
            {
                  //needs code  
            }
            else
            {
		//Title Screen
		start_visible = !start_visible;
            }
		repaint();
    }
    //Frame Menu Action Listeners
    private static class about implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            String status = e.getActionCommand();
            if(status.equals("About"))
            {
                JOptionPane.showMessageDialog(null, "Created by: Andrew St Germain");
            }
        }
    }
    private static class exit implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            String status = e.getActionCommand();
            if(status.equals("Exit"))
            {
                System.exit(0);
            }
        }
    }
    //figure out how to save and load
    
    /*
    private static class save implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            String status = e.getActionCommand();
            if(status.equals("Save Game"))
            {
                
            }
        }
    }
    private static class load implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            String status = e.getActionCommand();
            if(status.equals("Load Game"))
            {
                
            }
        }
    }
    
    */
    private static class newGame implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            String status = e.getActionCommand();
            if(status.equals("New Game"))
            {
                try 
                {
                    Runtime.getRuntime().exec("java -jar StarWars.jar");                    
                } 
                catch (IOException ex)
                {
                    Logger.getLogger(StarWars.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.exit(0);
            }
        }
    }                 
 
    public static void main(String args[])
    {       
        jf = new JFrame("Star Wars The Java Game");
        StarWars gui = new StarWars();       
        jf.add(gui);           
	jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setPreferredSize(new Dimension(505, 400));
	jf.setResizable(false);        
	jf.pack();	  	 
	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	int w = jf.getSize().width;
   	int h = jf.getSize().height;
   	int x = (dim.width-w)/2;
    	int y = (dim.height-h)/2;
	jf.setLocation(x,y); 	
	jf.setVisible(true);
	gui.requestFocus(true);
    }
}

