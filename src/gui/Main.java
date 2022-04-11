package gui;

import javafx.application.Application;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Random;

public class Main extends Application {

    static Random gen = new Random();
    static int xDirection, yDirection;
    double ballSpeed = 1;
    int player1Score = 0, player2Score = 0;


    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        Scene scene = new Scene(root,1250, 600, Color.BLACK);

        Tile ball = new Tile(625, 300);
        ball.setFill(Color.WHITE);
        root.getChildren().add(ball);

        Line centerLine = new Line(scene.getWidth() / 2, 0, scene.getWidth() / 2, scene.getHeight());
        centerLine.setStroke(Color.WHITE);
        root.getChildren().add(centerLine);

        Paddle player1 = new Paddle(scene.getWidth() - 150, 150, root, Color.WHITE);
        Paddle player2 = new Paddle(150, 250, root, Color.WHITE);

        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                ball.move(xDirection * ballSpeed, yDirection * ballSpeed);

                if (ball.getX() < 0) { // Checks if ball is scored for player 1
                    xDirection *= -1;
                    yDirection = (1 - (gen.nextInt(2) * 2));
                    ball.reset();
                    ballSpeed = 1;
                    player1Score++;
                }

                if (ball.getX() > 1240) { // Checks if ball is scored for player 2
                    xDirection *= -1;
                    yDirection = (1 - (gen.nextInt(2) * 2));
                    ball.reset();
                    ballSpeed = 1;
                    player2Score++;
                }

                if (ball.getY() > 590 || ball.getY() < 0) // Checks for if the ball hits the top or bottom of the window
                    yDirection *= -1;

                if (player1.isIntersecting(ball) || player2.isIntersecting(ball)) { // Paddle collision detection
                    xDirection *= -1;
                    ball.move(xDirection * 3, 0);
                    ballSpeed += 0.1;
                }
            }
        };

        scene.setOnKeyPressed(event -> {
            switch(event.getCode()) {
                case W: player2.movePaddle(-10); break;
                case S: player2.movePaddle(10); break;
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
        yDirection = (1 - (gen.nextInt(2) * 2));
        xDirection = (1 - (gen.nextInt(2) * 2));

        launch(args);
    }
}
