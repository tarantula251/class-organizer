package model.data;

public class Class
{
    private int id;
    private String name;
    private ClassType type;
    private Course course;
    private User teacher;

    public Class(int id, String name, ClassType type, Course course, User teacher)
    {
        this.id = id;
        this.name = name;
        this.type = type;
        this.course = course;
        this.teacher = teacher;
    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public ClassType getType()
    {
        return type;
    }

    public Course getCourse()
    {
        return course;
    }

    public User getTeacher()
    {
        return teacher;
    }
}
