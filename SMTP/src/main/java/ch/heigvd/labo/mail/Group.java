package ch.heigvd.labo.mail;

import java.util.ArrayList;
import java.util.List;

public class Group { // liste de Person (min. 3)
    private final List<Person> groupMembers;

    public Group() {
        this.groupMembers = new ArrayList<>();
    }

    public void addMember(Person person) {
        groupMembers.add(person);
    }
    public List<Person> getGroupMembers() {
        return groupMembers;
    }



}
