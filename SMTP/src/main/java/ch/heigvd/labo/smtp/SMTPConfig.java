package ch.heigvd.labo.smtp;

import ch.heigvd.labo.SMTP;
import ch.heigvd.labo.mail.Mail;
import ch.heigvd.labo.mail.Person;

import java.io.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Logger;

public class SMTPConfig {
    private final static Logger LOGGER = Logger.getLogger(SMTPConfig.class.getName());
    private String serverAddress;
    private int port;
    private int nbGroups;
    private int nbPeoplePerGroup;
    private static final String configurationProperties = "config/config.properties";
    private String listVictims;
    private String emailTemplates;
    private String[] witnessesToCc;

    private ArrayList<Person> peopleList;
    private ArrayList<String> emailContent;

    public SMTPConfig() {
        setConfigurationProperties();
        peopleList = setPeopleList();
        emailContent = setEmailContent();
    }

    public String getServerAddress() { return serverAddress; }
    public int getPort() { return port; }
    public int getNbGroups() { return nbGroups; }
    public int getNbPeoplePerGroup() { return nbPeoplePerGroup; }
    public String[] getWitnessesToCc() { return witnessesToCc; }
    public ArrayList<Person> getPeopleList() { return peopleList; }
    public ArrayList<String> getEmailContent() { return emailContent; }

    public ArrayList<Person> setPeopleList() {
        ArrayList<Person> people = new ArrayList<Person>();
        BufferedReader reader;

        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(listVictims), "UTF-8"));
            String address = reader.readLine();
            // ajout des personnes créées avec leur adresse email dans une liste
            while(address != null) {
                people.add(new Person(address));
                address = reader.readLine();
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        LOGGER.info("The people list has been set.");
        LOGGER.info(String.valueOf(people.size()));
        return people;
    }

    public ArrayList<String> setEmailContent(){
        // sélection des emails pour en former une liste
        String mailSeparator = "==";
        String subject;
        ArrayList<String> mails = new ArrayList<String>();
        StringBuilder text = new StringBuilder();

        BufferedReader reader;

        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(emailTemplates), "UTF-8"));

            // lecture du fichier de mails
            while((subject = reader.readLine()) != null) {
                text.append(subject + SMTPProtocol.endLine);
                String line = reader.readLine();

                // lecture d'un mail
                while (line != null && !line.equals(mailSeparator)) {
                    text.append(line);
                    text.append(SMTPProtocol.endLine);
                    line = reader.readLine();
                }

                // création du mail avec le sujet et le contenu
                // ajout du mail dans la liste de mails
                mails.add(text.toString());
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOGGER.info("The mail list has been filled.");
        return mails;
    }

    public void setConfigurationProperties(){
        Properties prop = null;
        try {
            prop = new Properties();
            prop.load(new FileInputStream(configurationProperties));
        } catch (Exception e) {
            e.printStackTrace();
        }
        serverAddress = prop.getProperty("serverAddress");
        port = Integer.parseInt(prop.getProperty("port"));
        nbGroups = Integer.parseInt(prop.getProperty("nbGroups"));
        nbPeoplePerGroup = Integer.parseInt(prop.getProperty("nbPeoplePerGroup"));
        listVictims = prop.getProperty("listVictims");
        emailTemplates = prop.getProperty("emailTemplates");
        witnessesToCc = prop.getProperty("witnessesToCc").split(", ");

        // TO DO : vérification nb de personnes par groupe -> min. 3

        nbPeoplePerGroup = Math.min(listVictims.length(), nbPeoplePerGroup);

        LOGGER.info("The properties have been set.");
    }

}
