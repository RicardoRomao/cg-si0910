package battleshipwarfare;

import battleshipwarfare.Boardpackage.Point;
import battleshipwarfare.Gamepackage.Game;
import battleshipwarfare.Gamepackage.GamePlayer;
import battleshipwarfare.PlayerPackage.HumanPlayer;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.behaviors.keyboard.KeyNavigatorBehavior;
import com.sun.j3d.utils.picking.PickCanvas;
import com.sun.j3d.utils.picking.PickResult;
import com.sun.j3d.utils.universe.SimpleUniverse;
import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GraphicsConfiguration;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Material;
import javax.media.j3d.PointLight;
import javax.media.j3d.Shape3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;

public class BSW extends Applet implements MouseListener {

    Game game;
    //private Appearance appearance;
    private PickCanvas pc; // É preciso para fazer o picking

    public static void main(String[] args){
        MainFrame mf = new MainFrame(new BSW(), 800, 600);
        mf.setResizable(false);
        mf.setVisible(true);
    }

    public BSW(){
        game = new Game();
        //appearance = new Appearance();
    }
    @Override
    public void init(){
        GraphicsConfiguration gc = SimpleUniverse.getPreferredConfiguration();
        Canvas3D canvas = new Canvas3D(gc);
        setLayout(new BorderLayout());
        add(canvas, BorderLayout.CENTER);
        BranchGroup bg = createSceneGraph();
	// BEGIN Ricardo Romão
	// É preciso para fazer o picking
	pc = new PickCanvas(canvas, bg);
	pc.setMode(PickCanvas.BOUNDS);
	canvas.addMouseListener(this);
	// END Ricardo Romão
        bg.compile();
        SimpleUniverse su = new SimpleUniverse(canvas);
        su.getViewingPlatform().setNominalViewingTransform();
        su.addBranchGraph(bg);
    }
    private BranchGroup createSceneGraph() {
        BranchGroup root = new BranchGroup();
        TransformGroup spin = new TransformGroup();
        spin.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        spin.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        BoundingSphere bs = new BoundingSphere();
        KeyNavigatorBehavior behavior = new KeyNavigatorBehavior(spin);

        GamePlayer gp = new GamePlayer(new HumanPlayer("Nuno"));
        gp.fillWater();
	//Board board = new Board();
        Shape3D boardShape = gp.getBoard().getShape();
	Appearance appearance = new Appearance();
	appearance.setMaterial(new Material());
	// BEGIN Ricardo Romão
	// Adicionei isto aqui só para fazer um teste com mudança de cores
	appearance.setCapability(Appearance.ALLOW_COLORING_ATTRIBUTES_WRITE);
	boardShape.setCapability(Shape3D.ALLOW_APPEARANCE_WRITE);
	// END Ricardo Romão
	boardShape.setAppearance(appearance);

        Background bg = new Background(1.0f, 1.0f, 1.0f);
        bg.setApplicationBounds(bs);

        root.addChild(bg);

	PointLight ptlight = new PointLight(new Color3f(Color.RED),
                                            new Point3f(-1f,-1f,2f),
                                            new Point3f(1f,0f,0f));
	ptlight.setInfluencingBounds(bs);
	root.addChild(ptlight);

        behavior.setSchedulingBounds(bs);
        spin.addChild(behavior);
        spin.addChild(boardShape);
        
        for(int i = 0; i <= gp.getBoard().getEndPoint().getX(); i++){
            for(int j = 0; j <= gp.getBoard().getEndPoint().getY(); j++){
		// BEGIN Ricardo Romão
		// Adicionei isto aqui só para fazer um teste com mudança de cores
		Shape3D shapezorro = gp.getBoard().getElementShape(new Point(i, j), true);
		shapezorro.setCapability(Shape3D.ALLOW_APPEARANCE_WRITE);
		// END Ricardo Romão
                spin.addChild(shapezorro);
            }
        }
        root.addChild(spin);

        return root;
    }
    @Override
    public void start() {}
    @Override
    public void stop() {}
    
    public void mouseClicked(java.awt.event.MouseEvent mouseEvent) {
	// BEGIN Ricardo Romão
	// Teste para ver como está o picking a funcionar
	// Para as células não me parece grande coisa,
	// deve faltar qualquer coisa :(
	pc.setShapeLocation(mouseEvent);
	PickResult result = pc.pickClosest();

	if (result == null) {
	    System.out.println("Nothing picked");
	}
	else {
	    Shape3D s = (Shape3D)result.getNode(PickResult.SHAPE3D);
	    if (s != null) {
		Color3f c1 = new Color3f(0.6f, 0.6f, 1.0f);
		Appearance app = new Appearance();
		app.setMaterial(new Material(c1, c1, c1, c1, 80.0f));
		s.setAppearance(app);
		System.out.println(s.getClass().getName());
	    }
	    else {
		System.out.println("null");
	    }
	}
	// END Ricard Romão
//        pc.setShapeLocation(mouseEvent);
//        PickResult[] results = pc.pickAll();
//        for (int i = 0; (results != null) && (i < results.length); i++) {
//            Node node = results[i].getObject();
//            if (node instanceof Shape3D) {
//                ((Shape3D)node).setAppearance(appearance);
//                System.out.println(node.toString());
//            }
//        }
    }
    
    public void mousePressed(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mouseReleased(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mouseEntered(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mouseExited(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }
}