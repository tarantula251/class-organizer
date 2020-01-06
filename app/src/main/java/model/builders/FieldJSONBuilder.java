package model.builders;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

import model.data.Cycle;
import model.data.DataComparator;
import model.data.Faculty;
import model.data.Field;
import model.data.Model;

public class FieldJSONBuilder implements JSONDataBuilder<Field>
{
    private ArrayList<Faculty> faculties;
    private ArrayList<Model> models;
    private ArrayList<Cycle> cycles;

    public FieldJSONBuilder(ArrayList<Faculty> faculties, ArrayList<Model> models, ArrayList<Cycle> cycles)
    {
        this.faculties = faculties;
        this.models = models;
        this.cycles = cycles;
    }

    @Override
    public Field buildData(JSONObject jsonObject) throws JSONException
    {
        int facultyId = jsonObject.getInt("faculty");
        int modelId = jsonObject.getInt("model");
        int cycleId = jsonObject.getInt("cycle");


        DataComparator dataComparator = new DataComparator();
        Faculty faculty;
        Model model;
        Cycle cycle;
        try
        {
            faculty = faculties.get(Collections.binarySearch(faculties, new Faculty(facultyId, ""), dataComparator));
            model = models.get(Collections.binarySearch(models, new Model(modelId, ""), dataComparator));
            cycle = cycles.get(Collections.binarySearch(cycles, new Cycle(cycleId, ""), dataComparator));
        }
        catch(IndexOutOfBoundsException e)
        {
            return null;
        }

        return new Field(jsonObject.getInt("id"),
                jsonObject.getString("name"),
                faculty,
                model,
                cycle);
    }
}
