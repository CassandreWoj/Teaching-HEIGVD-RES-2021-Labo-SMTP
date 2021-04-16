package ch.heigvd.labo.mail;

public class Mail {
    private String from;
    private String[] to;
    private String date;
    private String[] cc;
    private String[] bcc;
    private String subject;
    private String content;

    public Mail(String from, String[] to, String[] cc, String[] bcc, String subject, String content){
        this.from = from;
        this.to = to;
        this.date = String.valueOf(java.time.LocalDate.now());
        this.cc = cc;
        this.bcc = bcc;
        this.subject = subject;
        this.content = content;
    }

}
