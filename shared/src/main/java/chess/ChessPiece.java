package chess;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.ArrayList;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private final ChessGame.TeamColor pieceColor;
    private final PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessPiece that = (ChessPiece) o;
        return pieceColor == that.pieceColor && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        //ChessPiece piece = board.getPiece(myPosition);
        PieceMovesCalculator calculator = new PieceMovesCalculator();
        if (type == PieceType.BISHOP){
            return calculator.BishopMovesCalculator(pieceColor, type, myPosition, board);
        }
        else if (type == PieceType.ROOK){
            return calculator.RookMovesCalculator(pieceColor, type, myPosition, board);
        }

        Collection<ChessMove> bishop_moves = calculator.BishopMovesCalculator(pieceColor, type, myPosition, board);
        Collection<ChessMove> rook_moves = calculator.RookMovesCalculator(pieceColor, type, myPosition, board);
        Collection<ChessMove> queen_moves = new ArrayList<>();
        queen_moves.addAll(bishop_moves);
        queen_moves.addAll(rook_moves);
        if (type == PieceType.QUEEN){
            return queen_moves;
        }

//        if (type == PieceType.PAWN){
//            return calculator.PawnMovesCalculator(pieceColor, type, myPosition, board);
//        }
        if (type == PieceType.KING){
            return calculator.KingMovesCalculator(pieceColor, type, myPosition, board);
        }
        if (type == PieceType.KNIGHT){
            return calculator.KnightMovesCalculator(pieceColor, type, myPosition, board);
        }




        return null;



    }

}
