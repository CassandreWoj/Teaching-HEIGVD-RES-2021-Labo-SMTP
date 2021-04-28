package ch.heigvd.labo.prank;

import ch.heigvd.labo.config.ConfigurationManager;
import ch.heigvd.labo.mail.Group;
import ch.heigvd.labo.mail.Person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Logger;

public class PrankGenerator {
    private final static Logger LOG = Logger.getLogger(PrankGenerator.class.getName());

    private ConfigurationManager configurationManager;

    public PrankGenerator(ConfigurationManager configurationManager){
        this.configurationManager = configurationManager;
    }

    /**
     * Génère les pranks
     * @return pranks Une liste de pranks différents avec des groupes de victimes et des mails à leur envoyer
     */
    public ArrayList<Prank> generatePranks() throws Exception {
        int NB_MIN = 3;
        ArrayList<Prank> pranks = new ArrayList<Prank>();
        ArrayList<String> messages = configurationManager.getMessages();
        Collections.shuffle(messages); //Mélange la liste des messages

        int nbGroups = configurationManager.getNbGroups();
        int nbPerGroup = configurationManager.getNbPerGroup();
        int nbVictims = configurationManager.getVictims().size();

        // On vérifie que le nombre de personnes par groupe soit cohérent avec le nombre de groupes demandé et la taille
        // de la liste des victimes
        if( nbGroups * NB_MIN > nbVictims) {
            LOG.warning("A group is composed of at least 3 people ! Please lower the number of groups you want to create.");
            throw new Exception("Number of groups too high with not enough victims.");
        }

        // On vérifie qu'il y ait au moins 3 victimes par groupe (1 envoyeur et 2 récepteurs)
        if(nbVictims / nbGroups < NB_MIN || nbPerGroup < NB_MIN){
            nbPerGroup = nbVictims / NB_MIN;
        }

        ArrayList<Group> groups = createGroups(configurationManager.getVictims(), nbGroups, nbPerGroup);
        // Pour chaque groupe, on crée un prank à qui on donne :
        // - un contenu de mail
        // - des victimes (/!\ index 0 = sender)
        int index = 0;
        for (Group group : groups){
            Prank prank = new Prank();

            ArrayList<Person> victims = group.getGroupMembers();
            Collections.shuffle(victims); //Mélange la liste des victimes

            Person sender = victims.remove(0);
            prank.setVictimSender(sender);
            prank.setVictimsRcpt(victims);
            prank.setWitnessCc(configurationManager.getWitnessCc());
            prank.setWitnessBcc(configurationManager.getWitnessBcc());

            String content = messages.get(index);
            index = (index+1) % messages.size();
            prank.setMessage(content);

            pranks.add(prank);
        }
        return pranks;
    }

    /**
     * Génère les groupes de victimes
     * @param victims La liste complète de toutes les victimes possibles
     * @param nbGroups Le nombre de groupes demandé par l'utilisateur
     * @param nbPersonPerGroup Le nombre de personnes composant les groupes
     * @return groups La liste des groupes générés (/!\ La première personne dans le groupe est l'envoyeur du prank)
     */
    public ArrayList<Group> createGroups(ArrayList<Person> victims, int nbGroups, int nbPersonPerGroup) {
        ArrayList<Group> groups = new ArrayList<Group>();
        ArrayList<Person> copyVictim = new ArrayList<>(victims);
        Collections.shuffle(copyVictim); //Mélange la liste des victimes

        //Récupère toutes les victimes pour chaque groupe
        int nbPerGroup = 0;
        for(int i = 0; i < nbGroups; ++i) {
            nbPerGroup = nbPersonPerGroup;
            Group g = new Group();

            while(nbPerGroup > 0 && copyVictim.size() > 0){
                g.addMember(copyVictim.remove(0));
                nbPerGroup--;
            }
            groups.add(g);
        }
        return groups;
    }
}
