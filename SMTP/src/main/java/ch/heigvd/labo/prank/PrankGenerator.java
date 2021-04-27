package ch.heigvd.labo.prank;

import ch.heigvd.labo.config.IConfigurationManager;
import ch.heigvd.labo.mail.Group;
import ch.heigvd.labo.mail.Person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.logging.Logger;

public class PrankGenerator {
    Random random = new Random();
    private final static Logger LOG = Logger.getLogger(PrankGenerator.class.getName());


    //new
    private IConfigurationManager configurationManager;

    public PrankGenerator(IConfigurationManager configurationManager){
        this.configurationManager = configurationManager;
    }

    /**
     * Génère les pranks
     * @return
     */
    public ArrayList<Prank> generatePranks(){
        ArrayList<Prank> pranks = new ArrayList<Prank>();
        ArrayList<String> messages = configurationManager.getMessages();
        int nbGroups = configurationManager.getNbGroups();
        int nbPerGroup = configurationManager.getNbPerGroup();
        int nbVictims = configurationManager.getVictims().size();

        //Verify au minimum 3 victims per group
        if(nbVictims / nbGroups < 3){
            nbPerGroup = nbVictims/3;
        }
        LOG.info("NBGROUUUUPPPPY "+nbGroups);
        ArrayList<Group> groups = createGroups(configurationManager.getVictims(), nbGroups, nbPerGroup);
        //Pour chaque groupe, on crée un prank à qui on set :
        // - un contenu de mail
        // - des victimes (/!\ index 0 = sender)
        int index =0;
        for (Group group : groups){
            Prank prank = new Prank();
            LOG.info("AAAAAAAAAAAAAAAAAAAAAAA" + group.getGroupMembers().size());

            ArrayList<Person> victims = group.getGroupMembers();
            LOG.info("AAAAAAAAAAAAAAAAAAAAAAA" + victims.size());
            Collections.shuffle(victims); //Mélange la liste des victimes

            Person sender = victims.remove(0);
            prank.setVictimSender(sender);
            prank.setVictimsRcpt(victims);
            prank.setWitness(configurationManager.getWitnessCc());

            String content = messages.get(index);
            index = (index+1) % messages.size();
            prank.setMessage(content);

            pranks.add(prank);
        }
        return pranks;
    }


    /**
     * Génère les groupes de victimes
     * @param victims
     * @param nbGroups
     * @param nbPersonPerGroup
     * @return
     */
    public ArrayList<Group> createGroups(ArrayList<Person> victims, int nbGroups, int nbPersonPerGroup) {
        ArrayList<Group> groups = new ArrayList<Group>();
        ArrayList<Person> copyVictim = new ArrayList<>(victims);
        Collections.shuffle(copyVictim); //Mélange la liste des victimes

        LOG.info("NBGROU"+nbGroups);
        //Récupère toutes les victimes pour chaque groupe
        int nbPerGroup = nbPersonPerGroup;
        for(int i = 0; i < nbGroups; ++i) {
            Group g = new Group();

            while(nbPerGroup > 0 && copyVictim.size() > 0){
                Person vic = copyVictim.remove(0); // à supp
                g.addMember(vic);//copyVictim.remove(0));
                nbPerGroup--;
                LOG.info("AAAA victim add"+vic.getAddress());
            }
            groups.add(g);
        }
        return groups;
    }

    /*public PrankGenerator(int nbGroups, int nbPeoplePerGroup, ArrayList<Person> people, ArrayList<String> emailContent, String[] witnesses) {
        this.nbGroups = nbGroups;
        this.nbPeoplePerGroup = nbPeoplePerGroup;
        this.people = people;
        this.emailContent = emailContent;
        this.witnesses = witnesses;
    }*/

    /*private int nbGroups;
    private int nbPeoplePerGroup;
    private ArrayList<Person> people;
    private ArrayList<String> emailContent;
    private String[] witnesses;*/

    // création d'une liste de groupes
    /*public ArrayList<Group> createGroups() {
        // lecture du fichier d'adresses mail
        ArrayList<Group> groups = new ArrayList<Group>();
        ArrayList<Person> peopleCopy = people;
        Person member;
        // ajout des membres dans un groupe
        for(int i = 0; i < nbGroups; ++i) {
            Group g = new Group();

            for(int j = 0; j < nbPeoplePerGroup; ++j) { // TODO : modifier le nb max de personnes dans le groupe
                if(peopleCopy.isEmpty()) {
                    LOG.info("Breaking in group creation");
                    break;
                }
                member = peopleCopy.get(random.nextInt(peopleCopy.size()));
                if (!g.getGroupMembers().contains(member)) {
                    g.addMember(member);
                    peopleCopy.remove(member);
                }
            }
            // ajouter le groupe dans la liste des groupes
            groups.add(g);
        }

        return groups;
    }*/

    /*public ArrayList<Prank> createPranks(){
        ArrayList<Group> groups = createGroups();
        Collections.shuffle(groups);
        Collections.shuffle(emailContent);

        ArrayList<Prank> pranks = new ArrayList<Prank>();
        String subject;
        String content;
        Group group;
        Person sender;
        String[] parts;
        for(int i = 0; i < groups.size(); ++i){
            group = groups.get(i);
            sender = group.getGroupMembers().get(0);
            // récupérer le sujet et le contenu séparément de emailContent
            parts = emailContent.get(i % emailContent.size()).split("\r\n", 2);
            subject = parts[0];
            content = parts[1];

            // créer un email
            Mail mail = new Mail();//(subject, content);
            // mettre champs FROM avec le sender
            mail.setFrom(sender.getAddress());
            // mettre champs TO avec les autres membres du groupe
            String[] to = new String[group.getGroupMembers().size()-1];
            for(int j = 1; j < group.getGroupMembers().size(); ++j){
                to[j-1] = group.getGroupMembers().get(j).getAddress();
            }
            mail.setTo(to);
            // ajouter les copie et copie cachée
            mail.setCc(witnesses);


            pranks.add(new Prank(group, mail, sender));
        }
        return pranks;
    }*/

}
