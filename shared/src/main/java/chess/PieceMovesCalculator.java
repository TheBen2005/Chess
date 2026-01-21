package chess;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Objects;
public class PieceMovesCalculator {


    public PieceMovesCalculator(){

    }
    public Collection <ChessMove> BishopMovesCalculator(ChessGame.TeamColor teamColor, ChessPiece.PieceType pieceType, ChessPosition position, ChessBoard Board){
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
        while(true){
            upd_row += 1;
            upd_col += 1;
            if(upd_row > 8 || upd_col > 8){
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
        while(true){
            upd_row += 1;
            upd_col -= 1;
            if(upd_row > 8 || upd_col <= 0){
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
        while(true){
            upd_row -= 1;
            upd_col += 1;
            if(upd_row <= 0 || upd_col >= 8){
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
        while(true){
            upd_row -= 1;
            upd_col -= 1;
            if(upd_row <= 0 || upd_col <= 0){
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

    public Collection <ChessMove> RookMovesCalculator(ChessGame.TeamColor teamColor, ChessPiece.PieceType pieceType, ChessPosition position, ChessBoard Board){
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
        while(true){
            upd_col += 1;
            if(upd_row > 8 || upd_col > 8){
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
        while(true){
            upd_row += 1;
            if(upd_row > 8 || upd_col <= 0){
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
        while(true){
            upd_row -= 1;
            if(upd_row <= 0 || upd_col >= 8){
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
        while(true){
            upd_col -= 1;
            if(upd_row <= 0 || upd_col <= 0){
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

}
