package View;

import java.awt.*;
import java.beans.PropertyChangeSupport;

public class CacheUnitView {
    PropertyChangeSupport support;
    GUI ui;

    /**
     * initialize the ui,and observe to all listener on a change
     */
    public CacheUnitView() {
        support = new PropertyChangeSupport(this);
        ui = new GUI(support);
    }

    /**
     * calls functions that crate the ui.
     * @throws FontFormatException if not succeeded.
     */
    public void start() throws FontFormatException {

        ui.prepareGUI();
        ui.showEventDemo();
    }

    public void addPropertyChangeListener(java.beans.PropertyChangeListener pcl) {
        this.support.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(java.beans.PropertyChangeListener pcl) {
        this.support.removePropertyChangeListener(pcl);
    }

    public <T> void updateUIData(T t) {
        ui.getStatisticsFrame().setText((String) t);
    }

}
