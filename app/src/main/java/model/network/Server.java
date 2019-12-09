package model.network;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import model.builders.CycleJSONBuilder;
import model.builders.FacultyJSONBuilder;
import model.builders.FieldJSONBuilder;
import model.builders.JSONDataBuilder;
import model.builders.ModelJSONBuilder;
import model.data.Cycle;
import model.data.Faculty;
import model.data.Field;
import model.data.Model;
import model.data.User;

public class Server
{
    private Socket socket;
    private BufferedReader inputBuffer;
    private DataOutputStream dataOutputStream;
    private PrintWriter outputWriter;

    //Message codes
    private static final byte fieldGetCode = (byte)100;
    private static final byte facultyGetAllCode = (byte)200;
    private static final byte modelGetAllCode = (byte)201;
    private static final byte cycleGetAllCode = (byte)202;

    public Server(String ipAddress, int port) throws IOException
    {
        socket = new Socket(ipAddress, port);
        inputBuffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
        outputWriter = new PrintWriter(socket.getOutputStream(), true);
    }

    private void sendMessage(byte messageCode, String message) throws IOException
    {
        dataOutputStream.write(messageCode);
        dataOutputStream.flush();
        outputWriter.println(message);
    }

    private String readResponse() throws IOException
    {
        return inputBuffer.readLine();
    }
    private <T> ArrayList<T> getDataArrayList(byte messageCode, String message, JSONDataBuilder<T> dataBuilder)
    {
        ArrayList<T> result = new ArrayList<>();
        String response;

        try
        {
            sendMessage(messageCode, message);
            response = readResponse();

        }
        catch(IOException exception)
        {
            return null;
        }
        if(response == null)
        {
            return null;
        }

        try
        {
            JSONArray jsonArray = new JSONArray(response);
            for(int index = 0; index < jsonArray.length(); ++index)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(index);

                result.add(dataBuilder.buildData(jsonObject));
            }
        }
        catch(JSONException exception)
        {
            return null;
        }

        return result;
    }

    public ArrayList<Faculty> getFaculties()
    {
        FacultyJSONBuilder facultyJSONBuilder = new FacultyJSONBuilder();
        return getDataArrayList(facultyGetAllCode, "", facultyJSONBuilder);
    }

    public ArrayList<Model> getModels()
    {
        ModelJSONBuilder modelJSONBuilder = new ModelJSONBuilder();
        return getDataArrayList(modelGetAllCode, "", modelJSONBuilder);
    }

    public ArrayList<Cycle> getCycles()
    {
        CycleJSONBuilder cycleJSONBuilder = new CycleJSONBuilder();
        return getDataArrayList(cycleGetAllCode, "", cycleJSONBuilder);
    }

    public ArrayList<Field> getFields(Faculty faculty, Model model, Cycle cycle)
    {
        JSONObject requestData = new JSONObject();
        FieldJSONBuilder fieldJSONBuilder = new FieldJSONBuilder(faculty, model, cycle);

        if(faculty == null || model == null || cycle == null) return null;

        try
        {
            requestData.put("faculty", faculty.getId());
            requestData.put("model", model.getId());
            requestData.put("cycle", cycle.getId());
        }
        catch(JSONException e)
        {
            return null;
        }

        return getDataArrayList(fieldGetCode, requestData.toString(), fieldJSONBuilder);
    }
}
