// Ziwei Wang
// vivianwang
// Zhuo Chen
// aslantachen


import java.awt.Color;

import javalib.funworld.WorldScene;
import javalib.worldimages.CircleImage;
import javalib.worldimages.Posn;
import javalib.worldimages.WorldImage;

// To represent a background fish
class Fish{
  Posn center;
  int radius;
  Color col;
  int speed;
  boolean alive;
  
  //constructor
  Fish(Posn center, int radius, Color col, int speed, boolean alive){
    this.center = center;
    this.radius = radius;
    this.col = col;
    this.speed = speed;
    this.alive = alive;
  }
  
  
  /** produce the image of this fish */
  WorldScene fishImage(WorldScene background) {
      return background.placeImageXY(new CircleImage(this.radius, "solid", this.col), this.center.x, this.center.y);
  }
  
  // to make a fish move
  Fish moveAFish(){
    return new Fish(new Posn(this.center.x + speed, this.center.y),this.radius, this.col, this.speed, this.alive);
  }
  
  // is the fish able to eat the player
  public boolean eatThePlayerH(Posn that, int r){
    if (this.radius <= r)
    {return false;}
    else
    {return Math.sqrt((Math.pow(this.center.x - that.x , 2) +  Math.pow(this.center.y - that.y , 2)))<= this.radius +r;}
  }
  
  // Is this fish going to be eaten by player
  public boolean eatenByPlayer(Player that){
    if (this.radius > that.radius)
    {return false;}
    else
    {return Math.sqrt((Math.pow(that.center.x - this.center.x , 2) +  Math.pow(that.center.y - this.center.y , 2))) <= this.radius + that.radius;}
  }
  

}