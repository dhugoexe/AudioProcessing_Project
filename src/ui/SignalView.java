package ui;

import audio.AudioSignal;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class SignalView extends LineChart<Number, Number> {

    private XYChart.Series<Number, Number> series;

    public SignalView() {
        super(new NumberAxis(), new NumberAxis());
        series = new XYChart.Series<>();
        getData().add(series);
    }

    public void updateData(AudioSignal signal)
    {
        series.getData().clear();
        double[] data = signal.getSampleBuffer();
        for (int i = 0; i < data.length; i++) {
            series.getData().add(new XYChart.Data<>(i, data[i]));
        }
    }


}
