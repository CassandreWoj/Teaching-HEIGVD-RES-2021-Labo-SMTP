package ch.heigvd.labo.mail;

public class Person {
    private final String address;

    public Person(String address) {
        this.address = address;
        //si on fait firstname + lastname => découpage addresse uniquement pour addresse
        //qui sont en mode prenom.nom@....
    }

    public String getAddress() { return address; }

}
