package model.builders;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

import model.data.Class;
import model.data.ClassType;
import model.data.Course;
import model.data.DataComparator;
import model.data.User;

public class ClassFromTeacherJSONBuilder implements JSONDataBuilder<Class>
{
    ArrayList<Course> courses;
    User teacher;

    public ClassFromTeacherJSONBuilder(ArrayList<Course> courses, User teacher)
    {
        this.courses = courses;
        this.teacher = teacher;
    }

    @Override
    public Class buildData(JSONObject jsonObject) throws JSONException
    {
        int courseId = jsonObject.getInt("course");
        int typeId = jsonObject.getJSONObject("classType").getInt("id");
        String typeName = jsonObject.getJSONObject("classType").getString("name");
        ClassType classType = new ClassType(typeId, typeName);

        DataComparator dataComparator = new DataComparator();
        Course course;
        try
        {
            course = courses.get(Collections.binarySearch(courses, new Course(courseId), dataComparator));
        }
        catch(IndexOutOfBoundsException e)
        {
            return null;
        }

        return new Class(jsonObject.getInt("id"),
                classType,
                course,
                teacher);
    }
}
