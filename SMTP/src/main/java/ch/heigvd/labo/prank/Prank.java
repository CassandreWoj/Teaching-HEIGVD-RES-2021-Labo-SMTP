package ch.heigvd.labo.prank;

import ch.heigvd.labo.mail.Group;
import ch.heigvd.labo.mail.Mail;
import ch.heigvd.labo.mail.Person;
import ch.heigvd.labo.prank.PrankGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Prank {
    private final static Logger LOG = Logger.getLogger(PrankGenerator.class.getName());

    private String message;
    private Person victimSender;
    private final List<Person> victimsRcpt = new ArrayList<>();
    private final List<Person> witnessRcpt = new ArrayList<>();

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Person getVictimSender() { return victimSender; }
    public void setVictimSender(Person victimSender) { this.victimSender = victimSender; }

    public List<Person> getVictimsRcpt() { return victimsRcpt; }
    public void setVictimsRcpt(List<Person> victims){
        victimsRcpt.addAll(victims);
    }
    public List<Person> getWitness() { return witnessRcpt; }
    public void setWitness(List<Person> witness){
        victimsRcpt.addAll(witnessRcpt);
    }

    public Mail generateMail(){
        Mail mail = new Mail();

        mail.setTo(victimsRcpt.stream().toArray(String[]::new));
        LOG.info("To size:"+mail.getTo().length);
        mail.setCc(witnessRcpt.toArray(new String[]{}));
        LOG.info("Cc size:"+mail.getCc().length);
        mail.setFrom(victimSender.getAddress());
        LOG.info("SenderV:"+victimSender.getAddress());

        return mail;
    }



    //Ce quon a fait avant

    /*private Group group;
    private Mail mail;
    // a enlever ?
    //private Person sender;

    public Prank(){}

    public Prank(Group group, Mail mail, Person sender) {
        this.group = group;
        this.mail = mail;
        //this.sender = sender;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Mail getMail() {
        return mail;
    }*/
}