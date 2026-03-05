package chess;
import java.util.Collection;
import java.util.ArrayList;

public class PieceMovesCalculator {


    public PieceMovesCalculator() {

    }

    public Collection<ChessMove> bishopMovesCalculator(ChessGame.TeamColor teamColor, ChessPiece.PieceType pieceType, ChessPosition position, ChessBoard board) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        int row = position.getRow();
        int col = position.getColumn();
        int updRow = position.getRow();
        int updCol = position.getColumn();
        while (true) {
            updRow += 1;
            updCol += 1;
            if (updRow > 8 || updCol > 8) {
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
        updRow = position.getRow();
        updCol = position.getColumn();
        while (true) {
            updRow += 1;
            updCol -= 1;
            if (updRow > 8 || updCol <= 0) {
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
        updRow = position.getRow();
        updCol = position.getColumn();
        while (true) {
            updRow -= 1;
            updCol += 1;
            if (updRow <= 0 || updCol > 8) {
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
        updRow = position.getRow();
        updCol = position.getColumn();
        while (true) {
            updRow -= 1;
            updCol -= 1;
            if (updRow <= 0 || updCol <= 0) {
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

        return moves;
    }

    public Collection<ChessMove> rookMovesCalculator(ChessGame.TeamColor teamColor, ChessPiece.PieceType pieceType, ChessPosition position, ChessBoard board) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        int row = position.getRow();
        int col = position.getColumn();
        int updRow = position.getRow();
        int updCol = position.getColumn();
        while (true) {
            updCol += 1;
            if (updRow > 8 || updCol > 8) {
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
        updRow = position.getRow();
        updCol = position.getColumn();
        while (true) {
            updRow += 1;
            if (updRow > 8 || updCol <= 0) {
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
        updRow = position.getRow();
        updCol = position.getColumn();
        while (true) {
            updRow -= 1;
            if (updRow <= 0 || updCol > 8) {
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
        updRow = position.getRow();
        updCol = position.getColumn();
        while (true) {
            updCol -= 1;
            if (updRow <= 0 || updCol <= 0) {
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

        return moves;
    }

    public Collection<ChessMove> pawnMovesCalculatorWhite(ChessGame.TeamColor teamColor, ChessPiece.PieceType pieceType, ChessPosition position, ChessBoard board) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        int row = position.getRow();
        int col = position.getColumn();
        int updRow = position.getRow();
        int updCol = position.getColumn();
        updRow += 1;
        updCol += 1;
        if (updRow >= 1 && updRow <= 8 && updCol >= 1 && updCol <= 8) {
            ChessPiece piece2 = board.getPiece(new ChessPosition(updRow, updCol));
            if (piece2 != null) {
                if (piece2.getTeamColor() != teamColor) {

                    if(row == 7){
                        moves.add(new ChessMove(position, new ChessPosition(updRow, updCol), ChessPiece.PieceType.QUEEN));
                        moves.add(new ChessMove(position, new ChessPosition(updRow, updCol), ChessPiece.PieceType.BISHOP));
                        moves.add(new ChessMove(position, new ChessPosition(updRow, updCol), ChessPiece.PieceType.KNIGHT));
                        moves.add(new ChessMove(position, new ChessPosition(updRow, updCol), ChessPiece.PieceType.ROOK));

                    }
                    else{
                        moves.add(new ChessMove(position, new ChessPosition(updRow, updCol), null));
                    }
                }
            }
        }
        updCol -= 2;
        if (updRow >= 1 && updRow <= 8 && updCol >= 1 && updCol <= 8) {
            ChessPiece piece3 = board.getPiece(new ChessPosition(updRow, updCol));
            if (piece3 != null) {
                if (piece3.getTeamColor() != teamColor) {
                    if(row == 7){
                        moves.add(new ChessMove(position, new ChessPosition(updRow, updCol), ChessPiece.PieceType.QUEEN));
                        moves.add(new ChessMove(position, new ChessPosition(updRow, updCol), ChessPiece.PieceType.BISHOP));
                        moves.add(new ChessMove(position, new ChessPosition(updRow, updCol), ChessPiece.PieceType.KNIGHT));
                        moves.add(new ChessMove(position, new ChessPosition(updRow, updCol), ChessPiece.PieceType.ROOK));

                    }
                    else{
                        moves.add(new ChessMove(position, new ChessPosition(updRow, updCol), null));
                    }
                }
            }
        }
        updCol += 1;
        if (updRow >= 1 && updRow <= 8 && updCol >= 1 && updCol <= 8) {
            ChessPiece piece4 = board.getPiece(new ChessPosition(updRow, updCol));
            if (piece4 == null) {
                if(row == 7){
                    moves.add(new ChessMove(position, new ChessPosition(updRow, updCol), ChessPiece.PieceType.QUEEN));
                    moves.add(new ChessMove(position, new ChessPosition(updRow, updCol), ChessPiece.PieceType.BISHOP));
                    moves.add(new ChessMove(position, new ChessPosition(updRow, updCol), ChessPiece.PieceType.KNIGHT));
                    moves.add(new ChessMove(position, new ChessPosition(updRow, updCol), ChessPiece.PieceType.ROOK));

                }
                else{
                    moves.add(new ChessMove(position, new ChessPosition(updRow, updCol), null));
                }
            }

        }
        if (updRow >= 1 && updRow <= 8 && updCol >= 1 && updCol <= 8) {
            ChessPiece piece5 = board.getPiece(new ChessPosition(updRow, updCol));
            if (piece5 == null) {
                updRow += 1;
                if (row == 2) {
                    if (updRow >= 1 && updRow <= 8 && updCol >= 1 && updCol <= 8) {
                        ChessPiece piece6 = board.getPiece(new ChessPosition(updRow, updCol));
                        if (piece6 == null) {
                            moves.add(new ChessMove(position, new ChessPosition(updRow, updCol), null));
                        }
                    }
                }

            }
        }
        return moves;




    }


    public Collection<ChessMove> pawnMovesCalculatorBlack(ChessGame.TeamColor teamColor, ChessPiece.PieceType pieceType, ChessPosition position, ChessBoard board) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        int row = position.getRow();
        int col = position.getColumn();
        int updRow = position.getRow();
        int updCol = position.getColumn();
        updRow -= 1;
        updCol += 1;
        if (updRow >= 1 && updRow <= 8 && updCol >= 1 && updCol <= 8) {
            ChessPiece piece2 = board.getPiece(new ChessPosition(updRow, updCol));
            if (piece2 != null) {
                if (piece2.getTeamColor() != teamColor) {

                    if(row == 2){
                        moves.add(new ChessMove(position, new ChessPosition(updRow, updCol), ChessPiece.PieceType.QUEEN));
                        moves.add(new ChessMove(position, new ChessPosition(updRow, updCol), ChessPiece.PieceType.BISHOP));
                        moves.add(new ChessMove(position, new ChessPosition(updRow, updCol), ChessPiece.PieceType.KNIGHT));
                        moves.add(new ChessMove(position, new ChessPosition(updRow, updCol), ChessPiece.PieceType.ROOK));

                    }
                    else{
                        moves.add(new ChessMove(position, new ChessPosition(updRow, updCol), null));
                    }
                }
            }
        }
        updCol -= 2;
        if (updRow >= 1 && updRow <= 8 && updCol >= 1 && updCol <= 8) {
            ChessPiece piece3 = board.getPiece(new ChessPosition(updRow, updCol));
            if (piece3 != null) {
                if (piece3.getTeamColor() != teamColor) {
                    if(row == 2){
                        moves.add(new ChessMove(position, new ChessPosition(updRow, updCol), ChessPiece.PieceType.QUEEN));
                        moves.add(new ChessMove(position, new ChessPosition(updRow, updCol), ChessPiece.PieceType.BISHOP));
                        moves.add(new ChessMove(position, new ChessPosition(updRow, updCol), ChessPiece.PieceType.KNIGHT));
                        moves.add(new ChessMove(position, new ChessPosition(updRow, updCol), ChessPiece.PieceType.ROOK));

                    }
                    else{
                        moves.add(new ChessMove(position, new ChessPosition(updRow, updCol), null));
                    }
                }
            }
        }
        updCol += 1;
        if (updRow >= 1 && updRow <= 8 && updCol >= 1 && updCol <= 8) {
            ChessPiece piece4 = board.getPiece(new ChessPosition(updRow, updCol));
            if (piece4 == null) {
                if(row == 2){
                    moves.add(new ChessMove(position, new ChessPosition(updRow, updCol), ChessPiece.PieceType.QUEEN));
                    moves.add(new ChessMove(position, new ChessPosition(updRow, updCol), ChessPiece.PieceType.BISHOP));
                    moves.add(new ChessMove(position, new ChessPosition(updRow, updCol), ChessPiece.PieceType.KNIGHT));
                    moves.add(new ChessMove(position, new ChessPosition(updRow, updCol), ChessPiece.PieceType.ROOK));

                }
                else{
                    moves.add(new ChessMove(position, new ChessPosition(updRow, updCol), null));
                }
            }

        }
        if (updRow >= 1 && updRow <= 8 && updCol >= 1 && updCol <= 8) {
        ChessPiece piece5 = board.getPiece(new ChessPosition(updRow, updCol));
        if(piece5 == null) {
            updRow -= 1;
            if (row == 7) {
                if (updRow >= 1 && updRow <= 8 && updCol >= 1 && updCol <= 8) {
                    ChessPiece piece6 = board.getPiece(new ChessPosition(updRow, updCol));
                    if (piece6 == null) {
                        moves.add(new ChessMove(position, new ChessPosition(updRow, updCol), null));
                    }
                }
            }
        }

        }
        return moves;




    }


    public Collection<ChessMove> kingMovesCalculator(ChessGame.TeamColor teamColor, ChessPiece.PieceType pieceType, ChessPosition position, ChessBoard board) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        int row = position.getRow();
        int col = position.getColumn();
        int updRow = position.getRow();
        int updCol = position.getColumn();
        updRow += 1;
        if (updRow >= 1 && updRow <= 8 && updCol >= 1 && updCol <= 8) {
            ChessPiece piece2 = board.getPiece(new ChessPosition(updRow, updCol));
            if (piece2 == null){
                moves.add(new ChessMove(position, new ChessPosition(updRow, updCol), null));
            }
            else if (piece2.getTeamColor() != teamColor){
                moves.add(new ChessMove(position, new ChessPosition(updRow, updCol), null));
            }
        }
        updCol += 1;
        if (updRow >= 1 && updRow <= 8 && updCol >= 1 && updCol <= 8) {
            ChessPiece piece3 = board.getPiece(new ChessPosition(updRow, updCol));
            if (piece3 == null){
                moves.add(new ChessMove(position, new ChessPosition(updRow, updCol), null));
            }
            else if (piece3.getTeamColor() != teamColor){
                moves.add(new ChessMove(position, new ChessPosition(updRow, updCol), null));
            }
        }
        updRow -= 1;
        if (updRow >= 1 && updRow <= 8 && updCol >= 1 && updCol <= 8) {
            ChessPiece piece4 = board.getPiece(new ChessPosition(updRow, updCol));
            if (piece4 == null){
                moves.add(new ChessMove(position, new ChessPosition(updRow, updCol), null));
            }
            else if (piece4.getTeamColor() != teamColor){
                moves.add(new ChessMove(position, new ChessPosition(updRow, updCol), null));
            }
        }
        updRow -= 1;
        if (updRow >= 1 && updRow <= 8 && updCol >= 1 && updCol <= 8) {
            ChessPiece piece5 = board.getPiece(new ChessPosition(updRow, updCol));
            if (piece5 == null){
                moves.add(new ChessMove(position, new ChessPosition(updRow, updCol), null));
            }
            else if (piece5.getTeamColor() != teamColor){
                moves.add(new ChessMove(position, new ChessPosition(updRow, updCol), null));
            }
        }
        updCol -= 1;
        if (updRow >= 1 && updRow <= 8 && updCol >= 1 && updCol <= 8) {
            ChessPiece piece6 = board.getPiece(new ChessPosition(updRow, updCol));
            if (piece6 == null){
                moves.add(new ChessMove(position, new ChessPosition(updRow, updCol), null));
            }
            else if (piece6.getTeamColor() != teamColor){
                moves.add(new ChessMove(position, new ChessPosition(updRow, updCol), null));
            }
        }
        updCol -= 1;
        if (updRow >= 1 && updRow <= 8 && updCol >= 1 && updCol <= 8) {
            ChessPiece piece7 = board.getPiece(new ChessPosition(updRow, updCol));
            if (piece7 == null){
                moves.add(new ChessMove(position, new ChessPosition(updRow, updCol), null));
            }
            else if (piece7.getTeamColor() != teamColor){
                moves.add(new ChessMove(position, new ChessPosition(updRow, updCol), null));
            }
        }
        updRow += 1;
        if (updRow >= 1 && updRow <= 8 && updCol >= 1 && updCol <= 8) {
            ChessPiece piece8 = board.getPiece(new ChessPosition(updRow, updCol));
            if (piece8 == null){
                moves.add(new ChessMove(position, new ChessPosition(updRow, updCol), null));
            }
            else if (piece8.getTeamColor() != teamColor){
                moves.add(new ChessMove(position, new ChessPosition(updRow, updCol), null));
            }
        }
        updRow += 1;
        if (updRow >= 1 && updRow <= 8 && updCol >= 1 && updCol <= 8) {
            ChessPiece piece9 = board.getPiece(new ChessPosition(updRow, updCol));
            if (piece9 == null){
                moves.add(new ChessMove(position, new ChessPosition(updRow, updCol), null));
            }
            else if (piece9.getTeamColor() != teamColor){
                moves.add(new ChessMove(position, new ChessPosition(updRow, updCol), null));
            }
        }
        return moves;
    }


    public Collection<ChessMove> knightMovesCalculator(ChessGame.TeamColor teamColor, ChessPiece.PieceType pieceType, ChessPosition position, ChessBoard board) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        int row = position.getRow();
        int col = position.getColumn();
        int updRow = position.getRow();
        int updCol = position.getColumn();
        updRow += 1;
        updCol += 2;
        if (updRow >= 1 && updRow <= 8 && updCol >= 1 && updCol <= 8) {
            ChessPiece piece2 = board.getPiece(new ChessPosition(updRow, updCol));
            if (piece2 == null){
                moves.add(new ChessMove(position, new ChessPosition(updRow, updCol), null));
            }
            else if (piece2.getTeamColor() != teamColor){
                moves.add(new ChessMove(position, new ChessPosition(updRow, updCol), null));
            }
        }
        updRow -= 2;
        if (updRow >= 1 && updRow <= 8 && updCol >= 1 && updCol <= 8) {
            ChessPiece piece3 = board.getPiece(new ChessPosition(updRow, updCol));
            if (piece3 == null){
                moves.add(new ChessMove(position, new ChessPosition(updRow, updCol), null));
            }
            else if (piece3.getTeamColor() != teamColor){
                moves.add(new ChessMove(position, new ChessPosition(updRow, updCol), null));
            }
        }
        updRow -= 1;
        updCol -= 1;
        if (updRow >= 1 && updRow <= 8 && updCol >= 1 && updCol <= 8) {
            ChessPiece piece4 = board.getPiece(new ChessPosition(updRow, updCol));
            if (piece4 == null){
                moves.add(new ChessMove(position, new ChessPosition(updRow, updCol), null));
            }
            else if (piece4.getTeamColor() != teamColor){
                moves.add(new ChessMove(position, new ChessPosition(updRow, updCol), null));
            }
        }
        updCol -= 2;
        if (updRow >= 1 && updRow <= 8 && updCol >= 1 && updCol <= 8) {
            ChessPiece piece5 = board.getPiece(new ChessPosition(updRow, updCol));
            if (piece5 == null){
                moves.add(new ChessMove(position, new ChessPosition(updRow, updCol), null));
            }
            else if (piece5.getTeamColor() != teamColor){
                moves.add(new ChessMove(position, new ChessPosition(updRow, updCol), null));
            }
        }
        updRow += 1;
        updCol -= 1;
        if (updRow >= 1 && updRow <= 8 && updCol >= 1 && updCol <= 8) {
            ChessPiece piece6 = board.getPiece(new ChessPosition(updRow, updCol));
            if (piece6 == null){
                moves.add(new ChessMove(position, new ChessPosition(updRow, updCol), null));
            }
            else if (piece6.getTeamColor() != teamColor){
                moves.add(new ChessMove(position, new ChessPosition(updRow, updCol), null));
            }
        }
        updRow += 2;
        if (updRow >= 1 && updRow <= 8 && updCol >= 1 && updCol <= 8) {
            ChessPiece piece7 = board.getPiece(new ChessPosition(updRow, updCol));
            if (piece7 == null){
                moves.add(new ChessMove(position, new ChessPosition(updRow, updCol), null));
            }
            else if (piece7.getTeamColor() != teamColor){
                moves.add(new ChessMove(position, new ChessPosition(updRow, updCol), null));
            }
        }
        updRow += 1;
        updCol += 1;
        if (updRow >= 1 && updRow <= 8 && updCol >= 1 && updCol <= 8) {
            ChessPiece piece8 = board.getPiece(new ChessPosition(updRow, updCol));
            if (piece8 == null){
                moves.add(new ChessMove(position, new ChessPosition(updRow, updCol), null));
            }
            else if (piece8.getTeamColor() != teamColor){
                moves.add(new ChessMove(position, new ChessPosition(updRow, updCol), null));
            }
        }
        updCol += 2;
        if (updRow >= 1 && updRow <= 8 && updCol >= 1 && updCol <= 8) {
            ChessPiece piece9 = board.getPiece(new ChessPosition(updRow, updCol));
            if (piece9 == null){
                moves.add(new ChessMove(position, new ChessPosition(updRow, updCol), null));
            }
            else if (piece9.getTeamColor() != teamColor){
                moves.add(new ChessMove(position, new ChessPosition(updRow, updCol), null));
            }
        }
        return moves;

    }


}
