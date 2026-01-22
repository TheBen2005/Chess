package chess;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Objects;
public class PieceMovesCalculator {


    public PieceMovesCalculator() {

    }

    public Collection<ChessMove> BishopMovesCalculator(ChessGame.TeamColor teamColor, ChessPiece.PieceType pieceType, ChessPosition position, ChessBoard Board) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        int row = position.getRow();
        int col = position.getColumn();
        int upd_row = position.getRow();
        int upd_col = position.getColumn();
        //for(int j = 1; j <= 8; j++){
        //for(int i = 1; i <= 8; i++){
        //moves.add(new ChessMove(j, i, null));
        //}

        //}
        while (true) {
            upd_row += 1;
            upd_col += 1;
            if (upd_row > 8 || upd_col > 8) {
                break;
            }
            ChessPiece piece_2 = Board.getPiece(new ChessPosition(upd_row, upd_col));
            if (piece_2 == null) {
                moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), null));
                continue;
            }
            if (piece_2.getTeamColor() == teamColor) {
                break;
            }
            if (piece_2.getTeamColor() != teamColor) {
                moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), null));
                break;
            }
        }
        upd_row = position.getRow();
        upd_col = position.getColumn();
        while (true) {
            upd_row += 1;
            upd_col -= 1;
            if (upd_row > 8 || upd_col <= 0) {
                break;
            }
            ChessPiece piece_2 = Board.getPiece(new ChessPosition(upd_row, upd_col));
            if (piece_2 == null) {
                moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), null));
                continue;
            }
            if (piece_2.getTeamColor() == teamColor) {
                break;
            }
            if (piece_2.getTeamColor() != teamColor) {
                moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), null));
                break;
            }
        }
        upd_row = position.getRow();
        upd_col = position.getColumn();
        while (true) {
            upd_row -= 1;
            upd_col += 1;
            if (upd_row <= 0 || upd_col > 8) {
                break;
            }
            ChessPiece piece_2 = Board.getPiece(new ChessPosition(upd_row, upd_col));
            if (piece_2 == null) {
                moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), null));
                continue;
            }
            if (piece_2.getTeamColor() == teamColor) {
                break;
            }
            if (piece_2.getTeamColor() != teamColor) {
                moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), null));
                break;
            }
        }
        upd_row = position.getRow();
        upd_col = position.getColumn();
        while (true) {
            upd_row -= 1;
            upd_col -= 1;
            if (upd_row <= 0 || upd_col <= 0) {
                break;
            }
            ChessPiece piece_2 = Board.getPiece(new ChessPosition(upd_row, upd_col));
            if (piece_2 == null) {
                moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), null));
                continue;
            }
            if (piece_2.getTeamColor() == teamColor) {
                break;
            }
            if (piece_2.getTeamColor() != teamColor) {
                moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), null));
                break;
            }
        }

        return moves;
    }

    public Collection<ChessMove> RookMovesCalculator(ChessGame.TeamColor teamColor, ChessPiece.PieceType pieceType, ChessPosition position, ChessBoard Board) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        int row = position.getRow();
        int col = position.getColumn();
        int upd_row = position.getRow();
        int upd_col = position.getColumn();
        //for(int j = 1; j <= 8; j++){
        //for(int i = 1; i <= 8; i++){
        //moves.add(new ChessMove(j, i, null));
        //}

        //}
        while (true) {
            upd_col += 1;
            if (upd_row > 8 || upd_col > 8) {
                break;
            }
            ChessPiece piece_2 = Board.getPiece(new ChessPosition(upd_row, upd_col));
            if (piece_2 == null) {
                moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), null));
                continue;
            }
            if (piece_2.getTeamColor() == teamColor) {
                break;
            }
            if (piece_2.getTeamColor() != teamColor) {
                moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), null));
                break;
            }
        }
        upd_row = position.getRow();
        upd_col = position.getColumn();
        while (true) {
            upd_row += 1;
            if (upd_row > 8 || upd_col <= 0) {
                break;
            }
            ChessPiece piece_2 = Board.getPiece(new ChessPosition(upd_row, upd_col));
            if (piece_2 == null) {
                moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), null));
                continue;
            }
            if (piece_2.getTeamColor() == teamColor) {
                break;
            }
            if (piece_2.getTeamColor() != teamColor) {
                moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), null));
                break;
            }
        }
        upd_row = position.getRow();
        upd_col = position.getColumn();
        while (true) {
            upd_row -= 1;
            if (upd_row <= 0 || upd_col >= 8) {
                break;
            }
            ChessPiece piece_2 = Board.getPiece(new ChessPosition(upd_row, upd_col));
            if (piece_2 == null) {
                moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), null));
                continue;
            }
            if (piece_2.getTeamColor() == teamColor) {
                break;
            }
            if (piece_2.getTeamColor() != teamColor) {
                moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), null));
                break;
            }
        }
        upd_row = position.getRow();
        upd_col = position.getColumn();
        while (true) {
            upd_col -= 1;
            if (upd_row <= 0 || upd_col <= 0) {
                break;
            }
            ChessPiece piece_2 = Board.getPiece(new ChessPosition(upd_row, upd_col));
            if (piece_2 == null) {
                moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), null));
                continue;
            }
            if (piece_2.getTeamColor() == teamColor) {
                break;
            }
            if (piece_2.getTeamColor() != teamColor) {
                moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), null));
                break;
            }
        }

        return moves;
    }

    public Collection<ChessMove> PawnMovesCalculatorWhite(ChessGame.TeamColor teamColor, ChessPiece.PieceType pieceType, ChessPosition position, ChessBoard Board) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        int row = position.getRow();
        int col = position.getColumn();
        int upd_row = position.getRow();
        int upd_col = position.getColumn();
        upd_row += 1;
        upd_col += 1;
        if (upd_row >= 1 && upd_row <= 8 && upd_col >= 1 && upd_col <= 8) {
            ChessPiece piece_2 = Board.getPiece(new ChessPosition(upd_row, upd_col));
            if (piece_2 != null) {
                if (piece_2.getTeamColor() != teamColor) {

                    if(row == 7){
                        moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), ChessPiece.PieceType.QUEEN));
                        moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), ChessPiece.PieceType.BISHOP));
                        moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), ChessPiece.PieceType.KNIGHT));
                        moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), ChessPiece.PieceType.ROOK));

                    }
                    else{
                        moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), null));
                    }
                }
            }
        }
        upd_col -= 2;
        if (upd_row >= 1 && upd_row <= 8 && upd_col >= 1 && upd_col <= 8) {
            ChessPiece piece_3 = Board.getPiece(new ChessPosition(upd_row, upd_col));
            if (piece_3 != null) {
                if (piece_3.getTeamColor() != teamColor) {
                    if(row == 7){
                        moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), ChessPiece.PieceType.QUEEN));
                        moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), ChessPiece.PieceType.BISHOP));
                        moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), ChessPiece.PieceType.KNIGHT));
                        moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), ChessPiece.PieceType.ROOK));

                    }
                    else{
                        moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), null));
                    }
                }
            }
        }
        upd_col += 1;
        if (upd_row >= 1 && upd_row <= 8 && upd_col >= 1 && upd_col <= 8) {
            ChessPiece piece_4 = Board.getPiece(new ChessPosition(upd_row, upd_col));
            if (piece_4 == null) {
                if(row == 7){
                    moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), ChessPiece.PieceType.QUEEN));
                    moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), ChessPiece.PieceType.BISHOP));
                    moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), ChessPiece.PieceType.KNIGHT));
                    moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), ChessPiece.PieceType.ROOK));

                }
                else{
                    moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), null));
                }
            }

        }
        ChessPiece piece_5 = Board.getPiece(new ChessPosition(upd_row, upd_col));
        if(piece_5 == null){
            upd_row += 1;
            if (row == 2) {
                if (upd_row >= 1 && upd_row <= 8 && upd_col >= 1 && upd_col <= 8) {
                    ChessPiece piece_6 = Board.getPiece(new ChessPosition(upd_row, upd_col));
                    if (piece_6 == null) {
                        moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), null));
                    }
                }
            }

        }
        return moves;




    }


    public Collection<ChessMove> PawnMovesCalculatorBlack(ChessGame.TeamColor teamColor, ChessPiece.PieceType pieceType, ChessPosition position, ChessBoard Board) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        int row = position.getRow();
        int col = position.getColumn();
        int upd_row = position.getRow();
        int upd_col = position.getColumn();
        upd_row -= 1;
        upd_col += 1;
        if (upd_row >= 1 && upd_row <= 8 && upd_col >= 1 && upd_col <= 8) {
            ChessPiece piece_2 = Board.getPiece(new ChessPosition(upd_row, upd_col));
            if (piece_2 != null) {
                if (piece_2.getTeamColor() != teamColor) {

                    if(row == 2){
                        moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), ChessPiece.PieceType.QUEEN));
                        moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), ChessPiece.PieceType.BISHOP));
                        moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), ChessPiece.PieceType.KNIGHT));
                        moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), ChessPiece.PieceType.ROOK));

                    }
                    else{
                        moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), null));
                    }
                }
            }
        }
        upd_col -= 2;
        if (upd_row >= 1 && upd_row <= 8 && upd_col >= 1 && upd_col <= 8) {
            ChessPiece piece_3 = Board.getPiece(new ChessPosition(upd_row, upd_col));
            if (piece_3 != null) {
                if (piece_3.getTeamColor() != teamColor) {
                    if(row == 2){
                        moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), ChessPiece.PieceType.QUEEN));
                        moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), ChessPiece.PieceType.BISHOP));
                        moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), ChessPiece.PieceType.KNIGHT));
                        moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), ChessPiece.PieceType.ROOK));

                    }
                    else{
                        moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), null));
                    }
                }
            }
        }
        upd_col += 1;
        if (upd_row >= 1 && upd_row <= 8 && upd_col >= 1 && upd_col <= 8) {
            ChessPiece piece_4 = Board.getPiece(new ChessPosition(upd_row, upd_col));
            if (piece_4 == null) {
                if(row == 2){
                    moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), ChessPiece.PieceType.QUEEN));
                    moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), ChessPiece.PieceType.BISHOP));
                    moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), ChessPiece.PieceType.KNIGHT));
                    moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), ChessPiece.PieceType.ROOK));

                }
                else{
                    moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), null));
                }
            }

        }
        ChessPiece piece_5 = Board.getPiece(new ChessPosition(upd_row, upd_col));
        if(piece_5 == null){
            upd_row -= 1;
            if (row == 7) {
                if (upd_row >= 1 && upd_row <= 8 && upd_col >= 1 && upd_col <= 8) {
                    ChessPiece piece_6 = Board.getPiece(new ChessPosition(upd_row, upd_col));
                    if (piece_6 == null) {
                        moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), null));
                    }
                }
            }

        }
        return moves;




    }


    public Collection<ChessMove> KingMovesCalculator(ChessGame.TeamColor teamColor, ChessPiece.PieceType pieceType, ChessPosition position, ChessBoard Board) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        int row = position.getRow();
        int col = position.getColumn();
        int upd_row = position.getRow();
        int upd_col = position.getColumn();
        upd_row += 1;
        if (upd_row >= 1 && upd_row <= 8 && upd_col >= 1 && upd_col <= 8) {
            ChessPiece piece_2 = Board.getPiece(new ChessPosition(upd_row, upd_col));
            if (piece_2 == null){
                moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), null));
            }
            else if (piece_2.getTeamColor() != teamColor){
                moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), null));
            }
        }
        upd_col += 1;
        if (upd_row >= 1 && upd_row <= 8 && upd_col >= 1 && upd_col <= 8) {
            ChessPiece piece_3 = Board.getPiece(new ChessPosition(upd_row, upd_col));
            if (piece_3 == null){
                moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), null));
            }
            else if (piece_3.getTeamColor() != teamColor){
                moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), null));
            }
        }
        upd_row -= 1;
        if (upd_row >= 1 && upd_row <= 8 && upd_col >= 1 && upd_col <= 8) {
            ChessPiece piece_4 = Board.getPiece(new ChessPosition(upd_row, upd_col));
            if (piece_4 == null){
                moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), null));
            }
            else if (piece_4.getTeamColor() != teamColor){
                moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), null));
            }
        }
        upd_row -= 1;
        if (upd_row >= 1 && upd_row <= 8 && upd_col >= 1 && upd_col <= 8) {
            ChessPiece piece_5 = Board.getPiece(new ChessPosition(upd_row, upd_col));
            if (piece_5 == null){
                moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), null));
            }
            else if (piece_5.getTeamColor() != teamColor){
                moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), null));
            }
        }
        upd_col -= 1;
        if (upd_row >= 1 && upd_row <= 8 && upd_col >= 1 && upd_col <= 8) {
            ChessPiece piece_6 = Board.getPiece(new ChessPosition(upd_row, upd_col));
            if (piece_6 == null){
                moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), null));
            }
            else if (piece_6.getTeamColor() != teamColor){
                moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), null));
            }
        }
        upd_col -= 1;
        if (upd_row >= 1 && upd_row <= 8 && upd_col >= 1 && upd_col <= 8) {
            ChessPiece piece_7 = Board.getPiece(new ChessPosition(upd_row, upd_col));
            if (piece_7 == null){
                moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), null));
            }
            else if (piece_7.getTeamColor() != teamColor){
                moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), null));
            }
        }
        upd_row += 1;
        if (upd_row >= 1 && upd_row <= 8 && upd_col >= 1 && upd_col <= 8) {
            ChessPiece piece_8 = Board.getPiece(new ChessPosition(upd_row, upd_col));
            if (piece_8 == null){
                moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), null));
            }
            else if (piece_8.getTeamColor() != teamColor){
                moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), null));
            }
        }
        upd_row += 1;
        if (upd_row >= 1 && upd_row <= 8 && upd_col >= 1 && upd_col <= 8) {
            ChessPiece piece_9 = Board.getPiece(new ChessPosition(upd_row, upd_col));
            if (piece_9 == null){
                moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), null));
            }
            else if (piece_9.getTeamColor() != teamColor){
                moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), null));
            }
        }
        return moves;
    }


    public Collection<ChessMove> KnightMovesCalculator(ChessGame.TeamColor teamColor, ChessPiece.PieceType pieceType, ChessPosition position, ChessBoard Board) {
        ArrayList<ChessMove> moves = new ArrayList<>();
        int row = position.getRow();
        int col = position.getColumn();
        int upd_row = position.getRow();
        int upd_col = position.getColumn();
        upd_row += 1;
        upd_col += 2;
        if (upd_row >= 1 && upd_row <= 8 && upd_col >= 1 && upd_col <= 8) {
            ChessPiece piece_2 = Board.getPiece(new ChessPosition(upd_row, upd_col));
            if (piece_2 == null){
                moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), null));
            }
            else if (piece_2.getTeamColor() != teamColor){
                moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), null));
            }
        }
        upd_row -= 2;
        if (upd_row >= 1 && upd_row <= 8 && upd_col >= 1 && upd_col <= 8) {
            ChessPiece piece_3 = Board.getPiece(new ChessPosition(upd_row, upd_col));
            if (piece_3 == null){
                moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), null));
            }
            else if (piece_3.getTeamColor() != teamColor){
                moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), null));
            }
        }
        upd_row -= 1;
        upd_col -= 1;
        if (upd_row >= 1 && upd_row <= 8 && upd_col >= 1 && upd_col <= 8) {
            ChessPiece piece_4 = Board.getPiece(new ChessPosition(upd_row, upd_col));
            if (piece_4 == null){
                moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), null));
            }
            else if (piece_4.getTeamColor() != teamColor){
                moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), null));
            }
        }
        upd_col -= 2;
        if (upd_row >= 1 && upd_row <= 8 && upd_col >= 1 && upd_col <= 8) {
            ChessPiece piece_5 = Board.getPiece(new ChessPosition(upd_row, upd_col));
            if (piece_5 == null){
                moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), null));
            }
            else if (piece_5.getTeamColor() != teamColor){
                moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), null));
            }
        }
        upd_row += 1;
        upd_col -= 1;
        if (upd_row >= 1 && upd_row <= 8 && upd_col >= 1 && upd_col <= 8) {
            ChessPiece piece_6 = Board.getPiece(new ChessPosition(upd_row, upd_col));
            if (piece_6 == null){
                moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), null));
            }
            else if (piece_6.getTeamColor() != teamColor){
                moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), null));
            }
        }
        upd_row += 2;
        if (upd_row >= 1 && upd_row <= 8 && upd_col >= 1 && upd_col <= 8) {
            ChessPiece piece_7 = Board.getPiece(new ChessPosition(upd_row, upd_col));
            if (piece_7 == null){
                moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), null));
            }
            else if (piece_7.getTeamColor() != teamColor){
                moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), null));
            }
        }
        upd_row += 1;
        upd_col += 1;
        if (upd_row >= 1 && upd_row <= 8 && upd_col >= 1 && upd_col <= 8) {
            ChessPiece piece_8 = Board.getPiece(new ChessPosition(upd_row, upd_col));
            if (piece_8 == null){
                moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), null));
            }
            else if (piece_8.getTeamColor() != teamColor){
                moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), null));
            }
        }
        upd_col += 2;
        if (upd_row >= 1 && upd_row <= 8 && upd_col >= 1 && upd_col <= 8) {
            ChessPiece piece_9 = Board.getPiece(new ChessPosition(upd_row, upd_col));
            if (piece_9 == null){
                moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), null));
            }
            else if (piece_9.getTeamColor() != teamColor){
                moves.add(new ChessMove(position, new ChessPosition(upd_row, upd_col), null));
            }
        }
        return moves;

    }


}
