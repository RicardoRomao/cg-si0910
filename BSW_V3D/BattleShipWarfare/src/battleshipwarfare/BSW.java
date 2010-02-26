package battleshipwarfare;

import battleshipwarfare.Boardpackage.Point;
import battleshipwarfare.Gamepackage.Game;
import battleshipwarfare.Gamepackage.GamePlayer;
import battleshipwarfare.PlayerPackage.HumanPlayer;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.behaviors.keyboard.KeyNavigatorBehavior;
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
    //private PickCanvas pc;

    public static void main(String[] args){
        MainFrame mf = new MainFrame(new BSW(), 1200, 700);
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
                spin.addChild(gp.getBoard().getElementShape(new Point(i, j), true));
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
