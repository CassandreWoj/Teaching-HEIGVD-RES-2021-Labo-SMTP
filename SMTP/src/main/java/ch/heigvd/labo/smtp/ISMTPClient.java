package ch.heigvd.labo.smtp;

import ch.heigvd.labo.mail.Mail;
import ch.heigvd.labo.prank.Prank;

public interface ISMTPClient {

    void sendMail(Prank prank);
}
