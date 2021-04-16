package ch.heigvd.labo.mail;

public class Person {
    private String firstName;
    private String lastName;
    private final String address;

    public Person(String firstName, String lastName, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }
}
