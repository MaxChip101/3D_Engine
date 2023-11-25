import java.awt.*;

public class D3Renderer {

    static BeanObj renderObject(Camera camera, D3Obj object) {
        BeanObj renderedObject = object.rendered;
        renderedObject.color = object.color;
        renderedObject.image = object.image;
        renderedObject.string = object.string;
        renderedObject.font = object.font;
        renderedObject.shape = object.shape;
        renderedObject.rotationOffset = new Point(0, 0);
        renderedObject.rotation = 0;
        renderedObject.lineThickness = 0;

        D3 position = new D3 (object.position.x - camera.position.x, -object.position.y - (-camera.position.y), object.position.z - camera.position.z);

        double camYSin = Math.sin(Math.toRadians(camera.rotation.y));
        double camYCos = Math.cos(Math.toRadians(camera.rotation.y));
        double camXSin = Math.sin(Math.toRadians(camera.rotation.x));
        double camXCos = Math.cos(Math.toRadians(camera.rotation.x));

        double rotatedX = position.z * camYSin + position.x * camYCos;
        double rotatedZ = position.z * camYCos - position.x * camYSin;
        double rotatedY = rotatedZ * camXSin + position.y * camXCos;
        rotatedZ = rotatedZ * camXCos - position.y * camXSin;



        D3 rotatedPositions = new D3((int) rotatedX, (int) rotatedY , (int) rotatedZ);

        if (rotatedPositions.z != 0) {
            float distance = 100 * camera.dts / rotatedPositions.z;
            renderedObject.zindex = (int) distance;
            if (distance > 0) {
                renderedObject.bounds = new Rectangle(((int) rotatedPositions.x / 2) * (int) camera.dts / (int) rotatedPositions.z, ((int) rotatedPositions.y / 2) * (int) camera.dts / (int) rotatedPositions.z, (int) distance, (int) distance);
                double fixedRotation = Math.atan((rotatedPositions.x / 2) * camera.dts / rotatedPositions.z) / (camera.dts / (camXSin / camXCos) - ((rotatedPositions.y / 2) * camera.dts / rotatedPositions.z));
                renderedObject.rotation = (int) fixedRotation;
            }
        } else {
            renderedObject.bounds = new Rectangle((int) position.x, (int) position.y, 0, 0);
        }

        int screenWidthHalf = Main.screenWidth / 2;
        int screenHeightHalf = Main.screenHeight / 2;
        renderedObject.bounds.x += screenWidthHalf;
        renderedObject.bounds.y += screenHeightHalf;

        return renderedObject;
    }
}
