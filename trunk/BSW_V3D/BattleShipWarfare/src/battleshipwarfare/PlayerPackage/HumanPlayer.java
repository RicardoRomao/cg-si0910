package battleshipwarfare.PlayerPackage;

import battleshipwarfare.Boardpackage.*;
import battleshipwarfare.Elementspackage.*;
import java.util.Scanner;

public class HumanPlayer implements IPlayer {

    private String _playerName;
    private IBoard _shootingBoard;
    private PlayerType _playerType;

    public HumanPlayer(){
        this("Human Player");
    }
    public HumanPlayer(String playerName){
        _playerName = playerName;
        _shootingBoard = new Board();
        _playerType = PlayerType.HUMAN;
    }

    private boolean validInput(int x, int y, int d){
        if(x < 0 || x > _shootingBoard.getEndPoint().getX()){
            System.out.println("Coordenada X deve pertencer a {1.."
                    + _shootingBoard.getEndPoint().getX() + 1 + "}.");
            return false;
        }
        if(y < 0 || y > _shootingBoard.getEndPoint().getY()){
            System.out.println("Coordenada Y deve pertencer a {A.." +
                    (char)('A' + (_shootingBoard.getEndPoint().getY() + 1)) + "}.");
            return false;
        }
        if(d < 0 || d > 3){
            System.out.println("A direcção deve pertencer a {0..3}");
            return false;
        }
        return true;
    }

    public String getName(){
        return _playerName;
    }
    public PlayerType getPlayerType(){
        return _playerType;
    }

    public IElement getNewElement(ElementType type) {
        Scanner s = new Scanner(System.in);
        int x, y, d;
        do{
            System.out.println("Coordenada X {1.." + (1 + _shootingBoard.getEndPoint().getX()) + "}.");
            System.out.println("Coordenada Y {A.." +
                    (char)('A' + (_shootingBoard.getEndPoint().getY())) + "}.");
            System.out.println("Direcção (0=N; 1=S; 2=E; 3=W).");
            System.out.println("Coloque o seu " + type.toString());// Element.toString(type.ordinal()));
            System.out.print("Coordenada X = ");
            x = s.nextInt() - 1;
            System.out.print("Coordenada Y = ");
            y = s.next().toUpperCase().charAt(0) - 'A';
            System.out.print("Direcção = ");
            d = s.nextInt();
        }while(!validInput(x, y, d));

        Point direction = IElement.cardinalPoints[d];
        if(type == ElementType.AIRCRAFT)
            return new AircraftElement(new Point(x, y), direction);
        return new LineElement(type, new Point(x, y), direction);
    }

    public Point Play() {
        Scanner s = new Scanner(System.in);
        while(true){
            System.out.println("Indique a sua jogada (x, y)");
            System.out.print("X = ");
            int x = s.nextInt() - 1;
            System.out.print("Y = ");
            int y = s.next().toUpperCase().charAt(0) - 'A';
            Point p = new Point(x, y);
            if(_shootingBoard.isInBounds(p))
                return p;
            System.out.println("Coordenadas inválidas!");
        }

        
    }
    public void notifyHit(ElementType type, ElementStatus status){
        return;
    }

    
}
