package ch.heigvd.labo.smtp;

import java.io.File;

public class SMTPConfig {
    public static final String[] serverAddress = {"localhost", "mail01.heig-vd.ch"};
    public static final int port = 2525;

    public static final int nbGroups = 1;
    public static final int nbPeoplePerGroup = 3;
    public static final String witnessesToCc = "cassandre.wojciechowski@heig-vd.ch";
    public static final File listVictims = new File("config/email_addresses.utf8");
    public static final File emailTemplates = new File("config/email_template.utf8");
}
