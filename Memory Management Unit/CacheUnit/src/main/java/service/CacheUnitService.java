package service;



import com.mby.algorithm.IAlgoCache;
import com.mby.algorithm.main.java.LruAlgoCacheImpl;
import dao.DaoFileImpl;
import dao.IDao;
import dm.DataModel;
import memory.CacheUnit;

import java.util.HashMap;
import java.util.Map;

public class CacheUnitService<T>
        extends java.lang.Object {
    private IDao dao;
    private CacheUnit cacheAlgoAdmin;
    private  int capacity;
    private String algoType;
    private static int numOfRequests,numOfGETRequests,numOfUPDATERequests,numOfDELETERequests,numOfSwaps,numOfDataModel;

    /**
     * constructor init the statistic variables
     */
    public CacheUnitService()
    {
        capacity = 3;
        algoType = "LRU";
        dao = new DaoFileImpl<T>("src/main/resources/source.json");
        cacheAlgoAdmin = new CacheUnit(new LruAlgoCacheImpl<Long, DataModel<T>>(capacity));
        numOfRequests = 0;
        numOfGETRequests = 0;
        numOfUPDATERequests = 0;
        numOfDELETERequests = 0;
        numOfSwaps = 0;
        numOfDataModel = 0;
    }


    public int getCapacity() {
        return capacity;
    }
    public int getNumOfDataModel() {
        return numOfDataModel;
    }

    public String getAlgoType() {
        return algoType;
    }

    public int getNumOfGETRequests() {
        return numOfGETRequests;
    }
    public int getNumOfDELETERequests() {
        return numOfDELETERequests;
    }
    public int getNumOfUPDATERequests() {
        return numOfUPDATERequests;
    }
    public int getNumOfRequests() {
        return numOfRequests;
    }
    public int getNumOfSwaps() {
        return numOfSwaps;
    }

    /**
     *
     *@param dataModels which we have to update to ram
     *@return true if success ,false if not
     *
     */
    public boolean update(DataModel<T>[] dataModels)
    {
        numOfRequests+=1;
        DataModel<T>[] tempDM = cacheAlgoAdmin.putDataModels(dataModels);
        for (int i = 0; i < dataModels.length; i++) {
            if (tempDM[i] != null) {
                dao.save(tempDM[i]);
                numOfUPDATERequests += 1;
                numOfDataModel += 1;
            }
        }

        return true;
    }
    /**
     *
     *@param dataModels which we have to delete from ram
     *@return true if success ,false if not
     *
     */
    public boolean delete(DataModel<T>[] dataModels)
    {
        numOfRequests+=1;
        Long[] ids = new Long[dataModels.length];
        for (int i = 0; i < dataModels.length; i++) {
            dao.delete(dataModels[i]);
        }
        for (int i = 0; i < dataModels.length; i++) {
            ids[i]=dataModels[i].getDataModelId();
            numOfDELETERequests += 1;
            numOfDataModel += 1;
        }

        cacheAlgoAdmin.removeDataModels(ids);

        return true;
    }

    /**
     *
     *@param dataModels which we have to get from ram
     *@return the data models from the ram
     *
     */
    public DataModel<T>[] get(DataModel<T>[] dataModels) {
        numOfRequests+=1;
        Long[] data_ids = new Long[dataModels.length];
        DataModel<T>[] returneddata = new DataModel[dataModels.length];
        try {
            for (int i = 0; i < dataModels.length; i++) {
                data_ids[i] = dataModels[i].getDataModelId();
                numOfDataModel += 1;
            }
            returneddata = cacheAlgoAdmin.getDataModels(data_ids);
            for (int i = 0; i < dataModels.length; i++) {
                if (returneddata[i] == null) {
                    returneddata[i] = (DataModel<T>) dao.find(data_ids[i]);
                    if (returneddata[i] != null) {
                        numOfSwaps += 1;
                        DataModel<T>[] tempArray = new DataModel[1];
                        tempArray[0] = returneddata[i];
                        tempArray = cacheAlgoAdmin.putDataModels(tempArray);
                        if (tempArray[0] != null) {
                            dao.save(tempArray[0]);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  returneddata;
    }

    /**
     * update the data models from ram to hardDisk.
     */
    public void updateCache(){
        Map<Long,DataModel<T>> cacheMap = new HashMap();
        cacheMap = cacheAlgoAdmin.getCache();
        for (Map.Entry<Long, DataModel<T>> entry : cacheMap.entrySet())
        {
            dao.save(entry.getValue());
        }

    }
}
