package model.data;

import java.io.Serializable;

public class Model implements Data, Serializable
{
    private int id;
    private String name;

    public Model(int id, String name)
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
