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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import model.builders.ClassFromTeacherJSONBuilder;
import model.builders.CourseJSONBuilder;
import model.builders.CycleJSONBuilder;
import model.builders.FacultyJSONBuilder;
import model.builders.FieldJSONBuilder;
import model.builders.JSONDataBuilder;
import model.builders.ModelJSONBuilder;
import model.builders.ResultJSONBuilder;
import model.builders.UserJSONBuilder;
import model.data.Course;
import model.data.Cycle;
import model.data.Faculty;
import model.data.Field;
import model.data.Model;
import model.data.Result;
import model.data.User;
import model.data.Class;

public class Server
{
    private static Server instance = null;
    private Socket socket = null;
    private BufferedReader inputBuffer;
    private DataOutputStream dataOutputStream;
    private PrintWriter outputWriter;
    private String ipAddress;
    private int port;
    private User authorizedUser = null;

    //Message codes
    private static final byte authorizeCode = (byte)2;
    private static final byte classGetForTeacherCode = (byte)100;
    private static final byte userGetForClass = (byte)101;
    private static final byte gradeAddForUserEmailId = (byte)102;
    private static final byte userGetForEmailId = (byte)103;
    private static final byte gradeGetForUserClass = (byte)104;
    private static final byte gradeUpdate = (byte)105;
    private static final byte facultyGetAllCode = (byte)200;
    private static final byte modelGetAllCode = (byte)201;
    private static final byte cycleGetAllCode = (byte)202;
    private static final byte userGetAllCode = (byte)203;
    private static final byte courseGetAllCode = (byte)204;
    private static final byte fieldGetAllCode = (byte)205;

    static public Server getInstance()
    {
        if(instance == null) instance = new Server();
        return instance;
    }

    private Server()
    {}

    public String getIpAddress()
    {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress)
    {
        this.ipAddress = ipAddress;
    }

    public int getPort()
    {
        return port;
    }

    public void setPort(int port)
    {
        this.port = port;
    }

    public User getAuthorizedUser()
    {
        return authorizedUser;
    }

    public void setAuthorizedUser(User authorizedUser)
    {
        this.authorizedUser = authorizedUser;
    }

    public void connect() throws IOException
    {
        if(socket != null) return;
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
            exception.printStackTrace();
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
                T object = dataBuilder.buildData(jsonObject);
                if(object != null) result.add(object);
            }
        }
        catch(JSONException exception)
        {

            exception.printStackTrace();
            return null;
        }

        return result;
    }

    public ArrayList<User> getUsers()
    {
        UserJSONBuilder userJSONBuilder = new UserJSONBuilder();
        return getDataArrayList(userGetAllCode, "", userJSONBuilder);
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

    public ArrayList<Field> getFields(ArrayList<Faculty> faculties, ArrayList<Model> models, ArrayList<Cycle> cycles)
    {
        FieldJSONBuilder fieldJSONBuilder = new FieldJSONBuilder(faculties, models, cycles);

        if(faculties == null || models == null || cycles == null) return null;

        return getDataArrayList(fieldGetAllCode, "", fieldJSONBuilder);
    }

    public ArrayList<Course> getCourses(ArrayList<User> supervisors, ArrayList<Field> fields)
    {
        CourseJSONBuilder courseJSONBuilder = new CourseJSONBuilder(supervisors, fields);

        if(supervisors == null || fields == null) return null;

        return getDataArrayList(courseGetAllCode, "", courseJSONBuilder);
    }

    public ArrayList<Class> getClassesForTeacher(ArrayList<Course> courses, User teacher)
    {
        if(courses == null || teacher == null) return  null;

        JSONObject requestDataJson = new JSONObject();
        try
        {
            requestDataJson.put("teacher", teacher.getId());
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
        ClassFromTeacherJSONBuilder classFromTeacherJSONBuilder = new ClassFromTeacherJSONBuilder(courses, teacher);

        return getDataArrayList(classGetForTeacherCode, requestDataJson.toString(), classFromTeacherJSONBuilder);
    }

    public ArrayList<User> getUsersForClass(Class classObject)
    {
        if(classObject == null) return null;

        JSONObject requestDataJson = new JSONObject();
        try
        {
            requestDataJson.put("class", classObject.getId());
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
        UserJSONBuilder userJSONBuilder = new UserJSONBuilder();

        return getDataArrayList(userGetForClass, requestDataJson.toString(), userJSONBuilder);

    }

    public User getUserForEmailId(String emailId)
    {
        if(emailId == null) return null;

        JSONObject requestDataJson = new JSONObject();
        try
        {
            requestDataJson.put("emailId", emailId + "%");
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
        UserJSONBuilder userJSONBuilder = new UserJSONBuilder();

        ArrayList<User> userArray = getDataArrayList(userGetForEmailId, requestDataJson.toString(), userJSONBuilder);

        return userArray == null ? null : userArray.get(0);
    }

    public ArrayList<Result> getResultsForUserClass(User user, Class classObject)
    {
        if(user == null | classObject == null) return null;

        JSONObject requestDataJson = new JSONObject();
        try
        {
            requestDataJson.put("user", user.getId());
            requestDataJson.put("class", classObject.getId());
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
        ResultJSONBuilder resultJSONBuilder = new ResultJSONBuilder(classObject, user);

        return getDataArrayList(gradeGetForUserClass, requestDataJson.toString(), resultJSONBuilder);
    }

    public Integer addResultForUserEmailId(String emailId, String title, String description, float result, Class classObject) throws ServerException
    {
        JSONObject requestDataJson = new JSONObject();
        String response;
        try
        {
            requestDataJson.put("emailId", emailId);
            requestDataJson.put("title", title);
            requestDataJson.put("note", description);
            requestDataJson.put("score", result);
            requestDataJson.put("class", classObject.getId());
            sendMessage(gradeAddForUserEmailId, requestDataJson.toString());
            response = readResponse();
            return new JSONObject(response).getInt("id");
        }
        catch(JSONException e)
        {
            throw new ServerException(e.hashCode(), "JSON couldn't be parsed");
        }
        catch(IOException e)
        {
            throw new ServerException(e.hashCode(), e.getMessage());
        }
    }

    public void updateResult(int id, String title, String description, float result) throws ServerException
    {
        JSONObject requestDataJson = new JSONObject();
        String response;
        try
        {
            requestDataJson.put("id", id);
            requestDataJson.put("title", title);
            requestDataJson.put("note", description);
            requestDataJson.put("score", result);
            sendMessage(gradeUpdate, requestDataJson.toString());
            response = readResponse();
            ServerExceptionJSONBuilder serverExceptionJSONBuilder = new ServerExceptionJSONBuilder();
            ServerException serverException = serverExceptionJSONBuilder.buildData(new JSONArray(response).getJSONObject(0));
            if(serverException.getErrorCode() != 0) throw serverException;
        }
        catch(JSONException e)
        {
            throw new ServerException(e.hashCode(), "JSON couldn't be parsed");
        }
        catch(IOException e)
        {
            throw new ServerException(e.hashCode(), e.getMessage());
        }
    }

    public User authorize(String email, String password) throws ServerException
    {
        User user;
        JSONObject requestData = new JSONObject();
        JSONObject userJson;
        String response;
        try
        {
            String encryptedPassword = new String(MessageDigest.getInstance("MD5").digest(password.getBytes()));
            requestData.put("email", email);
            requestData.put("password", encryptedPassword);
            sendMessage(authorizeCode, requestData.toString());
            response = readResponse();
            userJson = new JSONArray(response).getJSONObject(0);
        }
        catch(JSONException e)
        {
            throw new ServerException(e.hashCode(), "JSON couldn't be parsed");
        }
        catch(IOException | NoSuchAlgorithmException e)
        {
            throw new ServerException(e.hashCode(), e.getMessage());
        }

        UserJSONBuilder userJSONBuilder = new UserJSONBuilder();
        try
        {
            user = userJSONBuilder.buildData(userJson);
        }
        catch(JSONException e)
        {
            ServerExceptionJSONBuilder serverExceptionJSONBuilder = new ServerExceptionJSONBuilder();
            try
            {
                throw serverExceptionJSONBuilder.buildData(userJson);
            }
            catch(JSONException ex)
            {
                throw new ServerException(ex.hashCode(), ex.getMessage());
            }
        }

        return user;
    }
}
