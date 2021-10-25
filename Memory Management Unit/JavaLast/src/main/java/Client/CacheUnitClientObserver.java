package Client;

import View.CacheUnitView;

public class CacheUnitClientObserver
        extends java.lang.Object
        implements java.beans.PropertyChangeListener {


    public CacheUnitClientObserver(){

    }

    /**
     *
     * @param evt get the request from the GUI,
     *            and send the request to function send
     */
    @Override
    public void	propertyChange(java.beans.PropertyChangeEvent evt) {
        CacheUnitClient client= new CacheUnitClient();
        String response = client.send((String) evt.getNewValue());
        CacheUnitView cacheUnitView = new CacheUnitView();
        cacheUnitView = (CacheUnitView) evt.getSource();
        cacheUnitView.updateUIData(response);

    }

}

