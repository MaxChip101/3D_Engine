import java.awt.*;

public class D3Line {
    D3 startPosition;
    D3 endPosition;
    int lineThickness;
    Color color;

    BeanObj rendered = new BeanObj();

    D3Line(D3 startPosition, D3 endPosition, int lineThickness, Color color) {
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.lineThickness = lineThickness;
        this.color = color;
    }

}
