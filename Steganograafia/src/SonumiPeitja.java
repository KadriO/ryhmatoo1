import java.awt.Color;
import java.util.Random;

public class SonumiPeitja {

	private int[][] pikslid;
	private String sonum;

	public SonumiPeitja(int[][] pikslid, String sonum) {
		this.pikslid = pikslid;
		this.sonum = sonum;
	}

	public int[][] getPikslid() {
		return pikslid;
	}

	public void setPikslid(int[][] pikslid) {
		this.pikslid = pikslid;
	}

	public String getSonum() {
		return sonum;
	}

	public void setSonum(String sonum) {
		this.sonum = sonum;
	}

	// Peidab sõnumi pildi punase värvi kõige vähima kaaluga bittidesse(LSB)
	// Meetod tagastab true, kui sõnumi punasesse peitmine õnnestub
	// Meetod tagastab false, kui sõnum on liiga pikk
	public boolean peidaSonumPunasesse() {
		int laius = pikslid[0].length;
		int korgus = pikslid.length;
		
		String sonumKahendSysteemis = TekstKahendsysteemi.messageKahendsysteemi(sonum);
		
		if (laius * korgus / 8 < sonumKahendSysteemis.length()) {
			System.out.println("Sõnum on liiga pikk!");
			return false;
		}

		String[] sonumiMassiiv = sonumKahendSysteemis.split(" ");

		//Leiame juhusliku arvu, millest alates alustab sõnumi peitmist pikslitesse
		Random arv = new Random();
		int suvalineArv = arv.nextInt(21);
		
		for (int y = 0; y < sonumiMassiiv.length; y++) {
			for (int x = 0; x < 8; x++) {
				int rida = (y * 8 + x + suvalineArv) / laius;
				int veerg = (y * 8 + x + suvalineArv) % laius;

				Color piksel = new Color(pikslid[rida][veerg]);
				int punaneVarv = piksel.getRed();
				String punaseBitid = Integer.toBinaryString(punaneVarv);

				// Asendab stringis viimase elemendi käesoleva sõnumi bitiga
				punaseBitid = punaseBitid.substring(0, punaseBitid.length() - 1)
						+ sonumiMassiiv[y].charAt(x);
				int punaseUusVaartus = Integer.parseInt(punaseBitid, 2);
				int rohelineVarv = piksel.getGreen();
				int sinineVarv = piksel.getBlue();

				//Loob muudetud piksli ja paneb tagasi pikslite massiivi
				Color muudetudPiksel = new Color(punaseUusVaartus,
						rohelineVarv, sinineVarv);
				pikslid[rida][veerg] = muudetudPiksel.getRGB();
			}
		}
		return true;
	}
}
