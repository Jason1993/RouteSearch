import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by wenqingcao on 9/13/16.
 */
public class Map {
    private HashMap<String, ArrayList<MapEdge>> graph = new HashMap<String, ArrayList<MapEdge>>();
    private HashMap<String, Integer> heuristics = new HashMap<>();
    public void buildEdge(String a, String b, int x) throws Exception
    {
        ArrayList<MapEdge> paths = graph.get(a);
        if (paths == null)
        {
            paths = new ArrayList<MapEdge>();
        }

        MapEdge temp = new MapEdge(a, b, x);
        paths.add(temp);
        graph.put(a,paths);
    }
    public void buildMap(ArrayList<String> s) throws Exception
    {
        int number = Integer.valueOf(s.get(3));
        int i;
        for (i = 4; i < (number+4); i++)
        {
            String[] temp = s.get(i).split(" ");
            this.buildEdge(temp[0],temp[1],Integer.valueOf(temp[2]));
        }
        int number2 = Integer.valueOf(s.get(i));
        number = ++i;
        for (i = number; i < (number+number2); i++)
        {
            String[] heu = s.get(i).split(" ");
            String temp1 = heu[0];
            Integer temp2 = Integer.valueOf(heu[1]);
            heuristics.put(temp1, temp2);
        }
    }
    public HashMap<String, ArrayList<MapEdge>> getGraph()
    {
        return graph;
    }

    public ArrayList<MapEdge> getEdges(String node)
    {
        return graph.get(node);
    }

    public HashMap<String, Integer> getHeuristics()
    {
        return heuristics;
    }
}
