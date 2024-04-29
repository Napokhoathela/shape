package com.example.shape;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    private Pane root;
    private Circle circle;
    private Rectangle rectangle;
    private Polygon triangle;
    private int currentIndex = 0;
    private Color[] colors = {Color.RED, Color.GREEN, Color.BLUE};

    @Override
    public void start(Stage primaryStage) {
        root = new Pane();
        Scene scene = new Scene(root, 600, 400);
        String cssPath = getClass().getResource("/styles.css").toExternalForm();
        scene.getStylesheets().add(cssPath);

        circle = new Circle(100, 200, 50);
        circle.setFill(colors[0]);
        circle.setOnMouseDragged(this::onMouseDragged);
        circle.getStyleClass().add("shape");

        rectangle = new Rectangle(250, 150, 100, 100);
        rectangle.setFill(colors[1]);
        rectangle.setOnMouseDragged(this::onMouseDragged);
        rectangle.getStyleClass().add("shape");

        triangle = new Polygon();
        triangle.getPoints().addAll(new Double[]{
                450.0, 300.0,
                400.0, 200.0,
                500.0, 200.0 });
        triangle.setFill(colors[2]);
        triangle.setOnMouseDragged(this::onMouseDragged);
        triangle.getStyleClass().add("shape");

        root.getChildren().addAll(circle);

        Button previousButton = new Button("Previous");
        previousButton.setLayoutX(10);
        previousButton.setLayoutY(10);
        previousButton.setOnAction(e -> previousShape());
        previousButton.getStyleClass().add("button");

        Button nextButton = new Button("Next");
        nextButton.setLayoutX(100);
        nextButton.setLayoutY(10);
        nextButton.setOnAction(e -> nextShape());
        nextButton.getStyleClass().add("button");

        Button changeBackgroundButton = new Button("Change Background");
        changeBackgroundButton.setLayoutX(190);
        changeBackgroundButton.setLayoutY(10);
        changeBackgroundButton.setOnAction(e -> changeBackground());
        changeBackgroundButton.getStyleClass().add("button");

        root.getChildren().addAll(previousButton, nextButton, changeBackgroundButton);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Shape Slider");
        primaryStage.show();

        nextShape(); // Display the first shape when the application starts
    }

    private void onMouseDragged(MouseEvent event) {
        if (event.getTarget() instanceof Circle) {
            Circle draggedCircle = (Circle) event.getTarget();
            draggedCircle.setCenterX(event.getX());
            draggedCircle.setCenterY(event.getY());
        } else if (event.getTarget() instanceof Rectangle) {
            Rectangle draggedRectangle = (Rectangle) event.getTarget();
            draggedRectangle.setX(event.getX() - draggedRectangle.getWidth() / 2);
            draggedRectangle.setY(event.getY() - draggedRectangle.getHeight() / 2);
        } else if (event.getTarget() instanceof Polygon) {
            Polygon draggedTriangle = (Polygon) event.getTarget();
            double deltaX = event.getX() - (draggedTriangle.getBoundsInParent().getWidth() / 2 + draggedTriangle.getBoundsInParent().getMinX());
            double deltaY = event.getY() - (draggedTriangle.getBoundsInParent().getHeight() / 2 + draggedTriangle.getBoundsInParent().getMinY());
            draggedTriangle.setLayoutX(draggedTriangle.getLayoutX() + deltaX);
            draggedTriangle.setLayoutY(draggedTriangle.getLayoutY() + deltaY);
        }
    }

    private void nextShape() {
        switch (currentIndex) {
            case 0:
                root.getChildren().remove(circle);
                root.getChildren().add(rectangle);
                break;
            case 1:
                root.getChildren().remove(rectangle);
                root.getChildren().add(triangle);
                break;
            case 2:
                root.getChildren().remove(triangle);
                root.getChildren().add(circle);
                break;
        }
        currentIndex = (currentIndex + 1) % 3;
    }

    private void previousShape() {
        switch (currentIndex) {
            case 0:
                root.getChildren().remove(circle);
                root.getChildren().add(triangle);
                break;
            case 1:
                root.getChildren().remove(rectangle);
                root.getChildren().add(circle);
                break;
            case 2:
                root.getChildren().remove(triangle);
                root.getChildren().add(rectangle);
                break;
        }
        currentIndex = (currentIndex - 1 + 3) % 3;
    }

    private void changeBackground() {
        root.setStyle("-fx-background-color: #" + Integer.toHexString((int) (Math.random() * 0x1000000)));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
