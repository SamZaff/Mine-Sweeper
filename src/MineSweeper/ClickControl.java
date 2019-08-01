package MineSweeper;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;


public class ClickControl extends MouseAdapter {
    private Point mouseDownCompCoords = null;
    private TileLayout TL;

    void init(TileLayout TL) {
        this.TL = TL;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseDownCompCoords = e.getPoint();

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();
        //if (mouseDownCompCoords.x == 0 && mouseDownCompCoords.y == 0) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                if (TL.layout[(my / 25)][mx / 25] == 0) {
                    TL.layout[(my / 25)][mx / 25] = 1;
                } else if (TL.layout[(my / 25)][mx / 25] == 9) {
                    TL.layout[(my / 25)][mx / 25] = 8;
                }
            }
            if (e.getButton() == MouseEvent.BUTTON3) {
                if (TL.layout[(my / 25)][mx / 25] == 0) {
                    TL.layout[(my / 25)][mx / 25] = 2;
                } else if (TL.layout[(my / 25)][mx / 25] == 2) {
                    TL.layout[(my / 25)][mx / 25] = 0;
                } else if (TL.layout[(my / 25)][mx / 25] == 9) {
                    TL.layout[(my / 25)][mx / 25] = 10;
                } else if (TL.layout[(my / 25)][mx / 25] == 10) {
                    TL.layout[(my / 25)][mx / 25] = 9;
                }
            }
        //}
        mouseDownCompCoords = null;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public void mouseDragged(MouseEvent e) {
        Point currCoords = e.getLocationOnScreen();
        GameStart.jf.setLocation(currCoords.x - mouseDownCompCoords.x, currCoords.y - mouseDownCompCoords.y);
    }
}
