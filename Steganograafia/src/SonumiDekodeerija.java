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
	
	public void loeSonumPunasest(int[][] pikslid){
		
		int laius = pikslid[0].length;
		int pikkus = pikslid.length;

		StringBuilder kaheksaBitti;
		
		for(int y=0; y<pikkus; y++){
			for(int x=0; x<laius; x++){
				int jarjekord = 0;
				kaheksaBitti = new StringBuilder();
				//vaatab alates esimesest pikslist 8 järgnevat pikslit läbi
				while(jarjekord<8){
					//kui veerg tuleb suurem kui laius, siis läheb järgmisele reale
					int veerg = x + jarjekord >= laius ? x + jarjekord - laius : x + jarjekord;
					int rida = x + jarjekord >= laius ? y + 1 : y;
					//alustan esimese piksli punase värvi kontrollimist
					Color piksel = new Color(pikslid[rida][veerg]);
					int punaneVarv = piksel.getRed();
					String punaseBitid = Integer.toBinaryString(punaneVarv);
					char viimaneNumber = punaseBitid.charAt(punaseBitid.length()-1);
					//punase värvi viimase arvu lisamine stringi
					kaheksaBitti.append(viimaneNumber);
					jarjekord += 1;
				}
				String sone = kaheksaBitti.toString();
				//Stringist arvu tegemine ja kodeerimine märgiks
				int kood = Integer.parseInt(sone, 2);
				String sonum = new Character((char)kood).toString();
				
				System.out.println(sonum);
			}
		}
		
	}
}
