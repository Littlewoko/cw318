import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
	// write your code here
        PuzzleCreator pz = new PuzzleCreator();
        ArrayList<Puzzle> puzzle = pz.createPuzzles();
        System.out.println(puzzle.size());

    }
}
