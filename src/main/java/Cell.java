import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Created by wsgreen on 4/8/17.
 */
public class Cell extends JPanel implements Comparable<Cell> {
  private final int _x;
  private final int _y;

  private double val;

  public Cell(int x, int y, double val) {
    this._x = x;
    this._y = y;

    this.val = val;

    this.setBorder(new EmptyBorder(1, 1, 1, 1));
  }
  public void paintComponent(Graphics g) {
    g.drawOval(_x, _y, 5, 5);
  }

  @Override
  public int getX() {
    return _x;
  }

  @Override
  public int getY() {
    return _y;
  }

  public double getVal() {
    return val;
  }

  public void setVal(double val) {
    this.val = val;
  }

  public int compareTo(Cell o) {
    return Double.compare(o.val, this.val);
  }
}
