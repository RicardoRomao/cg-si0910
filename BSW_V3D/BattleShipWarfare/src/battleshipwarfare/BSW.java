package battleshipwarfare;

import battleshipwarfare.Boardpackage.IBoard;
import battleshipwarfare.Boardpackage.Point;
import battleshipwarfare.Gamepackage.Game_3D;
import battleshipwarfare.Gamepackage.GameStatus;
import battleshipwarfare.PlayerPackage.PlayerType;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseZoom;
import com.sun.j3d.utils.picking.PickCanvas;
import com.sun.j3d.utils.picking.PickResult;
import com.sun.j3d.utils.picking.PickTool;
import com.sun.j3d.utils.universe.SimpleUniverse;
import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Panel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingLeaf;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Font3D;
import javax.media.j3d.FontExtrusion;
import javax.media.j3d.Material;
import javax.media.j3d.PointLight;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Text3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

public class BSW extends Applet implements MouseListener {
    
    private Game_3D game;                  //Game Object
    private PickCanvas pc;              //Picking Tool

    //Build in Game
    //private GamePlayer human = new GamePlayer(new HumanPlayer("Humanoid"));
    //private GamePlayer computer = new GamePlayer(new IAPlayer());

    public static void main(String[] args) {
        MainFrame mf = new MainFrame(new BSW(), 800, 600);
        mf.setResizable(false);
        mf.setVisible(true);
    }

    @Override
    public void init() {

        //Game Object
        game = new Game_3D();
        game.init();

        //Panel
        Panel container = new Panel();
        container.setSize(800, 400);
        container.setLocation(0, 100);
        container.setVisible(true);
        container.setLayout(null);

        /* Human Canvas */
        Canvas3D humanCanvas = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        humanCanvas.setSize(400, 400);
        humanCanvas.setLocation(0, 100);

        BranchGroup humanScene = getPlayerScene(PlayerType.HUMAN);

        SimpleUniverse humanUniverse = new SimpleUniverse(humanCanvas);
        humanUniverse.addBranchGraph(humanScene);

        /* Computer Canvas */
        Canvas3D computerCanvas = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        computerCanvas.setSize(400, 400);
        computerCanvas.setLocation(410, 100);

        BranchGroup computerScene = getPlayerScene(PlayerType.IA);

        SimpleUniverse computerUniverse = new SimpleUniverse(computerCanvas);
        computerUniverse.addBranchGraph(computerScene);

        pc = new PickCanvas(computerCanvas, computerScene);
        pc.setMode(PickCanvas.GEOMETRY); //NS - resolve o problema das coordenadas malucas!
        computerCanvas.addMouseListener(this);

        container.add(humanCanvas);
        container.add(computerCanvas);
        setLayout(new BorderLayout());
        add(container);
    }

    private BranchGroup getPlayerScene(PlayerType playerType) {

        BranchGroup root = new BranchGroup();

        Color3f white = new Color3f(1.0f, 1.0f, 1.0f);
        Color3f red = new Color3f(1.0f, 0.0f, 0.0f);
        Color3f darkGray = new Color3f(0.2f, 0.2f, 0.2f);
        Color3f bgColor = new Color3f(0.4f,0.4f,0.4f);
        
        //Bounding leaf node ?
        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),
                1000.0);
        BoundingLeaf boundingLeaf = new BoundingLeaf(bounds);
        root.addChild(boundingLeaf);

        //Background
        Background bckg = new Background(darkGray);
        bckg.setApplicationBounds(bounds);
        root.addChild(bckg);

        //Transform3D
        Transform3D trans3D = new Transform3D();
        trans3D.rotX(-Math.PI / 4.0d);
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

        IBoard board = (playerType==PlayerType.HUMAN?game.getHumanPlayer():game.getIAPlayer()).getBoard();
        Shape3D boardShape = board.getShape();

        PickTool.setCapabilities(boardShape, PickTool.INTERSECT_TEST);

        PointLight ptlight = new PointLight(new Color3f(Color.red),
                new Point3f(-1f, -1f, 2f),
                new Point3f(1f, 0f, 0f));
        ptlight.setInfluencingBounds(bounds);
        root.addChild(ptlight);

        transformGroup.addChild(myMouseRotate);
        transformGroup.addChild(myMouseZoom);
        transformGroup.addChild(boardShape);


        Shape3D sh;
        for (int i = 0; i <= board.getEndPoint().getX(); i++) {
            for (int j = 0; j <= board.getEndPoint().getY(); j++) {
                sh = board.getElementShape(new Point(i, j));
                PickTool.setCapabilities(sh, PickTool.INTERSECT_FULL);
                //spin.addChild(sh);
                // BEGIN Ricardo Romão
                // Adicionei isto aqui só para fazer um teste com mudança de cores
                Shape3D shapezorro = board.getElementShape(new Point(i, j));
                shapezorro.setCapability(Shape3D.ALLOW_APPEARANCE_WRITE | Shape3D.ALLOW_GEOMETRY_READ);
                // END Ricardo Romão
                transformGroup.addChild(shapezorro);
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
                String[] strPointArr = strPoint.split(",");
                Point p = new Point(Integer.parseInt(strPointArr[0]), Integer.parseInt(strPointArr[1]));
                game.playHuman(p);
                if(game.getStatus() == GameStatus.ENDED){
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

    //May be useful!?!
    private Shape3D getText(String text) {
        Appearance a = new Appearance();
        a.setMaterial(new Material());
        Font font = new Font("Verdana", Font.PLAIN, 1);
        Font3D font3D = new Font3D(font, new FontExtrusion());
        Text3D text3D = new Text3D(font3D, text);
        Shape3D textShape = new Shape3D(text3D);
        return textShape;
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

