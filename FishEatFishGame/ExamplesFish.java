// Ziwei Wang
// vivianwang
// Zhuo Chen
// aslantachen


import java.awt.Color;
import tester.*;
import javalib.funworld.WorldScene;
import javalib.worldimages.CircleImage;
import javalib.worldimages.EmptyImage;
import javalib.worldimages.Posn;
import javalib.worldimages.WorldImage;


class ExamplesFish{
  //Examples for player
  Player p1 = new Player(new Posn(1000, 500), 30, Color.black, 60, true);
  Player p2 = new Player(new Posn(300, 310), 30, Color.black, 30, true);
  Player p3 = new Player(new Posn(300, 310), 50, Color.black, 30, true);
  
  //Examples for fish  
  Fish f1 = new Fish (new Posn(-30, 300), 20, Color.blue, 30, true);
  Fish f2 = new Fish (new Posn(-40, 600), 30, Color.yellow, 40, true);
  Fish f3 = new Fish (new Posn(300, 800), 40, Color.red, 20, true);
  Fish f4 = new Fish (new Posn(300, 1000), 20, Color.green, 35, true);
  Fish f5 = new Fish (new Posn(300, 1200), 60, Color.cyan, 45, true);
  Fish f6 = new Fish (new Posn(300, 1400), 35, Color.pink, 30, true);
  
  //Examples for ListofFish
  MtLoFish mt = new MtLoFish();
  ConsLoFish l1 = new ConsLoFish(f1,new ConsLoFish(f2,this.mt));
  ConsLoFish l2 = new ConsLoFish(f4,new ConsLoFish(f3,l1));
  ConsLoFish l3 = new ConsLoFish(f6,new ConsLoFish(f5,l2));
  
  FishWorldFun w2 = new FishWorldFun(p2, l3);
  FishWorldFun w3 = new FishWorldFun(p3, l3);
  
  
  boolean testplayerUpdate(Tester t){
    return t.checkExpect(p2.playerUpdate(l3), p3)
        && t.checkExpect(p1.playerUpdate(l3), p1);
  } 

  
  boolean testontick(Tester t){
    return t.checkExpect(w2.onTick(), w3);
  }
  
  
  //Examples for FishWorldFun
  FishWorldFun w1 = new FishWorldFun(p1,this.l1);
  boolean runAnimation = this.w1.bigBang(1600, 1600, 0.3);



}