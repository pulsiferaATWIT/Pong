package gui;

import javafx.application.Application;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Random;

public class Main extends Application {

    static Random gen = new Random();
    static int xDirection, yDirection;
    double ballSpeed = 2;
    int player1Score = 0, player2Score = 0;


    @Override
    public void start(Stage primaryStage) {
        try {
            Pane root = new Pane();
            Scene scene = new Scene(root,1250, 600, Color.BLACK);

            Tile ball = new Tile(625, 300);
            ball.setFill(Color.WHITE);
            root.getChildren().add(ball);

            Line centerLine = new Line(scene.getWidth() / 2, 0, scene.getWidth() / 2, scene.getHeight() + 10);
            centerLine.setStroke(Color.WHITE);
            root.getChildren().add(centerLine);

            Text score1 = new Text(scene.getWidth() / 2 + 15, 40, "0");
            score1.setFont(new Font(30));
            score1.setFill(Color.WHITE);

            Text score2 = new Text(scene.getWidth() / 2 - 30, 40, "0");
            score2.setFont(new Font(30));
            score2.setFill(Color.WHITE);

            root.getChildren().addAll(score1, score2);

            Paddle player1 = new Paddle(scene.getWidth() - 150, 150, root, Color.WHITE);
            Paddle player2 = new Paddle(150, 250, root, Color.WHITE);

            AnimationTimer gameLoop = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    ball.move(xDirection * ballSpeed, yDirection * ballSpeed);

                    if (ball.getX() < 0) { // Checks if ball is scored for player 1
                        xDirection *= -1;
//                        yDirection = (1 - ((1 - (gen.nextInt(2) * 2)) * gen.nextInt(2) * 2));
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

                    if (ball.getY() > 600 || ball.getY() < 0) // Checks for if the ball hits the top or bottom of the window
                        yDirection *= -1;

                    if (player1.isIntersecting(ball) || player2.isIntersecting(ball)) { // Paddle collision detection
                        xDirection *= -1;
                        yDirection *= gen.nextDouble() + (gen.nextInt(2)); // Adds randomness to ball bounce
                        ball.move(xDirection * 3, 0);
                        ballSpeed *= 1.1;
                    }

                    // Updates Text
                    score1.setText(String.format("%d", player1Score));
                    score2.setText(String.format("%d", player2Score));

                    if (player1Score >= 11 || player2Score >= 11)
                        this.stop();
                }
            };

            scene.setOnKeyPressed(event -> { // Movement for player 2
                switch(event.getCode()) {
                    case W: player2.movePaddle(-10); break;
                    case S: player2.movePaddle(10); break;
                    case UP: player1.movePaddle(-10); break;
                    case DOWN: player1.movePaddle(10); break;
                    case H: player1Score++; break;
                    default: break;
                }
            });

//        scene.setOnMouseMoved(event -> { // Movement for player 1
//            if (event.getY() > player1.getY())
//                player1.movePaddle(5);
//
//            if (event.getY() < player1.getY())
//                player1.movePaddle(-5);
//        });

            gameLoop.start(); // Starts ball movement

            primaryStage.setResizable(false); // Fix window size
            primaryStage.setTitle("Pong");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            System.out.println(e.toString());
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        yDirection = (1 - (gen.nextInt(2) * 2));
        xDirection = (1 - (gen.nextInt(2) * 2));

        launch(args);
    }
}
