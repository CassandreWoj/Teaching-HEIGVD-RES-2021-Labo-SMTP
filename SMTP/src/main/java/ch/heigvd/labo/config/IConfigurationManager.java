package ch.heigvd.labo.config;

import ch.heigvd.labo.mail.Person;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface IConfigurationManager {
    public int getNbGroups();

    public ArrayList<String> getMessages();

    public ArrayList<Person> getVictims();

    public ArrayList<Person> getWitnessCc();

    public int getNbPerGroup();
}
