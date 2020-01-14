package model.data;

import java.io.Serializable;

public class UserType implements Data, Serializable
{
    private int id;
    private String name;

    public UserType(int id, String name)
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
