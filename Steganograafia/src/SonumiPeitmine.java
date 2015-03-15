import java.io.IOException;


public class SonumiPeitmine {

	public static void main(String[] args) throws IOException {
		String failiNimi = "Pingviinid.png";
		int[][] piltPikslitena = PildiUtiliit.piltPiksliteks(failiNimi);
		SonumiPeitja sonumiPeitja = new SonumiPeitja(piltPikslitena, "pingviinid");
		sonumiPeitja.peidaSonumPunasesse();
		PildiUtiliit.salvestaPikslidPildina(sonumiPeitja.getPikslid(), failiNimi);
	}

}
