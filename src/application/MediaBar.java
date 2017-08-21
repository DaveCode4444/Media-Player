package application;

/*
 * author - Devang Sawant
 * 
 */

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;


public class MediaBar extends HBox { //HBox --> Horizontal box that will be going at the bottom
	
	private Slider time;  //sliders for time and volume at the bottom
	private Slider volume;
	private Button playButton  = new Button("||");; // button to play and pause video
	private Label volumeLabel; // label for volume
	private MediaPlayer player;
	
	public MediaBar(MediaPlayer play){
		this.player = play;
		time = new Slider();
		volume = new Slider();
		
		volumeLabel = new Label("Volume: ");
		
		setAlignment(Pos.CENTER); //alignment of HBox to center
		setPadding(new Insets(5, 10, 5, 10));
		
		this.volume.setPrefWidth(70);
		this.volume.setMinWidth(30);
		this.volume.setValue(100);
		
		HBox.setHgrow(time, Priority.ALWAYS);
		this.playButton.setPrefWidth(30);
		
		getChildren().add(playButton);
		getChildren().add(time);
		getChildren().add(volumeLabel);
		getChildren().add(volume);
		
		playButton.setOnAction(new EventHandler<ActionEvent>(){ //this event handler handles the play and pausing of the video
			public void handle(ActionEvent e){
				Status status = player.getStatus(); //get current status of the player
				
				if(status == Status.PLAYING){
					if(player.getCurrentTime().greaterThanOrEqualTo(player.getTotalDuration())){
						player.seek(play.getStartTime());
						player.play();
					}else{
						player.pause();
						playButton.setText(">");
					}
				}
				
				if(status == Status.HALTED || status == Status.PAUSED || status == Status.STOPPED){
					player.play();
					playButton.setText("||");
				}
			}
		});
		
		player.currentTimeProperty().addListener(new InvalidationListener() { //move slider and update video time
			@Override
			public void invalidated(Observable observable) {
				updateValues();
			}
		});
		
		time.valueProperty().addListener(new InvalidationListener() {
			@Override
			public void invalidated(Observable observable) {
				if(time.isPressed()){
					player.seek(player.getMedia().getDuration().multiply(time.getValue()/100));
				}
			}
		});
		
		volume.valueProperty().addListener(new InvalidationListener() { //controls volume 
			
			@Override
			public void invalidated(Observable observable) {
				if(volume.isPressed()){
					player.setVolume(volume.getValue()/100);
				}
			}
		});
		
	}
	
	//updates video time
	protected void updateValues(){ 
		Platform.runLater(new Runnable(){
			public void run(){
				time.setValue((player.getCurrentTime().toMillis()/player.getTotalDuration().toMillis()) * 100);
			}
		});
		
		
		
	}

}
