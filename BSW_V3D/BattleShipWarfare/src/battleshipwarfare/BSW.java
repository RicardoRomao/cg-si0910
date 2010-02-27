package battleshipwarfare;

import battleshipwarfare.Boardpackage.Point;
import battleshipwarfare.Gamepackage.Game;
import battleshipwarfare.Gamepackage.GamePlayer;
import battleshipwarfare.PlayerPackage.HumanPlayer;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.behaviors.keyboard.KeyNavigatorBehavior;
import com.sun.j3d.utils.picking.PickCanvas;
import com.sun.j3d.utils.picking.PickResult;
import com.sun.j3d.utils.picking.PickTool;
import com.sun.j3d.utils.universe.SimpleUniverse;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GraphicsConfiguration;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.JApplet;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;

public class BSW extends JApplet implements ActionListener, MouseListener {

    Game game;
    //private Appearance appearance;
    private PickCanvas pc; // É preciso para fazer o picking
    private BranchGroup bg;
    private Canvas3D canvas;
    private GraphicsConfiguration gc;
    private static MainFrame mf;

    public static void main(String[] args){
        mf = new MainFrame(new BSW(), 800, 600);
        mf.setResizable(false);
        mf.setVisible(true);
    }

    public BSW(){
        game = new Game();
        //appearance = new Appearance();
    }
    @Override
    public void init(){
        buildMenuBar();
        initGame();
    }
    private void buildMenuBar(){
        JMenuBar menuBar = new JMenuBar();
        //Game
        JMenu gameMenu = new JMenu("Game");
        JMenuItem startGameItem = new JMenuItem("New");
        startGameItem.addActionListener(this);
        JMenuItem reStartGameItem = new JMenuItem("Restart");
        reStartGameItem.setEnabled(false);
        reStartGameItem.addActionListener(this);
        gameMenu.add(startGameItem);
        gameMenu.add(reStartGameItem);
        menuBar.add(gameMenu);
        //Rules
        JMenu rulesMenu = new JMenu("Rules");
        JMenuItem newRulesItem = new JMenuItem("New Rules");
        newRulesItem.addActionListener(this);
        rulesMenu.add(newRulesItem);
        menuBar.add(rulesMenu);

        this.setJMenuBar(menuBar);
    }
    private void initGame(){
        gc = SimpleUniverse.getPreferredConfiguration();
        canvas = new Canvas3D(gc);
        setLayout(new BorderLayout());
        add(canvas, BorderLayout.CENTER);

        bg = createSceneGraph();
	// BEGIN Ricardo Romão
	// É preciso para fazer o picking
	pc = new PickCanvas(canvas, bg);
        //pc.setMode(PickCanvas.INTERSECT_FULL);
	pc.setMode(PickCanvas.GEOMETRY); //NS - resolve o problema das coordenadas malucas!
	canvas.addMouseListener(this);
	// END Ricardo Romão
        bg.compile();

        SimpleUniverse su = new SimpleUniverse(canvas);
        su.getViewingPlatform().setNominalViewingTransform();
        su.addBranchGraph(bg);

        this.paint(this.getGraphics());
    }
    private BranchGroup createSceneGraph() {
        BranchGroup root = new BranchGroup();

        Transform3D trans3D = new Transform3D();
        trans3D.rotX(-Math.PI/4.0d);
        trans3D.setScale(0.5);

        TransformGroup spin = new TransformGroup(trans3D);
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

        PickTool.setCapabilities(boardShape, PickTool.INTERSECT_TEST);

        Background bckg = new Background(1.0f, 1.0f, 1.0f);
        bckg.setApplicationBounds(bs);

        root.addChild(bckg);

	PointLight ptlight = new PointLight(new Color3f(Color.RED),
                                            new Point3f(-1f,-1f,2f),
                                            new Point3f(1f,0f,0f));
	ptlight.setInfluencingBounds(bs);
	root.addChild(ptlight);

        behavior.setSchedulingBounds(bs);
        spin.addChild(behavior);
        spin.addChild(boardShape);
        Shape3D sh;
        for(int i = 0; i <= gp.getBoard().getEndPoint().getX(); i++){
            for(int j = 0; j <= gp.getBoard().getEndPoint().getY(); j++){
                sh = gp.getBoard().getElementShape(new Point(i, j), false);
                PickTool.setCapabilities(sh, PickTool.INTERSECT_FULL);
                //spin.addChild(sh);
		// BEGIN Ricardo Romão
		// Adicionei isto aqui só para fazer um teste com mudança de cores
		Shape3D shapezorro = gp.getBoard().getElementShape(new Point(i, j), false);
		shapezorro.setCapability(Shape3D.ALLOW_APPEARANCE_WRITE | Shape3D.ALLOW_GEOMETRY_READ);
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
                //(Aqui algo está(va) mal, retorna coordenadas malucas!!)
                //Resolvido
		System.out.println("Tipo: " + s.getClass().getName() + "; Valor: " + s.getName());
	    }
	    else {
		System.out.println("null");
	    }
	}
	// END Ricard Romão
    }
    
    public void mousePressed(MouseEvent e) {
        
    }

    public void mouseReleased(MouseEvent e) {
        
    }

    public void mouseEntered(MouseEvent e) {
        
    }

    public void mouseExited(MouseEvent e) {
        
    }

    public void actionPerformed(ActionEvent e) {        
        if(e.getActionCommand().equalsIgnoreCase("New")){
            //Isto não funciona! Porquê? :-(
            initGame();
            return;
        }
        if(e.getActionCommand().equalsIgnoreCase("New Rules")){
            //Nem isto!
            //renderRulesScreen();
            return;
        }
        
    }
    private BranchGroup createRulesSceneGraph(){
        return null;
    }
    private void renderRulesScreen(){
        gc = SimpleUniverse.getPreferredConfiguration();
        canvas = new Canvas3D(gc);
        setLayout(new BorderLayout());
        add(canvas, BorderLayout.CENTER);
        
        bg = createRulesSceneGraph();
        bg.compile();

        canvas.addMouseListener(this);
        pc = new PickCanvas(canvas, bg);
        pc.setMode(PickCanvas.GEOMETRY);

        SimpleUniverse su = new SimpleUniverse(canvas);
        su.getViewingPlatform().setNominalViewingTransform();
        su.addBranchGraph(bg);
//       JRootPane pane = this.getRootPane();
//       Container contentPane = pane.getContentPane();
//       contentPane.setLayout(new BorderLayout());
//       JPanel panel = new JPanel();
//       panel.setVisible(true);
//       JLabel testLabel = new JLabel("Testing");
//       testLabel.setVisible(true);
//       panel.add(testLabel);
//       contentPane.add(panel, BorderLayout.CENTER);
    }
}

