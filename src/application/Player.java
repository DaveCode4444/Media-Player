package application;

/*
 * author - Devang Sawant
 * 
 */

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class Player extends BorderPane{  //your application needs to have Media, MediaPlayer and MediaView to create a media player application
	
	private Media media;
	private MediaPlayer mediaPlayer;
	private MediaView view;
	private Pane mpane;
	private MediaBar bar;
	
	public Player(String file){
		media = new Media(file);
		mediaPlayer = new MediaPlayer(media);
		view = new MediaView(mediaPlayer);
		mpane = new Pane();
		
		mpane.getChildren().add(view); //to add media player to the center
		setCenter(mpane);
		
		bar = new MediaBar(mediaPlayer);
		setBottom(bar);
		setStyle("-fx-background-color: #bfc2c8");
		
		mediaPlayer.play();
		
	}

	public MediaPlayer getMediaPlayer() {
		return mediaPlayer;
	}

	public void setMediaPlayer(MediaPlayer mediaPlayer) {
		this.mediaPlayer = mediaPlayer;
	}
	
	

}
