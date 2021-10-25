package memory;

import com.mby.algorithm.IAlgoCache;
import dao.DaoFileImpl;
import dm.DataModel;

import java.util.Map;


public class CacheUnit<T> extends java.lang.Object{

    public IAlgoCache<java.lang.Long, DataModel<T>> algo;

    /**
     *
     * @param algo which algorithm to use an page fault
     */
    public CacheUnit(com.mby.algorithm.IAlgoCache<java.lang.Long, DataModel<T>> algo) {

        this.algo=algo;

    }

    /**
     *
     * @param ids- removes all data with these given ids
     */
    public void removeDataModels(java.lang.Long[] ids)
    {
        for( java.lang.Long id:ids) {
            algo.removeElement(id);
        }
    }

    /**
     *
     * @param datamodels which have to put in the ram
     * @return list of the data that insert to the ram
     */
    public DataModel<T>[] putDataModels(DataModel<T>[] datamodels)

    {
        DataModel<T>[] data=new DataModel[datamodels.length];
        for (int i=0;i<datamodels.length;i++){
            data[i] =  algo.putElement(datamodels[i].getDataModelId(),datamodels[i]);
        }
        return data;
    }

    /**
     *
     * @param ids of the data modals which have to return to the user.
     * @return the  data models of the ids.
     */
    public DataModel<T>[] getDataModels(java.lang.Long[] ids) {
        DataModel<T>[] data=new DataModel[ids.length];
        for(int i=0;i<ids.length;i++) {
            data[i] = algo.getElement(ids[i]) ;

        }
        return data;
    }

    /**
     *
     * @return all cache as a map
     */
    public Map<Long,DataModel<T>> getCache(){
       Map <Long,DataModel<T>> map = algo.getCache();
        return map;
    }


}
