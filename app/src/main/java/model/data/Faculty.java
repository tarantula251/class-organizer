package model.data;

import java.io.Serializable;

public class Faculty implements Data, Serializable
{
    private int id;
    private String name;

    public Faculty(int id, String name)
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
