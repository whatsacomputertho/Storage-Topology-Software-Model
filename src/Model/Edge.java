package Model;

public class Edge
{
    private Node nodes[] = new Node[2];
    private float weight;
    
    public Edge(Node v1, Node v2, float weight)
    {
        this.nodes[0] = v1;
        this.nodes[1] = v2;
        this.weight = weight;
    }

    public Node[] getNodes()
    {
        return nodes;
    }

    public void setNodes(Node[] nodes)
    {
        this.nodes = nodes;
    }

    public float getWeight()
    {
        return weight;
    }

    public void setWeight(float weight)
    {
        this.weight = weight;
    }
}
