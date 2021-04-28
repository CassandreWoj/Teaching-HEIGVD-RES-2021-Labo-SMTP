package ch.heigvd.labo.prank;

import ch.heigvd.labo.mail.Mail;
import ch.heigvd.labo.mail.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Prank {
    private final static Logger LOG = Logger.getLogger(PrankGenerator.class.getName());

    private String message;
    private Person victimSender;
    private final List<Person> victimsRcpt = new ArrayList<>();
    private final List<Person> witnessCcRcpt = new ArrayList<>();
    private final List<Person> witnessBccRcpt = new ArrayList<>();

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Person getVictimSender() { return victimSender; }
    public void setVictimSender(Person victimSender) { this.victimSender = victimSender; }

    public List<Person> getVictimsRcpt() { return victimsRcpt; }
    public void setVictimsRcpt(List<Person> victims){
        victimsRcpt.addAll(victims);
    }

    public List<Person> getWitnessCc() { return witnessCcRcpt; }
    public void setWitnessCc(List<Person> witness){
        witnessCcRcpt.addAll(witness);
    }

    public List<Person> getWitnessBcc() { return witnessBccRcpt; }
    public void setWitnessBcc(List<Person> witness){
        witnessBccRcpt.addAll(witness);
    }

    /**
     * Constructeur de mail
     * @return mail Un email initialisé avec toutes les valeurs nécessaires pour envoyer un email (from, to, cc, bcc, ...)
     */
    public Mail generateMail(){
        Mail mail = new Mail();

        //Separation du sujet et du contenu du mail
        String[] bodyMail = message.split("\r\n",2);
        String[] subject = bodyMail[0].split(": ",2);
        mail.setSubject(subject[1]);
        mail.setContent(bodyMail[1]);

        mail.setFrom(victimSender.getAddress()+","+victimSender.getFirstname()+","+victimSender.getLastname());

        // Récupère chaque adresse TO à qui envoyer un mail
        int index = 0;
        String[] to = new String[victimsRcpt.size()];
        for(Person victim : victimsRcpt){
            to[index] = victim.getAddress();
            index++;
        }
        mail.setTo(to);

        // Récupère chaque adresse CC à qui envoyer le mail en copie
        index = 0;
        String[] cc = new String[witnessCcRcpt.size()];
        for(Person victim : witnessCcRcpt){
            cc[index] = victim.getAddress();
            index++;
        }
        mail.setCc(cc);

        // Récupère chaque adresse BCC à qui envoyer le mail en copie cachée
        index = 0;
        String[] bcc = new String[witnessBccRcpt.size()];
        for(Person victim : witnessBccRcpt){
            bcc[index] = victim.getAddress();
            index++;
        }
        mail.setBcc(bcc);

        LOG.info("Prank generate mail done");

        return mail;
    }
}