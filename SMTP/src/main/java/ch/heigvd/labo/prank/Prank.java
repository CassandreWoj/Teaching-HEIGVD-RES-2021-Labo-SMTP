package ch.heigvd.labo.prank;

import ch.heigvd.labo.mail.Group;
import ch.heigvd.labo.mail.Mail;
import ch.heigvd.labo.mail.Person;

public class Prank {
    // on prank un Group de Person avec un Mail
    // ajout du groupe
    // création de l'e-mail
    // appel de sendMail de la classe SMTPClient

    private Group group;
    private Mail mail;
    private Person sender;

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Mail getMail() {
        return mail;
    }

    public void setMail(Mail mail) {
        this.mail = mail;
    }

    public Person getSender() {
        return sender;
    }

    public void setSender(Person sender) {
        this.sender = sender;
    }

}
