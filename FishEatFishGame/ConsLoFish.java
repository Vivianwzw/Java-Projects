// Ziwei Wang
// vivianwang
// Zhuo Chen
// aslantachen


import java.awt.Color;
import java.util.Random;

import javalib.funworld.WorldScene;
import javalib.worldimages.Posn;
import javalib.worldimages.WorldImage;

class ConsLoFish implements ILoFish{
  Fish first;
  ILoFish rest;
  
  ConsLoFish(Fish first, ILoFish rest){
    this.first = first;
    this.rest = rest;
  }
  
  public WorldScene placeFishImages(WorldScene background){
    return this.rest.placeFishImages(this.first.fishImage(background));
  }
  
  
  public ILoFish moveFish(){
    return new ConsLoFish(this.first.moveAFish(), this.rest.moveFish());
  }
  
  public boolean eatThePlayer(Posn that, int r){
    return this.first.eatThePlayerH(that, r) ||this.rest.eatThePlayer(that, r);
  }
  
  // Update ILoFish
  public ILoFish fishUpdate(Player that){
    if (this.first.eatenByPlayer(that))
    {return this.rest.fishUpdate(that);}
    else {return new ConsLoFish(this.first, this.rest.fishUpdate(that));}     
  }
  
  // to see if this player eat the fish
  public boolean eatenOrNot(Player that){
    return this.first.eatenByPlayer(that)||this.rest.eatenOrNot(that);
  }
  
  
  public int greatestRadius(){
    return Math.max(this.first.radius, this.rest.greatestRadius());
  }
  
  // generate more fish in a non-empty list
  public ILoFish generateFish() {
    Random r = new Random();
    Fish left = new Fish(new Posn(-r.nextInt(100), r.nextInt(1500)), 30 + r.nextInt(100),
        new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)), 10 + r.nextInt(10),true);
    Fish right = new Fish(new Posn(1600 + r.nextInt(1500), r.nextInt(1500)), 30 + r.nextInt(100),
        new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)), -10 - r.nextInt(20),true);
    if (this.amount() >= 20) {
      return this;
    } else {
      return new ConsLoFish(left, new ConsLoFish(right, this));
    }
  }
  
  // count the amount of fish in a non-empty list
  public int amount() {
    return 1 + this.rest.amount();
  }


  
}