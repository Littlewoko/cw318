import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.crypto.*;

public class PuzzleCreator {
    private static Cipher cipher;
    private static CryptoLib cl = new CryptoLib();

    public PuzzleCreator() {
        // empty constructor
    }

    /**
     * Generate 4096 puzzles
     * @return An array list consisting of 4096 puzzle objects
     */
    public ArrayList<Puzzle> createPuzzles() {
        int numPuzzles = 4096;
        ArrayList<Puzzle> puzzles = new ArrayList<>(numPuzzles);
        try {
            for (int i = 0; i < numPuzzles; i++) {
                puzzles.add(i, new Puzzle(i, cl.createKey(createRandomKey())));
            }
        } catch (Exception e) {

        }

        return puzzles;
    }

    /**
     * Generate a random 64 bit key that can be used to form a DES key. The
     * final 48 bits are zeroed.
     * @return The generated bits in an array of 8 bytes
     */
    public byte[] createRandomKey() {
        byte[] key = new byte[8];
        try {
            SecureRandom.getInstanceStrong().nextBytes(key);
            for (int i = 2; i < key.length; i++) {
                key[i] = 0;
            }

        } catch (Exception e) {

        }
        return key;
    }

    /**
     * Takes a byte array representing a key and a puzzle object and encrypts
     * the puzzles byte representation into a byte array representing the
     * encrypted puzzle.
     * @param key The key used for encryption
     * @param puzzle The puzzle to be encrypted
     * @return The encrypted puzzle
     */
    public byte[] encryptPuzzle(byte[] key, Puzzle puzzle) {
        byte[] encryptedPuzzle = new byte[32];
        try {
            cipher = Cipher.getInstance("DES");
            SecretKey secretKey = cl.createKey(key);

            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            encryptedPuzzle = cipher.doFinal(puzzle.getPuzzleAsBytes());
        } catch (Exception e) {

        }

        return encryptedPuzzle;
    }

    /**
     *
     */
    public void encryptPuzzlesToFile(String fn) {
        // steps:
        // Read in 26 bytes
        // Create puzzle with correct puzzle number and key
        // encrypt puzzle
        // write encrypted puzzle to file

        int numPuzzles = 4096;
        ArrayList<Puzzle> puzzles = new ArrayList<>(numPuzzles);
        String outputFile = "outputPuzzles.bin";

        try {
            InputStream inStream = new FileInputStream(fn);
            OutputStream outStream = new FileOutputStream(outputFile, true);

            // Read 32 bytes at a time
            byte [] buffer = new byte[32];

            // First 16 bytes = 0 = plaintext
            // 2 Bytes = puzzle number
            // 8 bytes = key
            byte [] puzzleBuffer = new byte[16];
            byte [] puzzleNumberBuffer = new byte[2];
            byte [] keyBuffer = new byte[14];

            byte [] encryptedPuzzle;

            // Current puzzle being read
            Puzzle curPuzzle;

            while (inStream.read(buffer) != -1) {
                int j = 0;
                int k = 0;
                for(int i=0; i < 26; i++) {
                    if(i<16) {
                        puzzleBuffer[i] = buffer[i];
                    } else if(i<18) {
                        puzzleNumberBuffer[j] = buffer[i];
                        j++;
                    } else {
                        keyBuffer[k] = buffer[i];
                        k++;
                    }
                }
                curPuzzle = new Puzzle(cl.byteArrayToSmallInt(puzzleNumberBuffer),
                        cl.createKey(keyBuffer));
                encryptedPuzzle = encryptPuzzle(keyBuffer, curPuzzle);

                outStream.write(encryptedPuzzle);
            }
        }
        catch (Exception e) {

        }
    }

}
