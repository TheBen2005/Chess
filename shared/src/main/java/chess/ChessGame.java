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


    private TeamColor currentTeam;
    private ChessBoard board;

    public ChessGame() {
        this.currentTeam = TeamColor.WHITE;
        this.board = new ChessBoard();
        this.board.resetBoard();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessGame chessGame = (ChessGame) o;
        return currentTeam == chessGame.currentTeam && Objects.equals(board, chessGame.board);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentTeam, board);
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return currentTeam;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        currentTeam = team;
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
        Collection<ChessMove> allowedMoves = new ArrayList<>();

        if (piece == null) {
            return null;
        }
        Collection<ChessMove> availableMoves = piece.pieceMoves(board, startPosition);
        return helperValidMoves(availableMoves, piece);
    }


    private Collection<ChessMove> helperValidMoves(
            Collection<ChessMove> availableMoves, ChessPiece piece){
        Collection<ChessMove> allowedMoves = new ArrayList<>();
        for(ChessMove move : availableMoves){
            ChessPosition position = move.getStartPosition();
            ChessPosition endPosition = move.getEndPosition();
            ChessPiece capturedPiece = board.getPiece(endPosition);
            board.addPiece(endPosition, piece);
            board.addPiece(position, null);
            if(isInCheck(piece.getTeamColor())){
                board.addPiece(position, piece);
                board.addPiece(endPosition, capturedPiece);
            }
            else{
                board.addPiece(position, piece);
                board.addPiece(endPosition, capturedPiece);
                allowedMoves.add(move);
            }
        }
        return allowedMoves;
    }







    /**
     * Makes a move in a chess game
     *
     * @param move chess move to perform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPosition position = move.getStartPosition();
        ChessPosition endPosition = move.getEndPosition();
        ChessPiece piece = board.getPiece(position);
        ChessPiece.PieceType promotionPiece = move.getPromotionPiece();
        if(piece == null){
            throw new InvalidMoveException("No Piece here.");
        }
        if(isInCheck(piece.getTeamColor())){
            throw new InvalidMoveException("Cannot move in check.");
        }
        else if(piece.getTeamColor() != currentTeam){
            throw new InvalidMoveException("Not your turn.");
        }
        Collection<ChessMove> legalMoves = validMoves(position);
        if(legalMoves != null) {
            if (legalMoves.contains(move)) {
                ChessPiece capturedPiece = board.getPiece(endPosition);
                if (piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                    int x = endPosition.getRow();
                    if (x == 8 && piece.getPieceType() == ChessPiece.PieceType.PAWN) {
                        board.addPiece(endPosition, new ChessPiece(piece.getTeamColor(), promotionPiece));
                        board.addPiece(position, null);
                        if (isInCheck(piece.getTeamColor())) {
                            board.addPiece(endPosition, capturedPiece);
                            board.addPiece(position, piece);
                            throw new InvalidMoveException("Can't move into check.");
                        }
                    } else {
                        board.addPiece(endPosition, piece);
                        board.addPiece(position, null);
                        if (isInCheck(piece.getTeamColor())) {
                            board.addPiece(endPosition, capturedPiece);
                            board.addPiece(position, piece);
                            throw new InvalidMoveException("Can't move into check.");
                        }
                    }
                } else if (piece.getTeamColor() == ChessGame.TeamColor.BLACK) {
                    int y = endPosition.getRow();
                    if (y == 1 && piece.getPieceType() == ChessPiece.PieceType.PAWN) {
                        board.addPiece(endPosition, new ChessPiece(piece.getTeamColor(), promotionPiece));
                        board.addPiece(position, null);
                        if (isInCheck(piece.getTeamColor())) {
                            board.addPiece(endPosition, capturedPiece);
                            board.addPiece(position, piece);
                            throw new InvalidMoveException("Can't move into check.");
                        }
                    } else {
                        board.addPiece(endPosition, piece);
                        board.addPiece(position, null);

                        if (isInCheck(piece.getTeamColor())) {
                            board.addPiece(endPosition, capturedPiece);
                            board.addPiece(position, piece);
                            throw new InvalidMoveException("Can't move into check.");
                        }
                    }
                }
            }
            else {
                throw new InvalidMoveException("Not Legal Move.");
            }
            if (currentTeam == ChessGame.TeamColor.WHITE) {
                currentTeam = ChessGame.TeamColor.BLACK;
            }
            else if (currentTeam == ChessGame.TeamColor.BLACK) {
                currentTeam = ChessGame.TeamColor.WHITE;
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
        ChessPosition kingPosition = new ChessPosition(0, 0);
        for(int i = 1; i <= 8; i++){
            for(int j = 1; j <= 8; j++){
                ChessPosition position = new ChessPosition(i, j);
                ChessPiece piece = board.getPiece(position);
                if(piece != null) {
                    if (piece.getPieceType() == ChessPiece.PieceType.KING && piece.getTeamColor() == teamColor) {
                        kingPosition = position;
                    }
                }
            }
        }
        for(int i = 1; i <= 8; i++){
            for(int j = 1; j <= 8; j++){
                ChessPosition position = new ChessPosition(i, j);
                ChessPiece piece = board.getPiece(position);
                PieceMovesCalculator calculator = new PieceMovesCalculator();
                Collection<ChessMove> legalMoves = new ArrayList<>();
                if(piece != null){
                if(piece.getPieceType() == ChessPiece.PieceType.BISHOP){
                    legalMoves = calculator.bishopMovesCalculator(piece.getTeamColor(), piece.getPieceType(), position, board);
                }
                else if(piece.getPieceType() == ChessPiece.PieceType.KNIGHT){
                    legalMoves = calculator.knightMovesCalculator(piece.getTeamColor(), piece.getPieceType(), position, board);
                }
                else if(piece.getPieceType() == ChessPiece.PieceType.ROOK){
                    legalMoves = calculator.rookMovesCalculator(piece.getTeamColor(), piece.getPieceType(), position, board);
                }
                else if(piece.getPieceType() == ChessPiece.PieceType.PAWN && piece.getTeamColor() == TeamColor.WHITE){
                    legalMoves = calculator.pawnMovesCalculatorWhite(piece.getTeamColor(), piece.getPieceType(), position, board);
                }
                else if(piece.getPieceType() == ChessPiece.PieceType.PAWN && piece.getTeamColor() == TeamColor.BLACK){
                    legalMoves = calculator.pawnMovesCalculatorBlack(piece.getTeamColor(), piece.getPieceType(), position, board);
                }
                else if(piece.getPieceType() == ChessPiece.PieceType.KING){
                    legalMoves = calculator.kingMovesCalculator(piece.getTeamColor(), piece.getPieceType(), position, board);
                }
                else if(piece.getPieceType() == ChessPiece.PieceType.QUEEN){
                    Collection<ChessMove> bishopMoves = calculator.bishopMovesCalculator(
                            piece.getTeamColor(), piece.getPieceType(), position, board);
                    Collection<ChessMove> rookMoves = calculator.rookMovesCalculator(piece.getTeamColor(), piece.getPieceType(), position, board);
                    Collection<ChessMove> queenMoves = new ArrayList<>();
                    queenMoves.addAll(bishopMoves);
                    queenMoves.addAll(rookMoves);
                    legalMoves = queenMoves;
                }
                if(legalMoves != null) {
                    if ((legalMoves.contains(new ChessMove(position, kingPosition, null))
                            || legalMoves.contains(new ChessMove(position, kingPosition, ChessPiece.PieceType.ROOK))
                            || legalMoves.contains(new ChessMove(position, kingPosition, ChessPiece.PieceType.BISHOP))
                            || legalMoves.contains(new ChessMove(position, kingPosition, ChessPiece.PieceType.KNIGHT))
                            || legalMoves.contains(new ChessMove(position, kingPosition, ChessPiece.PieceType.QUEEN)))
                            && piece.getTeamColor() != teamColor) {
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
        ChessPosition kingPosition = new ChessPosition(0, 0);
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                ChessPosition position = new ChessPosition(i, j);
                ChessPiece piece = board.getPiece(position);
                if (piece != null) {
                    if (piece.getPieceType() == ChessPiece.PieceType.KING && piece.getTeamColor() == teamColor) {
                        kingPosition = position;
                    }
                }
            }
        }
        ChessPiece king = board.getPiece(kingPosition);
        Collection<ChessMove> movesAllowed = validMoves(kingPosition);
        if (isInCheck(king.getTeamColor())) {
            for (int i = 1; i <= 8; i++) {
                for (int j = 1; j <= 8; j++) {
                    ChessPosition position = new ChessPosition(i, j);
                    ChessPiece piece = board.getPiece(position);
                    if (piece != null && piece.getTeamColor() == teamColor) {
                        Collection<ChessMove> blockMoves = validMoves(position);
                        if (blockMoves.size() >= 1) {
                            return false;
                        }
                    }
                }
            }
            return true;
        }
        return false;

    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves while not in check.
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(ChessGame.TeamColor teamColor) {
        ChessPosition kingPosition = new ChessPosition(0, 0);
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                ChessPosition position = new ChessPosition(i, j);
                ChessPiece piece = board.getPiece(position);
                if (piece != null) {
                    if (piece.getPieceType() == ChessPiece.PieceType.KING && piece.getTeamColor() == teamColor) {
                        kingPosition = position;
                    }
                }
            }
        }
        ChessPiece king = board.getPiece(kingPosition);

        if (!isInCheck(king.getTeamColor())) {
            for (int i = 1; i <= 8; i++) {
                for (int j = 1; j <= 8; j++) {
                    ChessPosition position = new ChessPosition(i, j);
                    ChessPiece piece = board.getPiece(position);
                    if (piece != null && piece.getTeamColor() == teamColor) {
                        Collection<ChessMove> blockMoves = validMoves(position);
                        if (blockMoves != null && blockMoves.size() >= 1) {
                            return false;
                        }
                    }
                }
            }
            return true;
        }
        return false;

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
