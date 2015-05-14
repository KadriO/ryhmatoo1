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

	public void erindiAken(String aknaNimi, String kuvatavTekst, boolean kasKodeerimine, Stage lava) {
		GridPane gp = new GridPane();
		gp.setPadding(new Insets(10, 20, 10, 20));
		Scene stseen = new Scene(gp, 400, 250);

		// teate kuvamine
		TextFlow teade = looTekstiväli(kuvatavTekst, 30, "bold", "center");
		teade.prefHeightProperty().bind(lava.heightProperty());
		teade.prefWidthProperty().bind(lava.widthProperty());
		gp.add(teade, 0, 0);

		// nupu loomine
		Button nupp = new Button("Tagasi");
		HBox hbox = new HBox();
		hbox.prefHeightProperty().bind(lava.heightProperty());
		hbox.prefWidthProperty().bind(lava.widthProperty());
		hbox.getChildren().add(nupp);
		hbox.setAlignment(Pos.CENTER);
		gp.add(hbox, 0, 1);

		lava.setMinHeight(250);
		lava.setMinWidth(400);

		gp.prefWidthProperty().bind(lava.widthProperty());
		gp.prefHeightProperty().bind(lava.heightProperty());

		lava.setTitle(aknaNimi);
		lava.setScene(stseen);
		lava.show();
		// nupu funktsioon
		nupp.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				lava.close();
				if(kasKodeerimine) kodeerimisAken();
				else dekodeerimisAken();
				
			}
		});
	}

	public TextFlow looTekstiväli(String tekstiväli, int kirjaSuurus, String kirjaPaksus, String joondus) {
		Text kiri = new Text(tekstiväli);
		if (kirjaPaksus.equalsIgnoreCase("bold"))
			kiri.setFont(Font.font("Helvetica", FontWeight.BOLD, kirjaSuurus));
		else if (kirjaPaksus.equalsIgnoreCase("normal"))
			kiri.setFont(Font.font("Helvetica", FontWeight.NORMAL, kirjaSuurus));
		TextFlow tekst = new TextFlow(kiri);
		if (joondus.equalsIgnoreCase("center"))
			tekst.setTextAlignment(TextAlignment.CENTER);
		else if (joondus.equalsIgnoreCase("justify"))
			tekst.setTextAlignment(TextAlignment.JUSTIFY);
		else if (joondus.equalsIgnoreCase("left"))
			tekst.setTextAlignment(TextAlignment.LEFT);
		return tekst;
	}

	@Override
	public void start(Stage peaLava) {
		BorderPane bp1 = new BorderPane();

		// Loon teise paani, mille panen esimese keskkohta, et võimaldada teksti
		// paigutust sinna. (Vastasel juhul nupud varjavad teksti ära)
		BorderPane bp2 = new BorderPane();
		bp1.setCenter(bp2);

		// Kolmanda paani loomine, kuhu sisestatakse kysimus ja raadionupud
		BorderPane bp3 = new BorderPane();
		bp2.setCenter(bp3);

		TextFlow pealkiri = looTekstiväli("Steganograafia", 30, "bold", "center");
		bp1.setTop(pealkiri);

		TextFlow tutvustavJutt = looTekstiväli(
				"Käesolev programm võimaldab sõnumi peitmist pilti ja sõnumi lugemist pildist. "
						+ "Kasutada saab pilte, mille laiendiks on .png, peitmine toimub ainult punasesse värvi "
						+ "ja täpitähti kasutada ei saa.", 15, "normal", "justify");
		tutvustavJutt.setPadding(new Insets(10, 10, 5, 10));
		bp2.setTop(tutvustavJutt);

		TextFlow kysimus = looTekstiväli("Kas soovid sõnumit peita (kodeerida) või pildist otsida (dekodeerida)?", 15,
				"normal", "justify");
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
		bp3.setCenter(vbox);

		bp1.prefHeightProperty().bind(peaLava.heightProperty());
		bp1.prefWidthProperty().bind(peaLava.widthProperty());
		bp2.prefHeightProperty().bind(bp1.heightProperty());
		bp2.prefWidthProperty().bind(bp1.widthProperty());
		bp3.prefHeightProperty().bind(bp2.heightProperty());
		bp3.prefWidthProperty().bind(bp2.widthProperty());
		vbox.prefHeightProperty().bind(peaLava.heightProperty());
		vbox.prefWidthProperty().bind(peaLava.widthProperty());

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
		Scene stseen = new Scene(bp1, 400, 300);
		peaLava.setMinHeight(300);
		peaLava.setMinWidth(400);
		peaLava.setTitle("Steganograafia");
		peaLava.setScene(stseen);
		peaLava.show();
	}
	// sõnumi kirjutamine ning pildi valimine
	public void kodeerimisAken() {
		GridPane gp = new GridPane();
		gp.setPadding(new Insets(30, 20, 0, 20));
		Stage lava = new Stage();

		TextFlow tutvustavTekst = looTekstiväli(
				"Kirjuta lahtrisse sõnum, mida soovid peita (täpitähti kasutamata) ja vali sobiv pilt (png laiendiga).",
				15, "normal", "justify");
		tutvustavTekst.setPadding(new Insets(10, 0, 15, 0));

		Label sõnumPeitmiseks = new Label("Kirjuta sõnum siia:");
		sõnumPeitmiseks.setFont(Font.font("Helvetica", FontWeight.NORMAL, 15));
		TextField tekst = new TextField(); // sõnumi sisestuse lahter
		HBox hbox = new HBox();
		sõnumPeitmiseks.setAlignment(Pos.CENTER_LEFT);
		sõnumPeitmiseks.setPadding(new Insets(0, 10, 0, 0));
		hbox.getChildren().add(sõnumPeitmiseks);
		tekst.setAlignment(Pos.CENTER_RIGHT);
		hbox.getChildren().add(tekst);
		hbox.setPadding(new Insets(15, 0, 20, 0));

		Button btPilt = new Button("Vali pilt");
		btPilt.setMinWidth(100);
		HBox hbox2 = new HBox();
		hbox2.setAlignment(Pos.CENTER);
		hbox2.getChildren().add(btPilt);

		gp.prefHeightProperty().bind(lava.heightProperty());
		gp.prefWidthProperty().bind(lava.widthProperty());
		hbox.prefWidthProperty().bind(lava.widthProperty());
		hbox.prefHeightProperty().bind(lava.heightProperty());
		hbox2.prefHeightProperty().bind(lava.heightProperty());
		hbox2.prefWidthProperty().bind(lava.widthProperty());

		gp.add(tutvustavTekst, 0, 0);
		gp.add(hbox, 0, 1);
		gp.add(hbox2, 0, 2);

		Scene stseen = new Scene(gp, 400, 300);
		lava.setMinHeight(300);
		lava.setMinWidth(400);
		lava.setTitle("Sõnumi peitmine");
		lava.setScene(stseen);
		lava.show();
		btPilt.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent e) {
				kodeerimisAken2(lava, tekst.getText());
			}
		});
	}
	// pildi valimine, pildi ja sõnumi kontroll ning sõnumi peitmine pilti
	public void kodeerimisAken2(Stage lava, String sonum) {
		FileChooser fc = new FileChooser();
		File file = fc.showOpenDialog(lava);
		Stage lava2 = new Stage();
		try {
			if (sonum.contains(Character.toString('õ')) || sonum.contains(Character.toString('ä'))
					|| sonum.contains(Character.toString('ö')) || sonum.contains(Character.toString('ü'))) {
				throw new TäpitäheErind("Sõnum sisaldas täpitähte!");
			}
			if (sonum.equals("")) {
				throw new TühjaSõnumiErind("Sisestatud sõnum oli tühi!");
			}
			if (!(file.getAbsolutePath().endsWith(".png"))) {
				throw new ValeFaililaiendErind("Valitud fail pole õige laiendiga!");
			}
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
						p1.salvestaPikslidPildina(sonumiPeitja.getPikslid(), file.getAbsolutePath());
					}
				} catch (IOException e) {
					e.printStackTrace();
				}

				BorderPane bp3 = new BorderPane();
				TextFlow jutt = looTekstiväli("Peitmine õnnestus!", 30, "bold", "center");
				jutt.setPadding(new Insets(10, 0, 30, 0));
				bp3.setTop(jutt);

				GridPane gp = new GridPane();
				bp3.setCenter(gp);

				TextFlow jutt2 = looTekstiväli("Fail asub aadressil: ", 15, "normal", "left");
				jutt2.setPadding(new Insets(30, 0, 10, 10));
				gp.add(jutt2, 0, 0);

				Button nupp = new Button("Algusesse");
				HBox hbox = new HBox();
				hbox.getChildren().add(nupp);
				hbox.setAlignment(Pos.CENTER_RIGHT);
				hbox.setPadding(new Insets(20, 10, 0, 0));
				gp.add(hbox, 0, 2);

				TextArea txt1 = new TextArea(p1.getKrypteeritudNimi(file));
				HBox hbox2 = new HBox();
				hbox2.getChildren().add(txt1);
				hbox2.setPadding(new Insets(0, 10, 0, 10));
				gp.add(hbox2, 0, 1);

				bp3.prefHeightProperty().bind(lava2.heightProperty());
				bp3.prefWidthProperty().bind(lava2.widthProperty());
				gp.prefHeightProperty().bind(lava2.heightProperty());
				gp.prefWidthProperty().bind(lava2.widthProperty());
				hbox.prefHeightProperty().bind(lava2.heightProperty());
				hbox.prefWidthProperty().bind(lava2.widthProperty());
				hbox2.prefHeightProperty().bind(lava2.heightProperty());
				hbox2.prefWidthProperty().bind(lava2.widthProperty());

				Scene stseen = new Scene(bp3, 500, 300);
				lava2.setTitle("Sõnumi peitmine");
				lava2.setScene(stseen);
				lava2.setMinHeight(300);
				lava2.setMinWidth(500);
				lava.close();
				lava2.show();

				// nupu "Algusesse" funktsioon
				nupp.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						lava2.close();
						Stage peaLava = new Stage();
						start(peaLava);
					}
				});
			}
		} catch (TäpitäheErind e) {
			lava.close();
			erindiAken("Täpitähe erind", e.getMessage(), true, lava);
		} catch (TühjaSõnumiErind e) {
			lava.close();
			erindiAken("Tühja sõnumi erind", e.getMessage(), true, lava);
		} catch (ValeFaililaiendErind e) {
			lava.close();
			erindiAken("Vale faililaiendi erind", e.getMessage(), true, lava);
		}
	}
	// pildist sõnumi murdmine
	public void dekodeerimisAken() {
		GridPane gp = new GridPane();
		gp.setPadding(new Insets(20, 40, 20, 40));
		Scene stseen = new Scene(gp, 400, 300);
		Stage lava = new Stage();

		gp.prefHeightProperty().bind(lava.heightProperty());
		gp.prefWidthProperty().bind(lava.widthProperty());

		TextFlow jutt = looTekstiväli("Vali enda arvutist png nimelaiendiga pilt, mille sõnumit soovid dekodeerida.",
				15, "normal", "justify");
		// akna suuruse muutumise sidumine
		jutt.prefHeightProperty().bind(lava.heightProperty());
		jutt.prefWidthProperty().bind(lava.widthProperty());
		gp.add(jutt, 0, 0);

		Button nupp = new Button("Algusesse");
		nupp.setAlignment(Pos.CENTER_RIGHT);

		Button btPilt = new Button("Vali pilt");
		btPilt.setAlignment(Pos.CENTER_LEFT);
		HBox hbox = new HBox();
		hbox.getChildren().add(btPilt);
		hbox.getChildren().add(nupp);
		// akna suuruse muutumise sidumine
		hbox.prefHeightProperty().bind(lava.heightProperty());
		hbox.prefWidthProperty().bind(lava.heightProperty());
		gp.add(hbox, 0, 1);

		Label peidetudSõnum = new Label("Peidetud sõnum oli:");
		peidetudSõnum.setFont(Font.font("Helvetica", FontWeight.NORMAL, 15));

		peidetudSõnum.prefHeightProperty().bind(lava.heightProperty());
		peidetudSõnum.prefWidthProperty().bind(lava.widthProperty());

		lava.setTitle("Sõnumi dekodeerimine");
		lava.setScene(stseen);
		lava.setMinWidth(400);
		lava.setMinHeight(300);
		lava.show();

		// nupu "Algusesse" funktsioon
		nupp.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				lava.close();
				Stage peaLava = new Stage();
				start(peaLava);
			}
		});

		btPilt.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent e) {
				FileChooser fc = new FileChooser();
				File file = fc.showOpenDialog(lava);
				if (file != null) {
					PildiUtiliit p1 = new PildiUtiliit(file.getAbsolutePath());
					int[][] piltPikslitena;
					try {
						if (!(file.getAbsolutePath().endsWith(".png"))) {
							throw new ValeFaililaiendErind("Valitud fail pole õige laiendiga!");
						}
						piltPikslitena = p1.piltPiksliteks(file.getAbsolutePath());
						SonumiDekodeerija sonumiDekodeerija = new SonumiDekodeerija(piltPikslitena);
						TextField txt = new TextField(sonumiDekodeerija.loeSonumPunasest(piltPikslitena));
						txt.prefHeightProperty().bind(lava.heightProperty());
						txt.prefWidthProperty().bind(lava.widthProperty());
						gp.add(peidetudSõnum, 0, 2);
						gp.add(txt, 0, 3);
					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (ValeFaililaiendErind e2) {
						lava.close();
						erindiAken("Vale faililaiendi erind", e2.getMessage(), false, lava);
					}
				}
				
			}
		});
	}

	public static void main(String[] args) {
		launch(args);
	}
}