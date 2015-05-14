import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class PildiUtiliit {
	
	private String failiNimi;
	private int[][] pikslid;
	
	public PildiUtiliit(String failiNimi) {
		super();
		this.failiNimi = failiNimi;
	}
	public PildiUtiliit() {
	}

	public String getFailiNimi() {
		return failiNimi;
	}
	public void setFailiNimi(String failiNimi) {
		this.failiNimi = failiNimi;
	}
	public int[][] getPikslid() {
		return pikslid;
	}
	public void setPikslid(int[][] pikslid) {
		this.pikslid = pikslid;
	}

	// Meetod koostab pildi pikslitest kahemõõtmelise massiivi
	public int[][] piltPiksliteks(String failiNimi) throws IOException {
		File fail = new File(failiNimi);
		//juurdepääs pildi andmetele
		BufferedImage pilt = ImageIO.read(fail);
		int laius = pilt.getWidth();
		int korgus = pilt.getHeight();
		//Kahemõõtmelise pikslite massiivi koostamine
		int[][] pikslid = new int[korgus][laius];
		for (int y = 0; y < korgus; y++) {
			int[] pikslirida = new int[laius];
			//Lisab igasse sisemisse massiivi ühes pildireas asuvate pikslite RGB väärtuse
			for (int x = 0; x < laius; x++) {
				pikslirida[x] = pilt.getRGB(x, y);
			}
			pikslid[y] = pikslirida;
		}
		return pikslid;
	}
	
	//Pikslite pildina salvestamine
	public void salvestaPikslidPildina(int[][] pikslid, String failiNimi) throws IOException {
		int laius = pikslid[0].length;
		int korgus = pikslid.length;
		//Pikslite RGB väärtustest pildi koostamine kasutades piksli koordinaate ja RGB väärtust
		BufferedImage pilt = new BufferedImage(laius, korgus, BufferedImage.TYPE_INT_ARGB);
		for(int y=0; y<korgus; y++){
			for(int x=0; x<laius; x++){
				pilt.setRGB(x, y, pikslid[y][x]);
			} 
		}
		
		//Uue pildifaili nimetamine ja salvestamine
		File fail = new File(failiNimi);
		File tulemusFail = new File(getKrypteeritudNimi(fail));
		ImageIO.write(pilt, getFailiLaiend(fail), tulemusFail);
	}
	
	public String getFailiLaiend(File sisendFail) {
		String lyhikeFailiNimi = sisendFail.getName();
		String failiLaiend = lyhikeFailiNimi.substring(lyhikeFailiNimi.lastIndexOf(".") + 1);
		return failiLaiend;
		
	}
	public String getKrypteeritudNimi(File sisendFail) {
		String lyhikeFailiNimi = sisendFail.getName();
//		String failiLaiend = lyhikeFailiNimi.substring(lyhikeFailiNimi.lastIndexOf(".") + 1);
		File tulemusFail = new File(sisendFail.getParent(), "krypteeritud_" + lyhikeFailiNimi);
		return tulemusFail.getAbsolutePath();
		
		
	}
}
