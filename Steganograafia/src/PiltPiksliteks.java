import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class PiltPiksliteks {

	private File fail;

	public PiltPiksliteks(File fail) {
		super();
		this.fail = fail;
	}

	public int[] piksliks() throws IOException {
		// Meetod teeb pildi piksliteks ja paneb need massiivi
		BufferedImage pilt = ImageIO.read(fail);
		int laius = pilt.getWidth();
		int pikkus = pilt.getHeight();
		int[] listPikslid = new int[pikkus * laius];
		// int[] pikslid = new int[pilt.getWidth()*pilt.getHeight()];
		for (int y = 0; y < pikkus; y++) {
			for (int x = 0; x < laius; x++) {
				int asukoht = (y * laius) + x;
				listPikslid[asukoht] = pilt.getRGB(x, y);
			}
		}
		return listPikslid;
	}

	// Meetod võtab pikslitega massiivi, muudab pikslite väärtuse kahendsüsteemi
	public String[] pikselKahendsysteemiks(int[] piksliteList) throws IOException {
		BufferedImage pilt = ImageIO.read(fail);
		int laius = pilt.getWidth();
		int pikkus = pilt.getHeight();
		// Ilmselt vaja kolm eelnevat rida ära kaotada, sest need korduvad
		// eelmises meetodis
		String[] listKahendsysteem = new String[pikkus * laius];
		for (int l = 0; l < piksliteList.length; l++) {
			// RGB arv värvideks
			Color piksel = new Color(piksliteList[l]);
			// Ühe piksli RGB väärtused
			int punaneVarv = piksel.getRed();
			String punaseBitid = Integer.toBinaryString(punaneVarv);
			while(punaseBitid.length()<8){
				punaseBitid = ("0"+punaseBitid);
			}
			//System.out.println(punaseBitid);
			//System.out.println(punaneVarv);
			
			int rohelineVarv = piksel.getGreen();
			String roheliseBitid = Integer.toBinaryString(rohelineVarv);
			while(roheliseBitid.length()<8){
				roheliseBitid = ("0"+roheliseBitid);
			}
			//System.out.println(roheliseBitid);
			//System.out.println(rohelineVarv);
			
			int sinineVarv = piksel.getBlue();
			String siniseBitid = Integer.toBinaryString(sinineVarv);
			while(siniseBitid.length()<8){
				siniseBitid = ("0"+siniseBitid);
			}
			//System.out.println(siniseBitid);
			
			// Lisan need massiivi
			String bitid = punaseBitid + roheliseBitid + siniseBitid;
			listKahendsysteem[l] = bitid;
		}
		return listKahendsysteem;
	}
}
