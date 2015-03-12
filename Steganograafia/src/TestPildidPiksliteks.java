import java.io.File;
import java.io.IOException;


public class TestPildidPiksliteks {

	public static void main(String[] args) throws IOException {
		
		PiltPiksliteks fail1 = new PiltPiksliteks(new File("c:/oop/Steganograafia/src/pingviinid.png"));
		int[] list = fail1.piksliks();
		//System.out.println(fail1.piksliks());
		//int[] pikslid = ImageUtil.convertToPixelArray("c:/oop/Steganograafia/src/pingviinid.png");
		
		//Ei kontrolli praegu esimest listi
		//for(int e:list){
		//	System.out.println(e);
		//}
		System.out.println("Lõpp");
		
		//System.out.println(Integer.toBinaryString(1));
		
		PiltPiksliteks fail2 = new PiltPiksliteks(new File("c:/oop/Steganograafia/src/pingviinid.png"));
		String[] pList = fail2.pikselKahendsysteemiks(list);
		
		for(String e:pList){
			System.out.println(e);
		}
		System.out.println("Lõpp2");
	}

}
