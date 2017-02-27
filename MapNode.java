import java.util.Comparator;

/**
 * Created by wenqingcao on 9/13/16.
 */
public class MapNode {
    private String name;
    private int estCost = 0;
    private int cost = 0;
    private int order =0;

    public MapNode(String s)
    {
        this.name = s;
    }

    public void setEstCost(int x)
    {
        this.estCost = x;
    }

    public void setName(String s)
    {
        this.name = s;
    }
    public String getName()
    {
        return name;
    }

    public int getEstCost()
    {
        return estCost;
    }

    public void setCost(int m)
    {
        this.cost = m;
    }

    public int getCost()
    {
        return cost;
    }

    public void setOrder(int n)
    {
        this.order = n;
    }

    public int getOrder()
    {
        return order;
    }
}
