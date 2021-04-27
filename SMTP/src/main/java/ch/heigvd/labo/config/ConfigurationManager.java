package ch.heigvd.labo.config;

import ch.heigvd.labo.mail.Person;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Logger;

public class ConfigurationManager implements IConfigurationManager{
    private final static Logger LOG = Logger.getLogger(SMTPConfig.class.getName());
    private String serverAddress;
    private int port;
    private int nbGroups;
    private int nbPeoplePerGroup;

    private ArrayList<Person> victims;
    private ArrayList<Person> witnessCc;
    private ArrayList<String> messages;
    private static final String configurationProperties = "config/config.properties";


    public ConfigurationManager() throws IOException {
        loadPropertiesFile(configurationProperties);
    }

    public int getNbGroups(){
        return nbGroups;
    }

    public ArrayList<String> getMessages(){
        return messages;
    }

    public ArrayList<Person> getVictims(){
        return victims;
    }

    public ArrayList<Person> getWitnessCc(){
        return witnessCc;
    }

    public int getNbPerGroup(){
        return nbPeoplePerGroup;
    }

    private void loadPropertiesFile(String fileName) throws IOException{
        Properties properties;
        try {
            properties = new Properties();
            properties.load(new FileInputStream(configurationProperties));
            this.serverAddress = properties.getProperty("serverAddress");
            this.port = Integer.parseInt(properties.getProperty("port"));
            this.nbGroups = Integer.parseInt(properties.getProperty("nbGroups"));
            this.nbPeoplePerGroup = Integer.parseInt(properties.getProperty("nbPeoplePerGroup"));

            this.witnessCc = new ArrayList<Person>();
            String[] ccAddresses = properties.getProperty("witnessesToCc").split(", ");
            for(String adr : ccAddresses){
                this.witnessCc.add(new Person(adr));
            }

            //récupérer le fichier
            this.victims =  loadAddressFile(properties.getProperty("listVictims"));
            this.messages =  loadMessageFile(properties.getProperty("emailTemplates"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Person> loadAddressFile(String fileName) throws IOException {
        ArrayList<Person> listVictims = new ArrayList<>();
        BufferedReader reader;

        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
            String address = reader.readLine();
            while (address != null) {
                listVictims.add(new Person(address));
                LOG.info("add"+address);
                address = reader.readLine();
            }
            reader.close();

        }catch (IOException e){
            LOG.info(e.getMessage());
            e.printStackTrace();
        }
        LOG.info("The people list has been set.");
        return listVictims;
    }

    private ArrayList<String> loadMessageFile(String fileName) throws IOException{
        ArrayList<String> messages = new ArrayList<>();
        BufferedReader reader;
        try {
            //A modifier
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
            String line = reader.readLine();
            while (line != null) {
                StringBuilder body = new StringBuilder();
                while((line != null) && (!line.equals("=="))){
                    body.append(line);
                    body.append("\r\n");
                    line = reader.readLine();
                }
                messages.add(body.toString());
                line = reader.readLine();
            }
            reader.close();

        }catch (IOException e){
            LOG.info(e.getMessage());
            e.printStackTrace();
        }

        return messages;
    }
}


