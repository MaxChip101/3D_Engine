public class Camera {

    D3 position;
    D3 rotation;
    float dts;
    int renderDist;

    Camera(D3 position, D3 rotation, float dts, int renderDist) {
        this.position = position;
        this.rotation = rotation;
        this.dts = dts;
        this.renderDist = renderDist;
    }



}
