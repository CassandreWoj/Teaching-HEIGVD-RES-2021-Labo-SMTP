package ch.heigvd.labo;


import ch.heigvd.labo.config.ConfigurationManager;
import ch.heigvd.labo.config.IConfigurationManager;
import ch.heigvd.labo.prank.Prank;
import ch.heigvd.labo.prank.PrankGenerator;
import ch.heigvd.labo.smtp.SMTPClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

public class SMTP
{
    private final static Logger LOGGER = Logger.getLogger(SMTP.class.getName());
    public static void main( String[] args ) throws IOException {
        // appel de la classe Prank pour envoyer les données

        //SMTPConfig conf = new SMTPConfig();
        IConfigurationManager conf = new ConfigurationManager();
        PrankGenerator generator = new PrankGenerator(conf);//conf.getNbGroups(), conf.getNbPeoplePerGroup(), conf.getPeopleList(), conf.getEmailContent(), conf.getWitnessesToCc());
        //PrankGenerator generator = new PrankGenerator(conf);//conf.getNbGroups(), conf.getNbPeoplePerGroup(), conf.getPeopleList(), conf.getEmailContent(), conf.getWitnessesToCc());
        SMTPClient client = new SMTPClient("localhost",2525);//conf.getServerAddress(), conf.getPort());

        ArrayList<Prank> pranks = generator.generatePranks();
        for(Prank prank : pranks) {
            //client.sendMail();
            LOGGER.info("A prank has been sent.");
       }
    }
}
