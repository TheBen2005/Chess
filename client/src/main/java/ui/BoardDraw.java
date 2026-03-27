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

        drawHeaders(out);

        drawChess(out);
        drawHeaders(out);

        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_WHITE);
    }

    private static void drawHeaders(PrintStream out) {
        out.print(SET_BG_COLOR_DARK_GREY);
        out.print(SET_TEXT_COLOR_WHITE);
        out.print("\u2003");
        out.print(" ");
        String[] headers = { "a", "b", "c", "d", "e", "f", "g", "h" };
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

    private static void drawChess(PrintStream out) {
        for(int j = 8; j >= 1; j--) {
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
                        if(j == 8){
                            piece = true;
                            if(i == 1 || i == 8){
                                out.print(BLACK_ROOK);
                            }
                            if(i == 2 || i == 7){
                                out.print(BLACK_KNIGHT);
                            }
                            if(i == 3 || i == 6){
                                out.print(BLACK_BISHOP);
                            }
                            if(i == 4){
                                out.print(BLACK_QUEEN);
                            }
                            if(i == 5){
                                out.print(BLACK_KING);
                            }
                        }
                        if(j == 1){
                            piece = true;
                            if(i == 1 || i == 8){
                                out.print(WHITE_ROOK);
                            }
                            if(i == 2 || i == 7){
                                out.print(WHITE_KNIGHT);
                            }
                            if(i == 3 || i == 6){
                                out.print(WHITE_BISHOP);
                            }
                            if(i == 4){
                                out.print(WHITE_QUEEN);
                            }
                            if(i == 5){
                                out.print(WHITE_KING);
                            }
                        }
                        if(j == 2){
                            piece = true;

                            out.print(WHITE_PAWN);
                        }
                        if(j == 7){
                            piece = true;

                            out.print(BLACK_PAWN);
                        }
                    } else {
                        out.print(SET_BG_COLOR_BLUE);
                        if(j == 8){
                            piece = true;
                            if(i == 1 || i == 8){
                                out.print(BLACK_ROOK);
                            }
                            if(i == 2 || i == 7){
                                out.print(BLACK_KNIGHT);
                            }
                            if(i == 3 || i == 6){
                                out.print(BLACK_BISHOP);
                            }
                            if(i == 4){
                                out.print(BLACK_QUEEN);
                            }
                            if(i == 5){
                                out.print(BLACK_KING);
                            }
                        }
                        if(j == 1){
                            piece = true;
                            if(i == 1 || i == 8){
                                out.print(WHITE_ROOK);
                            }
                            if(i == 2 || i == 7){
                                out.print(WHITE_KNIGHT);
                            }
                            if(i == 3 || i == 6){
                                out.print(WHITE_BISHOP);
                            }
                            if(i == 4){
                                out.print(WHITE_QUEEN);
                            }
                            if(i == 5){
                                out.print(WHITE_KING);
                            }
                        }
                        if(j == 2){
                            piece = true;

                            out.print(WHITE_PAWN);

                        }
                        if(j == 7){
                            piece = true;

                            out.print(BLACK_PAWN);
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
