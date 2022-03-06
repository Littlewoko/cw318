import javax.crypto.SecretKey;

public class Puzzle {
    private CryptoLib cl;
    private SecretKey secretKey;
    private int puzzleNumber;
    private byte[] puzzleAsBytes;
    final static int PLAIN_TEXT_LENGTH = 16;
    final static int KEY_LENGTH = 8;


    /**
     * Construct a puzzle object
     * @param puzzleNumber
     * @param secretKey
     */
    public Puzzle(int puzzleNumber, SecretKey secretKey) {
        cl = new CryptoLib();
        this.puzzleNumber = puzzleNumber;
        this.secretKey = secretKey;
        puzzleAsBytes = new byte[26];
        initPuzzleAsBytes();
    }

    public int getPuzzleNumber() {
        return puzzleNumber;
    }

    public SecretKey getKey() {
        return secretKey;
    }

    public byte[] getPuzzleAsBytes() {
        return puzzleAsBytes;
    }

    private void initPuzzleAsBytes() {
        // Initialise first 16 bytes as 0
        for (int i = 0; i < PLAIN_TEXT_LENGTH; i++) {
            puzzleAsBytes[i] = 0;
        }

        // Next two bytes represent puzzle number
        byte[] puzzleNumberAsBytes = cl.smallIntToByteArray(puzzleNumber);
        puzzleAsBytes[16] = puzzleNumberAsBytes[0];
        puzzleAsBytes[17] = puzzleNumberAsBytes[1];

        // Final 8 bytes represent secret key
        byte[] encodedKey = secretKey.getEncoded();
        int j = 18;
        for (int i = 0; i < KEY_LENGTH; i++) {
            puzzleAsBytes[j] = encodedKey[i];
            j++;
        }
    }

}
