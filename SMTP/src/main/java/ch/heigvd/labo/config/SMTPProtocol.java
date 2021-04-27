package ch.heigvd.labo.config;

public class SMTPProtocol {
    public static final String EHLO = "EHLO ";
    public static final String MAIL_FROM = "MAIL FROM: ";
    public static final String RCPT_TO = "RCPT TO: ";

    public static final String DATA = "DATA";

    public static final String FROM = "From: ";
    public static final String TO = "To: ";
    public static final String CC = "Cc: ";
    public static final String DATE = "Date: ";

    public static final String END_LINE = "\r\n";
    public static final String END_DATA = END_LINE + "." + END_LINE;
    public static final String MSG_QUIT = "QUIT";

    public static final String MSG_READY = "220 ";
    public static final String MSG_END = "250 ";
}
