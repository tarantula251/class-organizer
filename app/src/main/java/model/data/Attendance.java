package model.data;

import java.time.LocalDateTime;

public class Attendance
{
    private User user;
    private LocalDateTime checkInDateTime;
    private ClassDate classDate;

    public Attendance(User user, LocalDateTime checkInDateTime, ClassDate classDate)
    {
        this.user = user;
        this.checkInDateTime = checkInDateTime;
        this.classDate = classDate;
    }

    public User getUser()
    {
        return user;
    }

    public LocalDateTime getCheckInDateTime()
    {
        return checkInDateTime;
    }

    public ClassDate getClassDate()
    {
        return classDate;
    }
}
