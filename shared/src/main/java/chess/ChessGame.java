package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {


    private TeamColor current_team;
    private ChessBoard board;

    public ChessGame() {
        this.current_team = TeamColor.WHITE;
        this.board = new ChessBoard();
        this.board.resetBoard();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessGame chessGame = (ChessGame) o;
        return current_team == chessGame.current_team && Objects.equals(board, chessGame.board);
    }

    @Override
    public int hashCode() {
        return Objects.hash(current_team, board);
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return current_team;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        current_team = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        PieceMovesCalculator calculator = new PieceMovesCalculator();
        ChessPiece piece = board.getPiece(startPosition);
        if(piece == null){
            return null;
        }
        else if(piece.getPieceType() == ChessPiece.PieceType.KING){
            return calculator.KingMovesCalculator(current_team, ChessPiece.PieceType.KING, startPosition, board);
        }
        else if(piece.getPieceType() == ChessPiece.PieceType.ROOK){
            return calculator.RookMovesCalculator(current_team, ChessPiece.PieceType.ROOK, startPosition, board);
        }
        else if(piece.getPieceType() == ChessPiece.PieceType.KNIGHT){
            return calculator.KnightMovesCalculator(current_team, ChessPiece.PieceType.KNIGHT, startPosition, board);
        }
        else if(piece.getPieceType() == ChessPiece.PieceType.BISHOP){
            return calculator.BishopMovesCalculator(current_team, ChessPiece.PieceType.BISHOP, startPosition, board);
        }
        else if(piece.getPieceType() == ChessPiece.PieceType.QUEEN){
            Collection<ChessMove> bishop_moves = calculator.BishopMovesCalculator(current_team, ChessPiece.PieceType.KING, startPosition, board);
            Collection<ChessMove> rook_moves = calculator.RookMovesCalculator(current_team, ChessPiece.PieceType.KING, startPosition, board);
            Collection<ChessMove> queen_moves = new ArrayList<>();
            queen_moves.addAll(bishop_moves);
            queen_moves.addAll(rook_moves);
            return queen_moves;
        }
        else if(piece.getPieceType() == ChessPiece.PieceType.PAWN){
            if(piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                return calculator.PawnMovesCalculatorWhite(current_team, ChessPiece.PieceType.PAWN, startPosition, board);
            }
            else if(piece.getTeamColor() == ChessGame.TeamColor.BLACK) {
                return calculator.PawnMovesCalculatorBlack(current_team, ChessPiece.PieceType.PAWN, startPosition, board);
            }
            return null;
        }


        return null;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to perform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPosition start_position = move.getStartPosition();
        ChessPosition end_position = move.getEndPosition();
        ChessPiece piece = board.getPiece(start_position);
        ChessPiece.PieceType promotion_piece = move.getPromotionPiece();
        Collection<ChessMove> valid_moves = validMoves(start_position);
        if(piece == null){
            throw new RuntimeException("No Piece here.");
        }
        else if(piece.getTeamColor() != current_team){
            throw new RuntimeException("Not your turn.");
        }
        Collection<ChessMove> legal_moves = validMoves(start_position);
        if(legal_moves.contains(move)){
             if(piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                 int x = end_position.getRow();
                 if (x == 8 && piece.getPieceType() == ChessPiece.PieceType.PAWN) {
                     board.addPiece(end_position, new ChessPiece(piece.getTeamColor(), promotion_piece));
                     board.addPiece(start_position, null);
                 } else {
                     board.addPiece(end_position, new ChessPiece(piece.getTeamColor(), promotion_piece));
                     board.addPiece(start_position, null);
                 }
             }

            else if(piece.getTeamColor() == ChessGame.TeamColor.BLACK) {
                 int y = end_position.getRow();
                 if (y == 1 && piece.getPieceType() == ChessPiece.PieceType.PAWN) {
                     board.addPiece(end_position, new ChessPiece(piece.getTeamColor(), promotion_piece));
                     board.addPiece(start_position, new ChessPiece(TeamColor.BLACK, null));
                 } else {
                     board.addPiece(end_position, new ChessPiece(piece.getTeamColor(), promotion_piece));
                     board.addPiece(start_position, null);
                 }
             }
        else{
            throw new RuntimeException("Not Legal Move.");
        }
        if(current_team == ChessGame.TeamColor.WHITE){
            current_team = ChessGame.TeamColor.BLACK;
        }
        else if(current_team == ChessGame.TeamColor.BLACK){
            current_team = ChessGame.TeamColor.WHITE;
        }



        }

    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(ChessGame.TeamColor teamColor) {
        ChessPosition king_position;
        for(int i = 0; i <= 8; i++){
            for(int j = 0; j <= 8; j++){
                ChessPosition position = new ChessPosition(i, j);
                ChessPiece piece = board.getPiece(position);
                    if(piece.getPieceType() == ChessPiece.PieceType.KING && piece.getTeamColor() == teamColor){
                        king_position = position;
                    }

            }
        }
        for(int i = 0; i <= 8; i++){
            for(int j = 0; j <= 8; j++){
                ChessPosition position = new ChessPosition(i, j);
                ChessPiece piece = board.getPiece(position);
                Collection<ChessMove> legal_moves = validMoves(position);
                if(legal_moves.contains(king_position)){
                    ChessPosition king_position = position;
                }

            }
        }



        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(ChessGame.TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves while not in check.
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(ChessGame.TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return this.board;
    }
}
