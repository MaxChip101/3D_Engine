import java.awt.*;

public class D3Line {

    int scene;
    D3 position1;
    D3 position2;
    D3Hitbox hitbox;
    Color color;
    float lineThickness;
    BeanObj rendered = new BeanObj();

    D3Line(D3 position1, D3 position2, float lineThickness, Color color, int scene) {
        this.position1 = position1;
        this.position2 = position2;
        this.lineThickness = lineThickness;
        this.color = color;
        this.scene = scene;
    }

}
