import java.awt.*;

public class D3Obj {

    Prism bounds;
    Color color;
    int zindex;
    BeanObj rendered = new BeanObj();

    D3Obj(Prism bounds, Color color) {
        this.bounds = bounds;
        this.color = color;
    }

}
