package model.data;

import java.util.ArrayList;

public class Course
{
    private int id;
    private String name;
    private User supervisor;
    private ArrayList<User> students = new ArrayList<User>();

    public Course(int id, String name, User supervisor)
    {
        this.id = id;
        this.name = name;
        this.supervisor = supervisor;
    }

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

    public ArrayList<User> getStudents()
    {
        return students;
    }

    public boolean addStudent(User student)
    {
        return students.add(student);
    }
}