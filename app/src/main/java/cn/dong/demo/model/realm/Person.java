package cn.dong.demo.model.realm;

import io.realm.RealmObject;

/**
 * @author dong on 16/1/7.
 */
public class Person extends RealmObject {
    private String name;
    private Dog dog;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Dog getDog() {
        return dog;
    }

    public void setDog(Dog dog) {
        this.dog = dog;
    }
}
