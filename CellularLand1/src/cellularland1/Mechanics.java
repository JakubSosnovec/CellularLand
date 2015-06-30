
package cellularland1;

/**
 *
 * @author Deas
 */
public final class Mechanics {
    public static Mechanics inst = new Mechanics();
    private Mechanics() {
        newAutomaton();
    }
    
    public void newAutomaton() {
        // TODO Here I have to somehow generate the random automaton
        Loader l = new Loader("automataDB/life1.txt");
        automaton = l.load(size);
        boolGrid = automaton.initPosition;
    }
    
    
    private CAutomaton automaton;
    private boolean[][] boolGrid;
    
    /** This size is different from the size of buttons, because I want to 
     * simulate the automaton on a larger grid than just the grid displayed.
     */
    
    private final int size = 30;
    
    /** Number of cells on the margin of grid that are not displayed.
     * size of displayed grid = 2 * offset + size
     */
    private final int offset = 5;
    
    public void step(ButtonNode[][] buttons) {
        boolean[][] newBoolGrid = new boolean[size][size];
        
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                // Compute the number of living cells around:
                int alive = 0;
                for (int k = -1; k <= 1; k++) {
                    for (int l = -1; l <= 1; l++) {
                        if ((k != 0 || l != 0)
                                && i + k >= 0 && i + k < size
                                && j + l >= 0 && j + l < size) {
                            alive += boolGrid[i + k][j + l] ? 1 : 0;
                        }
                    }
                }
                if(boolGrid[i][j]) {
                    newBoolGrid[i][j] = automaton.willSurvive(alive);
                } else {
                    newBoolGrid[i][j] = automaton.willBeBorn(alive);
                }
            }
        }
        
        boolGrid = newBoolGrid;
        for(int i = offset; i < offset + buttons.length; i++) {
            for(int j = offset; j < offset + buttons.length; j++) {
                if(boolGrid[i][j]) {
                    buttons[i-offset][j-offset].birth();
                } else {
                    buttons[i-offset][j-offset].die();
                }
            }
        }
    }
    
    public boolean isCorrect(String S,String B) {
        return S.equals(automaton.getS()) && B.equals(automaton.getB());
    }
    
    public void resetAutomaton() {
        boolGrid = automaton.initPosition;
    }
    
    public void changed(int x, int y) {
        boolGrid[x + offset][y + offset] ^= true;
    }
    
    public String getS() {
        return automaton.getS();
    }
    public String getB() {
        return automaton.getB();
    }
}
