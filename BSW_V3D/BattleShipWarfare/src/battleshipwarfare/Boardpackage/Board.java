package battleshipwarfare.Boardpackage;

import battleshipwarfare.Elementspackage.IElement;
import battleshipwarfare.Settings3D;
import com.sun.j3d.utils.geometry.GeometryInfo;
import com.sun.j3d.utils.geometry.NormalGenerator;
import java.awt.Color;
import java.util.Hashtable;
import javax.media.j3d.Appearance;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Material;
import javax.media.j3d.Shape3D;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;

/**
 * Class that represents a game board.
 * @author RNR
 */
public class Board implements IBoard {

    private Hashtable<Point, IElement> _elements;
    private Point _endPoint;

    /**
     * Parameterless constructor.<br>
     * Initiates a board with the default size.
     */
    public Board() {
        this(IBoard.DEFAULT_ROWS, IBoard.DEFAULT_COLS);
    }

    /**
     * Constructs a board with the specified Rows and Cols.
     * @param rows The number of Rows.
     * @param cols The number of Cols
     */
    public Board(int rows, int cols) {
        _endPoint = new Point(cols - 1, rows - 1);
        _elements = new Hashtable<Point, IElement>();
    }

    public Point getEndPoint() {
        return _endPoint;
    }

    public boolean addElement(IElement elem, boolean withAdjacent) {
        if (isPlaceable(elem, withAdjacent)) {
            Point[] area = elem.getArea();
            for (int i = 0; i < area.length; i++) {
                _elements.put(area[i], elem);
            }
            //System.out.println("Added " + elem.getType().name());
            return true;
        }
        return false;
    }

    public IElement shoot(Point p) {
        IElement elem;
        if ((elem = _elements.get(p)) != null) {
            elem.hit(p);
            return elem;
        }
        return null;
    }

    public void drawInConsole(boolean own) {
        Point p;
        System.out.println(own ? "My Board" : "Opponent Board");
        System.out.println();
        System.out.print(" ");
        for (int i = 0; i <= _endPoint.getY(); i++) {
            System.out.print(i + 1);
        }
        System.out.println();
        for (int i = 0; i <= _endPoint.getY(); i++) {
            System.out.print((char) ('A' + i));
            for (int j = 0; j <= _endPoint.getX(); j++) {
                p = new Point(j, i);
                _elements.get(p).drawInConsole(p, own);
            }
            System.out.println(("|"));
        }
        for (int i = 0; i <= _endPoint.getY(); i++) {
            System.out.print("-");
        }
        System.out.println("-");
    }

    public boolean isInBounds(Point p) {
        return p.getX() >= 0 && p.getY() >= 0 && p.getX() <= _endPoint.getX()
                && p.getY() <= _endPoint.getY();
    }

    public Shape3D getShape() {
        Shape3D sh = new Shape3D();

        int idx = 0;

        GeometryInfo gi = new GeometryInfo(GeometryInfo.POLYGON_ARRAY);
        Point3d[] vertices = new Point3d[8];

        //É necessário contemplar na definição das dimensões do Board a margem entre cells
        //(n cols / 2) + (n cols / 2)= cols margens, mas é necessária mais uma
        //visto 10 cols terem 9 maergens + 2 exteriores

//        double deltaX = ((GameRules.getCurrentRules().getCols()/2))*Settings3D.getBoardCellMargin() +
//                Settings3D.getBoardCellMargin()/2;
//        double deltaY = ((GameRules.getCurrentRules().getRows()/2))*Settings3D.getBoardCellMargin() +
//                Settings3D.getBoardCellMargin()/2;

        double startX = Settings3D.getOwnBoardStartPointX();
        double startY = Settings3D.getOwnBoardStartPointY();
        double endX = Settings3D.getOwnBoardEndPointX();
        double endY = Settings3D.getOwnBoardEndPointY();

        //Pontos das faces [0..7]
        vertices[idx++] = new Point3d(startX, startY, -0.1);     //0
        vertices[idx++] = new Point3d(startX, endY, -0.1);       //1     1 3
        vertices[idx++] = new Point3d(endX, startY, -0.1);       //2     0 2
        vertices[idx++] = new Point3d(endX, endY, -0.1);         //3

        vertices[idx++] = new Point3d(startX, startY, 0.1);   //4
        vertices[idx++] = new Point3d(startX, endY, 0.1);     //5     5 7
        vertices[idx++] = new Point3d(endX, startY, 0.1);     //6     4 6
        vertices[idx++] = new Point3d(endX, endY, 0.1);       //7

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

        Material material = new Material(new Color3f(Color.white),
                new Color3f(0f,0.3f,0f),new Color3f(0f,0.2f,0f),
                new Color3f(Color.yellow),64f);
        material.setLightingEnable(true);

        Appearance appearance = new Appearance();
        appearance.setMaterial(material);
        appearance.setCapability(Appearance.ALLOW_COLORING_ATTRIBUTES_WRITE);
        
/*        appearance.setCapability(Appearance.ALLOW_TEXTURE_READ);
        appearance.setCapability(Appearance.ALLOW_TEXTURE_WRITE);

        URL filename =
            getClass().getClassLoader().getResource("images/water.jpg");
        BufferedImage bi = new BufferedImage(4, 4, BufferedImage.TYPE_INT_RGB);
        TextureLoader loader = new TextureLoader(bi);
        ImageComponent2D image = loader.getImage();
        if(image == null) {
          System.out.println("can't find texture file.");
        }
        Texture2D texture = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA,
        image.getWidth(), image.getHeight());
        texture.setImage(0, image);
        texture.setEnable(true);
//        texture.setMagFilter(Texture.BASE_LEVEL_LINEAR);
  //      texture.setMinFilter(Texture.BASE_LEVEL_LINEAR);
        appearance.setTexture(texture);
*/

        appearance.setColoringAttributes(new ColoringAttributes(new Color3f(1f, 0.3f, 0.2f), ColoringAttributes.NICEST));

        sh.setGeometry(gi.getIndexedGeometryArray());
        sh.setCapability(Shape3D.ALLOW_APPEARANCE_WRITE);
        sh.setAppearance(appearance);
        return sh;
    }

    public Shape3D getElementShape(Point p) {
        return _elements.get(p).getShape(p);
    }

    private boolean isPlaceable(IElement elem, boolean withAdjacent) {
        if (!isInBounds(elem.getArea())) {
            return false;
        }
        Point[] area = withAdjacent ? elem.getAreaWithAdjacent() : elem.getArea();
        for (int i = 0; i < area.length; i++) {
            if (_elements.containsKey(area[i])) {
                return false;
            }
        }
        return true;
    }

    private boolean isInBounds(Point[] area) {
        for (int i = 0; i < area.length && area[i] != null; i++) {
            if (!isInBounds(area[i])) {
                return false;
            }
        }
        return true;
    }
}
