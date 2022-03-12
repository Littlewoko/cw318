import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

/**
 * Class to demonstrate that we can yse our code to complete the Merkles
 * puzzle communication.
 * @author Bradley Cain Watkins
 * @author 980321
 */
public class Merkle {
    public static void main(String[] args) {
        // create an encrypted puzzle file representing
        // Alice's puzzles
        PuzzleCreator puz_create = new PuzzleCreator();
        puz_create.createPuzzles();
        String fn = "puzzles.bin";
        puz_create.encryptPuzzlesToFile(fn);

        // Crack a random puzzle (Bob's puzzle)
        PuzzleCracker puz_crack = new PuzzleCracker(fn);
        Puzzle crackedPuzzle = puz_crack.crack(60);

        // Find cracked puzzle within Alice's collection with same puzzle number
        SecretKey secretKey =
                puz_create.findKey(crackedPuzzle.getPuzzleNumber());

        // Using cracked key encrypt message before decrypting using puzzle
        // cracker
        final byte[] MESSAGE =
                "Testing Merkle's Puzzles!".getBytes(StandardCharsets.UTF_8);
        byte[] encryptedMessage;
        try {
            Cipher cipher = Cipher.getInstance("DES");

            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            encryptedMessage = cipher.doFinal(MESSAGE);

            puz_crack.decryptMessage(encryptedMessage);
        } catch (Exception e) { }


    }
}
