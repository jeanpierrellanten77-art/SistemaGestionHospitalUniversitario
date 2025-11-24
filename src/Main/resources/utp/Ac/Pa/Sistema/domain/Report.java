package utp.ac.pa.sistema.domain;

import java.time.LocalDateTime;
import java.util.List;

public class Report {
    private String id;
    private LocalDateTime generatedAt;
    private String title;
    private String content;
    private List<Auditoria> auditorias;

    public Report(String id, String title, String content, List<Auditoria> auditorias) {
        this.id = id;
        this.generatedAt = LocalDateTime.now();
        this.title = title;
        this.content = content;
        this.auditorias = auditorias;
    }

    public String getId() { return id; }
    public LocalDateTime getGeneratedAt() { return generatedAt; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public List<Auditoria> getAuditorias() { return auditorias; }

    @Override
    public String toString() {
        return "Report{" + id + ", " + title + ", generatedAt=" + generatedAt + '}';
    }
}
