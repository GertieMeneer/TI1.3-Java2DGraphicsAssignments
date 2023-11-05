import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.awt.geom.AffineTransform;

public class Spotlight extends Application {
    private final int WIDTH = 800;
    private final int HEIGHT = 600;
    private Image backgroundImage;
    private ImageView backgroundImageView;
    private Polygon star;

    @Override
    public void start(Stage primaryStage) {
        // load the background image
        backgroundImage = new Image("discord.jpg");
        backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setOpacity(0); // initially set opacity to 0
        AffineTransform a = new AffineTransform();
        backgroundImageView.setX(WIDTH / 2);
        backgroundImageView.setY(HEIGHT / 2);

        // create the star shape
        double[] starPoints = new double[]{
                0.0, -50.0,
                14.0, -16.0,
                47.0, -11.0,
                23.0, 7.0,
                29.0, 40.0,
                0.0, 25.0,
                -29.0, 40.0,
                -23.0, 7.0,
                -47.0, -11.0,
                -14.0, -16.0
        };
        star = new Polygon(starPoints);
        star.setFill(Color.WHITE);
        star.setStroke(Color.BLACK);
        star.setStrokeWidth(3);
        star.setOpacity(0.7);
        star.setMouseTransparent(true); // make the star shape transparent to mouse events

        // create the root pane and add the background and star
        Pane root = new Pane();
        root.getChildren().addAll(backgroundImageView, star);

        // create the scene and add it to the stage
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Cursor Star");
        primaryStage.show();

        // show the background image when the mouse enters its area
        backgroundImageView.setOnMouseEntered(event -> {
            backgroundImageView.setOpacity(1);
        });

        // hide the background image when the mouse exits its area
        backgroundImageView.setOnMouseExited(event -> {
            backgroundImageView.setOpacity(0);
        });

        // update the position of the star to follow the mouse cursor
        scene.setOnMouseMoved(event -> {
            double x = event.getX();
            double y = event.getY();

            // update the position of the star
            star.setLayoutX(x);
            star.setLayoutY(y);

            // update the clip of the background image view to show only the part inside the star
            Rectangle clipRect = new Rectangle(x - star.getBoundsInLocal().getWidth() / 2, y - star.getBoundsInLocal().getHeight() / 2, star.getBoundsInLocal().getWidth(), star.getBoundsInLocal().getHeight());
            clipRect.setFill(Color.WHITE);
            clipRect.setStroke(Color.BLACK);
            clipRect.setStrokeWidth(3);
            backgroundImageView.setClip(clipRect);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
