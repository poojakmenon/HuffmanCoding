package huffman;

/**
 * This class contains a character object, and a double representing
 * its probability of occurrence
 * @author Pooja Menon
 */
public class CharFreq implements Comparable<CharFreq> {
    private Character character;
    private double probOcc;

    // Constructor to set Character and probability 
    public CharFreq(Character character, double probOcc) {
        this.character = character;
        this.probOcc = probOcc;
    }

    // No-argument constructor: null character and sets probability to 0
    public CharFreq() {
        this(null, 0);
    }

    // Allows sorting by probOcc when using Collections.sort()
    @Override
    public int compareTo(CharFreq other) {
        Double thisProbOcc = this.probOcc;
        Double otherProbOcc = other.probOcc;
        
        int probComparison = thisProbOcc.compareTo(otherProbOcc);
        
        if (probComparison != 0) {
            return probComparison;
        } else {
            // If probabilities are equal, compare characters
            if (this.character == null && other.character == null) {
                return 0;
            } else if (this.character == null) {
                return 1;
            } else if (other.character == null) {
                return -1;
            }
            return this.character.compareTo(other.character);
        }
    }

    // Getters and setters
    public Character getCharacter() {
        return character;
    }

    public double getProbOcc() {
        return probOcc;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public void setProbOcc(double probOcc) {
        this.probOcc = probOcc;
    }
}