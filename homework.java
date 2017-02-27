/**
 * Created by wenqingcao on 9/17/16.
 */
/**
 * Created by wenqingcao on 9/6/16.
 */


import java.io.*;
import java.util.*;
import java.util.Collections;

public class homework {
    static int number = 0;
    public static ArrayList<String> readFile(String file) throws IOException {
        ArrayList<String> temp = new ArrayList<String>();
        BufferedReader reader = new BufferedReader(new FileReader (file));
        String         line = null;
        try {
            while((line = reader.readLine()) != null) {
                StringBuilder  stringBuilder = new StringBuilder();
                stringBuilder.append(line);
                temp.add(stringBuilder.toString());
            }
        } finally {
            reader.close();
        }
        return temp;
    }

    public static void writeFile(ArrayList<String> s) throws IOException {
        PrintWriter output = new PrintWriter("output.txt","UTF-8");
        Iterator<String> tempstring = s.iterator();
        while (tempstring.hasNext() == true)
        {
            String line = tempstring.next();
            output.println(line);
        }
        output.close();
    }

    public static boolean goalTest(String s,String t)
    {
        return s.equals(t);
    }

    public static ArrayList<String> conductBFS(String a, String b, Map x)
    {
        ArrayList<String> path = new ArrayList<>();
        if (goalTest(a,b)) {
            path.add(a);
            return path;
        }
        HashMap<String,String> pre = new HashMap<String,String>();
        Queue<MapNode> frontier = new LinkedList<MapNode>();
        Queue<String> nodeName = new LinkedList<String>();
        MapNode start = new MapNode(a);
        frontier.add(start);
        nodeName.add(a);
        HashSet<String> explored = new HashSet<String>();
        while (frontier.isEmpty() != true)
        {
            MapNode current = frontier.remove();
            nodeName.remove();
            explored.add(current.getName());
            ArrayList<MapEdge> expand = x.getEdges(current.getName());
            if (expand != null)
            {
                for (MapEdge e : expand)
                {
                    MapNode temp = e.getChildNode();
                    if (!(nodeName.contains(temp.getName()) || explored.contains(temp.getName())))
                    {
                        pre.put(e.getChild(),e.getParent());
                        if (goalTest(b,temp.getName())) {
                            String trace = b;
                            path.add(trace);
                            while (trace.equals(a) == false)
                            {
                                trace = pre.get(trace);
                                path.add(trace);
                            }
                            return path;
                        }
                        else
                        {
                            nodeName.add(temp.getName());
                            frontier.add(temp);
                        }
                    }
                }
            }
        }
        return path;
    }

    public static ArrayList<String> conductDFS(String a, String b, Map x)
    {
        ArrayList<String> path = new ArrayList<String>();
        if (goalTest(a,b)) {
            path.add(a);
            return path;
        }
        HashMap<String,String> pre = new HashMap<String,String>();
        Stack<MapNode> frontier = new Stack<MapNode>();
        Stack<String> nodeName = new Stack<String>();
        MapNode start = new MapNode(a);
        frontier.push(start);
        nodeName.push(a);
        HashSet<String> explored = new HashSet<String>();

        while (frontier.isEmpty() == false)
        {
            MapNode current = frontier.pop();
            nodeName.pop();
            explored.add(current.getName());
            if (goalTest(b,current.getName()))
            {
                String trace = b;
                path.add(trace);
                while (trace.equals(a) == false)
                {
                    trace = pre.get(trace);
                    path.add(trace);
                }
                return path;
            }
            ArrayList<MapEdge> expand = x.getEdges(current.getName());
            if (expand != null)
            {
                Collections.reverse(expand);
                for (MapEdge e : expand)
                {
                    MapNode temp = e.getChildNode();
                    if ((explored.contains(temp.getName()) == false) && (nodeName.contains(temp.getName()) == false))
                    {
                        pre.put(e.getChild(),e.getParent());
                        frontier.push(temp);
                        nodeName.push(temp.getName());
                    }
                }
            }
        }
        return path;
    }

    public static ArrayList<String> conductUCS(String a, String b, Map x)
    {
        ArrayList<String> path = new ArrayList<String>();
        if (goalTest(a,b)) {
            path.add(a+" "+"0");
            return path;
        }
        final Comparator<MapNode> ascending = new Comparator<MapNode>() {
            @Override
            public int compare(MapNode o1, MapNode o2) {
                if (o1.getCost()-o2.getCost() != 0)
                    return o1.getCost()-o2.getCost();
                else
                    return o1.getOrder() - o2.getOrder();
            }
        };
/*        PriorityQueue<MapNode> frontier = new PriorityQueue<MapNode>(new Comparator<MapNode>() {
            @Override
            public int compare(MapNode o1, MapNode o2) {
                int cost1 = o1.getCost();
                int cost2 = o2.getCost();

                    return o1.getCost() - o2.getCost();

            }
        });*/
        List<MapNode> frontier = new LinkedList<>();
        HashMap<String,String> pre = new HashMap<String,String>();
        Queue<String> nodeName = new LinkedList<String>();
        MapNode start = new MapNode(a);
        start.setOrder(number);
        number++;
        frontier.add(start);
        nodeName.add(a);
        HashSet<String> explored = new HashSet<String>();
        HashSet<MapNode> closed = new HashSet<>();
        while (frontier.isEmpty() == false)
        {
            MapNode current = frontier.remove(0);
            nodeName.remove();
//            explored.add(current.getName());
//            closed.add(current);
            if (goalTest(b,current.getName()))
            {
                String trace = b;
                path.add(trace+" "+current.getCost());
                while (trace.equals(a) == false)
                {
                    trace = pre.get(trace);
                    int tempcost = 0;
                    Iterator<MapNode> back = closed.iterator();
                    while (back.hasNext() == true)
                    {
                        MapNode li = back.next();
                        if (li.getName().equals(trace))
                        {
                            tempcost = li.getCost();
                        }
                    }
                    path.add(trace+" "+tempcost);
                }
                return path;
            }
            else
            {
                ArrayList<MapEdge> expand = x.getEdges(current.getName());
                if (expand != null)
                {
/*                    Collections.sort(expand, new Comparator<MapEdge>() {
                        @Override
                        public int compare(MapEdge o1, MapEdge o2) {
                            return o1.getValue() - o2.getValue();
                        }
                    });*/
                    for (MapEdge e : expand)
                    {
                        MapNode temp = e.getChildNode();
                        temp.setCost(current.getCost()+e.getValue());
                        if (nodeName.contains(temp.getName()) == false && explored.contains(temp.getName()) == false)
                        {
                            pre.put(e.getChild(),e.getParent());
                            temp.setOrder(number);
                            number++;
                            nodeName.add(temp.getName());
                            frontier.add(temp);
                        }
                        else
                        {
                            if (nodeName.contains(temp.getName()) == true && explored.contains(temp.getName()) == false)
                            {
                                Iterator<MapNode> check = frontier.iterator();
                                while (check.hasNext() == true)
                                {
                                    MapNode lo = check.next();
                                    if (lo.getName().equals(temp.getName()) && temp.getCost() < lo.getCost())
                                    {
                                        pre.remove(e.getChild());
                                        pre.put(e.getChild(),e.getParent());
                                        temp.setOrder(number);
                                        number++;
                                        frontier.remove(lo);
                                        nodeName.remove(lo.getName());
                                        frontier.add(temp);
                                        nodeName.add(temp.getName());
                                        break;
                                    }
                                }
                            }
                            else
                            {
                                if (nodeName.contains(temp.getName()) == false && explored.contains(temp.getName())== true)
                                {
                                    Iterator<MapNode> check = closed.iterator();
                                    while (check.hasNext() == true)
                                    {
                                        MapNode lo = check.next();
                                        if (lo.getName().equals(temp.getName()) && temp.getCost() < lo.getCost())
                                        {
                                            temp.setOrder(number);
                                            number++;
                                            closed.remove(lo);
                                            explored.remove(lo.getName());
                                            frontier.add(lo);
                                            nodeName.add(lo.getName());
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            closed.add(current);
            explored.add(current.getName());
            Collections.sort(frontier,ascending);
            nodeName.clear();
            Iterator<MapNode> copyName = frontier.iterator();
            while (copyName.hasNext())
            {
                MapNode m = copyName.next();
                String tempName = m.getName();
                nodeName.add(tempName);
            }
        }
        return path;
    }

    public static ArrayList<String> conductA(String a, String b, Map x)
    {
        ArrayList<String> path = new ArrayList<String>();
        if (goalTest(a,b)) {
            path.add(a+" "+"0");
            return path;
        }
        final Comparator<MapNode> ascending = new Comparator<MapNode>() {
            @Override
            public int compare(MapNode o1, MapNode o2) {
                if ((o1.getCost()+o1.getEstCost())-(o2.getCost()+o2.getEstCost()) != 0)
                    return (o1.getCost()+o1.getEstCost())-(o2.getCost()+o2.getEstCost());
                else
                    return o1.getOrder() - o2.getOrder();
            }
        };
/*        PriorityQueue<MapNode> frontier = new PriorityQueue<MapNode>(new Comparator<MapNode>() {
            @Override
            public int compare(MapNode o1, MapNode o2) {
                int cost1 = o1.getCost();
                int cost2 = o2.getCost();

                    return o1.getCost() - o2.getCost();

            }
        });*/
        List<MapNode> frontier = new LinkedList<>();
        HashMap<String,String> pre = new HashMap<String,String>();
        Queue<String> nodeName = new LinkedList<String>();
        HashMap<String, Integer> est = new HashMap<>();
        est = x.getHeuristics();
        int estCost = est.get(a);
        MapNode start = new MapNode(a);
        start.setEstCost(estCost);
        start.setOrder(number);
        number++;
        frontier.add(start);
        nodeName.add(a);
        HashSet<String> explored = new HashSet<String>();
        HashSet<MapNode> closed = new HashSet<>();
        while (frontier.isEmpty() == false)
        {
            MapNode current = frontier.remove(0);
            nodeName.remove();
//            explored.add(current.getName());
//            closed.add(current);
            if (goalTest(b,current.getName()))
            {
                String trace = b;
                path.add(trace+" "+current.getCost());
                while (trace.equals(a) == false)
                {
                    trace = pre.get(trace);
                    int tempcost = 0;
                    Iterator<MapNode> back = closed.iterator();
                    while (back.hasNext() == true)
                    {
                        MapNode li = back.next();
                        if (li.getName().equals(trace))
                        {
                            tempcost = li.getCost();
                        }
                    }
                    path.add(trace+" "+tempcost);
                }
                return path;
            }
            else
            {
                ArrayList<MapEdge> expand = x.getEdges(current.getName());
                if (expand != null)
                {
/*                    Collections.sort(expand, new Comparator<MapEdge>() {
                        @Override
                        public int compare(MapEdge o1, MapEdge o2) {
                            return o1.getValue() - o2.getValue();
                        }
                    });*/
                    for (MapEdge e : expand)
                    {
                        MapNode temp = e.getChildNode();
                        temp.setCost(current.getCost()+e.getValue());
                        temp.setEstCost(est.get(temp.getName()));
                        if (nodeName.contains(temp.getName()) == false && explored.contains(temp.getName()) == false)
                        {
                            pre.put(e.getChild(),e.getParent());
                            temp.setOrder(number);
                            number++;
                            nodeName.add(temp.getName());
                            frontier.add(temp);
                        }
                        else
                        {
                            if (nodeName.contains(temp.getName()) == true && explored.contains(temp.getName()) == false)
                            {
                                Iterator<MapNode> check = frontier.iterator();
                                while (check.hasNext() == true)
                                {
                                    MapNode lo = check.next();
                                    if (lo.getName().equals(temp.getName()) && (temp.getCost()+temp.getEstCost()) < (lo.getCost()+lo.getEstCost()))
                                    {
                                        pre.remove(e.getChild());
                                        pre.put(e.getChild(),e.getParent());
                                        temp.setOrder(number);
                                        number++;
                                        frontier.remove(lo);
                                        nodeName.remove(lo.getName());
                                        frontier.add(temp);
                                        nodeName.add(temp.getName());
                                        break;
                                    }
                                }
                            }
                            else
                            {
                                if (nodeName.contains(temp.getName()) == false && explored.contains(temp.getName())== true)
                                {
                                    Iterator<MapNode> check = closed.iterator();
                                    while (check.hasNext() == true)
                                    {
                                        MapNode lo = check.next();
                                        if (lo.getName().equals(temp.getName()) && (temp.getCost()+temp.getEstCost()) < (lo.getCost()+lo.getEstCost()))
                                        {
                                            temp.setOrder(number);
                                            number++;
                                            closed.remove(lo);
                                            explored.remove(lo.getName());
                                            frontier.add(lo);
                                            nodeName.add(lo.getName());
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            closed.add(current);
            explored.add(current.getName());
            Collections.sort(frontier,ascending);
            nodeName.clear();
            Iterator<MapNode> copyName = frontier.iterator();
            while (copyName.hasNext())
            {
                MapNode m = copyName.next();
                String tempName = m.getName();
                nodeName.add(tempName);
            }
        }
        return path;
    }

    public static void main(String[] args) throws Exception
    {

        ArrayList<String> test = readFile("input.txt");
        String type = test.get(0);
        String Begin = test.get(1);
        String Goal = test.get(2);
        switch (type) {
            case "BFS": {
                Map one = new Map();
                one.buildMap(test);
                ArrayList <String> result = new ArrayList<String>();
                result = conductBFS(Begin,Goal,one);
                int re = result.size();
                ArrayList <String> reverse = new ArrayList<String>();
                int i;
                for (i = 0; i < re; i++)
                {
                    int j = re -1- i;
                    reverse.add(result.get(j)+" "+i);
                }
                writeFile(reverse);
                break;
            }
            case "DFS": {
                Map one = new Map();
                one.buildMap(test);
                ArrayList <String> result = new ArrayList<String>();
                result = conductDFS(Begin,Goal,one);
                int re = result.size();
                ArrayList <String> reverse = new ArrayList<String>();
                int i;
                for (i = 0; i < re; i++)
                {
                    int j = re -1- i;
                    reverse.add(result.get(j)+" "+i);
                }
                writeFile(reverse);
                break;
            }
            case "UCS": {
                Map one = new Map();
                one.buildMap(test);
                ArrayList <String> result = new ArrayList<String>();
                result = conductUCS(Begin,Goal,one);
                int re = result.size();
                ArrayList <String> reverse = new ArrayList<String>();
                int i;
                for (i = 0; i < re; i++)
                {
                    int j = re -1- i;
                    reverse.add(result.get(j));
                }
                writeFile(reverse);
                break;
            }
            case "A*":
            {
                Map one = new Map();
                one.buildMap(test);
                ArrayList <String> result = new ArrayList<String>();
                result = conductA(Begin,Goal,one);
                int re = result.size();
                ArrayList <String> reverse = new ArrayList<String>();
                int i;
                for (i = 0; i < re; i++)
                {
                    int j = re -1- i;
                    reverse.add(result.get(j));
                }
                writeFile(reverse);
                break;
            }
        }
    }
    //System.out.print(test);
}

