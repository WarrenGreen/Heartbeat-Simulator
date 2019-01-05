import javax.swing.*;
import java.awt.*;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by wsgreen on 4/8/17.
 */
public class Heartbeat extends JFrame{
  private final int _width;
  private final int _height;
  private Cell[][] board;
  private final double THRESHOLD = 23;//22.1807;
  private PriorityQueue<Cell> priorityQueue;

  public Heartbeat(int width, int height) {
    this._width = 7*width;
    this._height = 7*height;

    Random rand = new Random();

    priorityQueue = new PriorityQueue<Cell>();
    board = new Cell[width][height];
    for (int i=0;i<width;i++) {
      for (int j=0;j<height;j++) {
        board[i][j] = new Cell(i,j, rand.nextFloat()*THRESHOLD);
        priorityQueue.add(board[i][j]);
      }
    }

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(_width +20, _height+40);
    setVisible(true);


  }
  @Override
  public void paint(Graphics g) {
    super.paint(g);
    Graphics2D g2 = (Graphics2D) g;

    for (int i=0; i<board.length; i++) {
      for (int j=0; j<board[0].length; j++) {
        Cell c = board[i][j];
        int alpha = getAlphaForTimestamp(c.getVal());
        Color normalColor = new Color(255,255,255,alpha);
        Color beatColor = new Color(255,0,0,255);
        if (c.getVal() ==0) {
          g2.setColor(beatColor);
        }else {
          g2.setColor(normalColor);
        }
        g2.fillOval(c.getX() * 7 + 10, c.getY() * 7 + 30, 5, 5);
      }
    }
  }

  /*private int getNextLinearTimestamp() {
    Cell c = priorityQueue.peek();
    return 255 - c.getVal();
  }*/

  private double getNextTimestamp() {
    Cell c = priorityQueue.peek();
    return THRESHOLD - c.getVal();
  }

  private double getValueForTimestamp(double newX) {
    return 256 *(1- Math.exp(-.25 * newX));
  }

  private int getAlphaForTimestamp(double newX) {
    double t = getValueForTimestamp(newX);
    return (int)Math.round(t);
  }

  private void updateCells(double timestamp) {
    priorityQueue = new PriorityQueue<Cell>();

    for (int i=0;i<board.length;i++) {
      for (int j=0;j<board[0].length;j++) {
        Cell c = board[i][j];
        double newX = c.getVal()*1.2+ timestamp;
        double newY = getValueForTimestamp(newX);
        if (newY >= 255) {
          c.setVal(0);
        } else {
          c.setVal(newX);
        }

        priorityQueue.add(c);
      }
    }
  }

  public void run(int steps) {
    for (int i=0; i<steps; i++) {
      double timestamp = getNextTimestamp();
      updateCells(timestamp);
      repaint();

      try {
        Thread.sleep(500);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  public static void main(String[] args) {

    int width = Integer.parseInt(args[0]);
    int height = Integer.parseInt(args[1]);

    Heartbeat h = new Heartbeat(width, height);
    // Remove comments to require a newline after starting to begin simulation
    // Scanner s = new Scanner(System.in);
    // s.nextLine();
    h.run(1000);
  }
}
