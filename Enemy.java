// Yousef Al-Wahami
// Enemy.java
// Consists of the Enemy class and the behaviour of an Enemy (speed, hp, type, pathways + destinations, animations)

import java.io.File;
import java.io.IOException;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.image.*; 
import java.io.*; 
import javax.imageio.*; 


public class Enemy {
 private int vx,vy; //x and y of rect, velocity of x and y
 public int x,y, hp, payout; //payout is money given for each kill
 private int [][]path;
 

 private int leg;
 private int speed;
 private int type;
 private double ang=-90;
 private int dx, dy; //direction vectors
 
  //used for animation
 private BufferedImage[]pics;
 private int frame;
 
 
 /*
  *idea is
  *have map[[pos1,pos2][pos1,pos2]]
  *map[i][j]
  *i = map 
  *j = position
  *
  *every map is different and therefore has different movement positions.
  */

   // dx,dy - used to let the enemy start before the starting spot
    public Enemy(int pnum, int dx, int dy, int t) {
     path = Util.fullPath[pnum];
     x = path[1][0] + dx;
     y = path[1][1] + dy;
     leg = 1;
     type = t;
     
     //payout, speed, and hp is determined by specific towers
     if(type==0){
       speed=4;
       hp=8;
       payout=5;
     }
     else if(type==1){
       speed=8;
       hp=6;
       payout=10;
     }
     else if(type==2){
       speed=2;
       hp=14;
       payout=15;
     }
     else if(type==3){
       speed=6;
       hp=25;
       payout=25;
     }
     
     
     try {
       //in order to rotate images, we must use BufferedImage
       pics = new BufferedImage[2];  
       for(int i = 0; i<2; i++){
       	//t is type, i is frame number
         pics[i] = ImageIO.read(new File("Animations/enemy"+t+"_"+i+".png"));
       }
     } 
     catch (IOException e) {
       System.out.println(e);
     }
  
    }
    
 
 public void move(){
   
   int [] from = path[leg]; //from destination
   int [] to = path[leg+1]; //to destination
   
   // dx, unique to speed
   if(to[0] > from[0]){
     dx = speed;
   }
   else if(to[0] < from[0]){
     dx = -speed;
   }
   else{
     dx = 0;
   }
   
   // dy, unique to speed
   if(to[1] > from[1]){
     dy = speed;
   }
   else if(to[1] < from[1]){
     dy = -speed;
   }
   else{
     dy = 0;
   }
   
   x+=dx;
   y+=dy;
   if(Util.dist(new int[]{x,y}, to) < speed){
     leg+=1;
   }
 
 }

    public Rectangle getRect(){
     return new Rectangle(x,y,40,40);
    }
     
    public void draw(Graphics g){
      frame++;
      
      //handles which direction the animations face/turn
      if(dx>0 && dy==0){
        ang=90;
      }
      else if(dx<0 && dy==0){
        ang=-90;
      }
      else if(dx==0 && dy>0){
        ang=180;
      }
      else if(dx==0 && dy<0){
        ang=0;
      }
      
      //handles the image rotation
      AffineTransform rot = new AffineTransform();
      rot.rotate((Math.toRadians(ang)),20,20);   // 75,84 is the center of my Image, this is the point of rotation.
      
      AffineTransformOp rotOp = new AffineTransformOp(rot, AffineTransformOp.TYPE_BILINEAR);  // The options are: TYPE_BICUBIC, TYPE_BILINEAR, TYPE_NEAREST_NEIGHBOR
      Graphics2D g2D = (Graphics2D)g;               // NEAREST_NEIGHBOR is fastest but lowest quality
      
      
      g2D.drawImage(pics[frame / 5 % pics.length],rotOp, x , y);
      
    //health bar follows enemy around
    g.setColor(Color.GREEN);
    g.fillRect(x,y+40,hp*3,10);
    }    
        public static void main(String[] args) {
  Game frame = new Game();
    } 
 
}