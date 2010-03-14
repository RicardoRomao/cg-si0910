package battleshipwarfare;

import battleshipwarfare.Boardpackage.IBoard;
import battleshipwarfare.Boardpackage.Point;
import battleshipwarfare.Gamepackage.GameStatus;
import battleshipwarfare.Gamepackage.Game_3D;
import battleshipwarfare.PlayerPackage.PlayerType;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseZoom;
import com.sun.j3d.utils.picking.PickCanvas;
import com.sun.j3d.utils.picking.PickResult;
import com.sun.j3d.utils.picking.PickTool;
import com.sun.j3d.utils.universe.SimpleUniverse;
import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingLeaf;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.PointLight;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

public class BSW extends Applet implements MouseListener {

    private static final int BTN_QUIT = 1;
    private static final String BTN_QUIT_IMG = "images/RB.png";
    private static final String BTN_QUIT_IMG_OVER = "images/RBO.png";
    private static final String BTN_QUIT_IMG_DOWN = "images/RBD.png";
    private static final int BTN_RESTART = 2;
    private static final String BTN_RESTART_IMG = "images/PB.png";
    private static final String BTN_RESTART_IMG_OVER = "images/PBO.png";
    private static final String BTN_RESTART_IMG_DOWN = "images/PBD.png";
    private static final int BTN_HELP = 3;
    private static final String BTN_HELP_IMG = "images/GB.png";
    private static final String BTN_HELP_IMG_OVER = "images/GBO.png";
    private static final String BTN_HELP_IMG_DOWN = "images/GBD.png";
    private Game_3D game;                  //Game Object
    private PickCanvas pc;              //Picking Tool
    private static MainFrame mf;
    private final Panel headContainer;
    private final Panel mainContainer;        //Panel containing the boards
    private final Panel footContainer;
    Color3f white = new Color3f(1.0f, 1.0f, 1.0f);
    Color3f red = new Color3f(1.0f, 0.0f, 0.0f);
    Color3f darkGray = new Color3f(0.2f, 0.2f, 0.2f);
    Color3f bgColor = new Color3f(0.4f, 0.4f, 0.4f);

    public BSW() {
        headContainer = new Panel();
        headContainer.setBackground(Color.black);
        headContainer.setVisible(false);
        mainContainer = new Panel();
        mainContainer.setBackground(Color.black);
        footContainer = new Panel();
        footContainer.setBackground(Color.black);
    }

    public static void main(String[] args) {
        mf = new MainFrame(new BSW(), 800, 600);
        mf.setBackground(Color.black);
        mf.setResizable(false);
    }

    @Override
    public void init() {

        game = new Game_3D();
        game.init();

        setLayout(null);

        if (!headContainer.isVisible()) {
            buildHeadContainer();
            add(headContainer);
        }
        buildMainContainer();
        add(mainContainer);
        buildFootContainer();
        add(footContainer);
        mf.setVisible(true);

        Runtime.getRuntime().gc();
    }

    private void buildHeadContainer() {

        headContainer.removeAll();
        headContainer.setBounds(5, 0, 800, 100);
        headContainer.setLayout(null);

        JLabel backGround = new JLabel(new ImageIcon("images/HeaderBackGround.png"));
        backGround.setBounds(0, 0, 800, 100);

        JButton restart = getCustomButton("Start", "BTN_RESTART",
                BTN_RESTART_IMG, BTN_RESTART_IMG_OVER,
                BTN_RESTART_IMG_DOWN, 460, 0, 100, 100);

        JButton help = getCustomButton("Help", "BTN_HELP",
                BTN_HELP_IMG, BTN_HELP_IMG_OVER,
                BTN_HELP_IMG_DOWN, 570, 0, 100, 100);

        JButton quit = getCustomButton("Quit", "BTN_QUIT",
                BTN_QUIT_IMG, BTN_QUIT_IMG_OVER,
                BTN_QUIT_IMG_DOWN, 680, 0, 100, 100);

        headContainer.add(quit);
        headContainer.add(restart);
        headContainer.add(help);
        headContainer.add(backGround);
        headContainer.setVisible(true);
    }

    private void buildFootContainer() {

        footContainer.removeAll();
        footContainer.setBounds(5, 500, 800, 100);
        footContainer.setVisible(true);
        footContainer.setLayout(null);

        /* Head Canvas holding UI controls */
        Canvas3D canvas = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        canvas.setBounds(0, 0, 800, 100);
        canvas.setBackground(Color.black);
        footContainer.add(canvas);

    }

    private void buildMainContainer() {

        mainContainer.removeAll();
        mainContainer.setBounds(5, 100, 800, 400);
        mainContainer.setVisible(true);
        mainContainer.setLayout(null);

        /* Human scene branchgroup creation */
        BranchGroup humanScene = getPlayerScene(PlayerType.HUMAN);

        /* Human Canvas */
        Canvas3D humanCanvas = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        humanCanvas.setSize(400, 400);
        humanCanvas.setLocation(0, 0);

        SimpleUniverse humanUniverse = new SimpleUniverse(humanCanvas);
        humanUniverse.addBranchGraph(humanScene);

        /* Computer scene branchgroup creation */
        BranchGroup computerScene = getPlayerScene(PlayerType.IA);

        /* Computer Canvas */
        Canvas3D computerCanvas = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        computerCanvas.setSize(400, 400);
        computerCanvas.setLocation(410, 0);

        SimpleUniverse computerUniverse = new SimpleUniverse(computerCanvas);
        computerUniverse.addBranchGraph(computerScene);

        /* Assign picking to the computer board, where humans will play */
        pc = new PickCanvas(computerCanvas, computerScene);
        pc.setMode(PickCanvas.GEOMETRY);
        computerCanvas.addMouseListener(this);

        mainContainer.add(humanCanvas);
        mainContainer.add(computerCanvas);
    }

    private BranchGroup getPlayerScene(PlayerType playerType) {

        BranchGroup root = new BranchGroup();

        //Bounding leaf node ?
        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),
                1000.0);
        BoundingLeaf boundingLeaf = new BoundingLeaf(bounds);
        root.addChild(boundingLeaf);

        //Background
        Background bckg = new Background(new Color3f(new Color(19,19,19)));
        bckg.setApplicationBounds(bounds);
        root.addChild(bckg);

        //Transform3D
        Transform3D trans3D = new Transform3D();
        trans3D.rotX(-Math.PI / 12.0d);
        trans3D.setScale(7.5f);
        trans3D.setTranslation(new Vector3f(0.0f, 0.0f, -30f));

        //TransformGroup
        TransformGroup transformGroup = new TransformGroup(trans3D);
        transformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        transformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        root.addChild(transformGroup);

        //MouseRotate
        MouseRotate myMouseRotate = new MouseRotate(transformGroup);
        myMouseRotate.setSchedulingBoundingLeaf(boundingLeaf);

        //MouseZoom
        MouseZoom myMouseZoom = new MouseZoom(transformGroup);
        myMouseZoom.setSchedulingBoundingLeaf(boundingLeaf);

        IBoard board = (playerType == PlayerType.HUMAN ? game.getHumanPlayer() : game.getIAPlayer()).getBoard();
        Shape3D boardShape = board.getShape();

        PickTool.setCapabilities(boardShape, PickTool.INTERSECT_TEST);

        PointLight pLight = new PointLight(new Color3f(Color.orange),
            new Point3f(2f,2f,0.1f), new Point3f(0f,0f,0f));
        pLight.setInfluencingBounds(bounds);
        root.addChild(pLight);

        transformGroup.addChild(myMouseRotate);
        transformGroup.addChild(myMouseZoom);
        transformGroup.addChild(boardShape);

        Shape3D sh;
        for (int i = 0; i <= board.getEndPoint().getX(); i++) {
            for (int j = 0; j <= board.getEndPoint().getY(); j++) {
                Shape3D elemShape = board.getElementShape(new Point(i, j));
                elemShape.setCapability(Shape3D.ALLOW_APPEARANCE_WRITE | Shape3D.ALLOW_GEOMETRY_READ);
                transformGroup.addChild(elemShape);
            }
        }
        return root;
    }

    public void mouseClicked(java.awt.event.MouseEvent mouseEvent) {
        pc.setShapeLocation(mouseEvent);
        PickResult result = pc.pickClosest();

        if (result == null) {
            System.out.println("Nothing picked");
        } else {
            Shape3D s = (Shape3D) result.getNode(PickResult.SHAPE3D);
            if (s != null) {
                String strPoint = s.getName();
		// Sem esta linha era laçada excepção
		// se fosse feito o click na board
		if (strPoint == null) return;
                String[] strPointArr = strPoint.split(",");
                Point p = new Point(Integer.parseInt(strPointArr[0]), Integer.parseInt(strPointArr[1]));
                game.playHuman(p);
                if (game.getStatus() == GameStatus.ENDED) {
                    //Call the ?? getGameOverScene ??
                }
                //                Color3f c1 = new Color3f(0.6f, 0.6f, 1.0f);
                //                Appearance app = new Appearance();
                //                app.setMaterial(new Material(c1, c1, c1, c1, 80.0f));
                //                s.setAppearance(app);
                //System.out.println("Tipo: " + s.getClass().getName() + "; Valor: " + s.getName());
            } else {
                System.out.println("null");
            }
        }
    }

    private void processButton(Object o) {

        JButton btn = (JButton) o;
        String btnCode = btn.getName();

        if (btnCode == null ? "BTN_QUIT" == null : btnCode.equals("BTN_QUIT")) {
            mf.dispose();
            System.exit(0);
        } else if (btnCode == null ? "BTN_RESTART" == null : btnCode.equals("BTN_RESTART")) {
            init();
        }
    }

    private JButton getCustomButton(String text, String name, String img,
            String overImg, String downImg,
            int x0, int y0, int x1, int y1) {
        JButton btn = new JButton(text);
        btn.setForeground(new Color(255, 255, 255));
        btn.setBounds(x0, y0, x1, y1);
        btn.setName(name);
        btn.setBackground(new Color(0, 0, 0, 0));
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setFont(new Font(Font.DIALOG, Font.BOLD, 18));
        btn.setIcon(new ImageIcon(img));
        btn.setPressedIcon(new ImageIcon(overImg));
        btn.setRolloverIcon(new ImageIcon(downImg));
        btn.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                processButton(e.getSource());
            }
        });
        btn.setVerticalTextPosition(JButton.CENTER);
        btn.setHorizontalTextPosition(JButton.CENTER);
        btn.setVisible(true);
        return btn;
    }

    @Override
    public void start() {
    }

    @Override
    public void stop() {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }
}

