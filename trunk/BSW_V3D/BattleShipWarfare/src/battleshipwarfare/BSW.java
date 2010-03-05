package battleshipwarfare;

import battleshipwarfare.Boardpackage.Point;
import battleshipwarfare.Gamepackage.Game;
import battleshipwarfare.Gamepackage.GamePlayer;
import battleshipwarfare.PlayerPackage.HumanPlayer;
import battleshipwarfare.PlayerPackage.IAPlayer;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseZoom;
import com.sun.j3d.utils.geometry.GeometryInfo;
import com.sun.j3d.utils.geometry.NormalGenerator;
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

//Não sei porquê mas não consigo alterar a Scene presente no applet
//como tal, acrescentei o Option3D que determina em que fase está a aplicação
//Só que... é necessário, de cada vez que se quer alterar a Scene,
//fazer: mf = new MainFrame(new BSW(Option3D.INIT), 800, 600);
//       mf.setResizable(false);
//       mf.setVisible(true);
//Não me parece boa estratégia, mas na ausência de outra, que remédio!
//
//Também me surgiu outro problema, estava a pensar no ecran de init
//ter dois "butões" com "New Game" e "Change Rules"
//Criei o botão com GeometryInfo e depois queria acrescentar o Text3D
//Mas devolve-me uma excepção e não percebo porquê...(Linhas 117 a 124)
public class BSW extends Applet implements MouseListener {

    private Shape3D theShape;
    private Game game;                  //Game Object
    private PickCanvas pc;              //Picking
    private static MainFrame mf;        //MainFrame:needs to be shared to allow changing contents
    private Option3D option;            //Determines witch scene to be loaded
    private GamePlayer human = new GamePlayer(new HumanPlayer("Humanoid"));
    private GamePlayer computer = new GamePlayer(new IAPlayer());

    public static void main(String[] args) {
        mf = new MainFrame(new BSW(), 800, 600);
        mf.setResizable(false);
        mf.setVisible(true);
    }

    @Override
    public void init() {
        Panel container = new Panel();
        container.setSize(800, 400);
        container.setLocation(0, 100);
        container.setVisible(true);
        container.setLayout(null);

        /* Human Canvas */
        Canvas3D humanCanvas = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        humanCanvas.setSize(400, 400);
        humanCanvas.setLocation(0, 100);

        BranchGroup humanScene = getPlayerScene(human);

        SimpleUniverse humanUniverse = new SimpleUniverse(humanCanvas);
        humanUniverse.addBranchGraph(humanScene);

        pc = new PickCanvas(humanCanvas, humanScene);
        pc.setMode(PickCanvas.GEOMETRY); //NS - resolve o problema das coordenadas malucas!
        humanCanvas.addMouseListener(this);

        /* Computer Canvas */
        Canvas3D computerCanvas = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        computerCanvas.setSize(400, 400);
        computerCanvas.setLocation(410, 100);

        BranchGroup computerScene = getPlayerScene(computer);

        SimpleUniverse computerUniverse = new SimpleUniverse(computerCanvas);
        computerUniverse.addBranchGraph(computerScene);


        container.add(humanCanvas);
        container.add(computerCanvas);
        setLayout(new BorderLayout());
        add(container);
    }

    private BranchGroup getPlayerScene(GamePlayer player) {
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

        player.buildBoard();
        Shape3D boardShape = player.getBoard().getShape();

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
        for (int i = 0; i <= player.getBoard().getEndPoint().getX(); i++) {
            for (int j = 0; j <= player.getBoard().getEndPoint().getY(); j++) {
                sh = player.getBoard().getElementShape(new Point(i, j), false);
                PickTool.setCapabilities(sh, PickTool.INTERSECT_FULL);
                //spin.addChild(sh);
                // BEGIN Ricardo Romão
                // Adicionei isto aqui só para fazer um teste com mudança de cores
                Shape3D shapezorro = player.getBoard().getElementShape(new Point(i, j), false);
                if (i==5 && j==5)
                    theShape = shapezorro;
                shapezorro.setCapability(Shape3D.ALLOW_APPEARANCE_WRITE | Shape3D.ALLOW_GEOMETRY_READ);
                // END Ricardo Romão
                transformGroup.addChild(shapezorro);
            }
        }
        return root;
    }

    private Shape3D getButton(String name, int index) {

        Shape3D sh = new Shape3D();
        sh.setName(name);

        int idx = 0;
        GeometryInfo gi = new GeometryInfo(GeometryInfo.POLYGON_ARRAY);

        Point3d[] vertices = new Point3d[8];

        double startX = (Settings3D.getInitButtonSizeX() / 2) * (-1);
        double startY = Settings3D.getInitButtonStartY() - (index * Settings3D.getInitButtonSizeY())
                - (index * Settings3D.getInitButtonMargin());
        double endX = Settings3D.getInitButtonSizeX() / 2;
        double endY = startY - Settings3D.getInitButtonSizeY();

        //Pontos das faces [0..7]
        vertices[idx++] = new Point3d(startX, endY, 0);         //0
        vertices[idx++] = new Point3d(startX, startY, 0);       //1     1 3
        vertices[idx++] = new Point3d(endX, endY, 0);           //2     0 2
        vertices[idx++] = new Point3d(endX, startY, 0);         //3

        vertices[idx++] = new Point3d(startX, endY, 0.1);       //4
        vertices[idx++] = new Point3d(startX, startY, 0.1);     //5     5 7
        vertices[idx++] = new Point3d(endX, endY, 0.1);         //6     4 6
        vertices[idx++] = new Point3d(endX, startY, 0.1);       //7

        gi.setCoordinates(vertices);

        idx = 0;

        int[] indices = new int[24];

        //Face de trás (4)
        indices[idx++] = 0;
        indices[idx++] = 1;
        indices[idx++] = 3;
        indices[idx++] = 2;

        //Face da frente (4)
        indices[idx++] = 7;
        indices[idx++] = 5;
        indices[idx++] = 4;
        indices[idx++] = 6;

        //Face Esquerda (4)
        indices[idx++] = 5;
        indices[idx++] = 1;
        indices[idx++] = 0;
        indices[idx++] = 4;

        //Face Direita (4)
        indices[idx++] = 2;
        indices[idx++] = 3;
        indices[idx++] = 7;
        indices[idx++] = 6;

        //Face Superior (4)
        indices[idx++] = 3;
        indices[idx++] = 1;
        indices[idx++] = 5;
        indices[idx++] = 7;

        //Face Inferior (4)
        indices[idx++] = 4;
        indices[idx++] = 0;
        indices[idx++] = 2;
        indices[idx++] = 6;

        gi.setCoordinateIndices(indices);

        int[] stripCounts = {4, 4, 4, 4, 4, 4};
        gi.setStripCounts(stripCounts);

        int[] contourCount = {1, 1, 1, 1, 1, 1};
        gi.setContourCounts(contourCount);

        NormalGenerator ng = new NormalGenerator();
        ng.generateNormals(gi);

        sh.setGeometry(gi.getIndexedGeometryArray());

        return sh;
    }

    private Shape3D getText(String text) {
        Appearance a = new Appearance();
        a.setMaterial(new Material());
        Font font = new Font("Verdana", Font.PLAIN, 1);
        Font3D font3D = new Font3D(font, new FontExtrusion());
        Text3D text3D = new Text3D(font3D, text);
        Shape3D textShape = new Shape3D(text3D);

        return textShape;
    }

    public void mouseClicked(java.awt.event.MouseEvent mouseEvent) {
        inGameMouseClicked(mouseEvent);
    }

    private void inInitMouseClicked(java.awt.event.MouseEvent mouseEvent) {
        pc.setShapeLocation(mouseEvent);
        PickResult result = pc.pickClosest();

        if (result == null) {
            System.out.println("Nothing picked");
        } else {
            Shape3D s = (Shape3D) result.getNode(PickResult.SHAPE3D);
            if (s != null) {
                if (s.getName().equalsIgnoreCase("New Game")) {
                    mf = new MainFrame(new BSW(), 800, 600);
                    mf.setResizable(false);
                    mf.setVisible(true);
                    return;
                }
            } else {
                System.out.println("null");
            }
        }
    }

    private void inGameMouseClicked(java.awt.event.MouseEvent mouseEvent) {
        // BEGIN Ricardo Romão
        // Teste para ver como está o picking a funcionar
        // Para as células não me parece grande coisa,
        // deve faltar qualquer coisa :(
        pc.setShapeLocation(mouseEvent);
        PickResult result = pc.pickClosest();

        if (result == null) {
            System.out.println("Nothing picked");
        } else {
            Shape3D s = (Shape3D) result.getNode(PickResult.SHAPE3D);
            if (s != null) {
                Color3f c1 = new Color3f(0.6f, 0.6f, 1.0f);
                Appearance app = new Appearance();
                app.setMaterial(new Material(c1, c1, c1, c1, 80.0f));
                s.setAppearance(app);
                theShape.setAppearance(app);
                //(Aqui algo está(va) mal, retorna coordenadas malucas!!)
                //Resolvido
                System.out.println("Tipo: " + s.getClass().getName() + "; Valor: " + s.getName());
            } else {
                System.out.println("null");
            }
        }
        // END Ricard Romão
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

