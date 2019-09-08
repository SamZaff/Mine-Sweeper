package MineSweeper;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.util.Random;

class TileLayout {
    private BufferedImage ClickedTile;
    private BufferedImage UnclickedTile;
    private int width;
    private int height;
    /*int[][] layout = {
            {9, 9, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {9, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9, 9, 9},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9, 9},
            {9, 9, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {9, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9, 9, 9},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9, 9},
            {9, 9, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {9, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9, 9, 9},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9, 9},
            {9, 9, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {9, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9, 9, 9},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9, 9}
    };*/

    int[][] layout;
    TileLayout(BufferedImage UnclickedTile, BufferedImage ClickedTile, int width, int height, int bombCount) {
        this.UnclickedTile = UnclickedTile;
        this.ClickedTile = ClickedTile;
        this.width = width;
        this.height = height;
        this.layout = new int[height/25][width/25];
        Random rand = new Random();

        for (int i = 0; i < height/25; i++) {
            for (int j = 0; j < width/25; j++) {
                if (rand.nextInt(2) == 0 && bombCount > 0) {
                    this.layout[i][j] = 9;
                    bombCount--;
                }
                else {
                    this.layout[i][j] = 0;
                }
            }
        }
    }
    /*void init(BufferedImage UnclickedTile, BufferedImage ClickedTile, int width, int height) {
        this.UnclickedTile = UnclickedTile;
        this.ClickedTile = ClickedTile;
    }*/



    int[][] shuffle() {
        Random random = new Random();
        for (int i = layout.length - 1; i > 0; i--) {
            for (int j = layout[i].length - 1; j > 0; j--) {
                int m = random.nextInt(i + 1);
                int n = random.nextInt(j + 1);

                int temp = layout[i][j];
                layout[i][j] = layout[m][n];
                layout[m][n] = temp;
            }
        }
        return layout;
    }


    int check(int y, int x){
        int counter = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (x + j >= 0 && x + j <= ((width/25)-1) && y + i >= 0 && y + i <= ((height/25)-1)) {
                    if (layout[y + i][x + j] == 9 || layout[y + i][x + j] == 8 || layout[y + i][x + j] == 10) {
                        counter++;
                    }
                }
            }
        }
        return counter;
    }

    private void revealMines() {
        for (int i = 0; i < layout.length; i ++) {
            for (int j = 0; j < layout[i].length; j++) {
                if (layout[i][j] == 9 || layout[i][j] == 10) {
                    layout[i][j] = 8;
                }
            }
        }
    }

    private void revealOpenSpaces(int y, int x) {
        if (layout[y][x] == 1 && check(y, x) == 0 ) {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (x + j >= 0 && x + j <= ((width/25)-1) && y + i >= 0 && y + i <= ((height/25)-1) && layout[y + i][x + j] != 8) {
                        layout[y + i][x + j] = 1;
                    }
                }
            }
        }
    }

    void draw(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        g2d.setFont(new Font("Arial Black", Font.PLAIN, 20));

        for (int i = 0; i < layout.length; i ++) {
            for (int j = 0; j < layout[i].length; j ++) {
                revealOpenSpaces(i, j);
                if (layout[i][j] == 0 || layout[i][j] == 9) {
                    g2d.drawImage(UnclickedTile, (j*UnclickedTile.getHeight()), (i*UnclickedTile.getWidth()), null);

                }

                else if (layout[i][j] == 1){
                    g2d.drawImage(ClickedTile, (j*ClickedTile.getHeight()), (i*ClickedTile.getWidth()), null);
                    if (check(i, j) == 1) {
                        g2d.setColor(Color.BLUE);
                    }
                    else if (check(i, j) == 2) {
                        g2d.setColor(new Color(34, 139, 34));
                    }
                    else if (check(i, j) == 3) {
                        g2d.setColor(Color.RED);
                    }
                    else if (check(i, j) == 4) {
                        g2d.setColor(new Color(25, 25, 112));
                    }
                    else /*if (check(i, j) == 5)*/ {
                        g2d.setColor(new Color(100, 0, 0));
                    }

                    if (check(i, j) > 0) {
                        g2d.drawString(Integer.toString(check(i, j)), (j*ClickedTile.getHeight()) + 5, (i*ClickedTile.getWidth()) + 20);
                    }


                }
                else if (layout[i][j] == 8) {
                    revealMines();
                    g2d.drawImage(ClickedTile, (j*ClickedTile.getHeight()), (i*ClickedTile.getWidth()), null);
                    g2d.setColor(Color.BLACK);
                    g2d.drawString("X", (j*ClickedTile.getHeight()) + 5, (i*ClickedTile.getWidth()) + 20);
                }
                else if (layout[i][j] == 2 || layout[i][j] == 10) {
                    g2d.drawImage(UnclickedTile, (j*ClickedTile.getHeight()), (i*ClickedTile.getWidth()), null);
                    g2d.setColor(new Color(120, 0, 0));
                    g2d.drawString("F", (j*ClickedTile.getHeight()) + 5, (i*ClickedTile.getWidth()) + 20);
                }

            }
        }
    }
}

