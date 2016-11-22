package cn.dong.demo.model.realm;

import io.realm.RealmObject;

/**
 * @author dong on 2016/11/21.
 */
public class Cat extends RealmObject {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
