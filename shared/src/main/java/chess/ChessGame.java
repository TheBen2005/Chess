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
        Collection<ChessMove> available_moves = new ArrayList<>();
        Collection<ChessMove> allowed_moves = new ArrayList<>();

        if(piece == null){
            return null;
        }
        else if(piece.getPieceType() == ChessPiece.PieceType.KING){
            available_moves = calculator.KingMovesCalculator(piece.getTeamColor(), ChessPiece.PieceType.KING, startPosition, board);
            for(ChessMove move : available_moves){
               ChessPosition start_position = move.getStartPosition();
               ChessPosition end_position = move.getEndPosition();
               ChessPiece captured_piece = board.getPiece(end_position);
               board.addPiece(end_position, piece);
               board.addPiece(start_position, null);
               if(isInCheck(piece.getTeamColor())){
                   board.addPiece(start_position, piece);
                   board.addPiece(end_position, captured_piece);
               }
               else{
                   board.addPiece(start_position, piece);
                   board.addPiece(end_position, captured_piece);
                   allowed_moves.add(move);
               }
            }
            return allowed_moves;
        }
        else if(piece.getPieceType() == ChessPiece.PieceType.ROOK){
            available_moves = calculator.RookMovesCalculator(piece.getTeamColor(), ChessPiece.PieceType.ROOK, startPosition, board);
            for(ChessMove move : available_moves){
                ChessPosition start_position = move.getStartPosition();
                ChessPosition end_position = move.getEndPosition();
                ChessPiece captured_piece = board.getPiece(end_position);
                board.addPiece(end_position, piece);
                board.addPiece(start_position, null);
                if(isInCheck(piece.getTeamColor())){
                    board.addPiece(start_position, piece);
                    board.addPiece(end_position, captured_piece);
                }
                else{
                    board.addPiece(start_position, piece);
                    board.addPiece(end_position, captured_piece);
                    allowed_moves.add(move);
                }
            }
            return allowed_moves;
        }
        else if(piece.getPieceType() == ChessPiece.PieceType.KNIGHT){
            available_moves = calculator.KnightMovesCalculator(piece.getTeamColor(), ChessPiece.PieceType.KNIGHT, startPosition, board);
            for(ChessMove move : available_moves){
                ChessPosition start_position = move.getStartPosition();
                ChessPosition end_position = move.getEndPosition();
                ChessPiece captured_piece = board.getPiece(end_position);
                board.addPiece(end_position, piece);
                board.addPiece(start_position, null);
                if(isInCheck(piece.getTeamColor())){
                    board.addPiece(start_position, piece);
                    board.addPiece(end_position, captured_piece);
                }
                else{
                    board.addPiece(start_position, piece);
                    board.addPiece(end_position, captured_piece);
                    allowed_moves.add(move);
                }
            }
            return allowed_moves;
        }
        else if(piece.getPieceType() == ChessPiece.PieceType.BISHOP){
            available_moves = calculator.BishopMovesCalculator(piece.getTeamColor(), ChessPiece.PieceType.BISHOP, startPosition, board);
            for(ChessMove move : available_moves){
                ChessPosition start_position = move.getStartPosition();
                ChessPosition end_position = move.getEndPosition();
                ChessPiece captured_piece = board.getPiece(end_position);
                board.addPiece(end_position, piece);
                board.addPiece(start_position, null);
                if(isInCheck(piece.getTeamColor())){
                    board.addPiece(start_position, piece);
                    board.addPiece(end_position, captured_piece);
                }
                else{
                    board.addPiece(start_position, piece);
                    board.addPiece(end_position, captured_piece);
                    allowed_moves.add(move);
                }
            }
            return allowed_moves;
        }
        else if(piece.getPieceType() == ChessPiece.PieceType.QUEEN){
            Collection<ChessMove> bishop_moves = calculator.BishopMovesCalculator(piece.getTeamColor(), ChessPiece.PieceType.BISHOP, startPosition, board);
            Collection<ChessMove> rook_moves = calculator.RookMovesCalculator(piece.getTeamColor(), ChessPiece.PieceType.ROOK, startPosition, board);
            Collection<ChessMove> queen_moves = new ArrayList<>();
            queen_moves.addAll(bishop_moves);
            queen_moves.addAll(rook_moves);
            available_moves = queen_moves;
            for(ChessMove move : available_moves){
                ChessPosition start_position = move.getStartPosition();
                ChessPosition end_position = move.getEndPosition();
                ChessPiece captured_piece = board.getPiece(end_position);
                board.addPiece(end_position, piece);
                board.addPiece(start_position, null);
                if(isInCheck(piece.getTeamColor())){
                    board.addPiece(start_position, piece);
                    board.addPiece(end_position, captured_piece);
                }
                else{
                    board.addPiece(start_position, piece);
                    board.addPiece(end_position, captured_piece);
                    allowed_moves.add(move);
                }
            }
            return allowed_moves;
        }
        else if(piece.getPieceType() == ChessPiece.PieceType.PAWN){
            if(piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                available_moves = calculator.PawnMovesCalculatorWhite(piece.getTeamColor(), ChessPiece.PieceType.PAWN, startPosition, board);
                for(ChessMove move : available_moves){
                    ChessPosition start_position = move.getStartPosition();
                    ChessPosition end_position = move.getEndPosition();
                    ChessPiece captured_piece = board.getPiece(end_position);
                    board.addPiece(end_position, piece);
                    board.addPiece(start_position, null);
                    if(isInCheck(piece.getTeamColor())){
                        board.addPiece(start_position, piece);
                        board.addPiece(end_position, captured_piece);
                    }
                    else{
                        board.addPiece(start_position, piece);
                        board.addPiece(end_position, captured_piece);
                        allowed_moves.add(move);
                    }
                }
                return allowed_moves;
            }
        else if(piece.getTeamColor() == ChessGame.TeamColor.BLACK) {
            available_moves = calculator.PawnMovesCalculatorBlack(piece.getTeamColor(), ChessPiece.PieceType.PAWN, startPosition, board);
            for(ChessMove move : available_moves){
                ChessPosition start_position = move.getStartPosition();
                ChessPosition end_position = move.getEndPosition();
                ChessPiece captured_piece = board.getPiece(end_position);
                board.addPiece(end_position, piece);
                board.addPiece(start_position, null);
                if(isInCheck(piece.getTeamColor())){
                    board.addPiece(start_position, piece);
                    board.addPiece(end_position, captured_piece);
                }
                else{
                    board.addPiece(start_position, piece);
                    board.addPiece(end_position, captured_piece);
                    allowed_moves.add(move);
                }
            }
            return allowed_moves;
        }
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
        if(piece == null){
            throw new InvalidMoveException("No Piece here.");
        }
        if(isInCheck(piece.getTeamColor())){
            throw new InvalidMoveException("Cannot move in check.");
        }
        else if(piece.getTeamColor() != current_team){
            throw new InvalidMoveException("Not your turn.");
        }
        Collection<ChessMove> legal_moves = validMoves(start_position);
        if(legal_moves != null) {
            if (legal_moves.contains(move)) {
                if (piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                    int x = end_position.getRow();
                    if (x == 8 && piece.getPieceType() == ChessPiece.PieceType.PAWN) {
                        board.addPiece(end_position, new ChessPiece(piece.getTeamColor(), promotion_piece));
                        board.addPiece(start_position, null);
                        if (isInCheck(piece.getTeamColor())) {
                            board.addPiece(end_position, null);
                            board.addPiece(start_position, piece);
                            throw new InvalidMoveException("Can't move into check.");
                        }
                    }
                    else {
                        board.addPiece(end_position, piece);
                        board.addPiece(start_position, null);
                        if (isInCheck(piece.getTeamColor())) {
                            board.addPiece(end_position, null);
                            board.addPiece(start_position, piece);
                            throw new InvalidMoveException("Can't move into check.");
                        }
                    }
                }
            } else if (piece.getTeamColor() == ChessGame.TeamColor.BLACK) {
                int y = end_position.getRow();
                if (y == 1 && piece.getPieceType() == ChessPiece.PieceType.PAWN) {
                    board.addPiece(end_position, new ChessPiece(piece.getTeamColor(), promotion_piece));
                    board.addPiece(start_position, null);
                    if (isInCheck(piece.getTeamColor())) {
                        board.addPiece(end_position, null);
                        board.addPiece(start_position, piece);
                        throw new InvalidMoveException("Can't move into check.");
                    }
                }
                else {
                    board.addPiece(end_position, piece);
                    board.addPiece(start_position, null);

                    if (isInCheck(piece.getTeamColor())) {
                        board.addPiece(end_position, null);
                        board.addPiece(start_position, piece);
                        throw new InvalidMoveException("Can't move into check.");
                    }
                }
            }
            else {
                throw new InvalidMoveException("Not Legal Move.");
            }
            if (current_team == ChessGame.TeamColor.WHITE) {
                current_team = ChessGame.TeamColor.BLACK;
            }
            else if (current_team == ChessGame.TeamColor.BLACK) {
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
        ChessPosition king_position = new ChessPosition(0, 0);
        for(int i = 1; i <= 8; i++){
            for(int j = 1; j <= 8; j++){
                ChessPosition position = new ChessPosition(i, j);
                ChessPiece piece = board.getPiece(position);
                if(piece != null) {
                    if (piece.getPieceType() == ChessPiece.PieceType.KING && piece.getTeamColor() == teamColor) {
                        king_position = position;
                    }
                }
            }
        }
        for(int i = 1; i <= 8; i++){
            for(int j = 1; j <= 8; j++){
                ChessPosition position = new ChessPosition(i, j);
                ChessPiece piece = board.getPiece(position);
                PieceMovesCalculator calculator = new PieceMovesCalculator();
                Collection<ChessMove> legal_moves = new ArrayList<>();
                if(piece != null){
                if(piece.getPieceType() == ChessPiece.PieceType.BISHOP){
                    legal_moves = calculator.BishopMovesCalculator(piece.getTeamColor(), piece.getPieceType(), position, board);
                }
                else if(piece.getPieceType() == ChessPiece.PieceType.KNIGHT){
                    legal_moves = calculator.KnightMovesCalculator(piece.getTeamColor(), piece.getPieceType(), position, board);
                }
                else if(piece.getPieceType() == ChessPiece.PieceType.ROOK){
                    legal_moves = calculator.RookMovesCalculator(piece.getTeamColor(), piece.getPieceType(), position, board);
                }
                else if(piece.getPieceType() == ChessPiece.PieceType.PAWN && piece.getTeamColor() == TeamColor.WHITE){
                    legal_moves = calculator.PawnMovesCalculatorWhite(piece.getTeamColor(), piece.getPieceType(), position, board);
                }
                else if(piece.getPieceType() == ChessPiece.PieceType.PAWN && piece.getTeamColor() == TeamColor.BLACK){
                    legal_moves = calculator.PawnMovesCalculatorBlack(piece.getTeamColor(), piece.getPieceType(), position, board);
                }
                else if(piece.getPieceType() == ChessPiece.PieceType.KING){
                    legal_moves = calculator.KingMovesCalculator(piece.getTeamColor(), piece.getPieceType(), position, board);
                }
                else if(piece.getPieceType() == ChessPiece.PieceType.QUEEN){
                    Collection<ChessMove> bishop_moves = calculator.BishopMovesCalculator(piece.getTeamColor(), piece.getPieceType(), position, board);
                    Collection<ChessMove> rook_moves = calculator.RookMovesCalculator(piece.getTeamColor(), piece.getPieceType(), position, board);
                    Collection<ChessMove> queen_moves = new ArrayList<>();
                    queen_moves.addAll(bishop_moves);
                    queen_moves.addAll(rook_moves);
                    legal_moves = queen_moves;
                }
                if(legal_moves != null) {
                    if (legal_moves.contains(new ChessMove(position, king_position, null)) && piece.getTeamColor() != teamColor) {
                        return true;
                    }
                }
                }
            }
        }
        return false;
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
