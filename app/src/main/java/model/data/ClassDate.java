package model.data;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ClassDate implements Data, Serializable
{
    private int id;
    private LocalDateTime dateTime;
    private boolean tookPlace;
    private Class classObject;

    public ClassDate(int id, LocalDateTime dateTime, boolean tookPlace, Class classObject)
    {
        this.id = id;
        this.dateTime = dateTime;
        this.tookPlace = tookPlace;
        this.classObject = classObject;
    }

    @Override
    public int getId()
    {
        return id;
    }

    public LocalDateTime getDateTime()
    {
        return dateTime;
    }

    public boolean hasTakenPlace()
    {
        return tookPlace;
    }

    public Class getClassObject()
    {
        return classObject;
    }
}
