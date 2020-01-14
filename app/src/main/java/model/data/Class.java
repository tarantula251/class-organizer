package model.data;

import java.io.Serializable;

public class Class implements Data, Serializable
{
    private int id;
    private ClassType type;
    private Course course;
    private User teacher;

    public Class(int id, ClassType type, Course course, User teacher)
    {
        this.id = id;
        this.type = type;
        this.course = course;
        this.teacher = teacher;
    }

    @Override
    public int getId()
    {
        return id;
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
