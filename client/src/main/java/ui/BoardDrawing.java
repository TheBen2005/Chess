package ui;

public class BoardDrawing {
    EscapeSequences escapeSequences = new EscapeSequences();
    for(int i = 0; i < 8; i++) {
        for(int j = 0; j < 8; j++){
            if(i % 2 == 0){
                if(j % 2 == 0){
                    escapeSequences.SET_BG_COLOR_BLACK;
                }
                else{
                    escapeSequences.SET_BG_COLOR_WHITE;
                }
            }
            else {
                if (j % 2 == 0) {
                    escapeSequences.SET_BG_COLOR_WHITE;
                } else {
                    escapeSequences.SET_BG_COLOR_BLACK;
                }
            }
            if(i == 2){
                escapeSequences.WHITE_PAWN;
            }
            if(i == 7){
                escapeSequences.BLACK_PAWN;
            }




        }
    }
}
