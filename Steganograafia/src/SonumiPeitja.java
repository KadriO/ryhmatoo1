import java.awt.Color;

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

	public void peidaSonumPunasesse() {
		int laius = pikslid[0].length;
		int korgus = pikslid.length;
		
		String sonumKahendSysteemis = TekstKahendsysteemi.messageKahendsysteemi(sonum);
		
		if (laius * korgus / 8 < sonumKahendSysteemis.length()) {
			System.out.println("Sõnum on liiga pikk!");
		}

		String[] sonumiMassiiv = sonumKahendSysteemis.split(" ");

		for (int y = 0; y < sonumiMassiiv.length; y++) {
			for (int x = 0; x < 8; x++) {
				int rida = (y * 8 + x) / laius;
				int veerg = (y * 8 + x) % laius;

				Color piksel = new Color(pikslid[rida][veerg]);
				int punaneVarv = piksel.getRed();
				String punaseBitid = Integer.toBinaryString(punaneVarv);

				// Asendab stringis viimase elemendi
				punaseBitid = punaseBitid.substring(0, punaseBitid.length() - 1)
						+ sonumiMassiiv[y].charAt(x);
				int punaseUusVaartus = Integer.parseInt(punaseBitid, 2);
				int rohelineVarv = piksel.getGreen();
				int sinineVarv = piksel.getBlue();

				Color muudetudPiksel = new Color(punaseUusVaartus,
						rohelineVarv, sinineVarv);
				pikslid[rida][veerg] = muudetudPiksel.getRGB();
			}
		}
	}
}
