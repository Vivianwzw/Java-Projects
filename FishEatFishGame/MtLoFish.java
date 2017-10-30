// Ziwei Wang
// vivianwang
// Zhuo Chen
// aslantachen


import java.awt.Color;
import java.util.Random;

import javalib.funworld.WorldScene;
import javalib.worldimages.Posn;
import javalib.worldimages.WorldImage;

class MtLoFish implements ILoFish{
  
  public WorldScene placeFishImages(WorldScene background){
    return background;
  }
  
  public ILoFish moveFish(){
    return this;
  }
  
  public boolean eatThePlayer(Posn that, int r){
    return false;
  }
  
  public ILoFish fishUpdate(Player that){
    return this;
  }
  
  public boolean eatenOrNot(Player that){
    return false;
  }
  
  public int greatestRadius(){
    return 0;
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
  
  // count the amount of fish in an empty list
  public int amount() {
    return 0;
  }
}
  