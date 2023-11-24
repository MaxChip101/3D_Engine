import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

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

    static Camera camera = new Camera(new D3(0, 200, 0), new D3(0, 0, 0), 60, 300, 1000, 960 / 620);

    static D3Obj obj1 = new D3Obj(new Prism(250, 0, 1000, 10, 10, 10), new Color(0, 0, 255));
    static D3Obj obj2 = new D3Obj(new Prism(250, 0, 1500, 10, 10, 10), new Color(255, 0, 255));
    static D3Obj obj3 = new D3Obj(new Prism(-250, 0, 1000, 10, 10, 10), new Color(255, 255, 0));
    static D3Obj obj4 = new D3Obj(new Prism(-250, 0, 1500, 10, 10, 10), new Color(0, 255, 0));
    static D3Obj obj5 = new D3Obj(new Prism(250, 500, 1000, 10, 10, 10), new Color(255, 55, 0));
    static D3Obj obj6 = new D3Obj(new Prism(250, 500, 1500, 10, 10, 10), new Color(255, 0, 100));
    static D3Obj obj7 = new D3Obj(new Prism(-250, 500, 1000, 10, 10, 10), new Color(0, 255, 255));
    static D3Obj obj8 = new D3Obj(new Prism(-250, 500, 1500, 10, 10, 10), new Color(150, 0, 200));


    static BeanObj renderedobj1 = buildProjectedObject(camera, obj1);
    static BeanObj renderedobj2 = buildProjectedObject(camera, obj2);
    static BeanObj renderedobj3 = buildProjectedObject(camera, obj3);
    static BeanObj renderedobj4 = buildProjectedObject(camera, obj4);
    static BeanObj renderedobj5 = buildProjectedObject(camera, obj5);
    static BeanObj renderedobj6 = buildProjectedObject(camera, obj6);
    static BeanObj renderedobj7 = buildProjectedObject(camera, obj7);
    static BeanObj renderedobj8 = buildProjectedObject(camera, obj8);


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


        graphics.objects.add(renderedobj1);
        graphics.objects.add(renderedobj2);
        graphics.objects.add(renderedobj3);
        graphics.objects.add(renderedobj4);
        graphics.objects.add(renderedobj5);
        graphics.objects.add(renderedobj6);
        graphics.objects.add(renderedobj7);
        graphics.objects.add(renderedobj8);

    }



    static void update() {
        screenWidth = frame.getBounds().width;
        screenHeight = frame.getBounds().height;

        if (key.keys.get(KeyEvent.VK_A)) {
            camera.position.z += -5 * Math.sin(Math.toRadians(camera.rotation.y));
            camera.position.x += -5 * Math.cos(Math.toRadians(camera.rotation.y));
        }
        if (key.keys.get(KeyEvent.VK_D)) {
            camera.position.z += 5 * Math.sin(Math.toRadians(camera.rotation.y));
            camera.position.x += 5 * Math.cos(Math.toRadians(camera.rotation.y));
        }
        if (key.keys.get(KeyEvent.VK_W)) {
            camera.position.z += 5 * Math.sin(Math.toRadians(camera.rotation.y + 90));
            camera.position.x += 5 * Math.cos(Math.toRadians(camera.rotation.y + 90));
        }
        if (key.keys.get(KeyEvent.VK_S)) {
            camera.position.z += 5 * Math.sin(Math.toRadians(camera.rotation.y - 90));
            camera.position.x += 5 * Math.cos(Math.toRadians(camera.rotation.y - 90));
        }
        if (key.keys.get(KeyEvent.VK_LEFT)) {
            camera.rotation.y += 1;
        }
        if (key.keys.get(KeyEvent.VK_RIGHT)) {
            camera.rotation.y -= 1;
        }
        if (key.keys.get(KeyEvent.VK_I)) {
            camera.dts += 5;
        }
        if (key.keys.get(KeyEvent.VK_O)) {
            camera.dts -= 5;
        }
        if (key.keys.get(KeyEvent.VK_UP)) {
            camera.rotation.x += 1;
        }
        if (key.keys.get(KeyEvent.VK_DOWN)) {
            camera.rotation.x -= 1;
        }

        renderedobj1 = buildProjectedObject(camera, obj1);
        renderedobj2 = buildProjectedObject(camera, obj2);
        renderedobj3 = buildProjectedObject(camera, obj3);
        renderedobj4 = buildProjectedObject(camera, obj4);
        renderedobj5 = buildProjectedObject(camera, obj5);
        renderedobj6 = buildProjectedObject(camera, obj6);
        renderedobj7 = buildProjectedObject(camera, obj7);
        renderedobj8 = buildProjectedObject(camera, obj8);

    }

    static BeanObj buildProjectedObject(Camera camera, D3Obj object) {
        BeanObj renderedObject = object.rendered;
        renderedObject.color = object.color;
        renderedObject.shape = "fillRect";
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
        double rotatedY = rotatedZ * camXSin - position.y * camXCos;
        rotatedZ = rotatedZ * camXCos - position.y * camXSin;



        D3 rotatedPositions = new D3((int) rotatedX, (int) rotatedY , (int) rotatedZ);

        if (rotatedPositions.z != 0) {
            int distance = 100 * camera.dts / rotatedPositions.z;
            renderedObject.zindex = distance;
            renderedObject.bounds = new Rectangle((rotatedPositions.x - distance / 2) * camera.dts / rotatedPositions.z, (rotatedPositions.y - distance / 2) * camera.dts / rotatedPositions.z, distance, distance);
            double fixedRotation = 90 + Math.atan((rotatedPositions.x - distance / 2) * camera.dts / rotatedPositions.z) / (camera.dts / (camXSin / camXCos) - ((rotatedPositions.y - distance / 2) * camera.dts / rotatedPositions.z));
            renderedObject.rotation = (int) fixedRotation;
        } else {
            renderedObject.bounds = new Rectangle(position.x, position.y, 0, 0);
        }

        int screenWidthHalf = Main.screenWidth / 2;
        int screenHeightHalf = Main.screenHeight / 2;
        renderedObject.bounds.translate(screenWidthHalf, screenHeightHalf);

        position.x += screenWidthHalf;
        position.y += screenHeightHalf;

        return renderedObject;
    }


}
