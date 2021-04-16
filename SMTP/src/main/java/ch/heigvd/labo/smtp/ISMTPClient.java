package ch.heigvd.labo.smtp;

import ch.heigvd.labo.mail.Mail;

public interface ISMTPClient {
    void sendMail(Mail mail);
}
