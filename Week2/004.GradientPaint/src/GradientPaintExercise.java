import java.awt.*;
import java.awt.geom.*;

import javafx.application.Application;

import static javafx.application.Application.launch;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class GradientPaintExercise extends Application {
    private ResizableCanvas canvas;
    private Point2D.Double focus;

    @Override
    public void start(Stage primaryStage) throws Exception
    {

        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);

        focus = new Point2D.Double(this.canvas.getWidth() / 2, this.canvas.getHeight() / 2);
        FXGraphics2D graphics = new FXGraphics2D(canvas.getGraphicsContext2D());

        canvas.setOnMouseDragged(e -> {
            this.focus = new Point2D.Double(e.getX(), e.getY());
            draw(graphics);
        });
        mainPane.setCenter(canvas);
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.setTitle("GradientPaint");
        primaryStage.show();
        draw(graphics);
    }


    public void draw(FXGraphics2D graphics)
    {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());
        graphics.translate(canvas.getWidth() / 2, canvas.getHeight() / 2);
        graphics.translate(1, -1);

        Point2D.Double center = new Point2D.Double(canvas.getWidth() / 2, canvas.getHeight() / 2);
        float radius = 25;

        Color[] colors = {Color.black, Color.red, Color.green, Color.BLUE, Color.CYAN, Color.orange};
        float[] fractions = {0.0f, 0.1f, 0.2f, 0.3f, 0.4f, 0.5f};
        RadialGradientPaint radialGradientPaint = new RadialGradientPaint(focus, 200, fractions, colors, MultipleGradientPaint.CycleMethod.REPEAT);

        graphics.setPaint(radialGradientPaint);
    }


    public static void main(String[] args)
    {
        launch(GradientPaintExercise.class);
    }

}
