import java.awt.*;
import java.awt.geom.*;

import javafx.application.Application;

import static javafx.application.Application.launch;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;

public class Rainbow extends Application
{
    Canvas canvas = new Canvas(1000, 800);

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
        primaryStage.setScene(new Scene(new Group(canvas)));
        primaryStage.setTitle("Rainbow");
        primaryStage.show();
    }


    public void draw(FXGraphics2D graphics)
    {
        graphics.translate(canvas.getWidth() / 2, canvas.getHeight() / 2);
        graphics.scale(1, -1);
        double res = 0.001;
        double x1, x2, y1, y2;

        for (double i = 0; i < Math.PI; i += res)
        {
            x1 = 300 * Math.cos(i);
            x2 = 400 * Math.cos(i);
            y1 = 300 * Math.sin(i);
            y2 = 400 * Math.sin(i);
            graphics.setColor(Color.getHSBColor((float) (1 / Math.PI * i), 1, 1));
            graphics.draw(new Line2D.Double(x1, y1, x2, y2));
        }
    }


    public static void main(String[] args)
    {
        launch(Rainbow.class);
    }

}
