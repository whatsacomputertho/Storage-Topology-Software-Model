package Model;

public class DataObject
{
    private String name;
    private float size;
    
    public DataObject(float objectSize, String name)
    {
        this.size = objectSize;
        this.name = name;
    }

    public float getSize()
    {
        return size;
    }

    public void setSize(float size)
    {
        this.size = size;
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
