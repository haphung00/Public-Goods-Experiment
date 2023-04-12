import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class GamePane extends Pane
{
	public static final int TOTAL_FISH = 10;
	
	private static final int WIDTH = 500;
	private static final int HEIGHT = 300;
	private static final int FISH_MAX_SIZE = 50;
	private static final int BOAT_SIZE = 80;

	private ImageView boatImageView;

	private Label scoreLabel;
	private Button finishButton;
	private TextInputDialog dialog;

	private ImageView[] fishImageViews;

	private int numFish;
	private int collectedFish;
	private String name;
	
	private OtherSubjectPane otherSubjectPane;
	
	public GamePane(OtherSubjectPane otherSubjectPane)
	{
		this.otherSubjectPane = otherSubjectPane;
		
		initialize();
		generateFish();
		takeNameInput();
		startGame();
	}

	private void initialize()
	{		
		this.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, new CornerRadii(0), new Insets(0))));;

		setPrefWidth(WIDTH);
		setPrefHeight(HEIGHT);
		
		boatImageView = new ImageView(new Image("boat.png"));
		boatImageView.setFitWidth(BOAT_SIZE);
		boatImageView.setFitHeight(BOAT_SIZE);
		boatImageView.setX(WIDTH / 2);
		boatImageView.setY(HEIGHT / 2);

		scoreLabel = new Label("Fish Caught: 0");
		scoreLabel.setLayoutX(20);
		scoreLabel.setLayoutY(20);
		
		finishButton = new Button();
		finishButton.setText("FINISH");
		finishButton.setPrefWidth(100);
		finishButton.setLayoutX(WIDTH - 100);

		getChildren().addAll(boatImageView, scoreLabel, finishButton);
		setOnKeyPressed(e -> {
			switch (e.getCode()) {
				case LEFT:
					boatImageView.setX(boatImageView.getX() > 0 ? boatImageView.getX() - 10 : boatImageView.getX());
					break;
				case RIGHT:
					boatImageView.setX(boatImageView.getX() < WIDTH ? boatImageView.getX() + 10 : boatImageView.getX());
					break;
				case UP:
					boatImageView
							.setY(boatImageView.getY() > 0 ? boatImageView.getY() - 10 : boatImageView.getY() - 10);
					break;
				case DOWN:
					boatImageView.setY(boatImageView.getY() < HEIGHT ? boatImageView.getY() + 10 : boatImageView.getY());
					break;
				default:
			}

			collectFish();
		});
	}
	
	private void takeNameInput()
	{
		dialog = new TextInputDialog();
		dialog.setTitle("Player's name");
		dialog.setContentText("Please enter your name");
		
		dialog.showAndWait().ifPresent(e -> {
			name = e;
		});
	}

	private void generateFish()
	{
		numFish = TOTAL_FISH;
		collectedFish = 0;

		fishImageViews = new ImageView[numFish];

		Random rand = new Random();

		for (int i = 0; i < numFish; i++) {
			int size = FISH_MAX_SIZE - rand.nextInt(30);
			int x = rand.nextInt(WIDTH - size);
			int y = HEIGHT * 2 / 3 + rand.nextInt(HEIGHT / 3 - size);

			ImageView fishImageView = new ImageView(new Image("fish.png"));
			fishImageView.setX(x);
			fishImageView.setY(y);
			fishImageView.setFitWidth(size);
			fishImageView.setFitHeight(size);

			fishImageViews[i] = fishImageView;

			getChildren().add(fishImageView);
		}
	}

	private void collectFish()
	{
		for (int i = 0; i < numFish; i++) {
			if (boatImageView.getBoundsInParent().intersects(fishImageViews[i].getBoundsInParent())) {
				if (getChildren().contains(fishImageViews[i])) {
					getChildren().remove(fishImageViews[i]);
					collectedFish++;
					scoreLabel.setText("Fish Caught: " + collectedFish);
				}
			}
		}
	}

	private void startGame()
	{
		finishButton.setOnMouseClicked(e -> {
			stopGame();
		});
	}

	private void stopGame()
	{
		writeData();
		System.exit(0);
	}
	
	private void writeData()
	{
		File file = new File("data.txt");
		try {
			PrintWriter pw = new PrintWriter(file);
			pw.write(String.format("Player: %s, %d fish caught%n", name, collectedFish));
			pw.write(otherSubjectPane.getData());
			pw.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
