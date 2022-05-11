package com.example.hellofx;




import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Calendar;

public class ClockPane extends Application {
    int hour;
    int minute;
    int second;

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public ClockPane() {
        Calendar calendar = Calendar.getInstance();
        this.hour = calendar.get(Calendar.HOUR_OF_DAY);
        this.minute = calendar.get(Calendar.MINUTE);
        this.second = calendar.get(Calendar.SECOND);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // circle
        Circle circle = new Circle(250, 250, 200, Color.WHITE);
        // line length
        final int SECONDHANDLELENGTH = 150;
        final int MINUTEHANDLELENGTH = 90;
        final int HOURHANDLELENGTH = 60;

        // 3/6/9/12 text
        Text text3 = new Text(440, 250, "3");
        Text text6 = new Text(250, 450, "6");
        Text text9 = new Text(50, 250, "9");
        Text text12 = new Text(250, 60, "12");

        // line
        Line secondLine = new Line();
        Line minuteLine = new Line();
        Line hourLine = new Line();

        // label
        Label labelTime = new Label(getHour()+":"+getMinute()+":"+getSecond());
        labelTime.setLayoutX(230);
        labelTime.setLayoutY(550);
//        Text textTime = new Text(230, 550, getHour()+":"+getMinute()+":"+getSecond());

        // pane
        Pane clockPane = new Pane();
        clockPane.getChildren().addAll(circle, text3, text6, text9, text12);
        clockPane.setStyle("-fx-background-color:grey;");
        clockPane.getChildren().addAll(secondLine, minuteLine, hourLine, labelTime);

        // scene
        Scene scene = new Scene(clockPane, 500, 600);

        // time counter
        EventHandler<ActionEvent> eventHandler = e -> {
            // time change
            Calendar calendar = Calendar.getInstance();
            setSecond(calendar.get(Calendar.SECOND));
            setMinute(calendar.get(Calendar.MINUTE));
            setHour(calendar.get(Calendar.HOUR_OF_DAY));

            // S/M/H alpha change
            double secondAlpha = getSecond()*Math.PI/30;
            double minuteAlpha = (getMinute()+getSecond()/60)*Math.PI/30;
            double hourAlpha = (getHour()+getMinute()/60+getSecond()/3600)*Math.PI/6;

            // line change
            secondLine.setStartX(250);
            secondLine.setStartY(250);
            secondLine.setEndX(250+SECONDHANDLELENGTH*Math.sin(secondAlpha));
            secondLine.setEndY(250-SECONDHANDLELENGTH*Math.cos(secondAlpha));

            minuteLine.setStartX(250);
            minuteLine.setStartY(250);
            minuteLine.setEndX(250+MINUTEHANDLELENGTH*Math.sin(minuteAlpha));
            minuteLine.setEndY(250-MINUTEHANDLELENGTH*Math.cos(minuteAlpha));

            hourLine.setStartX(250);
            hourLine.setStartY(250);
            hourLine.setEndX(250+HOURHANDLELENGTH*Math.sin(hourAlpha));
            hourLine.setEndY(250-HOURHANDLELENGTH*Math.cos(hourAlpha));

            // text change
//            textTime.setText(getHour()+":"+getMinute()+":"+getSecond());
            labelTime.setText(getHour()+":"+getMinute()+":"+getSecond());

        };
        Timeline animation = new Timeline(new KeyFrame(Duration.millis(1000), eventHandler));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();

        // stage
        primaryStage.setTitle("show clock");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}