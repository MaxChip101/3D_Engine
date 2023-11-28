import java.awt.*;

public class D3Renderer {

    static BeanObj renderObject(Camera camera, D3Obj object, int zindexBreak) {
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
        double tempY = position.y * camXCos - rotatedZ * camXSin; // Adjusted for Z-axis rotation
        double finalRotatedY = tempY * camZCos - rotatedX * camZSin; // Apply Z-axis rotation
        double finalRotatedX = tempY * camZSin + rotatedX * camZCos; // Apply Z-axis rotation
        double finalRotatedZ = position.y * camXSin + rotatedZ * camXCos; // Adjusted for Z-axis rotation


        D3 rotatedPositions = new D3((int) finalRotatedX, (int) finalRotatedY , (int) finalRotatedZ);

        float scaleFactor = 0.01f;

        if (rotatedPositions.z != 0) {
            float distance = 100 * camera.dts / rotatedPositions.z;
            renderedObject.zindex = (int) distance + zindexBreak;
            if (distance > 0 && Math.abs(distance) <= camera.renderDist) {
                renderedObject.bounds = new Rectangle((int) ((rotatedPositions.x / 2) * camera.dts / rotatedPositions.z), (int) ((rotatedPositions.y / 2) * camera.dts / rotatedPositions.z), (int) ((object.size.x * distance / 10000) / scaleFactor), (int) ((object.size.y * distance / 10000) / scaleFactor));
                double fixedRotation = Math.atan(renderedObject.bounds.x / (camera.dts / (camXSin / camXCos)) - renderedObject.bounds.y);
                renderedObject.rotation = (int) fixedRotation;
                if (renderedObject.font != null) {
                    renderedObject.font = new Font(object.font.getFontName(), object.font.getStyle(), (int) ((object.font.getSize() * Math.abs(distance) / 10000) / scaleFactor));
                }
            }
        } else {
            renderedObject.bounds = new Rectangle((int) position.x, (int) position.y, 0, 0);
        }

        int screenWidthHalf = Main.screenWidth / 2;
        int screenHeightHalf = Main.screenHeight / 2;
        renderedObject.bounds.x += screenWidthHalf - renderedObject.bounds.width / 2;
        renderedObject.bounds.y += screenHeightHalf - renderedObject.bounds.height / 2;

        return renderedObject;
    }
}
