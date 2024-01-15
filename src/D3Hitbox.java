public class D3Hitbox {

    D3 position;
    D3 size;

    String name;

    D3Hitbox(D3 position, D3 size, String name) {
        this.position = position;
        this.size = size;
        this.name = name;
    }

    public boolean intersects(D3Hitbox hitbox) {

        if (position.x < hitbox.size.x && size.x > hitbox.position.x && // x
                position.y < hitbox.size.y && size.y > hitbox.position.y && // y
                    position.z < hitbox.size.z && size.z > hitbox.position.z)  { // z
            return true;
        } else {
            return false;
        }
    }

}
