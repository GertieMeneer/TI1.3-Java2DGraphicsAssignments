import java.awt.*;
import java.awt.geom.*;

import javafx.application.Application;

import static javafx.application.Application.launch;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class Colors extends Application {
    private ResizableCanvas canvas;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.setTitle("Colors");
        primaryStage.show();
    }


    public void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        Area black = new Area(new Rectangle2D.Double(0, 0, 50, 50));
        Area blue = new Area(new Rectangle2D.Double(50, 0, 50, 50));
        Area cyan = new Area(new Rectangle2D.Double(100, 0, 50, 50));
        Area darkGray = new Area(new Rectangle2D.Double(150, 0, 50, 50));
        Area gray = new Area(new Rectangle2D.Double(200, 0, 50, 50));
        Area green = new Area(new Rectangle2D.Double(250, 0, 50, 50));
        Area lightGray = new Area(new Rectangle2D.Double(300, 0, 50, 50));
        Area magenta = new Area(new Rectangle2D.Double(350, 0, 50, 50));
        Area orange = new Area(new Rectangle2D.Double(400, 0, 50, 50));
        Area pink = new Area(new Rectangle2D.Double(450, 0, 50, 50));
        Area red = new Area(new Rectangle2D.Double(500, 0, 50, 50));
        Area white = new Area(new Rectangle2D.Double(550, 0, 50, 50));
        Area yellow = new Area(new Rectangle2D.Double(600, 0, 50, 50));


        graphics.setColor(Color.black);
        graphics.fill(black);
        graphics.setColor(Color.blue);
        graphics.fill(blue);
        graphics.setColor(Color.cyan);
        graphics.fill(cyan);
        graphics.setColor(Color.darkGray);
        graphics.fill(darkGray);
        graphics.setColor(Color.gray);
        graphics.fill(gray);
        graphics.setColor(Color.green);
        graphics.fill(green);
        graphics.setColor(Color.lightGray);
        graphics.fill(lightGray);
        graphics.setColor(Color.magenta);
        graphics.fill(magenta);
        graphics.setColor(Color.orange);
        graphics.fill(orange);
        graphics.setColor(Color.pink);
        graphics.fill(pink);
        graphics.setColor(Color.red);
        graphics.fill(red);
        graphics.setColor(Color.white);
        graphics.fill(white);
        graphics.setColor(Color.yellow);
        graphics.fill(yellow);



    }
    public static void main(String[] args)
    {
        launch(Colors.class);
    }

}
