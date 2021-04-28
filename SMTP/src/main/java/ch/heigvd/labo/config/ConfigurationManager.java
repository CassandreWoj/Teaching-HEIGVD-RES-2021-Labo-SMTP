package ch.heigvd.labo.config;

import ch.heigvd.labo.mail.Person;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Cette classe permet de récupérer les données de configuration pour générer les groupes de victimes, les listes de mails,
 * les pranks ainsi que les données pour l'envoi de mail à un serveur mail.
 */
public class ConfigurationManager{
    private final static Logger LOG = Logger.getLogger(ConfigurationManager.class.getName());
    private String serverAddress;
    private int port;
    private int nbGroups;
    private int nbPeoplePerGroup;

    private ArrayList<Person> victims;
    private ArrayList<Person> witnessCc;
    private ArrayList<Person> witnessBcc;
    private ArrayList<String> messages;
    private static final String configurationProperties = "config/config.properties";

    public ConfigurationManager() throws IOException {
        loadPropertiesFile(configurationProperties);
    }

    public String getServerAddress(){ return serverAddress; }
    public int getPort() { return port; }
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
    public ArrayList<Person> getWitnessBcc(){
        return witnessBcc;
    }
    public int getNbPerGroup(){
        return nbPeoplePerGroup;
    }

    /**
     *
     * @param fileName Un fichier de configuration comportant les informations nécessaires à la création de pranks
     * @throws IOException
     */
    private void loadPropertiesFile(String fileName) throws IOException{
        Properties properties;
        try {
            // Initialisation des données avec le fichier config.properties complété par l'utilisateur rigolo
            properties = new Properties();
            properties.load(new FileInputStream(configurationProperties));
            this.serverAddress = properties.getProperty("serverAddress");
            this.port = Integer.parseInt(properties.getProperty("port"));
            this.nbGroups = Integer.parseInt(properties.getProperty("nbGroups"));
            this.nbPeoplePerGroup = Integer.parseInt(properties.getProperty("nbPeoplePerGroup"));
            // Liste des personnes à ajouter en copie
            this.witnessCc = new ArrayList<Person>();
            if(properties.containsKey("witnessesToCc")  && properties.getProperty("witnessesToCc").length() > 0) {
                String[] ccAddresses = properties.getProperty("witnessesToCc").split(", ");
                for (String adr : ccAddresses) {
                    this.witnessCc.add(new Person(adr));
                }
            }
            // Liste des personnes à ajouter en copie cachée
            this.witnessBcc = new ArrayList<Person>();
            if(properties.containsKey("witnessesToBcc")  && properties.getProperty("witnessesToBcc").length() > 0) {
                String[] bccAddresses = properties.getProperty("witnessesToBcc").split(", ");
                for (String adr : bccAddresses) {
                    this.witnessBcc.add(new Person(adr));
                }
            }

            // Récupération du fichier
            this.victims =  loadAddressFile(properties.getProperty("listVictims"));
            this.messages =  loadMessageFile(properties.getProperty("emailTemplates"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param fileName Un fichier comportant une liste d'adresses mail des personnes à qui on veut envoyer des mails
     * @return listVictims Une liste de victimes créée à partir d'un fichier contenant des adresses mail
     * @throws IOException
     */
    private ArrayList<Person> loadAddressFile(String fileName) throws IOException {
        ArrayList<Person> listVictims = new ArrayList<>();
        BufferedReader reader;

        try {
            //Récupère les adresses emails des victimes et set la liste des victimes
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
            String address = reader.readLine();
            while (address != null) {
                listVictims.add(new Person(address));
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

    /**
     *
     * @param fileName Un fichier contenant des templates de mails à envoyer
     * @return messages Une liste de messages constituée des emails lus dans le fichier
     * @throws IOException
     */
    private ArrayList<String> loadMessageFile(String fileName) throws IOException{
        ArrayList<String> messages = new ArrayList<>();
        BufferedReader reader;

        try {
            // Récupère chaque contenu de message
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
            String line = reader.readLine();
            while (line != null) {
                StringBuilder body = new StringBuilder();
                // Les différents e-mails sont séparés avec des "=="
                while((line != null) && (!line.equals("=="))){
                    body.append(line + "\r\n");
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
        LOG.info("The messages list has been set.");
        return messages;
    }
}


