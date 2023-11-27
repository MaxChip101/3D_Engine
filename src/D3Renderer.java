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

        double camYSin = -Math.sin(Math.toRadians(camera.rotation.y));
        double camYCos = -Math.cos(Math.toRadians(camera.rotation.y));
        double camXSin = -Math.sin(Math.toRadians(camera.rotation.x));
        double camXCos = -Math.cos(Math.toRadians(camera.rotation.x));
        double camZSin = -Math.sin(Math.toRadians(camera.rotation.z));
        double camZCos = -Math.cos(Math.toRadians(camera.rotation.z));

        double rotatedX = position.z * camYSin + position.x * camYCos;
        double rotatedZ = position.z * camYCos - position.x * camYSin;
        double tempY = position.y * camXCos - rotatedZ * camXSin;
        double finalRotatedY = tempY * camZCos - rotatedX * camZSin;
        double finalRotatedX = tempY * camZSin + rotatedX * camZCos;
        double finalRotatedZ = position.y * camXSin + rotatedZ * camXCos;


        D3 rotatedPositions = new D3((int) finalRotatedX, (int) finalRotatedY , (int) finalRotatedZ);

        if (rotatedPositions.z != 0) {
            float distance = 100 * camera.dts / rotatedPositions.z;
            renderedObject.zindex = (int) distance;
            if (distance > 0) {
                renderedObject.bounds = new Rectangle(((int) rotatedPositions.x / 2) * (int) camera.dts / (int) rotatedPositions.z, ((int) rotatedPositions.y / 2) * (int) camera.dts / (int) rotatedPositions.z, (int) distance, (int) distance);
                double fixedRotation = Math.atan(renderedObject.bounds.x / (camera.dts / (camXSin / camXCos)) - renderedObject.bounds.y);
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
