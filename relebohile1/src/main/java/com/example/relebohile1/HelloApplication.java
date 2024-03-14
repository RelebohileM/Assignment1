package com.example.relebohile1;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.InputStream;

public class HelloApplication extends Application {

    private int currentIndex = 0;
    private Image[] images = new Image[6];

    @Override
    public void start(Stage primaryStage) {
        // Create a GridPane for the gallery
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(15);
        gridPane.setVgap(15);

        // Add thumbnail images and buttons to the grid
        for (int i = 0; i < 6; i++) {
            ImageView thumbnail = createThumbnail("Art" + i + ".jpg");
            Button viewButton = createViewButton("Art"+i);
            int finalI = i;
            viewButton.setOnAction(event -> viewFullImage(thumbnail.getImage(), finalI));
            gridPane.add(thumbnail, i % 3, i / 3);
            gridPane.add(viewButton, i % 3, i / 3);
        }


        // Create a scene and set it in the stage
        Scene scene = new Scene(gridPane, 400, 400);
        scene.getStylesheets().add(getClass().getResource("style1.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Art Gallery");
        primaryStage.show();
    }

    // Method to create a thumbnail ImageView
    private ImageView createThumbnail(String imageName) {
        InputStream inputStream = getClass().getResourceAsStream(imageName);
        if (inputStream != null) {
            try (inputStream) {
                Image image = new Image(inputStream);
                images[currentIndex] = image;
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(100);
                imageView.setFitHeight(100);
                imageView.setPreserveRatio(true);
                return imageView;
            } catch (Exception e) {
                e.printStackTrace();
                return new ImageView(); // Return an empty ImageView if image loading fails
            }
        } else {
            System.err.println("Image file '" + imageName + "' not found!");
            return new ImageView(); // Return an empty ImageView if image file not found
        }
    }

    // Method to create a button
    private Button createViewButton(String text) {
        return new Button(text);
    }

    private void viewFullImage(Image image, int index) {
        ImageView fullImageView = new ImageView(image);
        fullImageView.setPreserveRatio(true);

        // Set the fit width and height of the ImageView
        fullImageView.setFitWidth(400); // Adjust the width as needed
        fullImageView.setFitHeight(600); // Adjust the height as needed

        Button previousButton = new Button("Previous");
        previousButton.getStyleClass().add("button");
        previousButton.setOnAction(event -> {
            if (index > 0) {
                viewFullImage(images[index - 1], index - 1);
            }
        });

        Button nextButton = new Button("Next");
        nextButton.getStyleClass().add("button");
        nextButton.setOnAction(event -> {
            if (index < images.length - 1) {
                viewFullImage(images[index + 1], index + 1);
            }
        });

        Button exitButton = new Button("Exit");
        exitButton.getStyleClass().add("button");
        exitButton.setOnAction(event -> ((Stage) fullImageView.getScene().getWindow()).close());

        VBox vBox = new VBox(fullImageView, previousButton, nextButton, exitButton);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10));
        Stage fullImageStage = new Stage();
        fullImageStage.setScene(new Scene(vBox));
        fullImageStage.setTitle("Art Gallery");
        fullImageStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

