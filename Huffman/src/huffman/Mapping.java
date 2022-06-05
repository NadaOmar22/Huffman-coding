package huffman;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

class Mapping 
{
    private Map <String, String> map;
    private int indexSummation;

    public Mapping()
    {
        map = new HashMap<>();
        indexSummation = -1;
    }

    public void setMap(Map<String, String> map) 
    {
        this.map = map;
    }

    public Map <String, String> getMap() 
    {
        return this.map;
    }

    public void printMap() 
    {
        for (Map.Entry <String, String> entry : map.entrySet())
        {
            System.out.print(entry.getKey());
            System.out.print("->>");
            System.out.println(entry.getValue());
        }
    }

    public void setindexSummation(int indexSummation)
    {
        this.indexSummation = indexSummation;
    }

    //to get index (the loaction in map )
    public int search(String key) 
    {
        int index = 0;
        for (Map.Entry<String, String> oneRow : this.map.entrySet()) 
        {
            if (oneRow.getKey().equals(key)) 
            {
                return index;
            }
            index++;
        }
        return -1;
    }

    //to get the binary code
    public String searchByKey(String key) 
    {
        for (Map.Entry<String, String> oneRow : this.map.entrySet()) 
        {
            if (oneRow.getKey().equals(key)) 
            {
                return oneRow.getValue();
            }
        }
        return "";
    }

    public void sort() 
    {
        //map is unsorted
        LinkedHashMap<String, String> mapAfterSort = new LinkedHashMap<>();

        //reverse order becuse i need the sort is descending sort.
        map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> mapAfterSort.put(x.getKey(), x.getValue()));

        map = mapAfterSort;
    }

    public int getindexSummation() 
    {
        return indexSummation;
    }
}