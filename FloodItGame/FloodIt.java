import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import javalib.impworld.World;
import javalib.impworld.WorldScene;
import javalib.worldimages.OutlineMode;
import javalib.worldimages.Posn;
import javalib.worldimages.RectangleImage;
import javalib.worldimages.TextImage;
import javalib.worldimages.WorldImage;
import java.awt.Color;

// Ziwei Wang
// vivianwang
// Zhuo Chen
// aslantachen

// Represents a single square of the game area
class Cell {
  // In logical coordinates, with the origin at the top-left corner of the
  // screen
  int x;
  int y;
  Color color;
  boolean flooded;
  // the four adjacent cells to this one
  Cell left;
  Cell top;
  Cell right;
  Cell bottom;

  Cell(int x, int y, Color color, boolean flooded) {
    this.x = x;
    this.y = y;
    this.color = color;
    this.flooded = flooded;
    this.left = null;
    this.top = null;
    this.right = null;
    this.bottom = null;
  }

  // to draw out a single cellImage
  WorldImage cellImage() {
    return new RectangleImage(FloodItWorld.CELL_SIZE, FloodItWorld.CELL_SIZE, "solid", this.color);
  }
}

// to represent a FloodItWorld
class FloodItWorld extends World {
  // All the cells of the game
  ArrayList<ArrayList<Cell>> board = new ArrayList<ArrayList<Cell>>();
  // represents the number of color of the game
  int colorNum;
  // represents the number of column and line of the game
  int gridSize;

  // represents a original color list
  // if required color num is greater than
  // the num of color in
  ArrayList<Color> colorListOriginal = new ArrayList<Color>(Arrays.asList(Color.YELLOW, Color.RED,
      Color.GREEN, Color.ORANGE, Color.BLUE, Color.PINK, Color.GRAY));

  // represents a random color list
  ArrayList<Color> colorList;

  // the next color that flooded cells are going to change to
  Color nextColor;

  // check if the current world is still or not
  boolean stillornot = true;

  // calculate the times that user has clicked
  int click = 0;

  // represent the maximun step that user could click to win the game
  int maxStep;

  // check if the user wins the game
  // if win: winOrNot = 1
  // if lose: winOrNot = -1
  // if the game is ongoing : winOrNot = 0
  int winOrNot;

  // to record the start time of the game
  long startTime;

  // to represent a timer of the game
  long timer;

  // a variable used in onTick()
  // to let world flood like a wave
  int indice;

  // keep the max (x + y) of flooded cell
  int maxCordinate;

  // **************constructor******************
  // automatically generate an ArrayList<Cell>
  FloodItWorld(int colornum, int gridsize) {
    super();
    this.colorNum = colornum;
    this.gridSize = gridsize;
    this.colorList = this.generateColorList(colornum);
    this.board = this.generateBoard();
    this.nextColor = this.board.get(0).get(0).color;
    this.maxStep = this.colorNum + this.gridSize;
    this.startTime = System.currentTimeMillis();
    this.winOrNot = 0;
    this.indice = 0;
    this.maxCordinate = 0;
    this.onTickInit();
  }

  // constructor for tests
  FloodItWorld(ArrayList<ArrayList<Cell>> board, int colornum, int gridsize) {
    super();
    this.colorNum = colornum;
    this.gridSize = gridsize;
    this.board = board;
  }

  // ****************Constants***************
  // You can then refer to your constant as FloodItWorld.BOARD_SIZE
  // from anywhere in your program.
  // to represents the height and width of the playground
  static final int BOARD_SIZE = 800;
  static final int CELL_SIZE = 30;

  // ****************Methods***********************

  // ****************UpdateCell()*******************
  // Effect: to updates the relationship between cells
  public void updateCell(ArrayList<ArrayList<Cell>> board) {
    for (int i = 0; i < this.gridSize; i++) {
      for (int j = 0; j < this.gridSize; j++) {
        // updates left
        if (i == 0) {
          board.get(i).get(j).left = board.get(i).get(j);
        }
        else {
          board.get(i).get(j).left = board.get(i - 1).get(j);
        }
        // updates right
        if (i == this.gridSize - 1) {
          board.get(i).get(j).right = board.get(i).get(j);
        }
        else {
          board.get(i).get(j).right = board.get(i + 1).get(j);
        }
        // updates top
        if (j == 0) {
          board.get(i).get(j).top = board.get(i).get(j);
        }
        else {
          board.get(i).get(j).top = board.get(i).get(j - 1);
        }

        // updates bottom
        if (j == this.gridSize - 1) {
          board.get(i).get(j).bottom = board.get(i).get(j);
        }
        else {
          board.get(i).get(j).bottom = board.get(i).get(j + 1);
        }
      }
    }
  }

  // ********************GenerateBoard()*********************************
  // to generate a board
  public ArrayList<ArrayList<Cell>> generateBoard() {
    // this.board
    ArrayList<ArrayList<Cell>> tempBoard = new ArrayList<ArrayList<Cell>>();
    // i represents x while j represents y
    for (int i = 0; i < this.gridSize; i++) {
      ArrayList<Cell> column = new ArrayList<Cell>();
      for (int j = 0; j < this.gridSize; j++) {
        column.add(new Cell(i, j, this.generateColor(colorNum), false));
      }
      tempBoard.add(column);
    }

    // change the boolean of the top left cell to true
    tempBoard.get(0).get(0).flooded = true;
    this.updateCell(tempBoard);
    return tempBoard;
  }

  // if required number is greater than the size of original color list
  // randomly add color to the color list
  // Effect: generate a colorList with a given number
  public ArrayList<Color> generateColorList(int n) {
    this.colorList = new ArrayList<Color>();
    Random r = new Random();
    if (n <= this.colorListOriginal.size()) {
      for (int i = 0; i < n; i++) {
        this.colorList.add(this.colorListOriginal.get(i));
      }
    }
    else {
      int tempNum = n - this.colorListOriginal.size();
      // add the original color list to the color list
      for (int i = 0; i < this.colorListOriginal.size(); i++) {
        this.colorList.add(this.colorListOriginal.get(i));
      }
      // and randomly add some new colors to the color list
      for (int i = 0; i < tempNum; i++) {
        this.colorList.add(new Color(i * (255 / tempNum) + r.nextInt(255 / tempNum),
            i * (255 / tempNum) + r.nextInt(255 / tempNum),
            i * (255 / tempNum) + r.nextInt(255 / tempNum)));
      }
    }
    return this.colorList;
  }

  // int -> color
  // to generate a color
  public Color generateColor(int n) {
    Random r = new Random();
    return this.colorList.get(r.nextInt(n));
  }

  // *******************GameStatus()*********************
  // to check the winning status of the game
  public void gameStatus() {
    // user wins if he clicks less than the maxStep
    // and all the cells are flooded
    boolean temp = true;
    if (this.click <= this.maxStep) {
      for (ArrayList<Cell> arr : this.board) {
        for (Cell c : arr) {
          temp = temp && (c.color == this.nextColor);
        }
      }
      if (temp) {
        this.winOrNot = 1;
        this.stillornot = true;
      }
    }

    // user loses if he clicks more times than the maxStep
    if (this.click >= this.maxStep) {
      this.winOrNot = -1;
    }
  }

  // ************makeScene()*****************
  // produce the image of this world
  public WorldScene makeScene() {
    return this.addScore(this.addCell(this.addBG()));
  }

  // to draw out the background
  public WorldScene addBG() {
    WorldScene backGround;
    int frameSize = (this.gridSize + 1) * FloodItWorld.CELL_SIZE;
    WorldImage frame = new RectangleImage(frameSize, frameSize, OutlineMode.SOLID, Color.BLACK);
    backGround = this.getEmptyScene();
    backGround.placeImageXY(frame, frameSize / 2, frameSize / 2);
    return backGround;
  }

  // to draw out the cells
  public WorldScene addCell(WorldScene scene) {
    for (ArrayList<Cell> arr : this.board) {
      for (Cell c : arr) {
        scene.placeImageXY(c.cellImage(), (1 + c.x) * FloodItWorld.CELL_SIZE,
            (1 + c.y) * FloodItWorld.CELL_SIZE);
      }
    }
    return scene;
  }

  // to draw out the score and timer
  public WorldScene addScore(WorldScene scene) {
    WorldImage maxStepImage = new TextImage(
        "Steps: " + Integer.toString(this.click) + " / " + Integer.toString(this.maxStep), 60,
        Color.BLACK);
    WorldImage timeImage = new TextImage("Timer: " + Long.toString(this.timer), 60, Color.BLACK);
    WorldImage winImage = new TextImage("YOU WIN!", 60, Color.BLACK);
    WorldImage loseImage = new TextImage("YOU LOSE!", 60, Color.BLACK);
    scene.placeImageXY(maxStepImage, 365, 675);
    scene.placeImageXY(timeImage, 365, 750);
    if (this.winOrNot > 0) {
      scene.placeImageXY(winImage, 365, 825);
    }
    if (this.winOrNot < 0) {
      scene.placeImageXY(loseImage, 365, 825);
    }
    return scene;
  }

  // to react by the press of key
  public void onKeyEvent(String ke) {
    if (ke.equals("r")) {
      this.board = this.generateBoard();
      this.click = 0;
      this.nextColor = this.board.get(0).get(0).color;
      this.maxStep = this.colorNum + this.gridSize;
      this.startTime = System.currentTimeMillis();
      this.winOrNot = 0;
      this.indice = 0;
      this.maxCordinate = 0;
      this.onTickInit();
    }
  }

  // to react by the click of mouse
  public void onMouseClicked(Posn loc) {
    // change the still world state to not still
    // so that OnTick could update the world
    this.stillornot = false;
    // take the color that user click on
    for (ArrayList<Cell> arr : this.board) {
      for (Cell c : arr) {
        if (loc.x >= (c.x + 0.5) * FloodItWorld.CELL_SIZE
            && loc.x < (c.x + 1.5) * FloodItWorld.CELL_SIZE
            && loc.y >= (c.y + 0.5) * FloodItWorld.CELL_SIZE
            && loc.y < (c.y + 1.5) * FloodItWorld.CELL_SIZE) {
          this.nextColor = c.color;
        }
      }
    }
    // to change the times that player clicked on the board
    if (loc.x > 0 && loc.x < this.gridSize * FloodItWorld.CELL_SIZE && loc.y > 0
        && loc.y < this.gridSize * FloodItWorld.CELL_SIZE) {
      for (ArrayList<Cell> arr : this.board) {
        for (Cell c : arr) {
          if (loc.x >= (c.x + 0.5) * FloodItWorld.CELL_SIZE
              && loc.x < (c.x + 1.5) * FloodItWorld.CELL_SIZE
              && loc.y >= (c.y + 0.5) * FloodItWorld.CELL_SIZE
              && loc.y < (c.y + 1.5) * FloodItWorld.CELL_SIZE) {
            if (!c.flooded && c.color != this.board.get(0).get(0).color) {
              this.click++;
            }
          }
        }
      }
    }
  }

  // change the color of flooded cells
  // and
  // change the flooded status of neighbors of flooded cells
  // if they are the same color as nextcolor
  // change the flooded to true
  public void onTick() {
    // the timer keeps the time if the game is ongoing
    if (this.winOrNot == 0) {
      this.timer = (System.currentTimeMillis() - this.startTime) / 1000;
    }
    // to update the gameStatus()
    this.gameStatus();
    this.maxFloodedCordinate();
    // The world becomes not still only when there is a mouse click
    // only update the FloodItWorld when the world is not still
    if (!stillornot) {
      // when indice < x + y - 1, keep looping
      // used to be :(2 * this.gridsize - 1)
      // but too slow
      if (this.indice <= this.maxCordinate) {
        this.onTickHelper();
        this.indice = this.indice + 1;
      }
      else {
        this.stillornot = true;
        this.indice = 0;
      }
    }
    this.gameStatus();
  }

  // update the FloodItWorld
  public void onTickHelper() {
    // change the color of flooded cells
    for (ArrayList<Cell> arr : this.board) {
      for (Cell c : arr) {
        if (c.x + c.y == this.indice) {
          if (c.flooded == true) {
            c.color = this.nextColor;
            // change the flooded status of neighbors of flooded cells
            // if they are the same color as nextcolor
            // change the flooded to true
            if (c.left.color.equals(this.nextColor)) {
              c.left.flooded = true;
            }
            if (c.right.color.equals(this.nextColor)) {
              c.right.flooded = true;
            }
            if (c.top.color.equals(this.nextColor)) {
              c.top.flooded = true;
            }
            if (c.bottom.color.equals(this.nextColor)) {
              c.bottom.flooded = true;
            }
          }
        }
      }
    }
  }

  // to invoke the initial onTick in Constructor
  public void onTickInit() {
    // change the color of flooded cells
    for (ArrayList<Cell> arr : this.board) {
      for (Cell c : arr) {
        if (c.flooded == true) {
          c.color = this.nextColor;
          // change the flooded status of neighbors of flooded cells
          // if they are the same color as nextcolor
          // change the flooded to true
          if (c.left.color.equals(this.nextColor)) {
            c.left.flooded = true;
          }
          if (c.right.color.equals(this.nextColor)) {
            c.right.flooded = true;
          }
          if (c.top.color.equals(this.nextColor)) {
            c.top.flooded = true;
          }
          if (c.bottom.color.equals(this.nextColor)) {
            c.bottom.flooded = true;
          }
        }
      }
    }
  }

  // to calculate the maxCordinate of flooded cell
  public void maxFloodedCordinate() {
    for (ArrayList<Cell> arr : this.board) {
      for (Cell c : arr) {
        if (c.flooded == true) {
          if (c.x + c.y > this.maxCordinate)
            this.maxCordinate = c.x + c.y;
        }
      }
    }
  }
}