package Driver;

import Client.CacheUnitClientObserver;
import View.CacheUnitView;

import java.awt.*;

public class CacheUnitClientDriver {
    public static void main(String[] args) throws FontFormatException {
        CacheUnitClientObserver cacheUnitClientObserver =
                new CacheUnitClientObserver();
        CacheUnitView view = new CacheUnitView();
        view.addPropertyChangeListener(cacheUnitClientObserver);
        view.start();

    }
}
