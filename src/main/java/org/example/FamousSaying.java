package org.example;

public class FamousSaying {

    private int id = 0;
    private String content = "";
    private String author = "";

    public FamousSaying() {
    }

    public FamousSaying(int id, String content, String author) {
        this.id = id;
        this.content = content;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("{");
        sb.append("\"id\":").append(id);
        sb.append(", \"content\": \"").append(content).append('"');
        sb.append(", \"author\": \"").append(author).append('"');
        sb.append('}');
        return sb.toString();
    }
}
