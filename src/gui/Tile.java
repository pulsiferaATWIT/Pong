package gui;

import javafx.scene.shape.Rectangle;

public class Tile extends Rectangle { // needs extends rectangle to act like a node to be added to the root
    public Tile(double x, double y) {
        super(x, y, 10, 10);
    }

    public void move(double deltaX, double deltaY) { // Easier to use than relocate for moving locally
        this.setX(this.getX() + deltaX);
        this.setY(this.getY() + deltaY);
        this.relocate(this.getX(), this.getY());
    }

    public void reset() { // resets balls to center of screen
        this.setX(625);
        this.setY(300);
    }
}
