//Liisi Torga ja Kadri Onemar

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class SonumiPeitmine {

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.println("Sisesta sõnum:");
			String sonum = sc.nextLine();
			System.out.println("Sisesta pildi asukoht:");
			String failiNimi = sc.nextLine();
			File fail = new File(failiNimi);
			
			if (fail.exists()) {
				PildiUtiliit p1 = new PildiUtiliit(failiNimi);
				
				int[][] piltPikslitena = p1.piltPiksliteks(failiNimi);
				SonumiPeitja sonumiPeitja = new SonumiPeitja(piltPikslitena,
						sonum);
				boolean onnestus = sonumiPeitja.peidaSonumPunasesse();
				if (onnestus) {
					System.out.println("Peidan sõnumi pilti.");
					p1.salvestaPikslidPildina(
							sonumiPeitja.getPikslid(), failiNimi);
				}
				sc.close();
				break;
			} else {
				System.out.println("Faili " + failiNimi + " ei eksisteeri.");
			}
		}
	}

}
