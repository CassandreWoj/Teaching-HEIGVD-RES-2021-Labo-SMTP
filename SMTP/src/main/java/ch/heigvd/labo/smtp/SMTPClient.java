package ch.heigvd.labo.smtp;

import ch.heigvd.labo.SMTP;
import ch.heigvd.labo.config.SMTPProtocol;
import ch.heigvd.labo.mail.Mail;

import java.io.*;
import java.net.Socket;
import java.util.Base64;
import java.util.logging.Logger;

public class SMTPClient implements ISMTPClient {
    private final static Logger LOG = Logger.getLogger(SMTPClient.class.getName());

    private String serverAddress;
    private int port;
    private Socket socketClient;
    private BufferedReader reader;
    private PrintWriter writer;

    public SMTPClient(String serverAddress, int port) {
        this.serverAddress = serverAddress;
        this.port = port;
    }

    /**
     *
     * @param mail Le mail à envoyer en tant que prank
     * @throws IOException
     */
    @Override
    public void sendMail(Mail mail) throws IOException {
        LOG.info("Prank à envoyer via SMTP");
        // Etablissement d'une connexion avec le serveur SMTP précisé dans le fichier de configuration
        this.socketClient = new Socket(serverAddress, port);
        this.writer = new PrintWriter(new OutputStreamWriter(socketClient.getOutputStream(), "UTF-8"), true);
        this.reader = new BufferedReader(new InputStreamReader(socketClient.getInputStream(), "UTF-8"));

        String reponseServer = reader.readLine();
        LOG.info(reponseServer);

        //Envoi du message EHLO
        writer.print(SMTPProtocol.EHLO + "Prank" + SMTPProtocol.END_LINE);
        writer.flush();
        reponseServer = reader.readLine();
        LOG.info(reponseServer);

        // Vérification que le EHLO est reçu correctement
        if (!reponseServer.startsWith("250"))
            throw new IOException("SMTP error :" + reponseServer);

        while (!reponseServer.startsWith(SMTPProtocol.MSG_END)) {
            // Lit et affiche le stream tant que le message "250 " n'a pas été lu
            reponseServer = reader.readLine();
            LOG.info(reponseServer);
        }
        String[] sender = mail.getFrom().split(",");
        //MAIL FROM: <sender>
        writer.print(SMTPProtocol.MAIL_FROM + sender[0] + SMTPProtocol.END_LINE);
        writer.flush();
        reponseServer = reader.readLine();
        LOG.info(reponseServer);

        // RCPT TO: <victimes>
        for (String victim : mail.getTo()) {
            writer.print(SMTPProtocol.RCPT_TO + victim + SMTPProtocol.END_LINE);
            writer.flush();
            reponseServer = reader.readLine();
            LOG.info(reponseServer);
        }

        // RCPT TO: <victimes> en copie
        for (String victim : mail.getCc()) {
            writer.print(SMTPProtocol.RCPT_TO + victim + SMTPProtocol.END_LINE);
            writer.flush();
            reponseServer = reader.readLine();
            LOG.info(reponseServer);
        }

        // RCPT TO: <victimes> en copie cachée
        for(String victim : mail.getBcc()){
            writer.print(SMTPProtocol.RCPT_TO+victim+SMTPProtocol.END_LINE);
            writer.flush();
            reponseServer = reader.readLine();
            LOG.info(reponseServer);
        }

        // DATA
        writer.print(SMTPProtocol.DATA + SMTPProtocol.END_LINE);
        writer.flush();
        reponseServer = reader.readLine();
        LOG.info(reponseServer);

        // Ajout des adresses From:, To:, Cc: et Bcc:
        // Indication au serveur de l'encodage du mail
        writer.print("Content-Type: text/play; charset=\"utf-8\"\r\n");
        writer.print(SMTPProtocol.FROM + sender[0] + SMTPProtocol.END_LINE);

        writer.print(SMTPProtocol.TO + mail.getTo()[0]);
        for (int i = 1; i < mail.getTo().length; i++) {
            writer.print("," + mail.getTo()[i]);
        }
        writer.print(SMTPProtocol.END_LINE);

        if(mail.getCc().length > 0){
            writer.print(SMTPProtocol.CC + mail.getCc()[0]);
            for (int i = 1; i < mail.getCc().length; i++) {
                writer.print("," + mail.getCc()[i]);
            }
            writer.print(SMTPProtocol.END_LINE);
        }
        writer.flush();

        // Ajoute la date
        writer.print(SMTPProtocol.DATE +mail.getDate());
        writer.print(SMTPProtocol.END_LINE);
        LOG.info("Date: " + mail.getDate());


        // Encodage du sujet du mail avec base64 pour conserver les accents et les espaces
        String subjectB64 = Base64.getEncoder().encodeToString(mail.getSubject().getBytes());
        String subjectToSend = "=?utf-8?B?" + subjectB64 + "?=";
        // Envoi du sujet Suject: et du contenu du mail
        writer.print(SMTPProtocol.SUBJECT + subjectToSend);
        writer.print(SMTPProtocol.END_LINE);
        LOG.info("Subject: " + mail.getSubject());
        writer.print(mail.getContent());
        LOG.info("Content: " + mail.getContent());

        // Signature du mail avec le nom et le prénom de l'envoyeur
        String signatureSender = sender[1] + " " + sender[2];
        writer.print(signatureSender);
        LOG.info("Signature sender: "+signatureSender);

        writer.print(SMTPProtocol.END_DATA);
        writer.flush();

        reponseServer = reader.readLine();
        LOG.info(reponseServer);

        // Quand tout est envoyé, on ferme la connexion
        writer.print(SMTPProtocol.MSG_QUIT + SMTPProtocol.END_LINE);
        reader.close();
        writer.close();
        socketClient.close();
    }
}