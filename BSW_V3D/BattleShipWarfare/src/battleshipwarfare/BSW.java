package battleshipwarfare;

import battleshipwarfare.Boardpackage.Point;
import battleshipwarfare.Gamepackage.Game;
import battleshipwarfare.Gamepackage.GamePlayer;
import battleshipwarfare.PlayerPackage.HumanPlayer;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.behaviors.keyboard.KeyNavigatorBehavior;
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
import java.awt.GraphicsConfiguration;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
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
import javax.vecmath.Vector3d;


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

    private Game game;                  //Game Object
    private PickCanvas pc;              //Picking
    private static MainFrame mf;        //MainFrame:needs to be shared to allow changing contents
    private Option3D option;            //Determines witch scene to be loaded

    public static void main(String[] args){
        mf = new MainFrame(new BSW(Option3D.INIT), 800, 600);
        mf.setResizable(false);
        mf.setVisible(true);
    }

    public BSW(Option3D option){
        this.option = option;
    }
    @Override
    public void init(){
        GraphicsConfiguration gc = SimpleUniverse.getPreferredConfiguration();
        Canvas3D canvas = new Canvas3D(gc);
        setLayout(new BorderLayout());
        add(canvas, BorderLayout.CENTER);

        BranchGroup bg = null;
        switch(option.ordinal()){
            case 0:
                bg = createInitSceneGrapg();
                break;
            case 3:
                bg = createGameSceneGraph();
                break;
            default:
                break;
        }

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
    }

    private BranchGroup createInitSceneGrapg(){
        BranchGroup root = new BranchGroup();

        BoundingSphere bs = new BoundingSphere();

        Background bckg = new Background(0.0f, 0.0f, 0.0f);
        bckg.setApplicationBounds(bs);
        root.addChild(bckg);       

        Transform3D t3d = new Transform3D();
        t3d.setScale(0.1);
        t3d.setTranslation(new Vector3d(-0.2, 0, 0));
        TransformGroup tg = new TransformGroup(t3d);

        Shape3D button1 = getButton("New Game", 0);
        PickTool.setCapabilities(button1, PickTool.INTERSECT_TEST);
        button1.setCollidable(true);
        tg.addChild(button1);
        
//        Shape3D text1 = getText("New Game");
//        text1.setCollidable(true);
//        tg.addChild(text1);
        

        root.addChild(tg);
        return root;
    }
    private BranchGroup createGameSceneGraph() {
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
    private BranchGroup createRulesSceneGraph(){
        return null;
    }

    private Shape3D getButton(String name, int index){

        Shape3D sh = new Shape3D();
        sh.setName(name);

        int idx = 0;
        GeometryInfo gi = new GeometryInfo(GeometryInfo.POLYGON_ARRAY);

        Point3d[] vertices  = new Point3d[8];

        double startX = (Settings3D.getInitButtonSizeX()/2)*(-1);
        double startY = Settings3D.getInitButtonStartY() - (index*Settings3D.getInitButtonSizeY())
                - (index*Settings3D.getInitButtonMargin());
        double endX = Settings3D.getInitButtonSizeX()/2;
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
        indices[idx++]=0;
        indices[idx++]=1;
        indices[idx++]=3;
        indices[idx++]=2;

        //Face da frente (4)
        indices[idx++]=7;
        indices[idx++]=5;
        indices[idx++]=4;
        indices[idx++]=6;

        //Face Esquerda (4)
        indices[idx++]=5;
        indices[idx++]=1;
        indices[idx++]=0;
        indices[idx++]=4;

        //Face Direita (4)
        indices[idx++]=2;
        indices[idx++]=3;
        indices[idx++]=7;
        indices[idx++]=6;

        //Face Superior (4)
        indices[idx++]=3;
        indices[idx++]=1;
        indices[idx++]=5;
        indices[idx++]=7;

        //Face Inferior (4)
        indices[idx++]=4;
        indices[idx++]=0;
        indices[idx++]=2;
        indices[idx++]=6;

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
    private Shape3D getText(String text){
        Appearance a = new Appearance();
        a.setMaterial(new Material());
        Font font = new Font("Verdana", Font.PLAIN, 1);
        Font3D font3D = new Font3D(font, new FontExtrusion());
        Text3D text3D = new Text3D(font3D, text);
        Shape3D textShape = new Shape3D(text3D);

        return textShape;
    }

    public void mouseClicked(java.awt.event.MouseEvent mouseEvent) {
	switch(option.ordinal()){
            case 0:
                inInitMouseClicked(mouseEvent);
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                inGameMouseClicked(mouseEvent);
                break;
            case 4:
                break;
            default:
                break;
        }
    }

    private void inInitMouseClicked(java.awt.event.MouseEvent mouseEvent){
        pc.setShapeLocation(mouseEvent);
	PickResult result = pc.pickClosest();

	if (result == null) {
	    System.out.println("Nothing picked");
	}
	else {
	    Shape3D s = (Shape3D)result.getNode(PickResult.SHAPE3D);
	    if (s != null) {
		if(s.getName().equalsIgnoreCase("New Game")){
                    mf = new MainFrame(new BSW(Option3D.PLAY), 800, 600);
                    mf.setResizable(false);
                    mf.setVisible(true);
                    return;
                }
	    }
	    else {
		System.out.println("null");
	    }
	}
    }
    private void inGameMouseClicked(java.awt.event.MouseEvent mouseEvent){
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

    @Override
    public void start() {}
    @Override
    public void stop() {}
    public void mousePressed(MouseEvent e){

    }
    public void mouseReleased(MouseEvent e){

    }
    public void mouseEntered(MouseEvent e){

    }
    public void mouseExited(MouseEvent e){

    }
}

