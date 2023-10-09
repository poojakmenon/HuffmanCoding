package huffman;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;

public class HuffmanCoding {
    private String fileName;
    private ArrayList<CharFreq> sortedCharFreqList;
    private TreeNode huffmanRoot;
    private String[] encodings;

    /**
     *set filename
     * @author Pooja Menon
     */
    public HuffmanCoding(String f) { 
        fileName = f; 
    }

    /**
     * reads and sets sortedCharFreqList
     * sorted by frequency
     * @author Pooja Menon
     */
    public void makeSortedList() {
        StdIn.setFile(fileName);
        sortedCharFreqList = new ArrayList<CharFreq>();

        int[] size = new int[128];
        int charCount = 0;

        while (StdIn.hasNextChar()) {
            char character = StdIn.readChar();
            size[character] = size[character]+1;
            charCount++;
        }
	
        for ( int i = 0; i < size.length; i++ ) {
            if ( size [i] > 0 ) {
                double prob = (double)size[i]/(double)charCount;
                CharFreq newObj = new CharFreq ((char)i, prob);
                sortedCharFreqList.add(newObj);
            }
        }

        if ( sortedCharFreqList.size() == 1 ) { 

            char extra = (char) (sortedCharFreqList.get(0).getCharacter() + 1);

            if ( extra == 128 ) {
                extra = 0;
                CharFreq newChar = new CharFreq((char)extra, 0.00);
                sortedCharFreqList.add(newChar);
            } else {
                CharFreq newChar = new CharFreq((char)extra, 0.00);
                sortedCharFreqList.add(newChar);
            }
        }

        Collections.sort(sortedCharFreqList);
       
    }


    /**
     * sortedCharFreqList builds a huffman coding tree, stores its root
     * in huffmanRoot
     * @author Pooja Menon
     */
    public void makeTree() {
        Queue<TreeNode> source = new Queue<>();
        Queue<TreeNode> target = new Queue<>();
    
        for (CharFreq next : sortedCharFreqList) {
            source.enqueue(new TreeNode(next, null, null));
        }
    
        while (!source.isEmpty() || target.size() > 1) {
            TreeNode left = null;
            TreeNode right = null;
    
            if (!source.isEmpty() && (target.isEmpty() || source.peek().getData().getProbOcc() <= target.peek().getData().getProbOcc())) {
                left = source.dequeue();
            } else {
                left = target.dequeue();
            }
    

            if (!source.isEmpty() && (target.isEmpty() || source.peek().getData().getProbOcc() <= target.peek().getData().getProbOcc())) {
                right = source.dequeue();
            } else {
                right = target.dequeue();
            }
    
            
            TreeNode newNode = new TreeNode(new CharFreq(null, left.getData().getProbOcc() + right.getData().getProbOcc()), left, right);
    
            target.enqueue(newNode);
        }
    
        // The last node in the result queue is the root of the Huffman tree root of queue is last node in Huffman tree
        huffmanRoot = target.dequeue();
    }
    




    /**
     * Uses huffmanRoot to create string array of size 128 
     * each index in array has ASCII character's bitstring encoding
     * Characters not in the huffman coding tree have their spots in the array left null.
     * @author Pooja Menon
     */
    public void makeEncodings() {

	    String[] encodings = new String[128];
        makeString ( encodings, "", huffmanRoot );
        this.encodings = encodings;
    }


        private void makeString (String[] encodings, String encodedString, TreeNode currentNode) {
            if ( currentNode == null ) {
                return;
            }

            if ( currentNode.getRight() == null && currentNode.getLeft() == null ) {
                encodings[currentNode.getData().getCharacter()] = encodedString;
                return;
            }

            makeString( encodings, encodedString + "1", currentNode.getRight() );
            makeString( encodings, encodedString + "0", currentNode.getLeft() );

        }



    /**
     * uses writeBitString method to write the final encoding of 1's and 0's to the encoded file
     * @author Pooja Menon
     */
    public void encode(String encodedFile) {

        StdIn.setFile(fileName);

        String encodedString = ""; 

        while( StdIn.hasNextChar() ){
            char character = StdIn.readChar();
            encodedString = encodedString + encodings[character];
        }

        writeBitString(encodedFile, encodedString);
    }
    
    
    /**
     * Writes a given string of 1's and 0's to the given file byte by byte
     * and NOT as characters of 1 and 0 which take up 8 bits each
     * @param filename The file to write to (doesn't need to exist yet)
     * @param bitString The string of 1's and 0's to write to the file in bits
     * @author Ishaan Ivaturi
     */
    public static void writeBitString(String filename, String bitString) {
        byte[] bytes = new byte[bitString.length() / 8 + 1];
        int bytesIndex = 0, byteIndex = 0, currentByte = 0;

        // Pad the string with initial zeroes and then a one in order to bring
        // its length to a multiple of 8. When reading, the 1 signifies the
        // end of padding.
        int padding = 8 - (bitString.length() % 8);
        String pad = "";
        for (int i = 0; i < padding-1; i++) pad = pad + "0";
        pad = pad + "1";
        bitString = pad + bitString;

        // For every bit, add it to the right spot in the corresponding byte,
        // and store bytes in the array when finished
        for (char c : bitString.toCharArray()) {
            if (c != '1' && c != '0') {
                System.out.println("Invalid characters in bitstring");
                return;
            }

            if (c == '1') currentByte += 1 << (7-byteIndex);
            byteIndex++;
            
            if (byteIndex == 8) {
                bytes[bytesIndex] = (byte) currentByte;
                bytesIndex++;
                currentByte = 0;
                byteIndex = 0;
            }
        }
        
        // Write the array of bytes to the provided file

        try {
            FileOutputStream out = new FileOutputStream(filename);
            out.write(bytes);
            out.close();
        }
        catch(Exception e) {
            System.err.println("Error when writing to file!");
        }
    }

    /**
     * Using a given encoded file name, this method makes use of the readBitString method 
     * to convert the file into a bit string, then decodes the bit string using the 
     * tree, and writes it to a decoded file. 
     * 
     * @param encodedFile The file which has already been encoded by encode()
     * @param decodedFile The name of the new file we want to decode into
     * @author Pooja Menon
     */
    public void decode(String encodedFile, String decodedFile) {
        StdOut.setFile(decodedFile);
        String bitString = readBitString( encodedFile );

        TreeNode currentNode = huffmanRoot;

        int counter = 0;
        while ( counter < bitString.length() ) {
            char bit = bitString.charAt(counter);

            if ( bit == '0') {
                currentNode = currentNode.getLeft();
            } else {
                currentNode = currentNode.getRight();
            }

            if ( currentNode.getRight() == null && currentNode.getLeft() == null ) {
                StdOut.print ( currentNode.getData().getCharacter() );
                currentNode = huffmanRoot;
            }

            counter++;
        }
    }


    /**
     * Reads a given file byte by byte, and returns a string of 1's and 0's
     * representing the bits in the file
     * 
     * @param filename The encoded file to read from
     * @return String of 1's and 0's representing the bits in the file
     * @author Ishaan Ravaturi
     */
    public static String readBitString(String filename) {
        String bitString = "";
        
        try {
            FileInputStream in = new FileInputStream(filename);
            File file = new File(filename);

            byte bytes[] = new byte[(int) file.length()];
            in.read(bytes);
            in.close();
            
            // For each byte read, convert it to a binary string of length 8 and add it
            // to the bit string
            for (byte b : bytes) {
                bitString = bitString + 
                String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
            }

            // Detect the first 1 signifying the end of padding, then remove the first few
            // characters, including the 1
            for (int i = 0; i < 8; i++) {
                if (bitString.charAt(i) == '1') return bitString.substring(i+1);
            }
            
            return bitString.substring(8);
        }
        catch(Exception e) {
            System.out.println("Error while reading file!");
            return "";
        }
    }

    /*
     * Getters used by the driver. 
     */

    public String getFileName() { 
        return fileName; 
    }

    public ArrayList<CharFreq> getSortedCharFreqList() { 
        return sortedCharFreqList; 
    }

    public TreeNode getHuffmanRoot() { 
        return huffmanRoot; 
    }

    public String[] getEncodings() { 
        return encodings; 
    }
}
