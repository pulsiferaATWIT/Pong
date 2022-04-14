package gui;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Paddle {
    final static int LENGTH = 11;
    Tile[] tiles = new Tile[LENGTH];
    ArrayList<Tile> tile = new ArrayList<>();

    public Paddle(double x, double y, Pane root, Color color) {
        for (int i = 0; i < LENGTH; i++) {
            tiles[i] = new Tile(x, y);
            y += 10;
            tiles[i].setFill(color);
            root.getChildren().add(tiles[i]);
        }
    }

    public double getY() {
        return tiles[5].getY();
    }

    public void movePaddle(int deltaY) {
        for (Tile t : tiles) {
            t.move(0, deltaY);
        }

        if (tiles[0].getY() < 0)
            this.movePaddle(- (int) tiles[0].getY());

        if (tiles[0].getY() > 500)
            this.movePaddle(500 - (int) tiles[0].getY());
    }

    public boolean isIntersecting(Tile tile) { // Needs Refinement, dectects if a tile is intersecting with the paddle.
        return (tile.getX() > tiles[0].getX() && tile.getX() < tiles[0].getX() + 10) && (tile.getY() > tiles[0].getY() && tile.getY() < tiles[LENGTH-1].getY());
    }
}
