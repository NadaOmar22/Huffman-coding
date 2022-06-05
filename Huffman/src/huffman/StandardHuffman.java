package huffman;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class StandardHuffman 
{
    public static String readFromFile(String fileName) throws IOException 
    {
        File file = new File(fileName);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String currentLine = reader.readLine();
        reader.close();
        return currentLine;
    }

    public static void main(String[] args) throws IOException 
    {
        //the Compression part.
        String textToBeCompressed = StandardHuffman.readFromFile("mainFile.txt");

        HuffmanCompression compressionObj = new HuffmanCompression(textToBeCompressed);

        System.out.print("The Data From File before make Compress : " + textToBeCompressed + "\n\n");        
        
        compressionObj.compressionLogic();
        compressionObj.printCharCount();

        System.out.println("The Chars Code Numbers  after make commpression: ");
        compressionObj.printcodeNumber();
        compressionObj.getComResult();

        // The decompression part.
        HuffmanDecompression decompressionObj = new HuffmanDecompression();

        String binaryResult = StandardHuffman.readFromFile("afterCompression.txt");
        
        String codes = StandardHuffman.readFromFile("codeNumbers.txt");

        decompressionObj.convertToMap(codes);
       
        decompressionObj.decompress(binaryResult);      
        
        System.out.print("After make Decompression: ");

        decompressionObj.displayDecodedText();
        
        System.out.println();
    }
}