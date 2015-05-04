import java.io.IOException;
import java.util.Scanner;

public class SonumiDekodeerimine {

	public static void main(String[] args) throws IOException {

		Scanner sc = new Scanner(System.in);

		System.out.println("Sisesta faili nimi: ");
		String failiNimi = sc.nextLine();

		PildiUtiliit p1 = new PildiUtiliit(failiNimi);
		int[][] piltPikslitena = p1.piltPiksliteks(failiNimi);
		SonumiDekodeerija sonumiDekodeerija = new SonumiDekodeerija(piltPikslitena);
		System.out.println(sonumiDekodeerija.loeSonumPunasest(piltPikslitena));
		sc.close();

	}
}