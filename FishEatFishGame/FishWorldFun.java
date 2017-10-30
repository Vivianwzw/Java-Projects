// Ziwei Wang
// vivianwang
// Zhuo Chen
// aslantachen


import java.awt.Color;
import javalib.funworld.World;
import javalib.funworld.WorldScene;
import javalib.worldimages.FontStyle;
import javalib.worldimages.TextImage;
import javalib.worldimages.WorldEnd;


/** Represent the world of a Blob */
class FishWorldFun extends World {
  int width = 1600;
  int height = 1600;
  Player player;
  ILoFish fish;
  

  /** The constructor */
  public FishWorldFun(Player player, ILoFish fish) {
      super();
      this.player = player;
      this.fish = fish;
  }

  /**
   * produce the image of this world by adding the moving 
   * player and fish to the background image
   */
  public WorldScene makeScene() {
      return fish.placeFishImages(this
              .getEmptyScene())
              .placeImageXY(this.player.playerImage(), this.player.center.x, this.player.center.y)
              .placeImageXY(new TextImage("", Color.green), 800, 1400);
  }
  
  
  
  /** Move the Player when the player presses a key */
  public World onKeyEvent(String ke) {
          return new FishWorldFun(this.player.movePlayer(ke),this.fish);
  }
  
  /**
   * On tick move the Player in a right direction.
   */
  public World onTick() {
    if (fish.eatenOrNot(this.player))
      {return new FishWorldFun(this.player.loopOver().playerUpdate(this.fish)
                               ,this.fish.generateFish().moveFish().fishUpdate(this.player));}
    else {return new FishWorldFun(this.player.loopOver(),this.fish.generateFish().moveFish());}
  }
  
  /** produce the last image of this world by adding text to the image */
  public WorldScene lastScene(String s) {
      return this.makeScene().placeImageXY(new TextImage(s, Color.red), 80,
              50);
  }

  /**
   * Check whether the player hit the larger fish
   */
  public WorldEnd worldEnds() {
      // time ends is the blob falls into the black hole in the middle
      if (this.fish.eatThePlayer(this.player.center, this.player.radius)){
          return new WorldEnd(true, this.makeScene().placeImageXY(
                  new TextImage("The Player is eaten by larger fish!", 50, FontStyle.BOLD_ITALIC, Color.red),
                  800, 40));
      }
      if (this.player.radius > this.fish.greatestRadius()){
        return new WorldEnd(true, this.makeScene().placeImageXY(
            new TextImage("The Player is the largest fish!", 50, FontStyle.BOLD_ITALIC, Color.red),
            800, 40));
      }
      else {
          return new WorldEnd(false, this.makeScene());
      }
  }  
}