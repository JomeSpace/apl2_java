package org.example;// importing JSON simple libraries
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public class Main {
    public static void main(String[] args) throws Exception
    {
        Object object1 = new JSONParser().parse(new FileReader("package.json"));

        JSONObject jsonObject1 = (JSONObject)object1;

        String name = (String)jsonObject1.get("name");
        System.out.println(name);


        JSONObject jsonObject2 = new JSONObject();

        jsonObject2.put("name","John");

        PrintWriter printWriter = new PrintWriter("package.json");
        printWriter.write(jsonObject2.toJSONString());
        printWriter.flush();
        printWriter.close();


        Object object3 = new JSONParser().parse(new FileReader("package.json"));

        JSONObject jsonObject3 = (JSONObject)object3;

        name = (String)jsonObject3.get("name");
        System.out.println(name);
    }
}
