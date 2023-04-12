import javafx.scene.layout.HBox;

public class MainPane extends HBox
{
	GamePane gamePane;
	OtherSubjectPane otherSubjectPane;
	
	public MainPane()
	{
		otherSubjectPane = new OtherSubjectPane();
		gamePane = new GamePane(otherSubjectPane);

		getChildren().add(gamePane);
		getChildren().add(otherSubjectPane);
	}
	
	@Override
	public void requestFocus()
	{
		gamePane.requestFocus();
	}
}
