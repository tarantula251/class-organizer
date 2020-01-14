package model.data;

import java.io.Serializable;
import java.util.ArrayList;

public class Course implements Data, Serializable
{
    private int id;
    private String name;
    private User supervisor;
    private Field field;
    private ArrayList<User> students = new ArrayList<User>();

    public Course(int id)
    {
        this.id = id;
    }

    public Course(int id, String name, User supervisor, Field field)
    {
        this.id = id;
        this.name = name;
        this.supervisor = supervisor;
        this.field = field;
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

    public User getSupervisor()
    {
        return supervisor;
    }

    public Field getField()
    {
        return field;
    }

    public ArrayList<User> getStudents()
    {
        return students;
    }

    public boolean addStudent(User student)
    {
        return students.add(student);
    }
}
