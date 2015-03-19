
public class TekstKahendsysteemi {

	public static String messageKahendsysteemi(String s) {
		//Lisame alguse ja lõpu sümbolid
		s = "¤" + s + "~";
		
		//Teeme baidimassiivi
		byte[] baidid = s.getBytes();
		StringBuilder kahendkood = new StringBuilder();
		// Käime for-tsükliga üle iga baidi (tähe); iga baidi biti puhul kontrollime, kas see on seatud(1) või mitte(0) 
		// selleks kasutame AND-tehet arvuga 128 (kahendsüsteemis 10000000)
		// kui tehte tulemus on 1, siis on vasakpoolseim bitt seatud, vastasel juhul mitte.
		// edasi nihutame baidi bitte vasakule, saadud baidis on nüüd järgmine bitt kõige olulisem
		// nõnda saame teada, kas järgmine bitt on seatud ja nii edasi, kuni saamegi kokku panna
		// baidile vastava kahendkoodi
		for (byte b : baidid) {
			int v22rtus = b;
			
			for (int i = 0; i < 8; i++) {
				kahendkood.append((v22rtus & 128) == 0 ? 0 : 1);
				v22rtus <<= 1;
		     }
		     kahendkood.append(' ');
		  }
		  return kahendkood.toString();
	}
}
