import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.sql.Time;

public class AngryBirds extends Application
{

    private Pane root;
    private Circle projectile;
    private Rectangle[] blocks;
    private Label statusLabel;

    private boolean isShooting = false;
    private double forceY;
    private Timeline timeline = new Timeline();

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        root = new Pane();
        blocks = new Rectangle[5];

        // Maak blokken aan de rechterkant
        for (int i = 0; i < blocks.length; i++)
        {
            Rectangle block = new Rectangle(50, 50);
            block.setFill(Color.BLUE);
            block.setStroke(Color.BLACK);
            block.setX(500);
            block.setY(i * 70 + 50);
            blocks[i] = block;
            root.getChildren().add(block);
        }

        // Maak de katapult aan de linkerkant
        Rectangle catapultBase = new Rectangle(30, 150);
        catapultBase.setFill(Color.BLACK);
        catapultBase.setStroke(Color.BLACK);
        catapultBase.setX(50);
        catapultBase.setY(200);
        Rectangle catapultArm = new Rectangle(200, 30);
        catapultArm.setFill(Color.BLACK);
        catapultArm.setStroke(Color.BLACK);
        catapultArm.setX(20);
        catapultArm.setY(200);
        root.getChildren().addAll(catapultBase, catapultArm);

        // Maak een projectiel in de katapult
        projectile = new Circle(25);
        projectile.setFill(Color.RED);
        projectile.setStroke(Color.BLACK);
        projectile.setCenterX(100);
        projectile.setCenterY(200);
        root.getChildren().add(projectile);

        // Maak een label om de status weer te geven
        statusLabel = new Label("Klik en sleep om te schieten.");
        statusLabel.setLayoutX(50);
        statusLabel.setLayoutY(20);
        root.getChildren().add(statusLabel);

        // Voeg muisgebeurtenissen toe aan het projectiel
        projectile.setOnMousePressed(event ->
        {
            isShooting = true;
            statusLabel.setText("Loslaat om te schieten.");
        });
        projectile.setOnMouseDragged(event ->
        {
            if (isShooting)
            {
                projectile.setCenterX(event.getX());
                projectile.setCenterY(event.getY());
            }
        });
        projectile.setOnMouseReleased(event ->
        {
            if (isShooting)
            {
                double distanceX = catapultBase.getX() + catapultArm.getWidth() - projectile.getCenterX();
                double distanceY = catapultBase.getY() + catapultBase.getHeight() / 2 - projectile.getCenterY();
                double angle = Math.atan2(-distanceY, distanceX);
                double force = Math.sqrt(distanceX * distanceX + distanceY * distanceY) * 0.1;
                double forceX = Math.cos(angle) * force;
                this.forceY = Math.sin(angle) * force;
                projectile.setCenterX(100);
                projectile.setCenterY(200);
                isShooting = false;
                statusLabel.setText("Klik en sleep om te schieten.");
                shootProjectile(forceX, forceY);
            }
        });

        primaryStage.setTitle("Angry Birds");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    private void shootProjectile(double forceX, double forceY)
    {
        timeline = new Timeline(new KeyFrame(Duration.millis(10), event ->
        {
            // Bereken de nieuwe positie van het projectiel
            double x = projectile.getCenterX() + forceX;
            double y = projectile.getCenterY() + forceY;
            projectile.setCenterX(x);
            projectile.setCenterY(y);
            // Controleer of het projectiel een blok heeft geraakt
            for (Rectangle block : blocks)
            {
                if (block.getBoundsInParent().intersects(projectile.getBoundsInParent()))
                {
                    root.getChildren().remove(block);
                }
            }

            // Controleer of het projectiel buiten het scherm is
            // Werkt niet goed omdat hij gelijk checkt voordat de particle pas uit het scherm is gevlogen

            Bounds bounds = root.getBoundsInLocal();
            if (projectile.getCenterX() < bounds.getMinX() || projectile.getCenterX() > bounds.getMaxX()
                    || projectile.getCenterY() < bounds.getMinY() || projectile.getCenterY() > bounds.getMaxY())
            {
                timeline.stop();
                projectile.setCenterX(100);
                projectile.setCenterY(200);
                isShooting = false;

                // Controleer of er nog blokken over zijn
                boolean allBlocksGone = true;
                for (Rectangle block : blocks)
                {
                    if (root.getChildren().contains(block))
                    {
                        allBlocksGone = false;
                        break;
                    }
                }
                if (allBlocksGone)
                {
                    statusLabel.setText("Gefeliciteerd, je hebt gewonnen!");
                } else
                {
                    statusLabel.setText("Klik en sleep om te schieten.");
                }
            }

            // Pas de kracht van het projectiel aan
            this.forceY += 0.1;
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
