package battleshipwarfare;

import battleshipwarfare.Boardpackage.IBoard;
import battleshipwarfare.Boardpackage.Point;
import battleshipwarfare.Gamepackage.GameStatus;
import battleshipwarfare.Gamepackage.Game_3D;
import battleshipwarfare.PlayerPackage.PlayerType;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseZoom;
import com.sun.j3d.utils.image.TextureLoader;
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
import javax.media.j3d.Alpha;
import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingLeaf;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Font3D;
import javax.media.j3d.FontExtrusion;
import javax.media.j3d.LinearFog;
import javax.media.j3d.Material;
import javax.media.j3d.PointLight;
import javax.media.j3d.ScaleInterpolator;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Text3D;
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

    private Game_3D game;                  //Game Object
    private PickCanvas pc;              //Picking Tool
    private static MainFrame mf;
    private final Panel headContainer;
    private final Panel mainContainer;        //Panel containing the boards
    private final Panel footContainer;
    private Text3D scoreLeft;
    private Text3D scoreRight;
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

        updateScores();

        Runtime.getRuntime().gc();
    }

    private void buildHeadContainer() {

        headContainer.removeAll();
        headContainer.setBounds(5, 0, 800, 100);
        headContainer.setLayout(null);

        JLabel backGround = new JLabel(new ImageIcon(Settings3D.HEAD_BACKG));
        backGround.setBounds(0, 0, 800, 100);

        JButton restart = getCustomButton("Start", "BTN_RESTART",
                Settings3D.BTN_RESTART_IMG, Settings3D.BTN_RESTART_IMG_OVER,
                Settings3D.BTN_RESTART_IMG_DOWN, 460, 0, 100, 100);

        JButton help = getCustomButton("Help", "BTN_HELP",
                Settings3D.BTN_HELP_IMG, Settings3D.BTN_HELP_IMG_OVER,
                Settings3D.BTN_HELP_IMG_DOWN, 570, 0, 100, 100);

        JButton quit = getCustomButton("Quit", "BTN_QUIT",
                Settings3D.BTN_QUIT_IMG, Settings3D.BTN_QUIT_IMG_OVER,
                Settings3D.BTN_QUIT_IMG_DOWN, 680, 0, 100, 100);

        headContainer.add(quit);
        headContainer.add(restart);
        headContainer.add(help);
        headContainer.add(backGround);
        headContainer.setVisible(true);
    }

    private void buildFootContainer() {

        footContainer.removeAll();
        footContainer.setBounds(5, 500, 800, 100);

        /* Footer scene branchgroup creation */
        BranchGroup footerScene = getFootScene();

        Canvas3D canvas = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        canvas.setSize(800, 100);
        canvas.setLocation(0, 0);

        SimpleUniverse footUniverse = new SimpleUniverse(canvas);
        footUniverse.addBranchGraph(footerScene);

        footContainer.add(canvas);

        footContainer.setLayout(null);
        footContainer.setVisible(true);

    }

    private void buildGameOverScene(String text) {

        mainContainer.removeAll();
        mainContainer.setBounds(5, 100, 800, 400);
        mainContainer.setVisible(true);
        mainContainer.setLayout(null);

        /* Gameover scene branchgroup creation */
        BranchGroup gameOverScene = getGameOverScene(text);

        Canvas3D canvas = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        canvas.setSize(800, 400);
        canvas.setLocation(0, 0);

        SimpleUniverse humanUniverse = new SimpleUniverse(canvas);
        humanUniverse.addBranchGraph(gameOverScene);

        mainContainer.add(canvas);

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

        //Bounding leaf node
        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),
                1000.0);
        BoundingLeaf boundingLeaf = new BoundingLeaf(bounds);
        root.addChild(boundingLeaf);

        //Background
        Background bckg = new Background(new Color3f(new Color(19, 19, 19)));
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
                new Point3f(2f, 2f, 0.1f), new Point3f(0f, 0f, 0f));
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

    private BranchGroup getGameOverScene(String text) {

        BranchGroup root = new BranchGroup();
        TransformGroup spin = new TransformGroup();
        spin.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        root.addChild(spin);

        //Bounding leaf node
        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),
                500.0);
        BoundingLeaf boundingLeaf = new BoundingLeaf(bounds);
        root.addChild(boundingLeaf);

        Transform3D trans3D = new Transform3D();
        trans3D.setScale(0.5f);
        trans3D.setTranslation(new Vector3f(0.0f, -3.0f, -30f));

        //Appearance
        Appearance ap = new Appearance();
        ap.setMaterial(new Material());
        ap.setCapability(Appearance.ALLOW_TEXTURE_WRITE);

        //fog
        LinearFog fog = new LinearFog(.86f, .61f, .12f, 9f, 15f);
        fog.setInfluencingBounds(bounds);
        root.addChild(fog);

        //Text
        Font font = new Font("Arial", Font.BOLD, 1);
        FontExtrusion extursion = new FontExtrusion();
        Font3D font3d = new Font3D(font, extursion);
        Text3D msg = new Text3D(font3d, text, new Point3f(0, 0, 0));
        msg.setAlignment(Text3D.ALIGN_CENTER);
        Shape3D textShape = new Shape3D(msg, ap);

        PointLight pLight = new PointLight(new Color3f(new Color(24, 161, 37)),
                new Point3f(2f, 2f, 0f), new Point3f(0f, 0f, 0f));
        pLight.setInfluencingBounds(bounds);

        PointLight pLight2 = new PointLight(new Color3f(new Color(240, 205, 13)),
                new Point3f(2f, 2f, -5f), new Point3f(-1f, 0f, -5f));
        pLight2.setInfluencingBounds(bounds);

        root.addChild(pLight);
        root.addChild(pLight2);

        spin.addChild(textShape);

        //rotator
        Alpha alpha = new Alpha(-1, 10000);
        ScaleInterpolator scaler = new ScaleInterpolator(alpha, spin, trans3D, 0.4f, 1f);
        scaler.setSchedulingBounds(bounds);
        spin.addChild(scaler);

        //background
        Background bckg = new Background(new Color3f(new Color(19, 19, 19)));
        bckg.setApplicationBounds(bounds);
        root.addChild(bckg);

        return root;

    }

    private BranchGroup getFootScene() {

        BranchGroup root = new BranchGroup();
        
        //Bounding leaf node
        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),
                500.0);
        BoundingLeaf boundingLeaf = new BoundingLeaf(bounds);
        root.addChild(boundingLeaf);


        Transform3D trans3D = new Transform3D();
        trans3D.setScale(0.5f);
        trans3D.rotX(-Math.PI / 12.0d);
        trans3D.setTranslation(new Vector3f(0.0f, 0.0f, -30f));

        TransformGroup spin = new TransformGroup(trans3D);
        spin.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        root.addChild(spin);

        //Appearance
        Appearance ap = new Appearance();
        ap.setMaterial(new Material());
        ap.setCapability(Appearance.ALLOW_TEXTURE_WRITE);

        Font font = new Font("Arial", Font.BOLD, 1);
        Font3D font3d = new Font3D(font, new FontExtrusion());

        //Left text
        Text3D msgLeft = new Text3D(font3d, "Human Life: ", new Point3f(-8, -0.5f, 0));
        msgLeft.setAlignment(Text3D.ALIGN_CENTER);
        Shape3D textShapeLeft = new Shape3D(msgLeft, ap);

        //Right text
        Text3D msgRight = new Text3D(font3d, "Machine Life: ", new Point3f(1.3f, -0.5f, 0));
        msgLeft.setAlignment(Text3D.ALIGN_CENTER);
        Shape3D textShapeRight = new Shape3D(msgRight, ap);

        //Appearance
        Appearance apRed = new Appearance();
        Material scoreMaterial = new Material();
        scoreMaterial.setShininess(32);
        scoreMaterial.setDiffuseColor(new Color3f(new Color(6,198,113)));
        apRed.setMaterial(scoreMaterial);
        apRed.setCapability(Appearance.ALLOW_TEXTURE_WRITE);

        //Left Score Value
        scoreLeft = new Text3D(font3d, "0", new Point3f(-5, -0.5f, 0));
        scoreLeft.setCapability(Text3D.ALLOW_STRING_WRITE);
        msgLeft.setAlignment(Text3D.ALIGN_CENTER);
        Shape3D scoreShapeLeft = new Shape3D(scoreLeft, apRed);

        //Right Score Value
        scoreRight = new Text3D(font3d, "0", new Point3f(8, -0.5f, 0));
        scoreRight.setCapability(Text3D.ALLOW_STRING_WRITE);
        msgLeft.setAlignment(Text3D.ALIGN_CENTER);
        Shape3D scoreShapeRight = new Shape3D(scoreRight, apRed);

        PointLight pLight = new PointLight(new Color3f(new Color(240, 205, 13)),
                new Point3f(0f, 0f, -5f), new Point3f(-1f, 0f, -5f));
        pLight.setInfluencingBounds(bounds);

        root.addChild(pLight);

        //background
        TextureLoader backgroundTexture = new TextureLoader("images/FooterBackGround.png", this);
        Background background = new Background(backgroundTexture.getImage());
        background.setApplicationBounds(bounds);

        root.addChild(background);
        spin.addChild(textShapeLeft);
        spin.addChild(textShapeRight);
        spin.addChild(scoreShapeLeft);
        spin.addChild(scoreShapeRight);

        return root;

    }

    public void mouseClicked(java.awt.event.MouseEvent mouseEvent) {
        pc.setShapeLocation(mouseEvent);
        PickResult result = pc.pickClosest();

        if ((result != null) && (game.getStatus() != GameStatus.ENDED)) {
            Shape3D target = (Shape3D) result.getNode(PickResult.SHAPE3D);
            if (target != null) {
                String strPoint = target.getName();
                if (strPoint == null) {
                    return;
                }
                String[] strPointArr = strPoint.split(",");
                Point p = new Point(Integer.parseInt(strPointArr[0]), Integer.parseInt(strPointArr[1]));
                target.setName(null);
                game.playHuman(p);

                updateScores();

                if (game.getStatus() == GameStatus.ENDED) {
                    if (game.getWinner().getPlayer().getPlayerType() == PlayerType.HUMAN) {
                        buildGameOverScene("Human wins!");
                    } else {
                        buildGameOverScene("Computer wins! You suck!");
                    }
                }
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

    private void updateScores()
    {
        scoreLeft.setString(game.getHumanPlayer().getAlivePoints()+"");
        scoreRight.setString(game.getIAPlayer().getAlivePoints()+"");
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

