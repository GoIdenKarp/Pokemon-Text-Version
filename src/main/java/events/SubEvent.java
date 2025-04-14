package events;

import java.util.List;

public abstract class SubEvent {

    private List<String> beforeMain;
    private List<String> afterMain;

    public SubEvent() {

    }

    public SubEvent(List<String> beforeMain, List<String> afterMain) {
        this.beforeMain = beforeMain;
        this.afterMain = afterMain;
    }


    public List<String> getBeforeMain() {
        return beforeMain;
    }

    public void setBeforeMain(List<String> beforeMain) {
        this.beforeMain = beforeMain;
    }

    public List<String> getAfterMain() {
        return afterMain;
    }

    public void setAfterMain(List<String> afterMain) {
        this.afterMain = afterMain;
    }
}
