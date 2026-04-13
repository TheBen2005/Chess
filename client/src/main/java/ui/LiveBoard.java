package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import static ui.EscapeSequences.*;

public class LiveBoard {

    // Board dimensions.
    private static final int BOARD_SIZE_IN_SQUARES = 8;
    private static final int SQUARE_SIZE_IN_PADDED_CHARS = 3;
    private static final int LINE_WIDTH_IN_PADDED_CHARS = 1;

    // Padded characters.
    private static final String EMPTY = " \u2003 ";
    private static final String X = " X ";
    private static final String O = " O ";

    private static Random rand = new Random();


    public static void main(String[] args) {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        out.print(ERASE_SCREEN);

        drawBoard(true);

        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_WHITE);
    }

    public static void drawBoard(boolean color) {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        drawHeaders(out, color);

        drawChess(out, color);
        out.println();
        drawHeaders(out, color);

    }

    private static void drawHeaders(PrintStream out, Boolean color) {
        out.print(SET_BG_COLOR_DARK_GREY);
        out.print(SET_TEXT_COLOR_WHITE);
        String[] headers;
        out.print("\u2003");
        if(color == true) {
            headers = new String[]{"a", "b", "c", "d", "e", "f", "g", "h"};
        }
        else{
            headers = new String[]{"h", "g", "f", "e", "d", "c", "b", "a"};
        }
        for(int i = 0; i < 8; i++){
            out.print("\u2003" + headers[i] +  "\u2003");
        }


    }

    public static void drawChess(PrintStream out, Boolean color){
        ChessGame game = new ChessGame();
        ChessBoard board = new ChessBoard();
        board.resetBoard();
        game.setBoard(board);


        for(int i = 1; i < 9; i++){
            out.println();
            for(int j = 1; j < 9; j++){
                ChessPiece piece = game.getBoard().getPiece(new ChessPosition(i, j));
                if ((j + i) % 2 == 0) {
                    out.print(SET_BG_COLOR_BLUE);
                }
                else{
                    out.print(SET_BG_COLOR_WHITE);
                }
                if(piece == null){
                    out.print(" ");
                    out.print(" ");
                    out.print(" ");
                    out.print(" ");
                    out.print("\u2003");
                }


                else if(piece.getPieceType() == ChessPiece.PieceType.PAWN){
                    if(piece.getTeamColor() == ChessGame.TeamColor.WHITE){
                        out.print(" ");
                        out.print(WHITE_PAWN);
                        out.print(" ");
                    }
                    else if(piece.getTeamColor() == ChessGame.TeamColor.BLACK){
                        out.print(" ");
                        out.print(BLACK_PAWN);
                        out.print(" ");
                    }
                }
                else if(piece.getPieceType() == ChessPiece.PieceType.KING){
                    if(piece.getTeamColor() == ChessGame.TeamColor.WHITE){
                        out.print(" ");
                        out.print(WHITE_KING);
                        out.print(" ");
                    }
                    else if(piece.getTeamColor() == ChessGame.TeamColor.BLACK){
                        out.print(" ");
                        out.print(BLACK_KING);
                        out.print(" ");
                    }
                }
                else if(piece.getPieceType() == ChessPiece.PieceType.QUEEN){
                    if(piece.getTeamColor() == ChessGame.TeamColor.WHITE){
                        out.print(" ");
                        out.print(WHITE_QUEEN);
                        out.print(" ");
                    }
                    else if(piece.getTeamColor() == ChessGame.TeamColor.BLACK){
                        out.print(" ");
                        out.print(BLACK_QUEEN);
                        out.print(" ");
                    }
                }
                else if(piece.getPieceType() == ChessPiece.PieceType.ROOK){
                    if(piece.getTeamColor() == ChessGame.TeamColor.WHITE){
                        out.print(" ");
                        out.print(WHITE_ROOK);
                        out.print(" ");
                    }
                    else if(piece.getTeamColor() == ChessGame.TeamColor.BLACK){
                        out.print(" ");
                        out.print(BLACK_ROOK);
                        out.print(" ");
                    }
                }
                else if(piece.getPieceType() == ChessPiece.PieceType.KNIGHT){
                    if(piece.getTeamColor() == ChessGame.TeamColor.WHITE){
                        out.print(" ");
                        out.print(WHITE_KNIGHT);
                        out.print(" ");
                    }
                    else if(piece.getTeamColor() == ChessGame.TeamColor.BLACK){
                        out.print(" ");
                        out.print(BLACK_KNIGHT);
                        out.print(" ");
                    }
                }
                else if(piece.getPieceType() == ChessPiece.PieceType.BISHOP){
                    if(piece.getTeamColor() == ChessGame.TeamColor.WHITE){
                        out.print(" ");
                        out.print(WHITE_BISHOP);
                        out.print(" ");
                    }
                    else if(piece.getTeamColor() == ChessGame.TeamColor.BLACK){
                        out.print(" ");
                        out.print(BLACK_BISHOP);
                        out.print(" ");
                    }
                }



            }
            out.print(RESET_BG_COLOR);
        }


    }
}
