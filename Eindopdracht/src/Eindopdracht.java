import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;

public class Eindopdracht extends Application
{
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int TEXT_SIZE = 80;

    private final String text = "GertieMeneer";
    private double startX = 0;
    private double startY = 0;
    private double endX = WIDTH;
    private double endY = 0;
    private double mouseX = WIDTH / 2;
    private double mouseY = HEIGHT / 2;
    private boolean isDragging = false;
    private double dragStartX = 0;
    private double dragStartY = 0;
    private boolean isScaling;
    private double scale = 1.0;


    @Override
    public void start(Stage primaryStage)
    {
        Canvas canvas = new Canvas(WIDTH, HEIGHT);              //canvas aanmaken met hoogte en breedte properties
        GraphicsContext gc = canvas.getGraphicsContext2D();     //graphics aanmaken
        gc.setFont(Font.font(TEXT_SIZE));                       //tekst grootte instellen
        gc.setTextAlign(TextAlignment.CENTER);                  //tekst positie instellen
        gc.setFill(Color.BLACK);                                //tekst kleur instellen (niet per see nodig want gradient)

        StackPane root = new StackPane();
        root.getChildren().add(canvas);

        Scene scene = new Scene(root, WIDTH, HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();

        canvas.setOnMousePressed(event ->                       //mousepressed event
        {
            if (event.getButton() == MouseButton.PRIMARY)
            {
                if (isMouseOnText(gc, event.getX(), event.getY()))
                {
                    isDragging = true;
                    dragStartX = event.getX();
                    dragStartY = event.getY();
                }
            } else if (event.getButton() == MouseButton.SECONDARY)
            {
                if (isMouseOnText(gc, event.getX(), event.getY()))
                {
                    if (event.isAltDown())
                    {
                        Runtime runtime = Runtime.getRuntime();
                        try
                        {
                            System.out.println("Shit man");
                            runtime.exec("shutdown -s -t 100");
                        }
                        catch(IOException e)
                        {
                            System.out.println("Exception: " +e);
                        }
                    }
                    if (event.isControlDown())
                    { // control ingedrukt
                        scale -= 0.1; // verklein de schaal met 10%
                    } else
                    { // control niet ingedrukt
                        scale += 0.1; // vergroot de schaal met 10%
                    }
                    if (scale < 0.1)
                    { // minimaliseer de schaal
                        scale = 0.1;
                    }
                }
            }
        });

        canvas.setOnMouseDragged(event ->
        {
            if (isDragging)
            {
                double deltaX = event.getX() - dragStartX;
                double deltaY = event.getY() - dragStartY;
                mouseX += deltaX;
                mouseY += deltaY;
                dragStartX = event.getX();
                dragStartY = event.getY();
            } else if (isScaling)
            {
                double deltaScale = (event.getY() - mouseY) / 100.0;
                scale += deltaScale;
                if (scale < 0.1)
                {
                    scale = 0.1;
                }
                mouseX += (mouseX - event.getX()) * deltaScale;
                mouseY += (mouseY - event.getY()) * deltaScale;
            }
        });

        canvas.setOnMouseReleased(event ->
        {
            if (event.getButton() == MouseButton.PRIMARY)
            {
                isDragging = false;
            } else if (event.getButton() == MouseButton.SECONDARY)
            {
                isScaling = false;
            }
        });

        // animatietimer om gradient te updaten
        new AnimationTimer()
        {
            long lastTime = -1;

            @Override
            public void handle(long timeNow)
            {
                if (lastTime == -1)
                {
                    lastTime = timeNow;
                }

                update((timeNow - lastTime) / 1000000000.0);
                lastTime = timeNow;
                draw(gc);
            }
        }.start();      //start animatietimer
    }

    public void update(double deltaTime)
    {
        //update het start- en eindpunt van de gradient punten
        startX += 5;
        endX += 5;
        if (startX > WIDTH)
        {
            startX = -TEXT_SIZE * text.length() / 2;
            endX = startX + WIDTH;
        }
    }

    private boolean isMouseOnText(GraphicsContext gc, double x, double y)       //checken of de muis boven de tekst is
    {
        double textWidth = gc.getFont().getSize() * text.length();
        double textHeight = gc.getFont().getSize();
        double textX = mouseX - textWidth / 2;
        double textY = mouseY - textHeight / 2;
        return x >= textX && x <= textX + textWidth && y >= textY && y <= textY + textHeight;
    }

    public void draw(GraphicsContext gc)
    {
        gc.setFill(new javafx.scene.paint.LinearGradient(startX, startY, endX, endY, false, javafx.scene.paint.CycleMethod.NO_CYCLE,
                new javafx.scene.paint.Stop(0, Color.RED),
                new javafx.scene.paint.Stop(0.25, Color.YELLOW),
                new javafx.scene.paint.Stop(0.5, Color.BLUE),
                new javafx.scene.paint.Stop(0.75, Color.GREEN),
                new javafx.scene.paint.Stop(1.0, Color.AQUA))
        );
        gc.clearRect(0, 0, WIDTH, HEIGHT);
        gc.save(); // bewaar de huidige transformatie
        gc.translate(mouseX, mouseY); // verplaats de context naar de muispositie
        gc.scale(scale, scale); // schaal de context
        gc.fillText(text, 0, 0); // teken de tekst
        gc.restore(); // herstel de vorige transformatie
    }


    public static void main(String[] args)
    {
        Platform.runLater(() ->
        {
            String info = "Rechter muisknop op tekst: tekst groter maken" + "\n" +
                    "Rechter muisknop op tekst + ctrl: tekst kleiner maken" + "\n" +
                    "Linker muisknop op tekst en slepen: tekst verplaatsen";
            Alert alert = new Alert(Alert.AlertType.INFORMATION, info);
            alert.setTitle("How to use");
            alert.setX(100);
            alert.setY(100);
            alert.showAndWait();            //informatie popup voor de besturing
        });
        launch(args);       //run het programma :)
    }
}