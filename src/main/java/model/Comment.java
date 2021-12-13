package model;

public class Comment {

    public Comment(String name, String comment) {
        this.name = name;
        Comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    private String name;
    private String Comment;

}
