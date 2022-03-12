import javax.crypto.SecretKey;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
	// write your code here
        PuzzleCreator pz = new PuzzleCreator();
        ArrayList<Puzzle> puzzles = pz.createPuzzles();
        try {
            FileOutputStream fs = new FileOutputStream("puzzles.bin");
            for (int i = 0; i < puzzles.size(); i++) {
                fs.write(puzzles.get(i).getPuzzleAsBytes());
            }
            fs.close();

            pz.encryptPuzzlesToFile("puzzles.bin");

        } catch (Exception e) {

        }

    }
}
