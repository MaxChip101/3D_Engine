import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
    public static final int FRAME_RATE = 60;

    // Mouse Variables

    public static boolean mouse1Down;
    public static boolean mouse1Released;

    public static boolean mouse2Down;
    public static boolean mouse2Released;

    public static boolean mouse3Down;
    public static boolean mouse3Released;

    public static int mouseX;
    public static int mouseY;

    public static double scrollWheelRotation;

    // Sky Variables

    public static int skyZindex = 100; // default z index but with more objects this number will have to increase
    public static BufferedImage skyImage = BeanTools.loadImage("res/img/sky.jpg");
    public static int skyX = 0; // where the left of the sky starts and on right is visible
    public static float skyOffset = 2.565f; // adjust according to your sky image size
    public static float skyMovementOffset = 110; // adjust if you want but found this was the best value
    public static float skyWidthOffset = 6; // changes the width of the sky according to the screen size
    public static float skyHeightOffset = 6; // changes the width of the sky according to the screen size

    // Base Variables

    public static boolean baseEnabled = true; // if you want your plane to have a floor hitbox enabled
    public static float baseY = 0; // sets the y value of the base
    public static float baseFriction = 1.23f; // sets the friction of the base
    public static float baseBounciness = 0; // sets the bounciness of the base



    // Main function
    public static void main(String[] args){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(graphics);
        frame.addKeyListener(key);
        frame.addMouseListener(mouse);
        frame.addMouseMotionListener(mouse);
        frame.addMouseWheelListener(mouse);
        frame.setCursor(mouse.cursor);
        Image icon = BeanTools.loadImage("res/img/icon.jpg");
        frame.setIconImage(icon);
        start();
        graphics.Begin();
        frame.setResizable(FrameResizable);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    static int screenWidth;
    static int screenHeight;

    static Camera gameCamera = new Camera(new D3(0, 0, 0), new D3(0, 0, 0), 100);

    static D3Obj obj1 = new D3Obj(new D3(500, 0, 1000), new Point(100, 100), 0, 0, "fillOval", new Color(0, 0, 255), 0);
    static D3Obj obj2 = new D3Obj(new D3(500, 0, 2000), new Point(100, 100), 0, 0, "fillRect", new Color(255, 0, 255), 0);
    static D3Obj obj3 = new D3Obj(new D3(-500, 0, 1000), new Point(100, 100), 0, 0,"fillOval", new Color(255, 255, 0), 0);
    static D3Obj obj4 = new D3Obj(new D3(-500, 0, 2000), new Point(100, 100), 0, 0,"fillOval", new Color(0, 255, 0), 0);
    static D3Obj obj5 = new D3Obj(new D3(500, 1000, 1000), new Point(100, 100), 0, 10,"rect", new Color(255, 55, 0), 0);
    static D3Obj obj6 = new D3Obj(new D3(500, 1000, 2000), new Point(1000, 100), 0, 0,"fillRect", new Color(255, 0, 100), 0);
    static D3Obj obj7 = new D3Obj(new D3(-500, 1000, 1000), new Point(100, 100), 0, 0,"str",  new Color(0, 255, 255), 0);
    static D3Obj obj8 = new D3Obj(new D3(-500, 1000, 2000), new Point(100, 100), 0, 0,"img", new Color(150, 0, 200), 0);
    static D3Obj obj9 = new D3Obj(new D3(-500, 1000, 1500), new Point(100, 100), 0, 0,"fillRect", new Color(150, 50, 200), 0);

    static D3Line line1 = new D3Line(new D3(0, 50, 1000), new D3(0, 50, 2000), 10, new Color(0, 0, 0), 0);

    static D3Line wireframe1 = new D3Line(new D3(4000, 100, 5000), new D3(4000, 100, 4000), 10, new Color(0, 0, 0), 0);
    static D3Line wireframe2 = new D3Line(new D3(4000, 100, 5000), new D3(5000, 100, 5000), 10, new Color(0, 0, 0), 0);
    static D3Line wireframe3 = new D3Line(new D3(5000, 100, 5000), new D3(5000, 100, 4000), 10, new Color(0, 0, 0), 0);
    static D3Line wireframe4 = new D3Line(new D3(5000, 100, 4000), new D3(4000, 100, 4000), 10, new Color(0, 0, 0), 0);

    static D3Line wireframe5 = new D3Line(new D3(4000, 100, 5000), new D3(4000, 1100, 5000), 10, new Color(0, 0, 0), 0);
    static D3Line wireframe6 = new D3Line(new D3(4000, 100, 4000), new D3(4000, 1100, 4000), 10, new Color(0, 0, 0), 0);
    static D3Line wireframe7 = new D3Line(new D3(5000, 100, 5000), new D3(5000, 1100, 5000), 10, new Color(0, 0, 0), 0);
    static D3Line wireframe8 = new D3Line(new D3(5000, 100, 4000), new D3(5000, 1100, 4000), 10, new Color(0, 0, 0), 0);

    static D3Line wireframe9 = new D3Line(new D3(4000, 1100, 5000), new D3(4000, 1100, 4000), 10, new Color(0, 0, 0), 0);
    static D3Line wireframe10 = new D3Line(new D3(4000, 1100, 5000), new D3(5000, 1100, 5000), 10, new Color(0, 0, 0), 0);
    static D3Line wireframe11 = new D3Line(new D3(5000, 1100, 5000), new D3(5000, 1100, 4000), 10, new Color(0, 0, 0), 0);
    static D3Line wireframe12 = new D3Line(new D3(5000, 1100, 4000), new D3(4000, 1100, 4000), 10, new Color(0, 0, 0), 0);

    static D3Obj mirroredObj = new D3Obj(new D3(0, 1000, 2000), new Point(100, 100), 0, 0,"fillRect", new Color(150, 0, 200), 0);

    static BeanObj sky = new BeanObj();

    static ArrayList<D3Obj> objects = new ArrayList<>();
    static ArrayList<D3Line> lines = new ArrayList<>();

    static void start() {

        //frame.setSize(940, 620);
        frame.setSize(480, 360);
        frame.setTitle("3D engine");
        FrameResizable = true;
        graphics.setBackground(new Color(255, 255, 255));
        //frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //frame.setUndecorated(true);
        screenWidth = frame.getWidth();
        screenHeight = frame.getHeight();

        graphics.currentScene = 0;

        // updating the camera
        D3Renderer.updateCamera(gameCamera);

        // sky instancing
        sky.shape = "img";
        sky.zindex = skyZindex;
        sky.image = BeanTools.loadImage("res/img/sky.jpg");
        graphics.objects.add(sky);

        // mouse instancing
        mouse.setCursor("res/img/cursor.png", new Point(0, 0), "Bean_Cursor");
        frame.setCursor(mouse.cursor);


        // keys
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
        key.registerKey(KeyEvent.VK_SPACE);
        key.registerKey(KeyEvent.VK_SHIFT);
        key.registerKey(KeyEvent.VK_ESCAPE);
        key.registerKey(KeyEvent.VK_F11);

        addObject(mirroredObj, objects);

        addObject(obj1, objects);
        addObject(obj2, objects);
        addObject(obj3, objects);
        addObject(obj4, objects);
        addObject(obj5, objects);
        addObject(obj6, objects);
        addObject(obj7, objects);
        addObject(obj8, objects);
        addObject(obj9, objects);

        addLine(line1, lines);

        addLine(wireframe1, lines);
        addLine(wireframe2, lines);
        addLine(wireframe3, lines);
        addLine(wireframe4, lines);
        addLine(wireframe5, lines);
        addLine(wireframe6, lines);
        addLine(wireframe7, lines);
        addLine(wireframe8, lines);
        addLine(wireframe9, lines);
        addLine(wireframe10, lines);
        addLine(wireframe11, lines);
        addLine(wireframe12, lines);

        // object properties
        obj7.string = "Coming, who's coming, if you're coming right now let me see it, show me, huh he, he who is coming.";

        obj8.image = BeanTools.loadImage("res/img/icon.jpg");



        updateObjects(objects.toArray(D3Obj[]::new));

    }
    static float mouseSensitivity = 0.1f;

    static int speed = 10;

    static boolean mouseLocked = false;

    static boolean escPressed = false;

    static boolean fullscreen = false;
    static boolean f11Pressed = false;

    static int lockedMouseX;
    static int lockedMouseY;

    static void update() {

        screenWidth = frame.getBounds().width;
        screenHeight = frame.getBounds().height;

        gameCamera.dts = screenWidth + screenHeight - 200;

        if (key.keys.get(KeyEvent.VK_A)) {
            gameCamera.position.z += (float) (-speed * Math.sin(Math.toRadians(gameCamera.rotation.y)));
            gameCamera.position.x += (float) (-speed * Math.cos(Math.toRadians(gameCamera.rotation.y)));
        }
        if (key.keys.get(KeyEvent.VK_D)) {
            gameCamera.position.z += (float) (speed * Math.sin(Math.toRadians(gameCamera.rotation.y)));
            gameCamera.position.x += (float) (speed * Math.cos(Math.toRadians(gameCamera.rotation.y)));
        }
        if (key.keys.get(KeyEvent.VK_W)) {
            gameCamera.position.z += (float) (speed * Math.sin(Math.toRadians(gameCamera.rotation.y + 90)));
            gameCamera.position.x += (float) (speed * Math.cos(Math.toRadians(gameCamera.rotation.y + 90)));
        }
        if (key.keys.get(KeyEvent.VK_S)) {
            gameCamera.position.z += (float) (speed * Math.sin(Math.toRadians(gameCamera.rotation.y - 90)));
            gameCamera.position.x += (float) (speed * Math.cos(Math.toRadians(gameCamera.rotation.y - 90)));
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
        if (key.keys.get(KeyEvent.VK_SPACE)) {
            gameCamera.position.y += speed;
        }
        if (key.keys.get(KeyEvent.VK_Q)) {
            gameCamera.position.y -= speed;
        }
        if (key.keys.get(KeyEvent.VK_Z)) {
            obj1.position.y += 0.1;
        }

        if (key.keys.get(KeyEvent.VK_SHIFT)) {
            speed = 100;
        } else {
            speed = 10;
        }

        if (key.keys.get(KeyEvent.VK_ESCAPE) && !escPressed) {
            escPressed = true;
            mouseLocked = !mouseLocked;
        } else if (!key.keys.get(KeyEvent.VK_ESCAPE)) {
            escPressed = false;
        }



        if (key.keys.get(KeyEvent.VK_F11) && !f11Pressed) {
            f11Pressed = true;
            fullscreen = !fullscreen;
            if (fullscreen) {
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            } else {
                frame.setExtendedState(JFrame.NORMAL);
            }
        } else if (!key.keys.get(KeyEvent.VK_F11)) {
            f11Pressed = false;
        }

        if (mouseLocked) {
            mouse.MoveCursor(new Point(frame.getX() + screenWidth / 2, frame.getY() + screenHeight / 2));
            gameCamera.rotation.y += ((float) screenWidth / 2 - mouseX) * mouseSensitivity;
            gameCamera.rotation.x += ((float) screenHeight / 2 - mouseY) * mouseSensitivity;
        }

        if (gameCamera.rotation.x < -90) {
            gameCamera.rotation.x = -90;
        } else if (gameCamera.rotation.x > 90) {
            gameCamera.rotation.x = 90;
        }

        obj6.rotation += 10;

        D3Renderer.updateCamera(gameCamera);
        updateObjects(objects.toArray(D3Obj[]::new));
        updateLines(lines.toArray(D3Line[]::new));

        sky.bounds = new Rectangle(skyX, (int) ((gameCamera.rotation.x * gameCamera.dts / skyMovementOffset) - screenHeight * skyOffset), (int) (screenWidth * skyWidthOffset), (int) (screenHeight * skyHeightOffset));
    }

    static void addObject(D3Obj object, ArrayList<D3Obj> objects) {
        objects.add(object);
        D3Renderer.renderObject(object, 20);
        graphics.objects.add(object.rendered);
    }

    static void updateObjects(D3Obj[] objects) {
        for (D3Obj obj : objects) {
            obj.rendered = D3Renderer.renderObject(obj, 20);
        }
    }

    static void checkCollisions() {

    }

    static void addLine(D3Line line, ArrayList<D3Line> lines) {
        lines.add(line);
        D3Renderer.renderLine(line, 20);
        graphics.objects.add(line.rendered);
    }

    static void updateLines(D3Line[] lines) {
        for (D3Line line : lines) {
            line.rendered = D3Renderer.renderLine(line, 20);
        }
    }

}
