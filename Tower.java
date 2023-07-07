// Yousef Al-Wahami
// Tower.java
// Consists of the Tower class and the behaviour of a tower (shooting, cooldowns, animations)

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.image.*; 
import java.io.*; 
import javax.imageio.*; 

public class Tower {
 private int x, y;
 private int range, cooldown; //radius
 private int type;
 
 //used for animation
 private Image[]pics;
 private int frame;
 
    public Tower(int xx, int yy, int r, int t) {
     x = xx;
     y = yy;
     range = r;
     type = t;
     cooldown=0;
     frame=0;
     
     
     //custom animations for each tower
     pics = new Image[3];  
     for(int i = 0; i<3; i++){
       pics[i] = new ImageIcon("Animations/tower"+t+"_"+i+".png").getImage();
     }
    
     
    }

    //if enemy is inRange for tower, true. Otherwise false
    public boolean inRange(Enemy e){
      Rectangle rec = new Rectangle(x,y,40,40);
      
      //if the position of the enemy intersects with the range radius
      if(Util.circleDist(e.x,e.y, x,y) <= range){
        return true;
      }
      return false;
      
    }
    
    //damages the enemy
    public void shoot(Enemy e){
    	if(type==0){
    		//first tower
    		if(cooldown<=0){
    			cooldown = 50; //cooldown is 1 second
    			e.hp-=2; //damages 2 to the enemy
    			Game.fSound.play();
    		}
    	}
    	else if(type==1){
    		//second tower
    		if(cooldown<=0){
    			cooldown = 17; //cooldown is 0.3 sceoncds
    			e.hp-=1; //damages 1 to the enemy
    			Game.fSound.play();
    		}
    	}
    	else if(type==2){
    		//third tower
    		if(cooldown<=0){
    			cooldown = 150; //cooldown is 3 seconds
    			e.hp-=6; //damages 6 to the enemy
    			Game.fSound.play();
    		}
    	}
    	
    }
    
    public Rectangle getRect(){
     return new Rectangle(x,y,40,40);
    }
    
    public void draw(Graphics g){
      if(cooldown>0){
    	cooldown--;
   	  }
      frame++;
      
     //Reloading cooldown
     g.setColor(Color.BLUE);
     g.fillRect(x,y+40,cooldown/2,10);
     
     g.drawImage(pics[frame / 5 % pics.length], x, y, null);
 
    }    
     public static void main(String[] args) {
  Game frame = new Game();
    } 
    
}