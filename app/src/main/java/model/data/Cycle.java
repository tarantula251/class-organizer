package model.data;

public class Cycle implements Data
{
    private int id;
    private String name;

    public Cycle(int id, String name)
    {
        this.id = id;
        this.name = name;
    }

    @Override
    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }
}
