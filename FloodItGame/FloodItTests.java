import java.util.ArrayList;
import javalib.worldimages.RectangleImage;
import tester.*;
import java.awt.Color;

// Ziwei Wang
// vivianwang
// Zhuo Chen
// aslantachen

// examples and tests
class ExamplesFloodIt {
  FloodItWorld w1 = new FloodItWorld(7, 10);

  // color num and gridsize
  void Init() {
    //   w1 = new FloodItWorld(7, 10);
    w1.bigBang(750, 900, 0.2);
  }

  void Init2() {
    ArrayList<Cell> column1 = new ArrayList<Cell>();
    ArrayList<Cell> column2 = new ArrayList<Cell>();
    column1.add(cell1);
    column1.add(cell3);
    column2.add(cell2);
    column2.add(cell4);
    ArrayList<ArrayList<Cell>> board = new ArrayList<ArrayList<Cell>>();
    board.add(column1);
    board.add(column2);
    FloodItWorld w = new FloodItWorld(board, 2, 2);
    w.updateCell(w.board);
    w.onTick();
  }

  // examples for cell
  Cell cell1 = new Cell(0, 0, Color.BLUE, false);
  Cell cell2 = new Cell(1, 0, Color.BLUE, false);
  Cell cell3 = new Cell(0, 1, Color.CYAN, false);
  Cell cell4 = new Cell(1, 1, Color.GREEN, false);
  RectangleImage cell1Image = new RectangleImage(FloodItWorld.CELL_SIZE, FloodItWorld.CELL_SIZE,
      "solid", Color.BLUE);
  RectangleImage cell2Image = new RectangleImage(FloodItWorld.CELL_SIZE, FloodItWorld.CELL_SIZE,
      "solid", Color.BLUE);
  RectangleImage cell3Image = new RectangleImage(FloodItWorld.CELL_SIZE, FloodItWorld.CELL_SIZE,
      "solid", Color.CYAN);
  RectangleImage cell4Image = new RectangleImage(FloodItWorld.CELL_SIZE, FloodItWorld.CELL_SIZE,
      "solid", Color.GREEN);

  // tests for cellImage()
  boolean testCellImage(Tester t) {
    return t.checkExpect(this.cell1.cellImage(), this.cell1Image)
        && t.checkExpect(this.cell2.cellImage(), this.cell2Image)
        && t.checkExpect(this.cell3.cellImage(), this.cell3Image)
        && t.checkExpect(this.cell4.cellImage(), this.cell4Image);
  }

  // tests for updateCell
  boolean testUpdateCell(Tester t) {
    this.Init();
    FloodItWorld w = new FloodItWorld(2, 2);
    w.updateCell(w.board);
    return t.checkExpect(w.board.get(0).get(0).right, w.board.get(1).get(0))
        && t.checkExpect(w.board.get(1).get(0).left, w.board.get(0).get(0));
  }

  //   // tests for makeScene()
  //   boolean testMakeScene(Tester t) {
  //   return t.checkExpect(this.w1.makeScene().width, 0)
  //   && t.checkExpect(this.w1.makeScene().height, 0);
  //   }

  //  // tests for addBG()
  //  boolean testAddBG(Tester t) {
  //    return t.checkExpect(this.w1.addBG(), this.w1.getEmptyScene());
  //  }

  // // tests for addCell(WorldScene scene)
  // boolean testAddCell(Tester t) {
  // return t.checkExpect(this.w1.addCell(this.w1.getEmptyScene()).width, 750)
  // && t.checkExpect(this.w1.addCell(this.w1.getEmptyScene()).height, 900);
  // }

  // tests for generateColor(int n)
  boolean testGenerateColor(Tester t) {
    return t.checkRange(this.w1.generateColor(4).getRed(), 0, 256)
        && t.checkRange(this.w1.generateColor(4).getGreen(), 0, 256)
        && t.checkRange(this.w1.generateColor(4).getBlue(), 0, 256);
  }

  // tests for generateBoard()
  boolean testGenerateBoard(Tester t) {
    return t.checkExpect(this.w1.generateBoard().size(), this.w1.gridSize)
        && t.checkRange(this.w1.generateBoard().get(0).get(0).x, 0, FloodItWorld.CELL_SIZE * 10)
        && t.checkRange(this.w1.generateBoard().get(0).get(0).y, 0, FloodItWorld.CELL_SIZE * 10)
        && t.checkRange(this.w1.generateBoard().get(0).get(0).color.getGreen(), 0, 256)
        && t.checkRange(this.w1.generateBoard().get(0).get(0).color.getBlue(), 0, 256)
        && t.checkRange(this.w1.generateBoard().get(0).get(0).color.getRed(), 0, 256)
        && t.checkExpect(this.w1.generateBoard().get(0).get(0).flooded, true)
        && t.checkRange(this.w1.generateBoard().get(1).get(6).x, 0, FloodItWorld.CELL_SIZE * 10)
        && t.checkRange(this.w1.generateBoard().get(2).get(5).y, 0, FloodItWorld.CELL_SIZE * 10)
        && t.checkRange(this.w1.generateBoard().get(3).get(4).color.getGreen(), 0, 256)
        && t.checkRange(this.w1.generateBoard().get(4).get(3).color.getBlue(), 0, 256)
        && t.checkRange(this.w1.generateBoard().get(5).get(2).color.getRed(), 0, 256)
        && t.checkExpect(this.w1.generateBoard().get(6).get(1).flooded, false);
  }

  //  // tests for maxFloodedCoordinate
  //  boolean testMaxFloodedCoordinate() {
  //    return t.checkExpect();
  //  }
}