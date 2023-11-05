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

public class Mirror extends Application {
    ResizableCanvas canvas;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.setTitle("Mirror");
        primaryStage.show();
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
    }


    public void draw(FXGraphics2D graphics)
    {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());
        graphics.translate(this.canvas.getWidth() / 2, this.canvas.getHeight() / 2);
        graphics.scale(1, -1);

        graphics.setColor(Color.green);
        graphics.draw(new Line2D.Double(0, 0, 0, this.canvas.getHeight()));
        graphics.draw(new Line2D.Double(0, 0, this.canvas.getWidth(), 0));

//        graphics.setColor(Color.black);
//        graphics.drawLine((int)this.canvas.getWidth() /2, (int)this.canvas.getHeight()/2, 0, (int)this.canvas.getWidth()/2);

        GeneralPath path = new GeneralPath();

//        path.moveTo(0, 0);
//        path.lineTo(0, this.canvas.getHeight());

        graphics.setColor(Color.black);
        path.moveTo(0, 0);
        path.lineTo(100, 0);
        path.moveTo(100, 0);
        path.lineTo(100, 100);
        path.moveTo(100, 100);
        path.lineTo(0, 100);

        graphics.draw(path);

        graphics.setColor(Color.red);

        AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
        graphics.draw(tx.createTransformedShape(path));
    }


    public static void main(String[] args)
    {
        launch(Mirror.class);
    }

}
