package chess;

import java.util.Collection;
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
            return calculator.bishopMovesCalculator(pieceColor, type, myPosition, board);
        }
        else if (type == PieceType.ROOK){
            return calculator.rookMovesCalculator(pieceColor, type, myPosition, board);
        }

        Collection<ChessMove> bishopMoves = calculator.bishopMovesCalculator(pieceColor, type, myPosition, board);
        Collection<ChessMove> rookMoves = calculator.rookMovesCalculator(pieceColor, type, myPosition, board);
        Collection<ChessMove> queenMoves = new ArrayList<>();
        queenMoves.addAll(bishopMoves);
        queenMoves.addAll(rookMoves);
        if (type == PieceType.QUEEN){
            return queenMoves;
        }

        if (type == PieceType.PAWN){
            if (pieceColor == ChessGame.TeamColor.WHITE) {
                return calculator.pawnMovesCalculatorWhite(pieceColor, type, myPosition, board);
            }
            else{
                return calculator.pawnMovesCalculatorBlack(pieceColor, type, myPosition, board);
            }
        }
        if (type == PieceType.KING){
            return calculator.kingMovesCalculator(pieceColor, type, myPosition, board);
        }
        if (type == PieceType.KNIGHT){
            return calculator.knightMovesCalculator(pieceColor, type, myPosition, board);
        }




        return null;



    }

}
