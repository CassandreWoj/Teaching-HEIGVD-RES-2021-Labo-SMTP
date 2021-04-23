package ch.heigvd.labo.smtp;

import ch.heigvd.labo.mail.Mail;
import ch.heigvd.labo.prank.Prank;

public class SMTPClient implements ISMTPClient{
    private String serverAddress;
    private int port;

    public SMTPClient(String serverAddress, int port){
        this.serverAddress = serverAddress;
        this.port = port;
    }

    public void sendMail(Prank prank){
        // connexion au serveur
        // envoi des données
        // récupération du OK sent
        // fermer la connexion

        System.out.println(prank.getSender().getAddress());
        System.out.println(prank.getMail().getContent());
        System.out.println(prank.getGroup().getGroupMembers().get(1).getAddress());

    }


}
