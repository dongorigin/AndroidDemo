package cn.dong.demo.model.event;

/**
 * @author dong on 15/9/11.
 */
public class Test1Event extends BaseEvent {
    public String name;

    public Test1Event(String name) {
        this.name = name;
    }
}
