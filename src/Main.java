import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    // Instancing the dependencies
    static JFrame frame = new JFrame();
    static GraphicsHandler graphics = new GraphicsHandler();
    static KeyHandler key = new KeyHandler();
    static AudioHandler audio = new AudioHandler();
    static MouseHandler mouse = new MouseHandler();

    // Key variables
    public static int keypressed = -1;
    public static int keyreleased = -1;
    public static String keytyped = "none";

    // Screen variables
    public static Boolean FrameResizable;

    // Frame rate
    public static final int FRAME_RATE = 120;

    // Mouse Variables
    public static boolean mouse1Down;
    public static boolean mouse1Released;

    public static boolean mouse2Down;
    public static boolean mouse2Released;

    public static boolean mouse3Down;
    public static boolean mouse3Released;

    public static int mouseX;
    public static int mouseY;

    public static int scrollWheelRotation;


    // Main function
    public static void main(String[] args){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(graphics);
        frame.addKeyListener(key);
        frame.addMouseListener(mouse);
        frame.addMouseMotionListener(mouse);
        Image icon = Toolkit.getDefaultToolkit().getImage("res/img/icon.jpg");
        frame.setIconImage(icon);
        start();
        graphics.Begin();
        frame.setResizable(FrameResizable);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    static int screenWidth;
    static int screenHeight;

    static Camera gameCamera = new Camera(new D3(0, 200, 0), new D3(0, 0, 0), 60, 300, 1000);

    static D3Obj obj1 = new D3Obj(new Prism(500, 0, 1000, 10, 10, 10), 0, "fillOval", new Color(0, 0, 255));
    static D3Obj obj2 = new D3Obj(new Prism(500, 0, 2000, 10, 10, 10), 0, "fillRect", new Color(255, 0, 255));
    static D3Obj obj3 = new D3Obj(new Prism(-500, 0, 1000, 10, 10, 10), 0, "fillOval", new Color(255, 255, 0));
    static D3Obj obj4 = new D3Obj(new Prism(-500, 0, 2000, 10, 10, 10), 0, "fillOval", new Color(0, 255, 0));
    static D3Obj obj5 = new D3Obj(new Prism(500, 1000, 1000, 10, 10, 10), 0, "fillRect", new Color(255, 55, 0));
    static D3Obj obj6 = new D3Obj(new Prism(500, 1000, 2000, 10, 10, 10), 0, "fillRect", new Color(255, 0, 100));
    static D3Obj obj7 = new D3Obj(new Prism(-500, 1000, 1000, 10, 10, 10), 0, "fillOval", new Color(0, 255, 255));
    static D3Obj obj8 = new D3Obj(new Prism(-500, 1000, 2000, 10, 10, 10), 0, "img",new Color(150, 0, 200));

    static D3Line line1 = new D3Line(new D3(100, 50, 100), new D3(500, 100, 500), 10, new Color(0, 0, 0));

    static ArrayList<D3Obj> objects = new ArrayList<>();
    static ArrayList<D3Line> lines = new ArrayList<>();

    static void start() {

        //frame.setSize(940, 620);
        frame.setSize(480, 360);
        frame.setTitle("3D engine");
        FrameResizable = true;
        graphics.setBackground(new Color(255, 255, 255));
        //frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(false);
        screenWidth = frame.getWidth();
        screenHeight = frame.getHeight();

        key.registerKey(KeyEvent.VK_DOWN);
        key.registerKey(KeyEvent.VK_RIGHT);
        key.registerKey(KeyEvent.VK_LEFT);
        key.registerKey(KeyEvent.VK_UP);
        key.registerKey(KeyEvent.VK_D);
        key.registerKey(KeyEvent.VK_A);
        key.registerKey(KeyEvent.VK_W);
        key.registerKey(KeyEvent.VK_S);
        key.registerKey(KeyEvent.VK_O);
        key.registerKey(KeyEvent.VK_I);
        key.registerKey(KeyEvent.VK_Q);
        key.registerKey(KeyEvent.VK_E);
        key.registerKey(KeyEvent.VK_Z);
        key.registerKey(KeyEvent.VK_G);
        key.registerKey(KeyEvent.VK_F);

        objects.add(obj1);
        objects.add(obj2);
        objects.add(obj3);
        objects.add(obj4);
        objects.add(obj5);
        objects.add(obj6);
        objects.add(obj7);
        objects.add(obj8);

        updateObjects(objects.toArray(D3Obj[]::new), gameCamera);

        graphics.objects.add(obj1.rendered);
        graphics.objects.add(obj2.rendered);
        graphics.objects.add(obj3.rendered);
        graphics.objects.add(obj4.rendered);
        graphics.objects.add(obj5.rendered);
        graphics.objects.add(obj6.rendered);
        graphics.objects.add(obj7.rendered);
        graphics.objects.add(obj8.rendered);

        obj8.image = pathToBufferedImage("res/img/icon.jpg");

        lines.add(line1);

        updateLines(lines.toArray(D3Line[]::new), gameCamera);

        graphics.objects.add(line1.rendered);

    }

    static boolean mouseCam = false;

    static void update() {
        screenWidth = frame.getBounds().width;
        screenHeight = frame.getBounds().height;

        if (gameCamera.rotation.x < -90) {
            gameCamera.rotation.x = -90;
        } else if (gameCamera.rotation.x > 90) {
            gameCamera.rotation.x = 90;
        }

        if (key.keys.get(KeyEvent.VK_A)) {
            gameCamera.position.z += -5 * Math.sin(Math.toRadians(gameCamera.rotation.y));
            gameCamera.position.x += -5 * Math.cos(Math.toRadians(gameCamera.rotation.y));
        }
        if (key.keys.get(KeyEvent.VK_D)) {
            gameCamera.position.z += 5 * Math.sin(Math.toRadians(gameCamera.rotation.y));
            gameCamera.position.x += 5 * Math.cos(Math.toRadians(gameCamera.rotation.y));
        }
        if (key.keys.get(KeyEvent.VK_W)) {
            gameCamera.position.z += 5 * Math.sin(Math.toRadians(gameCamera.rotation.y + 90));
            gameCamera.position.x += 5 * Math.cos(Math.toRadians(gameCamera.rotation.y + 90));
        }
        if (key.keys.get(KeyEvent.VK_S)) {
            gameCamera.position.z += 5 * Math.sin(Math.toRadians(gameCamera.rotation.y - 90));
            gameCamera.position.x += 5 * Math.cos(Math.toRadians(gameCamera.rotation.y - 90));
        }
        if (key.keys.get(KeyEvent.VK_LEFT)) {
            gameCamera.rotation.y += 1;
        }
        if (key.keys.get(KeyEvent.VK_RIGHT)) {
            gameCamera.rotation.y -= 1;
        }
        if (key.keys.get(KeyEvent.VK_I)) {
            gameCamera.dts += 5;
        }
        if (key.keys.get(KeyEvent.VK_O)) {
            gameCamera.dts -= 5;
        }
        if (key.keys.get(KeyEvent.VK_UP)) {
            gameCamera.rotation.x += 1;
        }
        if (key.keys.get(KeyEvent.VK_DOWN)) {
            gameCamera.rotation.x -= 1;
        }
        if (key.keys.get(KeyEvent.VK_E)) {
            gameCamera.position.y += 10;
        }
        if (key.keys.get(KeyEvent.VK_Q)) {
            gameCamera.position.y -= 10;
        }
        if (key.keys.get(KeyEvent.VK_Z)) {
            obj1.bounds.y += 5;
        }
        if (key.keys.get(KeyEvent.VK_F)) {
            mouseCam = true;
        }
        if (key.keys.get(KeyEvent.VK_G)) {
            mouseCam = false;
        }

        if (mouseCam) {
            mouse.MoveCursor(new Point(frame.getX() + screenWidth / 2, frame.getY() + screenHeight / 2));
            gameCamera.rotation.y += screenWidth / 2 - mouseX;
            gameCamera.rotation.x += screenHeight / 2 - mouseY;
        }

        updateObjects(objects.toArray(D3Obj[]::new), gameCamera);
        updateLines(lines.toArray(D3Line[]::new), gameCamera);

    }

    static BufferedImage pathToBufferedImage(String path) {
        File file = new File(path);
        try {
            BufferedImage image = ImageIO.read(file);
            return image;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void updateObjects(D3Obj[] objects, Camera camera) {
        for (D3Obj obj : objects) {
            obj.rendered = buildProjectedObject(camera, obj);
        }
    }

    static void updateLines(D3Line[] lines, Camera camera) {
        for (D3Line line : lines) {
            line.rendered = buildProjectedLine(camera, line);
        }
    }

    static BeanObj buildProjectedLine(Camera camera, D3Line line) {
        BeanObj renderedLine = line.rendered;
        renderedLine.color = line.color;
        renderedLine.shape = "ln";
        renderedLine.rotationOffset = new Point(0, 0);
        renderedLine.rotation = 0;
        renderedLine.lineThickness = line.lineThickness;

        D3 startPosition = new D3(line.startPosition.x - camera.position.x, -line.startPosition.y - (-camera.position.y), line.startPosition.z - camera.position.z);
        D3 endPosition = new D3(line.endPosition.x - camera.position.x, -line.endPosition.y - (-camera.position.y), line.endPosition.z - camera.position.z);

        int[] projectedStart = projectPoint(camera, startPosition);
        int[] projectedEnd = projectPoint(camera, endPosition);

        renderedLine.zindex = projectedStart[2] - projectedEnd[2];
        renderedLine.bounds = new Rectangle(projectedStart[0], projectedStart[1], projectedEnd[0], projectedEnd[1]);


        return renderedLine;
    }

    static BeanObj buildProjectedObject(Camera camera, D3Obj object) {
        BeanObj renderedObject = object.rendered;
        renderedObject.color = object.color;
        renderedObject.image = object.image;
        renderedObject.string = object.string;
        renderedObject.font = object.font;
        renderedObject.shape = object.shape;
        renderedObject.rotationOffset = new Point(0, 0);
        renderedObject.rotation = 0;
        renderedObject.lineThickness = 0;

        D3 position = new D3(object.bounds.x - camera.position.x, -object.bounds.y - (-camera.position.y), object.bounds.z - camera.position.z);

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
            int distance = 100 * camera.dts / rotatedPositions.z;
            renderedObject.zindex = distance;
            if (distance > 0) {
                renderedObject.bounds = new Rectangle((rotatedPositions.x - distance / 2) * camera.dts / rotatedPositions.z, (rotatedPositions.y - distance / 2) * camera.dts / rotatedPositions.z, distance, distance);
                double fixedRotation = Math.atan((rotatedPositions.x - distance / 2) * camera.dts / rotatedPositions.z) / (camera.dts / (camXSin / camXCos) - ((rotatedPositions.y - distance / 2) * camera.dts / rotatedPositions.z));
                renderedObject.rotation = (int) fixedRotation;
            }
        } else {
            renderedObject.bounds = new Rectangle(position.x, position.y, 0, 0);
        }

        int screenWidthHalf = Main.screenWidth / 2;
        int screenHeightHalf = Main.screenHeight / 2;
        renderedObject.bounds.x += screenWidthHalf;
        renderedObject.bounds.y += screenHeightHalf;

        return renderedObject;
    }

    static int[] projectPoint(Camera camera, D3 position) {
        int[] projectedVariables = new int[4];
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
            int distance = 100 * camera.dts / rotatedPositions.z;
            projectedVariables[2] = distance;
            if (distance > 0) {
                projectedVariables[0] = (rotatedPositions.x - distance / 2) * camera.dts / rotatedPositions.z;
                projectedVariables[1] = (rotatedPositions.y - distance / 2) * camera.dts / rotatedPositions.z;
                double fixedRotation = Math.atan((rotatedPositions.x - distance / 2) * camera.dts / rotatedPositions.z) / (camera.dts / (camXSin / camXCos) - ((rotatedPositions.y - distance / 2) * camera.dts / rotatedPositions.z));
                projectedVariables[3] = (int) fixedRotation;
            }
        } else {
            projectedVariables[0] = position.x;
            projectedVariables[1] = position.y;
        }

        int screenWidthHalf = Main.screenWidth / 2;
        int screenHeightHalf = Main.screenHeight / 2;
        projectedVariables[0] += screenWidthHalf;
        projectedVariables[1] += screenHeightHalf;

        return projectedVariables;
    }


}
