package Model;

import java.util.ArrayList;

public class Node
{
    private float storageCapacity;
    private ArrayList<DataObject> objectsStored;
    private String name;
    
    public Node(float storageCapacity, String name)
    {
        this.objectsStored = new ArrayList();
        this.storageCapacity = storageCapacity;
        this.name = name;
    }

    public float getStorageCapacity()
    {
        return storageCapacity;
    }

    public void setStorageCapacity(float storageCapacity)
    {
        this.storageCapacity = storageCapacity;
    }

    public ArrayList<DataObject> getObjectsStored()
    {
        return objectsStored;
    }

    public void setObjectsStored(ArrayList<DataObject> objectsStored)
    {
        this.objectsStored = objectsStored;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}