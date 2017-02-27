/**
 * Created by wenqingcao on 9/13/16.
 */
public class MapEdge {
    private String parent;
    private String child;
    private int value;

    public MapEdge(String a, String b, int x)
    {
        this.parent = a;
        this.child = b;
        this.value = x;
    }

    public String getParent()
    {
        return this.parent;
    }

    public String getChild()
    {
        return this.child;
    }

    public int getValue()
    {
        return this.value;
    }

    public void setParent(String p)
    {
        this.parent=p;
    }

    public void setChild(String c)
    {
        this.child=c;
    }

    public void setValue(int x)
    {
        this.value = x;
    }

    public MapNode getChildNode()
    {
        MapNode temp = new MapNode(child);
        return temp;
    }

    public MapNode getParentNode()
    {
        MapNode temp = new MapNode(parent);
        return temp;
    }


}
