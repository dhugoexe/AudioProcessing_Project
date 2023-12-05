package ui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class VuMeter extends Canvas {
    private double level = 0.0;

    public VuMeter(double width, double height) {
        super(width, height);
    }

    public void update(double newLevel) {
        this.level = newLevel;
        draw();
    }

    private void draw() {
        GraphicsContext gc = getGraphicsContext2D();
        double width = getWidth();
        double height = getHeight();

        gc.clearRect(0, 0, width, height);

        Color color;
        if (level < 0.6) {
            color = Color.GREEN;
        } else if (level < 0.8) {
            color = Color.ORANGE;
        } else {
            color = Color.RED;
        }

        gc.setFill(color);
        gc.fillRect(0, height - (level * height), width, level * height);
    }
}
