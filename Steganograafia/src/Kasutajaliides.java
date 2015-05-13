import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Kasutajaliides extends Application {

	@Override
	public void start(Stage peaLava) {
		BorderPane bp1 = new BorderPane();
		// TextArea sissejuhatus = new TextArea("Sissejuhatus"); // teistsugune
		// tekst
		// bp1.setTop(sissejuhatus);

		// Loon teise paani, mille panen esimese keskkohta, et võimaldada teksti
		// paigutust sinna. (Vastasel juhul nupud varjavad teksti ära)
		BorderPane bp2 = new BorderPane();
		bp1.setCenter(bp2);
		
		//Kolmanda paani loomine, kuhu sisestatakse kysimus ja raadionupud
		BorderPane bp3 = new BorderPane();
		bp2.setCenter(bp3);

		// Sissejuhatava pealkirja loomine (eelmisega oli võimalik kasutajal
		// tekst ära kustutada)
		Text pealkiri = new Text("Sissejuhatus");//muuta
		pealkiri.setFont(Font.font("Helvetica", FontWeight.BOLD, 30));
		TextFlow jutt = new TextFlow(pealkiri);
		jutt.setTextAlignment(TextAlignment.CENTER);
		bp1.setTop(jutt);

		// Sissejuhatava teksti loomine
		Text tutvustavJutt = new Text("Käesolev programm võimaldab sõnumi peitmist pilti ja sõnumi lugemist pildist. "
				+ "Kasutada saab pilte, mille laiendiks on .png, peitmine toimub ainult punasesse värvi "
				+ "ja täpitähti kasutada ei saa.");
		tutvustavJutt.setFont(Font.font("Helvetica", FontWeight.NORMAL, 15));
		TextFlow tekst = new TextFlow(tutvustavJutt);
		tekst.setTextAlignment(TextAlignment.JUSTIFY);
		//Jätab teksti ümber tühja ala
		tekst.setPadding(new Insets(10, 10, 5, 10));
		bp2.setTop(tekst);
		
		//küsimuse tekst
		Text kysimuseTekst = new Text("Kas soovid sõnumit peita (kodeerida) või pildist otsida (dekodeerida)?");
		kysimuseTekst.setFont(Font.font("Helvetica", FontWeight.NORMAL, 15));
		TextFlow kysimus = new TextFlow(kysimuseTekst);
		kysimus.setTextAlignment(TextAlignment.JUSTIFY);
		kysimus.setPadding(new Insets(10, 10, 15, 10));
		bp3.setTop(kysimus);

		final ToggleGroup valikud = new ToggleGroup();
		RadioButton rb1 = new RadioButton("Kodeerimine");
		rb1.setFont(Font.font("Helvetica", FontWeight.NORMAL, 15));
		rb1.setToggleGroup(valikud);
		RadioButton rb2 = new RadioButton("Dekodeerimine");
		rb2.setFont(Font.font("Helvetica", FontWeight.NORMAL, 15));
		rb2.setToggleGroup(valikud);

		VBox vbox = new VBox();
		vbox.getChildren().add(rb1);
		vbox.getChildren().add(rb2);
		vbox.setPadding(new Insets(0, 10, 5, 10));
		
		//resizing ei tööta!!!
//		bp1.prefHeightProperty().bind(peaLava.heightProperty());
//		bp1.prefWidthProperty().bind(peaLava.widthProperty());
//		bp2.prefHeightProperty().bind(bp1.heightProperty());
//		bp2.prefWidthProperty().bind(bp1.widthProperty());
//		bp3.prefHeightProperty().bind(bp2.heightProperty());
//		bp3.prefWidthProperty().bind(bp2.widthProperty());
//		vbox.prefHeightProperty().bind(peaLava.heightProperty());
//		vbox.prefWidthProperty().bind(peaLava.widthProperty());

		valikud.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1) {

				RadioButton chk = (RadioButton) t1.getToggleGroup().getSelectedToggle();
				if (chk.getText().equals("Kodeerimine")) {
					peaLava.close();
					kodeerimisAken();
				} else {
					peaLava.close();
					dekodeerimisAken();
				}

			}
		});
		// Panen raadionupud teise paani alumisse ossa, et tekstid ära mahuksid
		bp3.setCenter(vbox);

		Scene stseen1 = new Scene(bp1, 400, 300);
		//akna minimaalsed mõõtmed
		peaLava.setMinHeight(350);
		peaLava.setMinWidth(450);
		peaLava.setTitle("Steganograafia");
		peaLava.setScene(stseen1);
		peaLava.show();

	}

	public void kodeerimisAken() {
		GridPane gp = new GridPane();
		gp.setPadding(new Insets(40, 40, 0, 40));
		gp.setHgap(5);
		gp.setVgap(5);
		//määran esimese veeru laiuse
		ColumnConstraints veerg = new ColumnConstraints();
		veerg.setPercentWidth(90);
		gp.getColumnConstraints().add(veerg);
		
		BorderPane bp1 = new BorderPane();
		
		//seletav tekst
		Text tutvustavTekst = new Text("Kirjuta lahtrisse sõnum, mida soovid peita. Täpitähti ei saa kasutada.");
		tutvustavTekst.setFont(Font.font("Helvetica", FontWeight.NORMAL, 15));
		TextFlow jutt = new TextFlow(tutvustavTekst);
		jutt.setTextAlignment(TextAlignment.JUSTIFY);
		jutt.setPadding(new Insets(10, 0, 15, 0));
		bp1.setCenter(jutt);
		
		Scene stseen2 = new Scene(gp, 400, 300);
		
		//resizing ei tööta!!
//		gp.prefHeightProperty().bind(stseen2.heightProperty());
//		gp.prefWidthProperty().bind(stseen2.widthProperty());

		Label labelike = new Label("Kirjuta sõnum siia:");
		labelike.setFont(Font.font("Helvetica", FontWeight.NORMAL, 15));

		//gp.setHalignment(labelike, HPos.RIGHT);
		TextField tekst = new TextField();
		
		//panen tekstilahtri ja sildi ühele vertikaalile
		HBox hbox = new HBox();
		
		//ei tööta!!!
//		hbox.prefWidthProperty().bind(stseen2.widthProperty());
//		hbox.prefHeightProperty().bind(stseen2.heightProperty());
		
		labelike.setAlignment(Pos.CENTER_LEFT);
		labelike.setMinWidth(140);
		//labelike.setPadding(new Insets(0, 10, 0, 0));
		hbox.getChildren().add(labelike);
		tekst.setAlignment(Pos.CENTER_RIGHT);
		tekst.setMinWidth(150);
		hbox.getChildren().add(tekst);
		System.out.println("sildi laius: "+labelike.getMinWidth());
		System.out.println("lahtri laius: "+tekst.getMinWidth());
		double hboxParemVasakJoondus = gp.getWidth()/2-labelike.getMinWidth()/2 - tekst.getMinWidth()/2-20;
		hbox.setPadding(new Insets(0, hboxParemVasakJoondus, 0, hboxParemVasakJoondus));
		
		Button btPilt = new Button("Vali pilt");
		btPilt.setMinWidth(100);
		HBox hbox2 = new HBox();
		hbox2.getChildren().add(btPilt);
		double nupuParemVasakJoondus = gp.getWidth()/2-btPilt.getMinWidth()/2-20;
		hbox2.setPadding(new Insets(10, nupuParemVasakJoondus, 0, nupuParemVasakJoondus));
		
		//ei tööta!!
//		hbox2.prefHeightProperty().bind(stseen2.heightProperty());
//		hbox2.prefWidthProperty().bind(stseen2.widthProperty());
		
//		gp.setMargin(btPilt, new Insets(10, 0, 0, 0));
		
		gp.add(bp1, 0, 0);
		gp.add(hbox, 0, 1);
		gp.add(hbox2, 0, 2);

		Stage secondStage = new Stage();
		//akna minimaalsed mõõtmed
		secondStage.setMinHeight(350);
		secondStage.setMinWidth(450);
		
		secondStage.setTitle("Sõnumi peitmine");
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
//				p1.salvestaPikslidPildina(sonumiPeitja.getPikslid(), file.getAbsolutePath());
				 
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
			Text tekst = new Text("Peitmine õnnestus!");
			tekst.setFont(Font.font("Helvetica", FontWeight.BOLD, 30));
			TextFlow jutt = new TextFlow(tekst);
			jutt.setTextAlignment(TextAlignment.CENTER);
			jutt.setPadding(new Insets(10, 0, 15, 0));
			bp3.setTop(jutt);
			
			GridPane gp = new GridPane();
			bp3.setCenter(gp);
			
			Text tekst2 = new Text("Fail asub aadressil: ");
			tekst2.setFont(Font.font("Helvetica", FontWeight.NORMAL, 15));
			TextFlow jutt2 = new TextFlow(tekst2);
			jutt2.setTextAlignment(TextAlignment.LEFT);
			jutt2.setPadding(new Insets(20, 0, 10, 0));
			//bp4.setTop(jutt2);
			gp.add(jutt2, 0, 0);
			
			Button nupp = new Button("Algusesse");
			//bp4.setRight(nupp);
			nupp.setPadding(new Insets(20, gp.getWidth()/2, 10, gp.getWidth()/2));
			gp.add(nupp, 0, 2);
			
			//TextArea txt = new TextArea("Peitmine õnnestus!");
			//bp3.setCenter(txt);
			TextArea txt1 = new TextArea(p1.getKrypteeritudNimi(file));
			txt1.setPrefHeight(25);
			txt1.setMaxHeight(25);
			txt1.setMinHeight(25);
			//bp4.setCenter(txt1);
			gp.add(txt1, 0, 1);

			Scene stseen3 = new Scene(bp3, 500, 300);
			Stage lava3 = new Stage();
			lava3.setTitle("Sõnumi peitmine");
			lava3.setScene(stseen3);
			//akna minimaalsed mõõtmed
			lava3.setMinHeight(300);
			lava3.setMinWidth(500);
			
			lava.close();
			lava3.show();
			
			//nupu "Algusesse" funktsioon
			nupp.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					lava3.close();
					Stage peaLava = new Stage();
					start(peaLava);
				}
				
			});
		}
	}

	public void dekodeerimisAken() {
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
						gpdeko.add(txt, 1, 3);
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
