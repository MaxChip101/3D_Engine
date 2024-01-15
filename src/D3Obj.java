import java.awt.*;
import java.awt.image.BufferedImage;

public class D3Obj {

    int scene;
    D3 position;
    Point size;
    D3Hitbox hitbox;
    float rotation;
    Color color;
    float lineThickness;
    BufferedImage image;
    String string;
    Font font;
    String shape;

    BeanObj rendered = new BeanObj();

    D3Obj(D3 position, Point size, float rotation, float lineThickness, String shape, Color color, int scene) {
        this.position = position;
        this.size = size;
        this.rotation = rotation;
        this.lineThickness = lineThickness;
        this.shape = shape;
        this.color = color;
        this.scene = scene;
    }

}
