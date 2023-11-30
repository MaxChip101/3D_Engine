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
            if (distance > 0) {
                renderedObject.bounds = new Rectangle((int) ((rotatedPositions.x / 2) * camera.dts / rotatedPositions.z), (int) ((rotatedPositions.y / 2) * camera.dts / rotatedPositions.z), (int) ((object.size.x * distance / 10000) / scaleFactor), (int) ((object.size.y * distance / 10000) / scaleFactor));
                double fixedRotation = Math.atan(renderedObject.bounds.x / (camera.dts / (camXSin / camXCos)) - renderedObject.bounds.y);
                renderedObject.rotation = (int) (fixedRotation + object.rotation);
                renderedObject.lineThickness = (int) ((object.lineThickness * distance / 10000) / scaleFactor);
                if (renderedObject.font != null) {
                    renderedObject.font = new Font(object.font.getFontName(), object.font.getStyle(), (int) ((object.font.getSize() * Math.abs(distance) / 10000) / scaleFactor));
                }
            }
        } else {
            renderedObject.bounds = new Rectangle(0, 0, 0, 0);
        }

        int screenWidthHalf = Main.screenWidth / 2;
        int screenHeightHalf = Main.screenHeight / 2;
        renderedObject.bounds.x += screenWidthHalf - renderedObject.bounds.width / 2;
        renderedObject.bounds.y += screenHeightHalf - renderedObject.bounds.height / 2;

        return renderedObject;
    }

    public BeanObj renderLine(Camera camera, D3Line line, int zindexBreak) {
        BeanObj renderedLine = line.rendered;
        renderedLine.color = line.color;
        renderedLine.shape = "ln";
        renderedLine.rotationOffset = new Point(0, 0);
        renderedLine.rotation = 0;
        renderedLine.lineThickness = 0;

        D3 position1 = new D3 (line.position1.x - camera.position.x, -line.position1.y - (-camera.position.y), line.position1.z - camera.position.z);
        D3 position2 = new D3 (line.position2.x - camera.position.x, -line.position2.y - (-camera.position.y), line.position2.z - camera.position.z);

        double camYSin = -Math.sin(Math.toRadians(camera.rotation.y));
        double camYCos = -Math.cos(Math.toRadians(camera.rotation.y));
        double camXSin = -Math.sin(Math.toRadians(camera.rotation.x));
        double camXCos = -Math.cos(Math.toRadians(camera.rotation.x));
        double camZSin = -Math.sin(Math.toRadians(camera.rotation.z));
        double camZCos = -Math.cos(Math.toRadians(camera.rotation.z));

        double rotatedX1 = position1.z * camYSin + position1.x * camYCos;
        double rotatedZ1 = position1.z * camYCos - position1.x * camYSin;
        double tempY1 = position1.y * camXCos - rotatedZ1 * camXSin;
        double finalRotatedY1 = tempY1 * camZCos - rotatedX1 * camZSin;
        double finalRotatedX1 = tempY1 * camZSin + rotatedX1 * camZCos;
        double finalRotatedZ1 = position1.y * camXSin + rotatedZ1 * camXCos;

        double rotatedX2 = position2.z * camYSin + position2.x * camYCos;
        double rotatedZ2 = position2.z * camYCos - position2.x * camYSin;
        double tempY2 = position2.y * camXCos - rotatedZ2 * camXSin;
        double finalRotatedY2 = tempY2 * camZCos - rotatedX2 * camZSin;
        double finalRotatedX2 = tempY2 * camZSin + rotatedX2 * camZCos;
        double finalRotatedZ2 = position2.y * camXSin + rotatedZ2 * camXCos;


        D3 rotatedPositions1 = new D3((int) finalRotatedX1, (int) finalRotatedY1, (int) finalRotatedZ1);
        D3 rotatedPositions2 = new D3((int) finalRotatedX2, (int) finalRotatedY2, (int) finalRotatedZ2);

        float scaleFactor = 0.01f;



        if (rotatedPositions1.z != 0 || rotatedPositions2.z != 0) {
            float distance1 = 100 * camera.dts / rotatedPositions1.z;
            float distance2 = 100 * camera.dts / rotatedPositions2.z;
            renderedLine.zindex = (int) (((distance1 + distance2) / 2) + zindexBreak);
            if (distance1 > 0 && distance2 > 0) {
                renderedLine.bounds = new Rectangle((int) ((rotatedPositions1.x / 2) * camera.dts / rotatedPositions1.z), (int) ((rotatedPositions1.y / 2) * camera.dts / rotatedPositions1.z), (int) ((rotatedPositions2.x / 2) * camera.dts / rotatedPositions2.z), (int) ((rotatedPositions2.y / 2) * camera.dts / rotatedPositions2.z));
                if (distance1 + distance2 / 2 > 1.0f) {
                    renderedLine.lineThickness = (int) Math.max((line.lineThickness * ((distance1 + distance2) / 2) / 10000) / scaleFactor, 0.1f);
                } else {
                    renderedLine.lineThickness = 0;
                }
            }
        } else {
            renderedLine.lineThickness = 0;
            renderedLine.bounds = new Rectangle(0, 0, 0, 0);
        }

        int screenWidthHalf = Main.screenWidth / 2;
        int screenHeightHalf = Main.screenHeight / 2;
        renderedLine.bounds.x += screenWidthHalf;
        renderedLine.bounds.y += screenHeightHalf;
        renderedLine.bounds.width += screenWidthHalf;
        renderedLine.bounds.height += screenHeightHalf;

        return renderedLine;
    }

}
