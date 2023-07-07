// Yousef Al-Wahami
// Game.java
// This game is a tower defense game, you start with $100 to place towers to fend off enemies, waves get harder as the game goes on.

// Inherited from BaseFrame.java to look nice

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import java.awt.image.*; 
import java.io.*; 
import javax.imageio.*; 
import java.applet.*;

class Game extends BaseFrame{
 public static final int INTRO=0, GAME=1, WIN=2, LOSS=3;
 public static int FRAME;
 private int screen = INTRO;
 private Font fnt;
 private Font fnt2;
 private ArrayList<Wave> waves;
 private ArrayList<Enemy> enemies;
 private ArrayList<Tower> towers;
 
 private boolean building=false; //if building mode
 private int tType; //tower type
 private int money;
 private int cost;
 private int curWave;
 private int mapNum;
 public static int healthPoints;

//Images
 private Image map;
 private Image bg, menu, win, loss;
 private Image coins;
 private Image hearts;

//custom font
Font comic;

File buildSfx = new File("Sfx/build.wav");
AudioClip bSound;
File errorSfx = new File("Sfx/error.wav");
AudioClip eSound;
File shootSfx = new File("Sfx/fire.wav");
public static AudioClip fSound;




 public Game(){
  super("Inavde Tower Defense", 1000,800); 
  enemies = new ArrayList<Enemy>();
  towers = new ArrayList<Tower>(); //ArrayList of current towers in scene
  fnt = new Font("Arial", Font.PLAIN, 22);
  fnt2 = new Font("Sfx/KOMIKAX.ttf", Font.PLAIN, 22);
  //fnt2 = new Font(
  int waves = 1;
  cost=0;
  healthPoints = 150; //total startig health
  money = 100; //100 dolla, starting money
  curWave = -1; //starting at -1 so it starts waves at [0]

  
  //loading static images, maps will be determined in menu
  bg = new ImageIcon("Graphics/background.png").getImage();
  win = new ImageIcon("Graphics/win.png").getImage();
  loss = new ImageIcon("Graphics/loss.png").getImage();
  menu = new ImageIcon("Graphics/menu.png").getImage();
  coins = new ImageIcon("Graphics/coins.png").getImage();
  hearts = new ImageIcon("Graphics/hearts.png").getImage();
  
  
  
  try{
   bSound = Applet.newAudioClip(buildSfx.toURL());
   eSound = Applet.newAudioClip(errorSfx.toURL());
   fSound = Applet.newAudioClip(shootSfx.toURL());
   
   //custom font named "comic", used for money/health
   //code from internet
   comic = Font.createFont(Font.TRUETYPE_FONT, new File("Graphics/KOMIKAX.ttf")).deriveFont(20f);
   GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
   ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Graphics/KOMIKAX.ttf")));
  }
  catch(Exception e){
   e.printStackTrace();
  }
  
  
 }
 
 public void build(){
   //in order to build, you need money
   if(mb==1 && building && money>=cost){
     Rectangle rec = new Rectangle(mx-20,my-20,40,40);
     //if touch is false, then it is touching an off-bounds area
     boolean touch=false;
     for(Tower t : towers){
       if(rec.intersects(t.getRect())){
         touch=true;
       }
       if(rec.intersects(Util.borderA) || rec.intersects(Util.borderB)){
       	touch=true;
       }
       //if(rec.intersects(){
         //touch=true;
       //} //borders
     }
     
     if(touch==false){
       towers.add(new Tower(mx-20, my-20,150,tType));
       money-=cost;
       bSound.play();
     }
     else{
     	//error sound
      eSound.play();
     }
  }
   
   //if key 1,2,3 = building mode (enables you to place towers)
   if(keys[49]){
    building=true;
    tType=0;
    cost=50;
    //tower 1 costs 50
  }
  else if(keys[50]){
  	building=true;
  	tType=1;
  	cost=75;
  	//tower 2 costs 75
  }
  else if(keys[51]){
  	building=true;
  	tType=2;
  	cost=100;
  	//tower 3 costs 100
  }
  if(keys[52]){
  	//key 4 stops building
  	building=false;
  }
   
 }


 
 public void spawn(int waveNum){
 	//for spawning; we create all enemies at once, and spawn them offscreen 
   for(int i=0; i<Util.waves[waveNum][1]; i++)
   	if(mapNum==0){
       enemies.add(new Enemy(mapNum,10-i*250, 0, Util.waves[waveNum][2]));
   	}
   	else if(mapNum==1){
   		enemies.add(new Enemy(mapNum,10-i*250, 0, Util.waves[waveNum][2]));
   	}
 }

 
 public void shoot(){
 	//if tower in range of enemy
  for(Tower t : towers){
    for(Enemy e: enemies){
      if(t.inRange(e)){
      	//tower do damage
        t.shoot(e);
        break;
      }
    }
  }
 }
 
 public void checkEnemyEnd(){
   ArrayList<Enemy>out = new ArrayList<Enemy>();
   for(Enemy e: enemies){
     //if enemy reaches the end
     if(e.y < 0){
       out.add(e);
       healthPoints-=e.hp;
     }
   }
   enemies.removeAll(out);
 }
 
 public void checkEnemyHealth(){
   ArrayList<Enemy>out = new ArrayList<Enemy>();
   for(Enemy e: enemies){
   	//if enemy reaches 0 hp
     if(e.hp <= 0){
       out.add(e);
       money+=e.payout; //give player specific $
     }
   }
   enemies.removeAll(out);
 }

 
 @Override
 public void move(){
 	if(screen==INTRO){
 		// map will change depending on selection, 1=plains, 2=tundra
 		
 		//Plains map
 		if(mb>0 && mx>86 && mx<415 && my>557 && my<710){
 			mapNum=0;
 			map = new ImageIcon("Maps/map1.png").getImage();
 			screen = GAME;
 		}
 		//Tundra map
 		if(mb>0 && mx>590 && mx<919 && my>557 && my<710){
 			mapNum=1;
 			map = new ImageIcon("Maps/map2.png").getImage();
 			screen = GAME;
 		}
 	}
   
   if(screen==GAME){
   	FRAME++;
   	//if the current wave reaches the frame counter, spawn the enemies and go next wave
   	if(Util.waves[curWave+1][0] == FRAME){
     curWave++;
     spawn(curWave);
   	}
   	//If HP reaches 0, you lose
    if(healthPoints<=0){
   		screen=LOSS;
   	}
   	//FRAME 20400 is when all enemies in the last wave finally cross over the exit.
   	if(FRAME>20400){
   		screen=WIN;
   	}
   	
   	for(Enemy e : enemies){
     e.move();
   	}
   	//constantly checking and doing these actions
   	build();
   	checkEnemyEnd();
   	checkEnemyHealth();
   	shoot();

   	}
 }
 
   @Override
 public void draw(Graphics g){
 	if(screen==INTRO){
 		g.drawImage(menu, 0, 0, null);
 	}
 	if(screen==GAME){
 		g.setFont(fnt);
  		g.setColor(Color.BLACK); 
  		g.drawImage(bg, 0, 0, null); //background planks
  		//border, stylistic 
	  	g.fillRect(0,0, 877, 690);
	  
	  	//the map
	  	g.drawImage(map, 0, 0, null);
	  
	  
	  	g.setFont(comic);
	  	// UserInterface - money and health
	  	g.drawImage(hearts, 755, 702, null);
	  	g.drawString(": "+healthPoints, 800, 725);
	  
	  	g.drawImage(coins, 755, 740, null);
	  	g.drawString(": "+money, 800, 770);
	  
	
	   	for(Enemy e : enemies){
	     	e.draw(g);
	   	}
	   	for(Tower t : towers){
	    	t.draw(g);
	   	}
	   	if(building){
	   		Color buildCol = new Color(0, 0, 255, 127);
	   		Color rangeCol = new Color(255, 255, 0, 127);
	   		

	     	//to see where your tower range will reach
	     	g.setColor(rangeCol);
	     	g.fillOval(mx-140,my-140,280,280);
	     	//to see where your tower will be placed
	     	g.setColor(buildCol);	
	     	g.fillRect(mx-20, my-20, 40 ,40);
	  	}
	  }
	  else if(screen==WIN){
	  	g.drawImage(win, 0, 0, null);
	  }
	  else if(screen==LOSS){
	  	g.drawImage(loss, 0, 0, null);
	  }
  
 }
  
    public static void main(String[] args) {
  Game frame = new Game();
    } 
 
}