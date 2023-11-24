public class Camera {

    D3 position;
    D3 rotation;
    int fov;
    int dts;
    int renderDist;
    int aspectRatio;

    Camera(D3 position, D3 rotation, int fov, int dts, int renderDist, int aspectRatio) {
        this.position = position;
        this.rotation = rotation;
        this.fov = fov;
        this.dts = dts;
        this.renderDist = renderDist;
        this.aspectRatio = aspectRatio;
    }



}
