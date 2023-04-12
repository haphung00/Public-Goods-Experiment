import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application
{
	public void start(Stage primaryStage) throws Exception
	{
		MainPane mainPane = new MainPane();
		
		Scene scene = new Scene(mainPane);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Fish Collection Game");
		primaryStage.show();		
		primaryStage.setResizable(false);
		
		mainPane.requestFocus();
	}
	
	public static void main(String[] args)
	{
		launch(args);
	}
}
