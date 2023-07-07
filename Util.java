// Yousef Al-Wahami
// Util.java
// Used for refering to when needed, less headache in actual code.

import java.awt.*;
import java.awt.geom.*;

public class Util {


   public static int randint(int a, int b){
     return (int)(Math.random()*(b-a+1)+a);
   }
    
   public static double dist(int[]a, int[]b){
     return Math.sqrt(Math.pow(b[0]-a[0],2) + Math.pow(b[1]-a[1],2));
   }
   
   //Circle distance formula
   public static double circleDist(int x1, int y1, int x2, int y2){
     return Math.sqrt(Math.pow(x1-x2,2) + Math.pow(y1-y2,2));
   }
   
   //Path for both maps, used for enemy guiding
   public static int [][][]fullPath = {{{-10,325},{0,325},{700,325},{700,635},{510,635},{510,13},{323,13},{323,637},{136, 637},{136,-800}},{{-10,135},{0,135},{135,135},{135,261},{260,261},{260,385},{135,385},{135,575},{698,575},{698,385},{572,385},{572,261},{698,261},{698,135},{574,135},{574,-800}}};
   
   
   public static int [][]waves = 
   //0 = basic
   //1 = fast
   //2 = slow
   //3 = tank
   {{250,3,0},{1000,3,1},{1750,3,2},{2500,4,3},{2550,3,1},{3300,2,2},{3350,1,2},{4050,2,3},{4800,5,0},{4850,2,2},{4900,4,1},{5650,3,2},{5700,3,0},{5750,2,1},{6500,5,0},{6550,3,2},{7300,4,3},{7350,4,2},{7400,2,0},{8150,8,0},{8200,5,1},{8250,3,2},{9000,2,3},{9050,4,1},{9750,9,2},{9800,3,0},{10550,7,0},{10600,4,1},{10650,1,3},{11550,5,3},{11600,5,2},{11650,5,1},{11700,5,0},{12450,2,3},{12500,4,0},{12550,3,1},{12800,5,2},{12850,5,0},{13600,8,2},{13650,8,3},{13700,8,1},{13750,8,0},{14750,10,1},{14800,7,2},{15900,15,2},{15950,4,3},{17000,12,0},{17050,9,1},{17100,7,2},{18000,10,1},{18050,7,3},{19050,20,3},{19100,20,2},{19150,20,1},{19200,20,0},{155550,0,1}};
   
   
   //so you cant build over UI
   public static Rectangle borderA = new Rectangle(855,0,125,800);
   public static Rectangle borderB = new Rectangle(0,667,1000,133);


}