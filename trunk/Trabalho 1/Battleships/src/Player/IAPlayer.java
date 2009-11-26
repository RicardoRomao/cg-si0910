package Player;

import java.awt.Point;
import java.lang.reflect.Type;
import java.util.ArrayList;

/*
 * IAPlayer tem de saber adicionalmente o tamanho
 * do game board para poder gerar os pontos que estão
 * dísponiveis.
 */
public class IAPlayer implements IPlayer {
    ArrayList<Point> availMoves;
    ArrayList<Point> planedMoves;
	Point lastPlay; // Auxiliar para no caso de ter um hit tentar calcular direcções

    private Point getRandomPoint() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

	// Método tem de ser publico para num hit com sucesso
	// o game obrigar o IAPlayer a planear as próximas
	// jogadas
    public void planMoves(Type t) {
		// Se planedMoves estiver vazio
		//   gera os pontos para o tipo t
		// Senão
		//   tenta calcular a direcção
		//   Se sucesso
		//     elimina pontos não pertencentes à direcção
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Point[] getElement(Type elem) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Point play() {
		// Se planedMoves não estiver vazio
		//   escolhe um ponto aleatório do array
		// Senão
		//   gera um ponto aleatório
        throw new UnsupportedOperationException("Not supported yet.");
    }
}