package ch.heigvd.labo.mail;

public class Person {
    private final String address;
    private final String firstname;
    private final String lastname;

    public Person(String address) {
        this.address = address;

        //Récupère le Prénom et le Nom selon l'adresse mail
        //Paterne : prenom.nom@gmail.com
        String name = address.substring(0, address.indexOf("."));
        firstname = name.substring(0, 1).toUpperCase() + name.substring(1);

        name = address.substring(address.indexOf(".")+1,address.indexOf("@"));
        lastname = name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    public String getAddress() { return address; }
    public String getFirstname() { return firstname; }
    public String getLastname() { return lastname; }

}
