package ch.heigvd.labo.smtp;

import ch.heigvd.labo.mail.Mail;

import java.io.IOException;

public interface ISMTPClient {

    void sendMail(Mail mail) throws IOException;
}
