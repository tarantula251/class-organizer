package model.data;

public class UserType implements Data
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
