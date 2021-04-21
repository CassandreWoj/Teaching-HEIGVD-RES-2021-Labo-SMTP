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
import java.util.Random;

public class PrankGenerator {
    Random random = new Random();

    public PrankGenerator() {

    }

    // création d'un groupe
    public ArrayList<Group> createGroups() {

        // lecture du fichier d'adresses mail
        ArrayList<Person> people = null;
        BufferedReader reader;

        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(SMTPConfig.listVictims), "UTF-8"));
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

        ArrayList<Group> groups = null;

        // ajout des membres dans un groupe
        for(int i = 0; i < SMTPConfig.nbGroups; ++i) {
            Group g = null;
            // sélection du premier membre du groupe (le sender) aléatoire
            g.addMember(people.get(random.nextInt() % people.size()));

            for(int j = 0; j < SMTPConfig.nbPeoplePerGroup; ++j) { // TODO : modifier le nb max de personnes dans le groupe
                Person member = people.get(random.nextInt() % people.size());
                if (!g.getGroupMembers().contains(member)) {
                    g.addMember(member);
                }
            }
            // ajouter le groupe dans la liste des groupes
            groups.add(g);
        }

        return groups;

    }

    private ArrayList<Mail> createMailList(){
        // sélection des emails pour en former une liste
        String mailSeparator = "==";
        String subject;
        ArrayList<Mail> mails = null;
        StringBuilder text = new StringBuilder();

        BufferedReader reader;

        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(SMTPConfig.emailTemplates), "UTF-8"));

            // lecture du fichier de mails
            while((subject = reader.readLine()) != null) {
                String line = reader.readLine();

                // lecture d'un mail
                while (line != null && !line.equals(mailSeparator)) {
                    text.append(line);
                    text.append(SMTPProtocol.endLine);
                    line = reader.readLine();
                }

                // création du mail avec le sujet et le contenu
                // ajout du mail dans la liste de mails
                mails.add(new Mail(subject, line));
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return mails;
    }

    public ArrayList<Prank> createPranks(){
        return null;
    }

}
