package ch.heigvd.labo.mail;

import java.util.ArrayList;

public class Group {
    private final ArrayList<Person> groupMembers  = new ArrayList<>();
    public void addMember(Person person) {
        groupMembers.add(person);
    }
    public ArrayList<Person> getGroupMembers() {
        return groupMembers;
    }
}
