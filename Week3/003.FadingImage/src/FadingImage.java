import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

import javafx.animation.AnimationTimer;
import javafx.application.Application;

import static javafx.application.Application.launch;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.imageio.ImageIO;

import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class FadingImage extends Application {
    private ResizableCanvas canvas;
    private BufferedImage image1;
    private BufferedImage image2;
    float opacity = 0.0f;
    private boolean fading = false;


    @Override
    public void start(Stage stage) throws Exception {

        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());
        new AnimationTimer() {
            long last = -1;

            @Override
            public void handle(long now) {
                if (last == -1)
                    last = now;
                update((now - last) / 1000000000.0);
                last = now;
                draw(g2d);
            }
        }.start();

        try {
            File file1 = new File("Week3/003.FadingImage/resources/discord.jpg");
            File file2 = new File("Week3/003.FadingImage/resources/discord-mod.jpg");
            image1 = ImageIO.read(file1);
            image2 = ImageIO.read(file2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        draw(g2d);
        stage.setScene(new Scene(mainPane));
        stage.setTitle("Fading image");
        stage.show();

    }

    public void draw(FXGraphics2D graphics) {
        opacity = Math.min(1.0f, Math.max(0.0f, opacity));
        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f - opacity));
        graphics.drawImage(image1, 100, 100, null);
        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        graphics.drawImage(image2, 100, 100, null);
    }


    public void update(double deltaTime) {
        if (opacity == 1.0f) {
            fading = true;
            try {
                Thread.sleep(2000); // pauze van 2 seconden
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else if (opacity == 0.0f) {
            fading = false;
//            opacity = 0.0f;
            try {
                Thread.sleep(2000); // pauze van 2 seconden
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (fading) {
            opacity -= 0.1f;
        } else {
            opacity += 0.1f;
        }

    }


    public static void main(String[] args) {
        launch(FadingImage.class);
    }

}
