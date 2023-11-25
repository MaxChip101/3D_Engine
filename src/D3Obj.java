import java.awt.*;
import java.awt.image.BufferedImage;

public class D3Obj {

    D3 position;
    D3 size;
    float rotation;
    Color color;
    BufferedImage image;
    String string;
    Font font;
    String shape;

    BeanObj rendered = new BeanObj();

    D3Obj(D3 position, D3 size, float rotation, String shape, Color color) {
        this.position = position;
        this.size = size;
        this.rotation = rotation;
        this.shape = shape;
        this.color = color;
    }

}
