import java.awt.*;
import java.awt.image.BufferedImage;

public class D3Obj {

    Prism bounds;
    Color color;
    BufferedImage image;
    String string;
    Font font;
    String shape;

    BeanObj rendered = new BeanObj();

    D3Obj(Prism bounds, String shape, Color color) {
        this.bounds = bounds;
        this.shape = shape;
        this.color = color;
    }

}
