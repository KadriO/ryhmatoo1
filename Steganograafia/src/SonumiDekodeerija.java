import java.awt.Color;

public class SonumiDekodeerija {

	private int[][] pikslid;

	public SonumiDekodeerija(int[][] pikslid) {
		super();
		this.pikslid = pikslid;
	}

	public int[][] getPikslid() {
		return pikslid;
	}

	public void setPikslid(int[][] pikslid) {
		this.pikslid = pikslid;
	}

	public StringBuilder bittideLugemine(int xKoordinaat, int yKoordinaat) {

		int laius = pikslid[0].length;

		StringBuilder kaheksaBitti;

		int jarjekord = 0;
		kaheksaBitti = new StringBuilder();
		// vaatab alates esimesest pikslist 8 järgnevat pikslit läbi
		while (jarjekord < 8) {
			// kui veerg tuleb suurem kui laius, siis läheb järgmisele reale
			int veerg = xKoordinaat + jarjekord >= laius ? xKoordinaat + jarjekord - laius : xKoordinaat + jarjekord;
			int rida = xKoordinaat + jarjekord >= laius ? yKoordinaat + 1 : yKoordinaat;
			// alustan esimese piksli punase värvi kontrollimist
			Color piksel = new Color(pikslid[rida][veerg]);
			int punaneVarv = piksel.getRed();
			String punaseBitid = Integer.toBinaryString(punaneVarv);
			char viimaneNumber = punaseBitid.charAt(punaseBitid.length() - 1);
			// punase värvi viimase arvu lisamine stringi
			kaheksaBitti.append(viimaneNumber);
			jarjekord += 1;
		}
		return kaheksaBitti;
	}

	public String bitidSymboliks(StringBuilder kaheksaBitti) {
		String symbol = kaheksaBitti.toString();
		// Stringist arvu tegemine ja kodeerimine märgiks
		int kood = Integer.parseInt(symbol, 2);
		String sonum = new Character((char) kood).toString();
		return sonum;
	}

	public String loeSonumPunasest(int[][] pikslid) {

		int laius = pikslid[0].length;
		int pikkus = pikslid.length;

		for (int y = 0; y < pikkus; y++) {
			for (int x = 0; x < laius; x++) {
				StringBuilder kaheksaBitti = bittideLugemine(x, y);
				String symbol = bitidSymboliks(kaheksaBitti);

				if (symbol.equals("~")) {
					kaheksaBitti = bittideLugemine(x + 8, y);
					String symbol2 = bitidSymboliks(kaheksaBitti);
					// Prindib kaks esimest sümbolit
					// System.out.print(symbol);
					// System.out.println(symbol2);

					if (symbol2.equals("~")) { // Sõnumi algus
						// sõnumi mahutamise jaoks
						StringBuilder sonum = new StringBuilder();

						for (int a = y; a < pikkus; a++) {
							for (int b = x + 16; b < laius; b += 8) {
								kaheksaBitti = bittideLugemine(b, a);
								String symbol3 = bitidSymboliks(kaheksaBitti);

								// System.out.println(symbol3);

								if (symbol3.equals("~") && sonum.charAt(sonum.length() - 1) == '~')
									//eelmadab viimase sümboli ja kuvab sõnumi
									return sonum.substring(0, sonum.length()-1);
								else
									sonum.append(symbol3);
							}

						}

					}
				}
			}
		}
		return null;
	}
}
