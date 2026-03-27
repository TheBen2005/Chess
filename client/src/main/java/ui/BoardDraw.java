package ui;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import static ui.EscapeSequences.*;

public class BoardDraw {

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
    public static void drawBoard(boolean color){
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        drawHeaders(out, color);

        drawChess(out, color);
        drawHeaders(out, color);

    }

    private static void drawHeaders(PrintStream out, Boolean color) {
        out.print(SET_BG_COLOR_DARK_GREY);
        out.print(SET_TEXT_COLOR_WHITE);
        out.print("\u2003");
        out.print(" ");
        String[] headers;
        if(color == true) {
            headers = new String[]{"a", "b", "c", "d", "e", "f", "g", "h"};
        }
        else{
            headers = new String[]{"h", "g", "f", "e", "d", "c", "b", "a"};
        }
        for(int i = 0; i < 8; i++){
            out.print("\u2003" + headers[i] +  " ");
        }
        out.print("  ");
        out.println();
    }

    private static void drawHeader(PrintStream out, String headerText) {
        int prefixLength = SQUARE_SIZE_IN_PADDED_CHARS / 2;
        int suffixLength = SQUARE_SIZE_IN_PADDED_CHARS - prefixLength - 1;

        out.print(EMPTY.repeat(prefixLength));
        printHeaderText(out, headerText);
        out.print(EMPTY.repeat(suffixLength));
    }

    private static void printHeaderText(PrintStream out, String player) {
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_GREEN);

        out.print(player);

    }

    private static void drawChess(PrintStream out, Boolean pieceColor) {
        int row = 0;
        int end = 0;
        int increment = 0;
        String pawn = "";
        String rook = "";
        String knight = "";
        String bishop = "";
        String king = "";
        String queen = "";
        String pawnTwo = "";
        String rookTwo = "";
        String knightTwo = "";
        String bishopTwo = "";
        String kingTwo = "";
        String queenTwo = "";
        int pawnRowOne;
        int pawnRowTwo;
        int backRow;
        int frontRow;

        if(pieceColor == true){
            pawnRowOne = 2;
            pawnRowTwo = 7;
            backRow = 8;
            frontRow = 1;
            row = 8;
            end = 0;
            pawn = WHITE_PAWN;
            rook = WHITE_ROOK;
            knight = WHITE_KNIGHT;
            bishop = WHITE_BISHOP;
            king = WHITE_KING;
            queen = WHITE_QUEEN;
            pawnTwo = BLACK_PAWN;
            rookTwo = BLACK_ROOK;
            knightTwo = BLACK_KNIGHT;
            bishopTwo = BLACK_BISHOP;
            kingTwo = BLACK_KING;
            queenTwo = BLACK_QUEEN;
            increment = -1;
        }
        else{
            pawnRowOne = 7;
            pawnRowTwo = 2;
            backRow = 1;
            frontRow = 8;
            row = 1;
            end = 9;
            pawn = BLACK_PAWN;
            rook = BLACK_ROOK;
            knight = BLACK_KNIGHT;
            bishop = BLACK_BISHOP;
            king = BLACK_KING;
            queen = BLACK_QUEEN;
            pawnTwo = WHITE_PAWN;
            rookTwo = WHITE_ROOK;
            knightTwo = WHITE_KNIGHT;
            bishopTwo = WHITE_BISHOP;
            kingTwo = WHITE_KING;
            queenTwo = WHITE_QUEEN;
            increment = 1;
        }

        for(int j = row; j != end; j += increment) {
            for (int i = 0; i < 10; i++) {
                if(i == 0 || i == 9) {
                    out.print(SET_BG_COLOR_DARK_GREY);
                    out.print(SET_TEXT_COLOR_LIGHT_GREY);
                    String row_num = Integer.toString(j);
                    out.print(" " + row_num + " ");
                }

                else{
                    boolean color = true;
                    boolean piece = false;

                    if ((j + i) % 2 == 0) {
                        color = false;
                    }
                    if (color == true) {
                        out.print(SET_BG_COLOR_WHITE);
                        if(j == backRow){
                            piece = true;
                            if(i == 1 || i == 8){
                                out.print(rookTwo);
                            }
                            if(i == 2 || i == 7){
                                out.print(knightTwo);
                            }
                            if(i == 3 || i == 6){
                                out.print(bishopTwo);
                            }
                            if(i == 4){
                                out.print(queenTwo);
                            }
                            if(i == 5){
                                out.print(kingTwo);
                            }
                        }
                        if(j == frontRow){
                            piece = true;
                            if(i == 1 || i == 8){
                                out.print(rook);
                            }
                            if(i == 2 || i == 7){
                                out.print(knight);
                            }
                            if(i == 3 || i == 6){
                                out.print(bishop);
                            }
                            if(i == 4){
                                out.print(queen);
                            }
                            if(i == 5){
                                out.print(king);
                            }
                        }
                        if(j == pawnRowOne){
                            piece = true;

                            out.print(pawn);
                        }
                        if(j == pawnRowTwo){
                            piece = true;

                            out.print(pawnTwo);
                        }
                    } else {
                        out.print(SET_BG_COLOR_BLUE);
                        if(j == backRow){
                            piece = true;
                            if(i == 1 || i == 8){
                                out.print(rookTwo);
                            }
                            if(i == 2 || i == 7){
                                out.print(knightTwo);
                            }
                            if(i == 3 || i == 6){
                                out.print(bishopTwo);
                            }
                            if(i == 4){
                                out.print(queenTwo);
                            }
                            if(i == 5){
                                out.print(kingTwo);
                            }
                        }
                        if(j == frontRow){
                            piece = true;
                            if(i == 1 || i == 8){
                                out.print(rook);
                            }
                            if(i == 2 || i == 7){
                                out.print(knight);
                            }
                            if(i == 3 || i == 6){
                                out.print(bishop);
                            }
                            if(i == 4){
                                out.print(queen);
                            }
                            if(i == 5){
                                out.print(king);
                            }
                        }
                        if(j == pawnRowOne){
                            piece = true;

                            out.print(pawn);

                        }
                        if(j == pawnRowTwo){
                            piece = true;

                            out.print(pawnTwo);
                        }
                    }
                    if(piece == false) {
                        out.print(EMPTY);
                    }
                    }
                }
            out.println();

            }

        }




}
