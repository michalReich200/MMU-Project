import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dao.DaoFileImpl;
import dm.DataModel;
import org.junit.Ignore;
import org.junit.Test;
import server.Request;
import util.CLI;
//import /*C:.Users.user.CacheUnit.src.*/main.java.DataModel.java;
import java.io.*;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

import static org.junit.Assert.*;

public class CacheUnitTest {
    private BufferedReader br;

    @Test
    public void CacheUnitTest(){

    }
    @Test
    public void DataModelTest(){
        DataModel data=new DataModel(1234L,"file1");
        DataModel data2=new DataModel(12345L,"file2");
        assertFalse(data2==data);
        //assertEquals(data2.toString(),"DataModel{id=12345, content=file2}");
        //assertEquals(data2.getDataModelId(),12345);

    }
    @Test
    public void DaoFileImplTest(){
        DaoFileImpl<String> file= new DaoFileImpl<String>("C:/Users/user/CacheUnit/src/main/resources/dataSource.txt",3);
        DataModel d=new DataModel(12345L,"file2");
        file.save(d);
        //file.delete( );
    }
    @Test
    public void CLItest() throws FileNotFoundException {
        CLI cli=new CLI(System.in,System.out);
        System.out.println(cli.changeListener);

    }
    @Test
    public void RequestTest() throws FileNotFoundException {


    }


    @Test
    public void testUpdate() {

        Map<String, String> headerReq = new HashMap<>();
        headerReq.put("action", "UPDATE");

        DataModel<String>[] dmArray = new DataModel[]{new DataModel<String>(1L, "a"), new DataModel<String>(2L, "b")};

        Request<DataModel<String>[]> req = new Request<>(headerReq, dmArray);
        req.getHeaders();
        Gson gson = new Gson();
        try {
            Socket socket = new Socket("127.0.0.1", 12345);
            DataOutputStream writer = new DataOutputStream(socket.getOutputStream());
            writer.writeUTF(gson.toJson(req));
            writer.flush();


            DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String content = "";

            do {
                content = in.readUTF();
                sb.append(content);
            } while (in.available() != 0);

            content = sb.toString();
            Boolean response=true;
            response = new Gson().fromJson(content, response.getClass());
            System.out.println("message from server: " + response);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //@Ignore
    @Test
    public void testGet(){
        Map<String, String> headerReq = new HashMap<>();
        headerReq.put("action", "GET");

        DataModel<String>[] dmArray = new DataModel[]{new DataModel<String>(1L, "bb"), new DataModel<String>(2L, "aa")};
        Request<DataModel<String>[]> req = new Request<>(headerReq, dmArray);

        Gson gson = new Gson();
        try {
            Socket socket = new Socket("127.0.0.1", 12345);
            DataOutputStream writer = new DataOutputStream(socket.getOutputStream());
            writer.writeUTF(gson.toJson(req));
            writer.flush();

            DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String content = "";

            do {
                content = in.readUTF();
                sb.append(content);
            } while (in.available() != 0);

            content = sb.toString();
            Type requestType = new TypeToken<DataModel<String>[]>() {}.getType();
            DataModel<String>[] response = new Gson().fromJson(content, requestType);
            System.out.println("message from server: " + Arrays.toString(response));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //@Ignore
    @Test
    public void testDelete(){
        Map<String, String> headerReq = new HashMap<>();
        headerReq.put("action", "DELETE");

        DataModel<String>[] dmArray = new DataModel[]{new DataModel<String>(1L, "b")};
        Request<DataModel<String>[]> req = new Request<>(headerReq, dmArray);

        Gson gson = new Gson();
        try {
            Socket socket = new Socket("127.0.0.1", 12345);
            DataOutputStream writer = new DataOutputStream(socket.getOutputStream());
            writer.writeUTF(gson.toJson(req));
            writer.flush();

            DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String content = "";

            do {
                content = in.readUTF();
                sb.append(content);
            } while (in.available() != 0);

            content = sb.toString();
            Boolean response=true;
            response = new Gson().fromJson(content, response.getClass());
            System.out.println("message from server: " + response);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

