package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        Scene scene = new Scene(root,1000, 600, Color.BLACK);

        Tile ball = new Tile(200, 200);
        ball.setFill(Color.WHITE);
        root.getChildren().add(ball);

        Paddle player1 = new Paddle(850, 150, root, Color.WHITE);
        Paddle player2 = new Paddle(150, 250, root, Color.WHITE);

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

        primaryStage.setTitle("Pong");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
