package model.data;

public class Field
{
    private int id;
    private String name;
    private String cycle;
    private Faculty faculty;
    private Model model;

    public Field(int id, String name, String cycle, Faculty faculty, Model model)
    {
        this.id = id;
        this.name = name;
        this.cycle = cycle;
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

    public String getCycle()
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
