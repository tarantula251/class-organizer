package model.data;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Result implements Data, Serializable
{
    private int id;
    private double score;
    private String title;
    private String note;
    private LocalDateTime lastUpdated;
    private User student;
    private Class classObject;

    public Result(int id, double score, String title, String note, LocalDateTime lastUpdated, User student, Class classObject)
    {
        this.id = id;
        this.score = score;
        this.title = title;
        this.note = note;
        this.lastUpdated = lastUpdated;
        this.student = student;
        this.classObject = classObject;
    }

    @Override
    public int getId()
    {
        return id;
    }

    public double getScore()
    {
        return score;
    }

    public String getTitle()
    {
        return title;
    }

    public String getNote()
    {
        return note;
    }

    public LocalDateTime getLastUpdated()
    {
        return lastUpdated;
    }

    public User getStudent()
    {
        return student;
    }

    public Class getClassObject()
    {
        return classObject;
    }
}
