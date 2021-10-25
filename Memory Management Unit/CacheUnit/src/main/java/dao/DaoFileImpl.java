package dao;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.lang.Object;
import java.lang.Long;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dm.DataModel;

public class DaoFileImpl<T> extends Object implements IDao<Long, DataModel<T>> {

    private String filePath;
    private int capacity;
    private Map <Long, DataModel<T>> map;
    private ObjectOutputStream os;
    private Gson gson;

    /**
     *
     * @param filePath the path of the file  which  we use as the hard disk
     */
    public DaoFileImpl(String filePath) {
        this.filePath = filePath;
        map = new HashMap<Long, DataModel<T>>();
        gson = new Gson();
    }

    /***
     *
     * @param filePath the path of the file  which  we use as the hard disk
     * @param capacity the capacity of the ram we use an hash map to simulate it
     *
     */
    public DaoFileImpl(String filePath, int capacity) {
        this.filePath = filePath;
        this.capacity = capacity;
        map = new HashMap<Long, DataModel<T>>(capacity);
    }

    /**
     *
     * @param entity this is the data we want to delete from the hardDisk-our file
     *               the function reads the file to a map
     *               removes the wanted data and updates the hardDisk
     */
    @Override
    public void delete(DataModel<T> entity) {

        try {
            read();
       } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        map.remove(entity.getDataModelId());
        try {
            Update();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param id is the id of the data we want to fined
     * @return the anted data if its found
     */
    @Override
    public DataModel<T> find(Long id) {

        try {
            read();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return map.get(id);
    }

    /**
     * this function reads and copies the data from the hardDisk-file to the RAM-map
     * @throws FileNotFoundException
     */

    private void read() throws FileNotFoundException{
        File file = new File(filePath);
        Type arrayType = new TypeToken<HashMap<Long, DataModel<T>>>() {
        }.getType();

        try (FileReader fileReader = new FileReader(file)) {
            Gson gson = new Gson();
            HashMap<Long, DataModel<T>> fileContent = gson.fromJson(fileReader, arrayType);
            if (fileContent != null) {
                map = fileContent;
            }
        } catch (FileNotFoundException exception) {
            throw exception;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     *
     * @param entity -the new or updated data we want to save from RAM to hardDisk
     *              reads the hardDisk to map adds given entity to  map then copies map back to hardDisk
     */
    @Override
    public void save(DataModel<T> entity) {

        try {
            read();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (entity != null) {
            if (!(entity.equals( find(entity.getDataModelId()))))
                map.put(entity.getDataModelId(), new DataModel<T>(entity.getDataModelId(), entity.getContent()));
            try {
                Update();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * writes the content in map to hardDisk
     * @throws Exception
     */
    private void Update() throws Exception{
        File file = new File(filePath);
        try (FileWriter fileWriter = new FileWriter(file)) {
            Gson gson = new Gson();
            String jsonEntity = gson.toJson(map);
            fileWriter.write(jsonEntity);
        } catch (FileNotFoundException exception) {
            throw exception;
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    }