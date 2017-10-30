// Ziwei Wang
// vivianwang
// Zhuo Chen
// aslantachen


import javalib.funworld.WorldScene;
import javalib.worldimages.Posn;


interface ILoFish{
  public WorldScene placeFishImages(WorldScene background);
  public ILoFish moveFish();
  public boolean eatThePlayer(Posn that, int r);
  public ILoFish fishUpdate(Player that);
  public boolean eatenOrNot(Player that);
  public int greatestRadius();
  public ILoFish generateFish();
  public int amount();
}




