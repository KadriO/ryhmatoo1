
public class TekstKahendsysteemi {

	public static String messageKahendsysteemi(String s) {
		//teen stringi baidimassiiviks
		byte[] bytes = s.getBytes();
		StringBuilder binary = new StringBuilder();
		for (byte b : bytes) {
			int v22rtus = b;
			
			for (int i = 0; i < 8; i++) {
				binary.append((v22rtus & 128) == 0 ? 0 : 1);
				v22rtus <<= 1;
		     }
		     binary.append(' ');
		  }
		  return binary.toString();
	}
}
