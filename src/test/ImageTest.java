package test;

import com.th.tank.ResourceMgr;
import org.junit.Assert;
import org.junit.Test;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.junit.Assert.fail;

/**
 * @author TanHaooo
 * @date 2021/1/3 14:50
 */
public class ImageTest {

    @Test
    public void test() {
        // fail("Not yet implemented");
        try {
            BufferedImage image = ImageIO.read(new File(""));
            Assert.assertNotNull(image);
            BufferedImage image2=ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("images/tankL.gif"));
            Assert.assertNotNull(image2);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
