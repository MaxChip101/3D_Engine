import java.awt.*;
import java.util.ArrayList;

public class HitboxHandler {

    public ArrayList<D3Obj> objects;

    HitboxHandler() {
        objects = new ArrayList<>();
    }

    public D3Obj CheckCollisions(D3Obj object) {
        for (int i = 0; i < objects.size(); i++) {
            if (objects.get(i) != object && object.hitbox.intersects(objects.get(i).hitbox)) {
                return objects.get(i);
            }
        }
        return object;
    }
}
