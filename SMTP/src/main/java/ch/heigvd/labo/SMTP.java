package ch.heigvd.labo;


import ch.heigvd.labo.prank.Prank;
import ch.heigvd.labo.prank.PrankGenerator;
import ch.heigvd.labo.smtp.SMTPClient;
import ch.heigvd.labo.smtp.SMTPConfig;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SMTP
{
    private final static Logger LOGGER = Logger.getLogger(SMTP.class.getName());
    public static void main( String[] args )
    {
        // appel de la classe Prank pour envoyer les données

        SMTPConfig conf = new SMTPConfig();
        PrankGenerator generator = new PrankGenerator(conf.getNbGroups(), conf.getNbPeoplePerGroup(), conf.getPeopleList(), conf.getEmailContent(), conf.getWitnessesToCc());
        SMTPClient client = new SMTPClient(conf.getServerAddress(), conf.getPort());

        ArrayList<Prank> pranks = generator.getPranks();
        client.sendMail(pranks.get(0));
        /*
        for(Prank prank : pranks) {
            client.sendMail(prank);
            LOGGER.info("A prank has been sent.");
        }*/
    }
}
