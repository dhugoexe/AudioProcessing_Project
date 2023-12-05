package ui;

import audio.AudioIO;
import audio.AudioProcessor;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

public class MainWindow extends Application {

    //UI VAR
    private int app_length = 3440;
    private int app_wight = 1440;
    final Button startBtn = new Button ("Start");
    final Button stopBtn = new Button ("Stop");
    final ComboBox inputComboBox = new ComboBox();
    final ComboBox outputComboBox = new ComboBox();
    final TextField sampleRate = new TextField();

    private SignalView signalViewInput = new SignalView();
    private SignalView signalViewOutput = new SignalView();
    VuMeter vuMeter = new VuMeter(30, 150);

    //AUDIO PROCESSING VAR
    private AudioProcessor audioProcessor;
    TargetDataLine inLine;
    SourceDataLine outLine;


    @Override public void start(Stage stage) {
        stage.setTitle("ComboBoxSample");
        Scene scene = new Scene(new Group(), app_length, app_wight);

        inputComboBox.getItems().addAll(AudioIO.getAudioMixers());
        outputComboBox.getItems().addAll(AudioIO.getAudioMixers());



        GridPane setupGrid = new GridPane();
        setupGrid.setVgap(4);
        setupGrid.setHgap(10);
        setupGrid.setPadding(new Insets(5, 5, 5, 5));
        setupGrid.add(new Label("Input: "), 0, 0);
        setupGrid.add(inputComboBox, 1, 0);
        setupGrid.add(new Label("Output: "), 2, 0);
        setupGrid.add(outputComboBox, 3, 0);
        setupGrid.add(new Label("SampleRate: "), 4, 0);
        setupGrid.add(sampleRate, 5,0);
        setupGrid.add(startBtn, 6, 0);
        setupGrid.add(stopBtn, 7,0);

        signalViewInput.setLayoutX(100);
        signalViewInput.setLayoutY(100);
        signalViewInput.setTitle("INPUT");

        signalViewOutput.setLayoutX(700);
        signalViewOutput.setLayoutY(100);
        signalViewOutput.setTitle("OUTPUT");


        vuMeter.update(0);



        AnimationTimer timerPlot = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (audioProcessor.isThreadRunning()) {
                    signalViewInput.updateData(audioProcessor.getInputSignal());
                    signalViewOutput.updateData(audioProcessor.getOutputSignal());
                }
            }
        };

         // Width and height of the VuMeter

        AnimationTimer timerVuMeter = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (audioProcessor.isThreadRunning())
                {
                    vuMeter.update(audioProcessor.getInputSignal().getdBlevel());
                    System.out.println(audioProcessor.getInputSignal().getdBlevel());
                }
            }
        };


        startBtn.setOnAction(actionEvent ->
        {
            System.out.println("connecting...");
            try {
                inLine = AudioIO.obtainAudioInput("Default Audio Device", 16000);
                outLine = AudioIO.obtainAudioOutput("Default Audio Device", 16000);
                System.out.println("connected!");
                audioProcessor = new AudioProcessor(inLine, outLine, 1024);
                inLine.open();
                inLine.start();

                outLine.open();
                outLine.start();
                new Thread(audioProcessor).start();
                System.out.println("A new thread has been created!");
                timerPlot.start();
                timerVuMeter.start();

            } catch (LineUnavailableException e) {
                throw new RuntimeException(e);
            }
        });

        stopBtn.setOnAction(actionEvent -> {
            audioProcessor.setThreadRunning(false);
            inLine.close();
            outLine.close();
            timerPlot.stop();
            timerVuMeter.stop();
        });



        Group root = (Group)scene.getRoot();
        root.getChildren().add(setupGrid);
        root.getChildren().add(signalViewInput);
        root.getChildren().add(signalViewOutput);
        root.getChildren().add(vuMeter);
        stage.setTitle("Audio Processing");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


}
