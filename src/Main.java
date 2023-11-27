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

    static D3Renderer renderer = new D3Renderer();

    static Camera gameCamera = new Camera(new D3(0, 200, 0), new D3(0, 0, 0), 100, 1000);

    static D3Obj obj1 = new D3Obj(new D3(500, 0, 1000), new D3(10, 10, 10), 0, "fillOval", new Color(0, 0, 255));
    static D3Obj obj2 = new D3Obj(new D3(500, 0, 2000), new D3(10, 10, 10), 0, "fillRect", new Color(255, 0, 255));
    static D3Obj obj3 = new D3Obj(new D3(-500, 0, 1000), new D3(10, 10, 10), 0, "fillOval", new Color(255, 255, 0));
    static D3Obj obj4 = new D3Obj(new D3(-500, 0, 2000), new D3(10, 10, 10), 0, "fillOval", new Color(0, 255, 0));
    static D3Obj obj5 = new D3Obj(new D3(500, 1000, 1000), new D3(10, 10, 10), 0, "fillRect", new Color(255, 55, 0));
    static D3Obj obj6 = new D3Obj(new D3(500, 1000, 2000), new D3(10, 10, 10), 0, "fillRect", new Color(255, 0, 100));
    static D3Obj obj7 = new D3Obj(new D3(-500, 1000, 1000), new D3(10, 10, 10), 0, "fillOval", new Color(0, 255, 255));
    static D3Obj obj8 = new D3Obj(new D3(-500, 1000, 2000), new D3(10, 10, 10), 0, "img",new Color(150, 0, 200));
    static D3Obj obj9 = new D3Obj(new D3(-500, 1000, 1500), new D3(10, 10, 10), 0, "fillRect" ,new Color(150, 50, 200));

    static ArrayList<D3Obj> objects = new ArrayList<>();

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

        addObject(obj1, objects, gameCamera);
        addObject(obj2, objects, gameCamera);
        addObject(obj3, objects, gameCamera);
        addObject(obj4, objects, gameCamera);
        addObject(obj5, objects, gameCamera);
        addObject(obj6, objects, gameCamera);
        addObject(obj7, objects, gameCamera);
        addObject(obj8, objects, gameCamera);
        addObject(obj9, objects, gameCamera);

        obj8.image = pathToBufferedImage("res/img/icon.jpg");

        updateObjects(objects.toArray(D3Obj[]::new), gameCamera);

    }

    static boolean mouseCam = false;
    static float mouseSensitivity = 0.1f;


    static void update() {
        screenWidth = frame.getBounds().width;
        screenHeight = frame.getBounds().height;

        gameCamera.dts = screenWidth + screenHeight - 500;

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
            gameCamera.rotation.z += 1;
        }
        if (key.keys.get(KeyEvent.VK_O)) {
            gameCamera.rotation.z -= 1;
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
            obj1.position.y += 1;
        }
        if (key.keys.get(KeyEvent.VK_F)) {
            mouseCam = true;
        }
        if (key.keys.get(KeyEvent.VK_G)) {
            mouseCam = false;
        }

        if (mouseCam) {
            mouse.MoveCursor(new Point(frame.getX() + screenWidth / 2, frame.getY() + screenHeight / 2));
            gameCamera.rotation.y += (screenWidth / 2 - mouseX) * mouseSensitivity;
            gameCamera.rotation.x += (screenHeight / 2 - mouseY) * mouseSensitivity;
        }

        updateObjects(objects.toArray(D3Obj[]::new), gameCamera);

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

    static void addObject(D3Obj object, ArrayList<D3Obj> objects, Camera camera) {
        objects.add(object);
        renderer.renderObject(camera, object);
        graphics.objects.add(object.rendered);
    }

    static void updateObjects(D3Obj[] objects, Camera camera) {
        for (D3Obj obj : objects) {
            obj.rendered = renderer.renderObject(camera, obj);
        }
    }

}
