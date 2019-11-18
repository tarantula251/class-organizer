package model.data;

public class User
{
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private UserType type;

    public User(int id, String firstName, String lastName, String email, UserType type)
    {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.type = type;
    }

    public int getId()
    {
        return id;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public String getEmail()
    {
        return email;
    }

    public UserType getType()
    {
        return type;
    }
}
