import java.awt.*;
import java.awt.geom.*;
import java.io.File;
import java.util.ArrayList;

import javafx.application.Application;

import static javafx.application.Application.launch;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class Rainbow extends Application {
    private ResizableCanvas canvas;

    @Override
    public void start(Stage stage) throws Exception
    {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        stage.setScene(new Scene(mainPane));
        stage.setTitle("Rainbow");
        stage.show();
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
    }


    public void draw(FXGraphics2D graphics)
    {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        Font font = new Font("Dialog", Font.BOLD, 48);

        graphics.setFont(font);

        ArrayList<Color> colors = new ArrayList<>();
        colors.add(Color.orange);
        colors.add(Color.yellow);
        colors.add(Color.green);
        colors.add(Color.cyan);
        colors.add(Color.lightGray);
        colors.add(Color.blue);
        colors.add(Color.MAGENTA);
        colors.add(Color.pink);
        colors.add(Color.orange);

        ArrayList<String> rainbow = new ArrayList<>();
        rainbow.add("r");
        rainbow.add("a");
        rainbow.add("i");
        rainbow.add("n");
        rainbow.add("b");
        rainbow.add("o");
        rainbow.add("w");

        int x = 150;
        int y = 300;
        int count = 0;
        float angle = 270;

        for (int i = 0; i < rainbow.size(); i++) {
            AffineTransform tx = new AffineTransform();
            tx.rotate(Math.toRadians(angle), x, y);
            graphics.setTransform(tx);
            graphics.setColor(colors.get(i));
            graphics.drawString(rainbow.get(i), x, y);
            x += 50;
            count++;
            angle += 22.5;

            if (count > 3)
            {
                y += 40;
            }
            else
            {
                y -= 40;
            }
        }

    }


    public static void main(String[] args)
    {
        launch(Rainbow.class);
    }

}
