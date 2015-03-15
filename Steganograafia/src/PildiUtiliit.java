import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class PildiUtiliit {

	// Meetod koostab pildi pikslitest kahem��tmelise massiivi
	public static int[][] piltPiksliteks(String failiNimi) throws IOException {
		File fail = new File(failiNimi);
		BufferedImage pilt = ImageIO.read(fail);
		int laius = pilt.getWidth();
		int korgus = pilt.getHeight();
		//Kahem��tmelise pikslite massiivi koostamine
		int[][] pikslid = new int[korgus][laius];
		for (int y = 0; y < korgus; y++) {
			int[] pikslirida = new int[laius];
			//Lisab igasse sisemisse massiivi �he pildirea pikslite RGB v��rtuse
			for (int x = 0; x < laius; x++) {
				pikslirida[x] = pilt.getRGB(x, y);
			}
			pikslid[y] = pikslirida;
		}
		return pikslid;
	}
	
	public static void salvestaPikslidPildina(int[][] pikslid, String failiNimi) throws IOException {
		int laius = pikslid[0].length;
		int korgus = pikslid.length;
		//Pikslite RGB v��rtustest pildi koostamine
		BufferedImage pilt = new BufferedImage(laius, korgus, BufferedImage.TYPE_INT_ARGB);
		for(int y=0; y<korgus; y++){
			for(int x=0; x<laius; x++){
				pilt.setRGB(x, y, pikslid[y][x]);
			}
		}
		//Uue pildifaili nimetamine ja salvestamine
		String lyhikeFailiNimi = failiNimi.substring(failiNimi.lastIndexOf("\\") + 1);
		String failiTee = failiNimi.substring(0, failiNimi.lastIndexOf("\\") + 1);
		String failiLaiend = lyhikeFailiNimi.substring(lyhikeFailiNimi.lastIndexOf(".") + 1);
		File tulemusFail = new File(failiTee + "kr�pteeritud_" + lyhikeFailiNimi);
		ImageIO.write(pilt, failiLaiend, tulemusFail);		
	}
}