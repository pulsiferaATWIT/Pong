package gui;

import javafx.application.Application;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Random;

public class Main extends Application {

    static Random random = new Random();
    static int xDirection, yDirection;
    int ballSpeed = 1;


    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        Scene scene = new Scene(root,1250, 600, Color.BLACK);

        Tile ball = new Tile(625, 300);
        ball.setFill(Color.WHITE);
        root.getChildren().add(ball);

        Paddle player1 = new Paddle(scene.getWidth() - 150, 150, root, Color.WHITE);
        Paddle player2 = new Paddle(150, 250, root, Color.WHITE);

        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                ball.move(xDirection * ballSpeed, yDirection * ballSpeed);

                if (ball.getY() > 590 || ball.getY() < 0)
                    yDirection *= -1;
                if (ball.getX() < 0 || ball.getX() > 1240)
                    xDirection *= -1;
            }
        };

        scene.setOnKeyPressed(event -> {
            switch(event.getCode()) {
                case W: player2.movePaddle(-5); break;
                case S: player2.movePaddle(5); break;
                default: break;
            }
        });

        scene.setOnMouseMoved(event -> {
            if (event.getY() > player1.getY())
                player1.movePaddle(5);

            if (event.getY() < player1.getY())
                player1.movePaddle(-5);
        });

        gameLoop.start();

        primaryStage.setTitle("Pong");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        switch (random.nextInt(4)) {
            case 0: xDirection = -1; yDirection = -1; break;
            case 1: xDirection = -1; yDirection = 1; break;
            case 2: xDirection = 1; yDirection = -1; break;
            case 3: xDirection = 1; yDirection = 1; break;
        }

        launch(args);
    }
}
