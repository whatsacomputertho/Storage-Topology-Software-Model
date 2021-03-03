package Model;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class StorageTopology
{
    private ArrayList<Node> nodes;
    private ArrayList<DataObject> objects;
    private ArrayList<Edge> edges;
    
    public StorageTopology(float storageCapacity, float objectSize, ArrayList<Float> weights)
    {
        this.nodes = new ArrayList();
        this.objects = new ArrayList();
        this.edges = new ArrayList();
        int numNodes = (int)Math.ceil(Math.random() * 10);
        int numObjects = (int)Math.floor(Math.random() * numNodes);
        
        for(int i = 0; i < numNodes; i++)
        {
            this.nodes.add(new Node(storageCapacity, String.format("N%d", i)));
        }
        
        for(int i = 0; i < numObjects; i++)
        {
            this.objects.add(new DataObject(objectSize, String.format("X%d", i)));
        }
        
        int k = 0;
        for(int i = 0; i < numNodes; i++)
        {
            for(int j = i + 1; j < numNodes; j++)
            {
                Edge e = new Edge(nodes.get(i), nodes.get(j), weights.get(k));
                this.edges.add(e);
                k++;
            }
        }
    }
    
    public StorageTopology(float storageCapacity, float objectSize, ArrayList<Float> weights, int numItems, boolean isNodes)
    {
        this.nodes = new ArrayList();
        this.objects = new ArrayList();
        this.edges = new ArrayList();
        if(isNodes)
        {
            for(int i = 0; i < numItems; i++)
            {
                this.nodes.add(new Node(storageCapacity, String.format("N%d", i)));
            }
            
            int numObjects = (int)Math.floor(Math.random() * numItems);
            
            for(int i = 0; i < numObjects; i++)
            {
                this.objects.add(new DataObject(objectSize, String.format("X%d", i)));
            }
        }
        else
        {
            for(int i = 0; i < numItems; i++)
            {
                this.objects.add(new DataObject(objectSize, String.format("X%d", i)));
            }
            
            int numNodes = (int)Math.ceil(Math.random() * numItems) + (int)Math.ceil(Math.random() * 10);
            
            for(int i = 0; i < numNodes; i++)
            {
                this.nodes.add(new Node(storageCapacity, String.format("N%d", i)));
            }
        }
        
        int k = 0;
        for(int i = 0; i < this.nodes.size(); i++)
        {
            for(int j = i + 1; j < this.nodes.size(); j++)
            {
                Edge e = new Edge(nodes.get(i), nodes.get(j), weights.get(k));
                this.edges.add(e);
                k++;
            }
        }
    }
    
    public StorageTopology(float storageCapacity, float objectSize, ArrayList<Float> weights, int numNodes, int numObjects)
    {
        this.nodes = new ArrayList();
        this.objects = new ArrayList();
        this.edges = new ArrayList();
        for(int i = 0; i < numNodes; i++)
        {
            this.nodes.add(new Node(storageCapacity, String.format("N%d", i)));
        }
        
        for(int i = 0; i < numObjects; i++)
        {
            this.objects.add(new DataObject(objectSize, String.format("X%d", i)));
        }
        
        int k = 0;
        for(int i = 0; i < numNodes; i++)
        {
            for(int j = i + 1; j < numNodes; j++)
            {
                Edge e = new Edge(nodes.get(i), nodes.get(j), weights.get(k));
                this.edges.add(e);
                k++;
            }
        }
    }

    public ArrayList<Node> getNodes()
    {
        return nodes;
    }

    public void setNodes(ArrayList<Node> nodes)
    {
        this.nodes = nodes;
    }

    public ArrayList<DataObject> getObjects()
    {
        return objects;
    }

    public void setObjects(ArrayList<DataObject> objects)
    {
        this.objects = objects;
    }

    public ArrayList<Edge> getEdges()
    {
        return edges;
    }

    public void setEdges(ArrayList<Edge> edges)
    {
        this.edges = edges;
    }
    
    public ArrayList<ArrayList<Integer>> generateAllDiscreteAllocationPatterns()
    {
        ArrayList<ArrayList<Integer>> allocationPatterns = new ArrayList();
        for(int i = 0; i < Math.pow(this.objects.size(), this.nodes.size()); i++)
        {
            int decPattern = i;
            ArrayList<Integer> pattern = new ArrayList();
            for(int j = this.nodes.size() - 1; j >= 0; j--)
            {
                int baseNPlaceValue = 0;
                int power = (int)Math.pow(this.objects.size(), j);
                while(decPattern >= power)
                {
                        decPattern -= power;
                        baseNPlaceValue++;
                }
                pattern.add(baseNPlaceValue);
            }
            allocationPatterns.add(pattern);
        }
        return allocationPatterns;
    }
    
    public ArrayList<ArrayList<DataObject>> generateAllDiscreteAllocations()
    {
        ArrayList<ArrayList<DataObject>> allocations = new ArrayList();
        for(ArrayList<Integer> pattern : generateAllDiscreteAllocationPatterns())
        {
            ArrayList<DataObject> allocation = new ArrayList();
            for(int i = 0; i < pattern.size(); i++)
            {
                allocation.add(objects.get(pattern.get(i)));
            }
            allocations.add(allocation);
        }
        return allocations;
    }
    
    public ArrayList<ArrayList<DataObject>> filterFeasibleDiscreteAllocations(ArrayList<ArrayList<DataObject>> allPatterns)
    {
        ArrayList<ArrayList<DataObject>> feasiblePatterns;
        for(DataObject x : this.objects)
        {
            for(int i = allPatterns.size() - 1; i >= 0; i--)
            {
                boolean hasX = false;
                for(DataObject y : allPatterns.get(i))
                {
                    if(x.equals(y))
                    {
                        hasX = true;
                    }
                }
                if(!hasX)
                {
                    allPatterns.remove(i);
                }
            }
        }
        feasiblePatterns = allPatterns;
        return feasiblePatterns;
    }
    
    private void clearObjectsStored()
    {
        for(Node n : this.nodes)
        {
            n.getObjectsStored().clear();
        }
    }
    
    public void allocateDiscreteObjects(ArrayList<DataObject> allocation)
    {
        clearObjectsStored();
        for(int i = 0; i < allocation.size(); i++)
        {
            ArrayList<DataObject> objectsStored = new ArrayList();
            objectsStored.add(allocation.get(i));
            this.nodes.get(i).setObjectsStored(objectsStored);
        }
    }
    
    public float calculateMinimumLatencyByNode(Node node, DataObject object)
    {
        ArrayList<Float> latencies = new ArrayList();
        for(Node n : this.nodes)
        {
            if(n.getObjectsStored().get(0).equals(object))
            {
                for(Edge e : this.edges)
                {
                    if((e.getNodes()[0].equals(node) && e.getNodes()[1].equals(n)) || (e.getNodes()[0].equals(n) && e.getNodes()[1].equals(node)))
                    {
                        latencies.add(e.getWeight());
                    }
                    else if(node.equals(n))
                    {
                        latencies.add(0.0f);
                    }
                }
            }
        }
        float minimumLatency = latencies.get(0);
        for(float f : latencies)
        {
            if(f < minimumLatency)
            {
                minimumLatency = f;
            }
        }
        return minimumLatency;
    }
    
    public ArrayList<Float> calculateMinimumLatenciesByNode(Node node)
    {
        ArrayList<Float> minimumLatencies = new ArrayList();
        for(DataObject object : this.objects)
        {
            minimumLatencies.add(calculateMinimumLatencyByNode(node, object));
        }
        return minimumLatencies;
    }
    
    public float calculateWorstCaseLatencyByNode(Node node)
    {
        ArrayList<Float> minimumLatencies = calculateMinimumLatenciesByNode(node);
        float worstCaseLatency = minimumLatencies.get(0);
        for(float f : minimumLatencies)
        {
            if(f > worstCaseLatency)
            {
                worstCaseLatency = f;
            }
        }
        return worstCaseLatency;
    }
    
    public ArrayList<ArrayList<Float>> calculateMinimumLatencies()
    {
        ArrayList<ArrayList<Float>> minimumLatencies = new ArrayList();
        for(Node node : this.nodes)
        {
            minimumLatencies.add(calculateMinimumLatenciesByNode(node));
        }
        return minimumLatencies;
    }
    
    public float calculateWorstCaseLatency()
    {
        ArrayList<Float> worstCaseLatencies = new ArrayList();
        for(Node node : this.nodes)
        {
            worstCaseLatencies.add(calculateWorstCaseLatencyByNode(node));
        }
        float worstCaseLatency = worstCaseLatencies.get(0);
        for(float f : worstCaseLatencies)
        {
            if(f > worstCaseLatency)
            {
                worstCaseLatency = f;
            }
        }
        return worstCaseLatency;
    }
    
    public static ArrayList<Float> readWeightsFromFile(String filePath)
    {
        ArrayList<Float> weights = new ArrayList();
        try
        {
            File f = new File(filePath);
            Scanner s = new Scanner(f);
            while(s.hasNext())
            {
                float fl = Float.parseFloat(s.next());
                weights.add(fl);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return weights;
    }
}