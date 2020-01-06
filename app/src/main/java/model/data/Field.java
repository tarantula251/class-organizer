package model.data;

public class Field implements Data
{
    private int id;
    private String name;
    private Faculty faculty;
    private Model model;
    private Cycle cycle;

    public Field(int id)
    {
        this.id = id;
    }

    public Field(int id, String name, Faculty faculty, Model model, Cycle cycle)
    {
        this.id = id;
        this.name = name;
        this.faculty = faculty;
        this.model = model;
        this.cycle = cycle;
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

    public Faculty getFaculty()
    {
        return faculty;
    }

    public Model getModel()
    {
        return model;
    }

    public Cycle getCycle()
    {
        return cycle;
    }
}
