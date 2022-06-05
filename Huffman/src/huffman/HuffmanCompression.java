package huffman;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class HuffmanCompression 
{
    private String input;
    private int possibilitiesNumber;
    private ArrayList<Mapping> mapsList;
    private Map<Character, Integer> charOccrrences;
    private String compressionResult;

    HuffmanCompression(String input) 
    {
        this.charOccrrences = new HashMap<>();
        this.input = input;
        mapsList = new ArrayList<>();
        possibilitiesNumber = 0;
    }

    private void countOccrrences() 
    {
        StringBuilder inputTemp = new StringBuilder(input);
        Map<String, String> map = new HashMap<>();
        Mapping possibilities = new Mapping();

        for (int i = 0; i < inputTemp.length(); i++) 
        {
            int count = 1;
            int sequencesPos = i;

            for (int j = i + 1; j < inputTemp.length(); j++) 
            {
                if (inputTemp.charAt(i) == inputTemp.charAt(j))
                {
                    count++;
                    ++sequencesPos;
                    char temp = inputTemp.charAt(j);
                    inputTemp.setCharAt(j, inputTemp.charAt(sequencesPos));
                    inputTemp.setCharAt(sequencesPos, temp);
                }
            }
            map.put(String.valueOf(inputTemp.charAt(i)), String.valueOf((double) count / inputTemp.length()));
            charOccrrences.put(inputTemp.charAt(i), count);
            i = sequencesPos;
            possibilitiesNumber++;
        }

        possibilities.setMap(map);
        possibilities.sort();
        mapsList.add(possibilities);
    }

    private void movingForward()
    {
        Map<String, String> currentMap = mapsList.get(0).getMap();
        String key1 = "", key2 = "", value1 = "", value2 = "";

        for (int counter = possibilitiesNumber; counter > 2; counter--)
        {
            int count = 0;

            Map<String, String> nextMap = new HashMap<>();

            for (Map.Entry<String, String> curMap : currentMap.entrySet()) 
            {
                if (count == currentMap.size() - 2) 
                {
                    key1 = curMap.getKey();
                    value1 = curMap.getValue();
                } 
                else if (count == currentMap.size() - 1) 
                {
                    key2 = curMap.getKey();
                    value2 = curMap.getValue();
                } 
                else 
                {
                    nextMap.put(curMap.getKey(), curMap.getValue());
                }
                ++count;
            }
            double merge = (double) Double.parseDouble(value1) + (double) (Double.parseDouble(value2));

            nextMap.put((key1 + key2), String.valueOf(merge));

            Mapping nextListMap = new Mapping();
            nextListMap.setMap(nextMap);
            nextListMap.sort();
            nextListMap.setindexSummation(nextListMap.search((key1 + key2)));

            mapsList.add(nextListMap);
            currentMap = nextListMap.getMap();
        }
    }

    private void movingBack() 
    {
        Mapping currentMapping = mapsList.get(mapsList.size() - 1);
        Mapping previousMapping = new Mapping();
        boolean check = true;
        for (int counter = possibilitiesNumber - 1; counter >= 0; counter--) 
        {
            int count1 = 0;

            //iterate to the currentMap of all elements
            for (Map.Entry<String, String> curMapRow : currentMapping.getMap().entrySet())
            {
                String code = "";
                if ((previousMapping.getMap()).isEmpty()) 
                {
                    if (count1 == currentMapping.getMap().size() - 2) 
                    {
                        curMapRow.setValue(code + '0');
                    } 
                    
                    else if (count1 == (currentMapping.getMap().size()) - 1)
                    {
                        curMapRow.setValue(code + '1');
                    }
                } 
                
                else if (count1 == currentMapping.getindexSummation() && counter != 0) 
                {
                    int count2 = 0;
                    code = curMapRow.getValue();
                    check = false;
                    for (Map.Entry<String, String> previousMap : previousMapping.getMap().entrySet()) {
                        if (count2 == previousMapping.getMap().size() - 2) 
                        {
                            previousMap.setValue(code + '0');
                        } 
                        
                        else if (count2 == previousMapping.getMap().size() - 1) 
                        {
                            previousMap.setValue(code + '1');
                        }
                        count2++;
                    }
                } 
                else if (counter != 0) 
                {
                    check = false;
                    for (Map.Entry<String, String> previousMap : previousMapping.getMap().entrySet()) 
                    {
                        if (previousMap.getKey().equals(curMapRow.getKey())) 
                        {
                            previousMap.setValue(curMapRow.getValue());
                        }
                    }
                }                
                count1++;
            }
            
            if (check == false && counter > 1) 
            {
                mapsList.set((counter - 1), previousMapping);
                currentMapping = previousMapping;
                previousMapping = mapsList.get(counter - 2);
            } 
            
            else if (possibilitiesNumber - 1 == 0 || possibilitiesNumber - 1 == 1) 
            {
                mapsList.set(0, currentMapping);
            } 
            
            else if (check == true) 
            {
                previousMapping = mapsList.get(mapsList.size() - 2);
                mapsList.set((counter - 1), currentMapping);
            }
        }
    }

    public void compressionLogic() throws IOException 
    {
        this.countOccrrences();
        this.movingForward();
        this.movingBack();
        this.codenumberFile();
        compressionResult = compResult();
    }

    public void printCharCount() 
    {
        System.out.println("The Count of each Charcter: ");
        for (Map.Entry<Character, Integer> oneRow : charOccrrences.entrySet())
        {
            System.out.print(oneRow.getKey());
            System.out.print("->>");
            System.out.print(oneRow.getValue());
            System.out.println();
        }
        System.out.println();
    }

    public void printcodeNumber() 
    {
        Mapping codeNumbers = mapsList.get(0);
        codeNumbers.printMap();
    }

    private String compResult() throws IOException
    {
        String compResult = "";
        for (int i = 0; i < input.length(); i++) 
        {
            compResult += mapsList.get(0).searchByKey(String.valueOf(input.charAt(i)));
        }

        //Save the Result in file.
        File file = new File("afterCompression.txt");
        file.createNewFile();
        BufferedWriter writePointer = new BufferedWriter(new FileWriter(file));
        writePointer.write(compResult);
        writePointer.close();
        return compResult;
    }
    
    public void getComResult()
    {
        System.out.print("\nAfter make Compression: " + compressionResult + "\n\n");
    }

    private void codenumberFile() throws IOException 
    {
        File file = new File("codeNumbers.txt");
        file.createNewFile();
        BufferedWriter writePointer = new BufferedWriter(new FileWriter(file));

        for (Map.Entry<String, String> Letter_CodeNumber : mapsList.get(0).getMap().entrySet())
        {
            writePointer.write(Letter_CodeNumber.getKey());
            writePointer.write(Letter_CodeNumber.getValue());
            writePointer.write(',');
        }
        writePointer.close();
    }
}