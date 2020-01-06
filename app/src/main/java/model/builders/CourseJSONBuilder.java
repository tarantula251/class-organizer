package model.builders;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

import model.data.Course;
import model.data.DataComparator;
import model.data.Field;
import model.data.User;

public class CourseJSONBuilder implements JSONDataBuilder<Course>
{
    ArrayList<User> supervisors;
    ArrayList<Field> fields;

    public CourseJSONBuilder(ArrayList<User> supervisors, ArrayList<Field> fields)
    {
        this.supervisors = supervisors;
        this.fields = fields;
    }

    @Override
    public Course buildData(JSONObject jsonObject) throws JSONException
    {
        int supervisorId = jsonObject.getInt("supervisor");
        int fieldId = jsonObject.getInt("field");


        DataComparator dataComparator = new DataComparator();
        User supervisor;
        Field field;
        try
        {
            supervisor = supervisors.get(Collections.binarySearch(supervisors, new User(supervisorId), dataComparator));
            field = fields.get(Collections.binarySearch(fields, new Field(fieldId), dataComparator));
        }
        catch(IndexOutOfBoundsException e)
        {
            return null;
        }
        return new Course(jsonObject.getInt("id"),
                jsonObject.getString("name"),
                supervisor,
                field);
    }
}
