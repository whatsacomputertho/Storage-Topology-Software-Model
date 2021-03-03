package Main;

import Model.DataObject;
import Model.Edge;
import Model.StorageTopology;
import java.util.ArrayList;

public class Main
{
    public static void main(String[] args)
    {
        //Initialize storage topology
        StorageTopology topology = new StorageTopology(1.0f, 1.0f, StorageTopology.readWeightsFromFile("src/Model/weights.txt"), 5, 3);
        
        //Generate all feasible allocations
        ArrayList<ArrayList<DataObject>> feasibleAllocations = topology.filterFeasibleDiscreteAllocations(topology.generateAllDiscreteAllocations());
        
        //Actually allocate all feasible allocations and print local and global worst case latencies
        for(int i = 0; i < feasibleAllocations.size(); i++)
        {
            topology.allocateDiscreteObjects(feasibleAllocations.get(i));
            System.out.printf("Allocation: %d\tWorst Case Latency: %.2f\n", i, topology.calculateWorstCaseLatency());
        }
    }
}
