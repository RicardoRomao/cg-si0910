package Player;

import java.awt.Point;
import java.lang.reflect.Type;
import java.util.ArrayList;

/*
 * IAPlayer tem de saber adicionalmente o tamanho
 * do game board para poder gerar os pontos que est�o
 * d�sponiveis.
 */
public class IAPlayer implements IPlayer {
    ArrayList<Point> availMoves;
    ArrayList<Point> planedMoves;
	Point lastPlay; // Auxiliar para no caso de ter um hit tentar calcular direc��es

    private Point getRandomPoint() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

	// M�todo tem de ser publico para num hit com sucesso
	// o game obrigar o IAPlayer a planear as pr�ximas
	// jogadas
    public void planMoves(Type t) {
		// Se planedMoves estiver vazio
		//   gera os pontos para o tipo t
		// Sen�o
		//   tenta calcular a direc��o
		//   Se sucesso
		//     elimina pontos n�o pertencentes � direc��o
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Point[] getElement(Type elem) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Point play() {
		// Se planedMoves n�o estiver vazio
		//   escolhe um ponto aleat�rio do array
		// Sen�o
		//   gera um ponto aleat�rio
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void draw() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}