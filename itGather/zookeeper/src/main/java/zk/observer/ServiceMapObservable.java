package zk.observer;

import java.util.Map;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by 54074 on 2017-10-25.
 */
public class ServiceMapObservable extends Observable {
    private Map eventInfo;
    public ServiceMapObservable() {
    }
    public ServiceMapObservable(Map eventInfo) {
        this.eventInfo=eventInfo;
    }

    @Override
    public synchronized void addObserver(Observer o) {
        super.addObserver(o);
    }

    @Override
    public synchronized void deleteObserver(Observer o) {
        super.deleteObserver(o);
    }

    @Override
    public void notifyObservers() {
        super.notifyObservers();
    }

    @Override
    public void notifyObservers(Object arg) {
        super.notifyObservers(arg);
    }

    @Override
    public synchronized void deleteObservers() {
        super.deleteObservers();
    }

    @Override
    protected synchronized void setChanged() {
        super.setChanged();
    }

    @Override
    protected synchronized void clearChanged() {
        super.clearChanged();
    }

    @Override
    public synchronized boolean hasChanged() {
        return super.hasChanged();
    }

    @Override
    public synchronized int countObservers() {
        return super.countObservers();
    }

    public void dataChange()
    {
        this.setChanged();
        this.notifyObservers(eventInfo);

    }


    public void setData(Map eventInfo)
    {
        this.eventInfo=eventInfo;
        dataChange();
    }
    public Map getEventInfo()
    {
        return this.eventInfo;
    }
}
