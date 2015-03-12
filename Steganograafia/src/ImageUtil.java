
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import java.io.IOException;
import java.io.File;

/**
 * @author Priit Danelson
 */
public class ImageUtil {
  /**
   * @return one dimensional array of given image. Reads in rows starting from
   *         top left corner and appends them all to single array with total
   *         size of image width*height
   */
  public static int[] convertToPixelArray(String fileName) throws IOException{
	BufferedImage image = ImageIO.read(new File(fileName));
    int width = image.getWidth();
    int height = image.getHeight();
    int[] result = new int[width * height];

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int indexToStore = (y * width) + x;
        result[indexToStore] = image.getRGB(x, y);
        
        
      }
    }

    return result;
  }
}
