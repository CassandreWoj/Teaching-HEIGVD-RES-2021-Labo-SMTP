package ch.heigvd.labo.smtp;

import ch.heigvd.labo.config.SMTPProtocol;
import ch.heigvd.labo.mail.Mail;

import java.io.*;
import java.net.Socket;
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

    @Override
    public void sendMail(Mail mail) throws IOException {
        LOG.info("Prank envoyé via SMTP");
        this.socketClient = new Socket(serverAddress, port);
        this.writer = new PrintWriter(new OutputStreamWriter(socketClient.getOutputStream(), "UTF-8"), true);
        this.reader = new BufferedReader(new InputStreamReader(socketClient.getInputStream(), "UTF-8"));

        String reponseServer = reader.readLine();
        LOG.info(reponseServer);

        //EHLO message
        writer.print(SMTPProtocol.EHLO + "Prank" + SMTPProtocol.END_LINE);
        writer.flush();
        reponseServer = reader.readLine();
        LOG.info(reponseServer);
        //Vérifie que le EHLO est reçu correctement
        if (!reponseServer.startsWith("250"))
            throw new IOException("SMTP error :" + reponseServer);

        while (!reponseServer.startsWith(SMTPProtocol.MSG_END)) {
            //Lit le stream tant que le message "250 " n'a pas été lu
            reponseServer = reader.readLine();
            LOG.info(reponseServer);
        }

        //MAIL FROM: mail
        writer.print(SMTPProtocol.MAIL_FROM + mail.getFrom() + SMTPProtocol.END_LINE);
        writer.flush();
        reponseServer = reader.readLine();
        LOG.info(reponseServer);

        //To : mail
        for (String victim : mail.getTo()) {
            writer.print(SMTPProtocol.RCPT_TO + victim + SMTPProtocol.END_LINE);
            writer.flush();
            reponseServer = reader.readLine();
            LOG.info(reponseServer);
        }

        //Cc : mail
        for (String victim : mail.getCc()) {
            writer.print(SMTPProtocol.RCPT_TO + victim + SMTPProtocol.END_LINE);
            writer.flush();
            reponseServer = reader.readLine();
            LOG.info(reponseServer);
        }

        //Bcc : mail
        /*for(String victim : mail.getBcc()){
            writer.print(SMTPProtocol.RCPT_TO+victim+SMTPProtocol.END_LINE);
            writer.flush();
            reponseServer = reader.readLine();
            LOG.info(reponseServer);
        }*/

        //Data message
        writer.print(SMTPProtocol.DATA + SMTPProtocol.END_LINE);
        writer.flush();
        reponseServer = reader.readLine();
        LOG.info(reponseServer);

        //ajout des adresses From:, To:, Cc:, Bcc:,
        writer.print("Content-Type: text/play; charset=\"utf-8\"\r\n");
        writer.print(SMTPProtocol.FROM + mail.getFrom() + SMTPProtocol.END_LINE);

        writer.print(SMTPProtocol.TO + mail.getTo()[0]);
        for (int i = 1; i < mail.getTo().length; i++) {
            writer.print("," + mail.getTo()[i]);
        }
        writer.print(SMTPProtocol.END_LINE);

        writer.print(SMTPProtocol.CC + mail.getCc()[0]);
        for (int i = 1; i < mail.getCc().length; i++) {
            writer.print("," + mail.getCc()[i]);
        }
        writer.print(SMTPProtocol.END_LINE);
        writer.flush();

        writer.print(mail.getSubject());
        LOG.info(mail.getSubject());
        writer.print(mail.getContent());
        LOG.info(mail.getContent());
        writer.print(SMTPProtocol.END_DATA);
        writer.flush();

        reponseServer = reader.readLine();
        LOG.info(reponseServer);
        writer.print(SMTPProtocol.MSG_QUIT + SMTPProtocol.END_LINE);
        reader.close();
        writer.close();
        socketClient.close();
    }
}