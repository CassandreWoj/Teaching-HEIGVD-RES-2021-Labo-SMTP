package ch.heigvd.labo.mail;

public class Mail {
    private String from;
    private String[] to;
    private String[] cc;
    private String[] bcc;
    private String date;
    private String subject;
    private String content;

    public Mail(){
        this.date = java.time.LocalDate.now().toString();
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String[] getTo() { return to; }

    public void setTo(String[] to) {
        this.to = to;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String[] getCc() { return cc; }

    public void setCc(String[] cc) {
        this.cc = cc;
    }

    public String[] getBcc() { return bcc;     }

    public void setBcc(String[] bcc) {
        this.bcc = bcc;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
