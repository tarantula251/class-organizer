package model.data;

public class Field
{
    private int id;
    private int cycle;
    private String name;
    private Faculty faculty;
    private Model model;

    public Field(int id, String name, int cycle, Faculty faculty, Model model)
    {
        this.id = id;
        this.cycle = cycle;
        this.name = name;
        this.faculty = faculty;
        this.model = model;
    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public int getCycle()
    {
        return cycle;
    }

    public Faculty getFaculty()
    {
        return faculty;
    }

    public Model getModel()
    {
        return model;
    }
}
