package ch.heigvd.labo.prank;

import ch.heigvd.labo.SMTP;
import ch.heigvd.labo.mail.Group;
import ch.heigvd.labo.mail.Mail;
import ch.heigvd.labo.mail.Person;
import ch.heigvd.labo.smtp.SMTPConfig;
import ch.heigvd.labo.smtp.SMTPProtocol;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.logging.Logger;

public class PrankGenerator {
    Random random = new Random();
    private final static Logger LOGGER = Logger.getLogger(PrankGenerator.class.getName());
    private int nbGroups;
    private int nbPeoplePerGroup;
    private ArrayList<Person> people;
    private ArrayList<String> emailContent;
    private String[] witnesses;

    public PrankGenerator(int nbGroups, int nbPeoplePerGroup, ArrayList<Person> people, ArrayList<String> emailContent, String[] witnesses) {
        this.nbGroups = nbGroups;
        this.nbPeoplePerGroup = nbPeoplePerGroup;
        this.people = people;
        this.emailContent = emailContent;
        this.witnesses = witnesses;
    }

    public ArrayList<Prank> getPranks(){
        return createPranks();
    }

    // création d'une liste de groupes
    public ArrayList<Group> createGroups() {
        // lecture du fichier d'adresses mail
        ArrayList<Group> groups = new ArrayList<Group>();
        ArrayList<Person> peopleCopy = people;
        Person member;
        // ajout des membres dans un groupe
        for(int i = 0; i < nbGroups; ++i) {
            Group g = new Group();

            for(int j = 0; j < nbPeoplePerGroup; ++j) { // TODO : modifier le nb max de personnes dans le groupe
                if(peopleCopy.isEmpty()) {
                    LOGGER.info("Breaking in group creation");
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
    }

    public ArrayList<Prank> createPranks(){
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
            Mail mail = new Mail(subject, content);
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
    }

}
