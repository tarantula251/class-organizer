package model.data;

import java.time.LocalDateTime;

public class Result
{
    private int id;
    private double mark;
    private String note;
    private LocalDateTime addedDateTime;
    private User student;
    private ClassDate classDate;

    public Result(int id, double mark, String note, LocalDateTime addedDateTime, User student, ClassDate classDate)
    {
        this.id = id;
        this.mark = mark;
        this.note = note;
        this.addedDateTime = addedDateTime;
        this.student = student;
        this.classDate = classDate;
    }

    public int getId()
    {
        return id;
    }

    public double getMark()
    {
        return mark;
    }

    public String getNote()
    {
        return note;
    }

    public LocalDateTime getAddedDateTime()
    {
        return addedDateTime;
    }

    public User getStudent()
    {
        return student;
    }

    public ClassDate getClassDate()
    {
        return classDate;
    }
}
