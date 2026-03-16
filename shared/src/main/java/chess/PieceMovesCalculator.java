package chess;
import java.util.Collection;
import java.util.ArrayList;

public class PieceMovesCalculator {


    public PieceMovesCalculator() {

    }
    public void addMovesDirection(ArrayList<ChessMove> moves, ChessGame.TeamColor teamColor,
                                    ChessPosition position,
                                    ChessBoard board, int rowDir,
                                    int colDir) {

        int updRow = position.getRow();
        int updCol = position.getColumn();
        while (true) {
            updRow += rowDir;
            updCol += colDir;
            if (updRow > 8 || updCol > 8 || updRow <= 0 || updCol <= 0) {
                break;
            }
            ChessPiece piece2 = board.getPiece(new ChessPosition(updRow, updCol));
            if (piece2 == null) {
                moves.add(new ChessMove(position, new ChessPosition(updRow, updCol), null));
                continue;
            }
            if (piece2.getTeamColor() == teamColor) {
                break;
            }
            if (piece2.getTeamColor() != teamColor) {
                moves.add(new ChessMove(position, new ChessPosition(updRow, updCol), null));
                break;
            }
        }
    }

    public void kingKnightHelper(ArrayList<ChessMove> moves,
                                ChessGame.TeamColor teamColor,
                                 ChessPosition position, ChessBoard board,
                                 int rowDir, int colDir) {

        int updRow = position.getRow();
        int updCol = position.getColumn();
        updRow += rowDir;
        updCol += colDir;
        if (updRow >= 1 && updRow <= 8 && updCol >= 1 && updCol <= 8) {
            ChessPiece piece2 = board.getPiece(new ChessPosition(updRow, updCol));
            if (piece2 == null) {
                moves.add(new ChessMove(position, new ChessPosition(updRow, updCol), null));
            } else if (piece2.getTeamColor() != teamColor) {
                moves.add(new ChessMove(position, new ChessPosition(updRow, updCol), null));
            }
        }
    }

    private void addPromotionMoves(ArrayList<ChessMove> moves,
                                   ChessPosition position,
                                   ChessPosition newPosition){
        moves.add(new ChessMove(position, newPosition, ChessPiece.PieceType.QUEEN));
        moves.add(new ChessMove(position, newPosition, ChessPiece.PieceType.BISHOP));
        moves.add(new ChessMove(position, newPosition, ChessPiece.PieceType.KNIGHT));
        moves.add(new ChessMove(position, newPosition, ChessPiece.PieceType.ROOK));
    }

        public void pawnPromotionHelper(ArrayList<ChessMove> moves,
                                        ChessGame.TeamColor teamColor,
                                        ChessPosition position, ChessBoard board,
                                        int rowDir, int colDir, int correctRow){
            int row = position.getRow();
            int col = position.getColumn();
            int updRow = position.getRow();
            int updCol = position.getColumn();
            updRow += rowDir;
            updCol += colDir;
            if (updRow >= 1 && updRow <= 8 && updCol >= 1 && updCol <= 8) {
                ChessPiece piece2 = board.getPiece(new ChessPosition(updRow, updCol));
                if (piece2 != null) {
                    if (piece2.getTeamColor() != teamColor) {

                        if(row == correctRow){
                            addPromotionMoves(moves, position, new ChessPosition(updRow, updCol));

                        }
                        else{
                            moves.add(new ChessMove(position, new ChessPosition(updRow, updCol), null));
                        }
                    }
                }
            }







    }

    public Collection<ChessMove> bishopMovesCalculator(ChessGame.TeamColor teamColor,
                                                       ChessPiece.PieceType pieceType,
                                                       ChessPosition position,
                                                       ChessBoard board) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        addMovesDirection(moves, teamColor, position, board, 1, 1 );
        addMovesDirection(moves, teamColor, position, board, 1, -1 );
        addMovesDirection(moves, teamColor, position, board, -1, 1 );
        addMovesDirection(moves, teamColor, position, board, -1, -1 );
        return moves;
    }

    public Collection<ChessMove> rookMovesCalculator(ChessGame.TeamColor teamColor,
                                                     ChessPiece.PieceType pieceType,
                                                     ChessPosition position,
                                                     ChessBoard board) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        addMovesDirection(moves, teamColor, position, board, 0, 1 );
        addMovesDirection(moves, teamColor, position, board, 0, -1 );
        addMovesDirection(moves, teamColor, position, board, 1, 0 );
        addMovesDirection(moves, teamColor, position, board, -1, 0 );
        return moves;
    }

    public Collection<ChessMove> pawnMovesCalculatorWhite(ChessGame.TeamColor teamColor,
                                                          ChessPiece.PieceType pieceType,
                                                          ChessPosition position,
                                                          ChessBoard board) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        int row = position.getRow();
        int col = position.getColumn();
        int updRow = position.getRow() + 1;
        int updCol = position.getColumn();
        pawnPromotionHelper(moves, teamColor, position, board, 1, 1, 7);
        pawnPromotionHelper(moves, teamColor, position, board, 1, -1, 7);

        if (updRow >= 1 && updRow <= 8 && updCol >= 1 && updCol <= 8) {
            ChessPiece piece4 = board.getPiece(new ChessPosition(updRow, updCol));
            if (piece4 == null) {
                if (row == 7) {
                    addPromotionMoves(moves, position, new ChessPosition(updRow, updCol));

                } else {
                    moves.add(new ChessMove(position, new ChessPosition(updRow, updCol), null));
                }

                if (row == 2) {
                    updRow += 1;
                    ChessPiece piece5 = board.getPiece(new ChessPosition(updRow, updCol));
                    if (piece5 == null) {
                        moves.add(new ChessMove(position, new ChessPosition(updRow, updCol), null));
                    }
                }
            }
        }

        return moves;

    }


    public Collection<ChessMove> pawnMovesCalculatorBlack(ChessGame.TeamColor teamColor,
                                                          ChessPiece.PieceType pieceType,
                                                          ChessPosition position,
                                                          ChessBoard board) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        int row = position.getRow();
        int col = position.getColumn();
        int updRow = position.getRow() - 1;
        int updCol = position.getColumn();
        pawnPromotionHelper(moves, teamColor, position, board, -1, 1, 2);
        pawnPromotionHelper(moves, teamColor, position, board, -1, -1, 2);

        if (updRow >= 1 && updRow <= 8 && updCol >= 1 && updCol <= 8) {
            ChessPiece piece4 = board.getPiece(new ChessPosition(updRow, updCol));
            if (piece4 == null) {
                if (row == 2) {
                    addPromotionMoves(moves, position, new ChessPosition(updRow, updCol));

                } else {
                    moves.add(new ChessMove(position, new ChessPosition(updRow, updCol), null));
                }

                if (row == 7) {
                    updRow -= 1;
                    ChessPiece piece5 = board.getPiece(new ChessPosition(updRow, updCol));
                    if (piece5 == null) {
                        moves.add(new ChessMove(position, new ChessPosition(updRow, updCol), null));
                    }
                }
            }
        }

        return moves;




    }


    public Collection<ChessMove> kingMovesCalculator(ChessGame.TeamColor teamColor,
                                                     ChessPiece.PieceType pieceType,
                                                     ChessPosition position,
                                                     ChessBoard board) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        int row = position.getRow();
        int col = position.getColumn();
        kingKnightHelper(moves, teamColor, position, board, 1, 0);
        kingKnightHelper(moves, teamColor, position, board, 1, 1);
        kingKnightHelper(moves, teamColor, position, board, 0, 1);
        kingKnightHelper(moves, teamColor, position, board, -1, 1);
        kingKnightHelper(moves, teamColor, position, board, -1, 0);
        kingKnightHelper(moves, teamColor, position, board, -1, -1);
        kingKnightHelper(moves, teamColor, position, board, 0, -1);
        kingKnightHelper(moves, teamColor, position, board, 1, -1);
        return moves;
    }


    public Collection<ChessMove> knightMovesCalculator(ChessGame.TeamColor teamColor,
                                                       ChessPiece.PieceType pieceType,
                                                       ChessPosition position,
                                                       ChessBoard board) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        int row = position.getRow();
        int col = position.getColumn();
        kingKnightHelper(moves, teamColor, position, board, 2, 1);
        kingKnightHelper(moves, teamColor, position, board, 2, -1);
        kingKnightHelper(moves, teamColor, position, board, -2, 1);
        kingKnightHelper(moves, teamColor, position, board, -2, -1);
        kingKnightHelper(moves, teamColor, position, board, 1, 2);
        kingKnightHelper(moves, teamColor, position, board, 1, -2);
        kingKnightHelper(moves, teamColor, position, board, -1, 2);
        kingKnightHelper(moves, teamColor, position, board, -1, -2);
        return moves;

    }


}
