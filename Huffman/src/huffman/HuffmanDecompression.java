package huffman;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HuffmanDecompression 
{
    public  static Map <Character , String > charWithCode = new HashMap<>();
    public  static ArrayList<String> one = new ArrayList<>();
    public  static ArrayList<String> zero = new ArrayList<>();
    public  static ArrayList<Character> decode = new ArrayList<>();

    /**
     * This function take string and convert it to map to be used in decompress
     * @param string
     */
    public static void convertToMap(String string)
    {
        String parts[] = string.split(",");

        for(String part : parts) 
        {
            int len = part.length();
            String code  = "" ;

            //like make substring.
            for(int i = 1 ; i < len ; i++)
            {
                code += part.charAt(i);
            }
            charWithCode.put(part.charAt(0), code);
        }
    }

    /**
     * This function take string which consists of 0s and 1s and return the correct char for each group of these number
     * @param compressResult
     */
    public static void decompress(String compressResult)
    {
        boolean result ;
        String str = "", code = "";
        int i = 0 ;
        char c = compressResult.charAt(0);

        // separate the codes into 2 arraylists to make it easy to reach correct code
        for (String s :charWithCode.values())
        {
            if (s.charAt(0) == '1')
            {
                one.add(s);
            }
            if(s.charAt(0) == '0')
            {
                zero.add(s);
            }
        }
        while (i < compressResult.length()) 
        {
            str += compressResult.charAt(i);

            if(c == '1')
            {
                result = one.contains(str);

                if(result == true)
                {
                    code += compressResult.charAt(i);
                    char ch = getkey(charWithCode, code);
                    decode.add(ch);
                    str = "";
                    code ="";

                    if(i+1 != compressResult.length()) 
                    {
                        if (compressResult.charAt(i + 1) == '1')
                            c = '1';
                        else
                            c = '0';
                    }
                }
                else 
                {
                    code += compressResult.charAt(i);
                }
            }
            else 
            {
                result = zero.contains(str);

                if(result == true)
                {
                    code += compressResult.charAt(i);
                    char ch = getkey(charWithCode , code);
                    decode.add(ch);
                    str = "";
                    code ="";

                    if(i+1 != compressResult.length()) 
                    {
                        if (compressResult.charAt(i + 1) == '1')
                            c = '1';
                        else
                            c = '0';
                    }
                }
                else
                {
                    code += compressResult.charAt(i);
                }
            }
            i++;
        }
    }

    /**
     * This function to return the char of specific code
     * @param map
     * @param code
     * @return char value
     */
    public static char getkey(Map <Character , String > map, String code)
    {
        char c ='\n';
        for (Map.Entry <Character , String > entry: map.entrySet())
        {
            if(code.equals(entry.getValue()))
            {
                c = entry.getKey();
            }
        }
        return c;
    }

    /**
     * This function to write the result of decompression on a file
     * @throws IOException
     */
    public static void displayDecodedText() throws IOException 
    {
        String string = "";
        for (char c : decode) 
        {
            string += c;
        }
        
        System.out.println(string);
        File file = new File("decodedText.txt");
        file.createNewFile();
        try 
        {
            FileWriter writer = new FileWriter("decodedText.txt");
            writer.write(string);
            writer.close();
        }
        
        catch (IOException e) 
        {
            System.out.println("An error occurred.");
        }
    }
}