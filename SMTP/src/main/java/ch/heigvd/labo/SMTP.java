package ch.heigvd.labo;

import ch.heigvd.labo.config.ConfigurationManager;
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
        ConfigurationManager conf = new ConfigurationManager();
        PrankGenerator generator = new PrankGenerator(conf);
        SMTPClient client = new SMTPClient(conf.getServerAddress(), conf.getPort());

        ArrayList<Prank> pranks;
        try {
            pranks = generator.generatePranks();
            for(Prank prank : pranks) {
                client.sendMail(prank.generateMail());
                LOGGER.info("A prank has been sent.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
