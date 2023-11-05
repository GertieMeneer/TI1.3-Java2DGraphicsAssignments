import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;

public class House extends Application
{
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Canvas canvas = new Canvas(500, 500);
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
        primaryStage.setScene(new Scene(new Group(canvas)));
        primaryStage.setTitle("House");
        primaryStage.show();
    }


    public void draw(FXGraphics2D graphics)
    {
        graphics.drawLine(70, 40, 30, 90);
        graphics.drawLine(70, 40, 110, 90);
        graphics.drawLine(110, 90, 110, 150);
        graphics.drawLine(110, 150, 30, 150);
        graphics.drawLine(30, 150, 30, 90);
        graphics.drawLine(40, 150, 40, 110);
        graphics.drawLine(40, 110, 60, 110);
        graphics.drawLine(60, 110, 60, 150);
        graphics.drawLine(70, 110, 100, 110);
        graphics.drawLine(100, 110, 100, 130);
        graphics.drawLine(100, 130, 70, 130);
        graphics.drawLine(70, 130, 70, 110);
    }


    public static void main(String[] args)
    {
        launch(House.class);
    }

}
