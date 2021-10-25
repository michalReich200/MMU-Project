package service;


import dm.DataModel;

public class CacheUnitController<T>
        extends java.lang.Object{
    private CacheUnitService cacheService;

    /**
     * constractor
     */
    public CacheUnitController(){

        cacheService = new CacheUnitService<>();
    }

    /**
     *
     * @param dataModels which we have to update in ram
     * @return true if it success false if not
     */
    public boolean update(DataModel<T>[] dataModels) {
        return cacheService.update(dataModels);
    }

    /**
     *
     * @param dataModels which we have to delete from ram
     * @return true if it success false if not
     */
    public boolean delete(DataModel<T>[] dataModels){
        return cacheService.delete(dataModels);

    }

    /**
     *
     * @param dataModels which we have to get from ram
     * @return the data models from the ram
     */
    public DataModel<T>[] get(DataModel<T>[] dataModels){
        return cacheService.get(dataModels);

    }

    /**
     *
     * @return string of all statistics
     */
    public String getStatistic() {
        String Algorithm = cacheService.getAlgoType();
        int Capacity = cacheService.getCapacity();
        int numOfRequests = cacheService.getNumOfRequests();
        int numOfGETRequests = cacheService.getNumOfGETRequests();
        int numOfUPDATERequests = cacheService.getNumOfUPDATERequests();
        int numOfDELETERequests = cacheService.getNumOfDELETERequests();
        int numOfDataModels = cacheService.getNumOfDataModel();
        int numOfSwaps = cacheService.getNumOfSwaps();
        String statistics="Algorithm:" +Algorithm +" \n"
                +"Capacity:" + Capacity +" \n"+
                "Total number of requests:"+numOfRequests+" \n" +
                "Total number of GET requests:"+numOfGETRequests+" \n" +" \n"+
                "Total number of UPDATE requests:"+ numOfUPDATERequests+ " \n"+
                "Total number of DELETE requests:"+numOfDELETERequests + "\n" +
                "Total number of swaps:" + numOfSwaps;
        return statistics;
    }

    /**
     * call to function that update the hardDisk
     */
    public void shutdown(){
        cacheService.updateCache();
    }
}
