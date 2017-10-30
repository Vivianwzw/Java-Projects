// Ziwei Wang
// vivianwang
// Zhuo Chen
// aslantachen


import java.awt.Color;

import javalib.funworld.WorldScene;
import javalib.worldimages.CircleImage;
import javalib.worldimages.Posn;
import javalib.worldimages.WorldImage;

// To represent a player class
class Player {

  Posn center; // represents the x and y coordinate of the player
  int radius; // represents the radius of the player
  Color col;  
  int speed;
  boolean alive;

  /** The constructor */
  Player(Posn center, int radius, Color col, int speed,boolean alive) {
      this.center = center;
      this.radius = radius;
      this.col = col;
      this.speed = speed;
      this.alive = alive;
  }
  
  
  /** produce the image of this player */
  WorldImage playerImage() {
      return new CircleImage(this.radius, "solid", this.col);
  }
  
  //move this player 5 pixels in the direction given by the ke
  public Player movePlayer(String ke) {
    if (ke.equals("right")) {
        return new Player(new Posn(this.center.x + this.speed, this.center.y),
                this.radius, this.col, this.speed, this.alive);
    } else if (ke.equals("left")) {
        return new Player(new Posn(this.center.x - this.speed, this.center.y),
                this.radius, this.col, this.speed, this.alive);
    } else if (ke.equals("up")) {
        return new Player(new Posn(this.center.x, this.center.y - this.speed),
                this.radius, this.col, this.speed, this.alive);
    } else if (ke.equals("down")) {
        return new Player(new Posn(this.center.x, this.center.y + this.speed),
                this.radius, this.col, this.speed, this.alive);
    }
    else return this;
  }
  
  Player playerUpdate(ILoFish that){
    //if (that.eatenOrNot(this))
    {return new Player(this.center,this.radius + 10 ,this.col, this.speed, this.alive);}
    //else{return this;}
  }
  
  Player loopOver(){
    if (this.center.x > 1600 + this.radius)
    {return new Player(new Posn(0 - this.radius, this.center.y),this.radius ,this.col, this.speed, this.alive);}
    else if (this.center.x < 0 - this.radius)
    {return new Player(new Posn(1600 + this.radius, this.center.y),this.radius ,this.col, this.speed, this.alive);}
    else if (this.center.y > 1600 + this.radius)
    {return new Player(new Posn(this.center.x, 0 - this.radius),this.radius ,this.col, this.speed, this.alive);}
    else if (this.center.y < 0 - this.radius)
    {return new Player(new Posn(this.center.x, 1600 + this.radius),this.radius ,this.col, this.speed, this.alive);}
    else{
      return this;
    }
  }
}