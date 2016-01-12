package cn.dong.demo.model.realm;

import io.realm.RealmObject;

/**
 * @author dong on 16/1/7.
 */
public class Dog extends RealmObject {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
