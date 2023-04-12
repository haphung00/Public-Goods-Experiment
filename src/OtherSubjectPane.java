import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class OtherSubjectPane extends VBox
{
	private static final int OTHER_SUBJECT_NUM = 9;
	private static final double SUCCESS_PROBABILITY = 0.5;
	
	private String[] otherPlayerData;

	public OtherSubjectPane()
	{
		otherPlayerData = new String[OTHER_SUBJECT_NUM];
		
		for (int i = 0 ; i < OTHER_SUBJECT_NUM ; i++) {
			int numCollected = 0;
			
			for (int j = 0 ; j < GamePane.TOTAL_FISH ; j++) {
				if (Math.random() < SUCCESS_PROBABILITY) {
					numCollected++;
				}
			}
			
			OtherSubjectLabel other = new OtherSubjectLabel(i, numCollected);
			
			getChildren().add(other);
			otherPlayerData[i] = other.getData();
		}
		
		setPrefWidth(100);
	}
	
	public String getData()
	{
		String data = "Other Players' Data\n";
		
		for (int i = 0 ; i < OTHER_SUBJECT_NUM ; i++) {
			data += otherPlayerData[i] + "\n";
		}
		
		return data;
	}

	static class OtherSubjectLabel extends Label
	{
		private int order;
		private int fishCaught;
		
		public OtherSubjectLabel(int order, int fishCaught)
		{
			this.order = order;
			this.fishCaught = fishCaught;
			setText(getData());
		}
		
		public String getData()
		{
			return String.format("Player %d: %d", order, fishCaught);
		}
	}
}
