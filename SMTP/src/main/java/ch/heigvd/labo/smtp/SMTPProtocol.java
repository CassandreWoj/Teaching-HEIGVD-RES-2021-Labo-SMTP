package ch.heigvd.labo.smtp;

public class SMTPProtocol {
    public static final String ehlo = "EHLO ";
    public static final String mailFrom = "MAIL FROM: ";
    public static final String rcptTo = "RCPT TO: ";

    public static final String data = "DATA";

    public static final String from = "From: ";
    public static final String to = "To: ";
    public static final String cc = "Cc: ";
    public static final String date = "Date: ";

    public static final String endLine = "\r\n";
    public static final String endData = endLine + "." + endLine;
    public static final String msgQuit = "QUIT";

    public static final String msgReady = "220 ";
    public static final String msgEnd = "250 ";
}
