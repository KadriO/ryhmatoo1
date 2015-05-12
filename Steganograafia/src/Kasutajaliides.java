import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Kasutajaliides extends Application {

	@Override
	public void start(Stage peaLava) {
		BorderPane bp1 = new BorderPane();
		TextArea sissejuhatus = new TextArea("Sissejuhatus"); // teistsugune tekst
		bp1.setTop(sissejuhatus);

		final ToggleGroup valikud = new ToggleGroup();
		RadioButton rb1 = new RadioButton("Kodeerimine");
		rb1.setToggleGroup(valikud);
		RadioButton rb2 = new RadioButton("Dekodeerimine");
		rb2.setToggleGroup(valikud);

		VBox vbox = new VBox();
		vbox.getChildren().add(rb1);
		vbox.getChildren().add(rb2);

		valikud.selectedToggleProperty().addListener(
				new ChangeListener<Toggle>() {
					@Override
					public void changed(ObservableValue<? extends Toggle> ov,
							Toggle t, Toggle t1) {

						RadioButton chk = (RadioButton) t1.getToggleGroup()
								.getSelectedToggle(); 
						if (chk.getText().equals("Kodeerimine")) {
							peaLava.close();
							kodeerimisAken();
						}else{
							peaLava.close();
							dekodeerimisAken();
						}

					}
				});
		bp1.setCenter(vbox);

		Scene stseen1 = new Scene(bp1, 300, 300);
		peaLava.setTitle("Steganograafia");
		peaLava.setScene(stseen1);
		peaLava.show();

	}

	public void kodeerimisAken() {
		GridPane gp = new GridPane();
		gp.setPadding(new Insets(40, 0, 0, 50));
		gp.setHgap(5);
		gp.setVgap(5);

		Scene stseen2 = new Scene(gp, 300, 150);

		Label labelike = new Label("Sõnum");

		gp.setHalignment(labelike, HPos.RIGHT);
		TextField tekst = new TextField();

		Button btPilt = new Button("Vali pilt");

		gp.setMargin(btPilt, new Insets(10, 0, 0, 0));

		gp.add(labelike, 0, 0);
		gp.add(tekst, 1, 0);
		gp.add(btPilt, 1, 2);

		Stage secondStage = new Stage();
		secondStage.setTitle("Second Stage");
		secondStage.setScene(stseen2);
		secondStage.show();
		btPilt.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent e) {
				onPiltValitud(secondStage, tekst.getText());
			}
		});
		

	}

	public void onPiltValitud(Stage lava, String sonum) {
		FileChooser fc = new FileChooser();
		File file = fc.showOpenDialog(lava);
		if (file != null) {
			PildiUtiliit p1 = new PildiUtiliit(file.getAbsolutePath());
			int[][] piltPikslitena;
			SonumiPeitja sonumiPeitja = null;
			try {
				piltPikslitena = p1.piltPiksliteks(file.getAbsolutePath());
				sonumiPeitja = new SonumiPeitja(piltPikslitena, sonum);
				boolean onnestus = sonumiPeitja.peidaSonumPunasesse();
				if (!onnestus) {
					Alert a = new Alert(AlertType.ERROR);
					a.setTitle("ALERT!");
					a.setContentText("Peitmine ebaõnnestus, sest sõnum oli liiga pikk.");
					a.showAndWait();
					return;
				
			} else {
				p1.salvestaPikslidPildina(sonumiPeitja.getPikslid(),
						file.getAbsolutePath());
				
				
			}
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			
			BorderPane bp3 = new BorderPane();
			TextArea txt = new TextArea("Peitmine õnnestus!");
			bp3.setCenter(txt);
			TextArea txt1 = new TextArea("Fail asub aadressil: " + p1.getKrypteeritudNimi(file));
			bp3.setBottom(txt1);
			
			
			Scene stseen3 = new Scene(bp3, 500, 500);
			Stage lava3 = new Stage();
			lava3.setTitle("Lõpp");
			lava3.setScene(stseen3);
			lava.close();
			lava3.show();
		}
	}

	public void dekodeerimisAken(){
		GridPane gpdeko = new GridPane();
		gpdeko.setPadding(new Insets(40, 0, 0, 50));
		gpdeko.setHgap(5);
		gpdeko.setVgap(5);

		Scene stseen2 = new Scene(gpdeko, 300, 150);

		Button btPilt = new Button("Vali pilt");

		gpdeko.setMargin(btPilt, new Insets(10, 0, 0, 0));

		gpdeko.add(btPilt, 1, 2);

		Stage secondStage = new Stage();
		secondStage.setTitle("Second Stage");
		secondStage.setScene(stseen2);
		secondStage.show();
		btPilt.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent e) {
				FileChooser fc = new FileChooser();
				File file = fc.showOpenDialog(secondStage);
				if (file != null) {
					PildiUtiliit p1 = new PildiUtiliit(file.getAbsolutePath());
					int[][] piltPikslitena;
					try {
						piltPikslitena = p1.piltPiksliteks(file.getAbsolutePath());
						SonumiDekodeerija sonumiDekodeerija = new SonumiDekodeerija(piltPikslitena);
						TextField txt = new TextField(sonumiDekodeerija.loeSonumPunasest(piltPikslitena));
						gpdeko.add(txt, 1,3);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					

				}
			}
		});
	}
	public static void main(String[] args) {
		launch(args);
	}
}
