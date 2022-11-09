package ru.kpfu.itis.java3.semesterwork1.entity;

public class Answer {
    private int id;
    private String text;
    private int question;
    private int userId;
    private int likes;
    private boolean isBest;

    public Answer(int id, String text, int question, int userId) {
        this(id, text, question, userId, 0, false);
    }

    public Answer(int id, String text, int question, int userId, int likes) {
        this(id, text, question, userId, likes, false);
    }

    public Answer(int id, String text, int question, int userId, int likes, boolean isBest) {
        this.id = id;
        this.text = text;
        this.question = question;
        this.userId = userId;
        this.likes = likes;
        this.isBest = isBest;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getQuestion() {
        return question;
    }

    public void setQuestion(int question) {
        this.question = question;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public boolean isBest() {
        return isBest;
    }

    public void setBest(boolean best) {
        isBest = best;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Answer answer = (Answer) o;

        if (id != answer.id) return false;
        if (question != answer.question) return false;
        if (userId != answer.userId) return false;
        if (likes != answer.likes) return false;
        if (isBest != answer.isBest) return false;
        return text.equals(answer.text);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + text.hashCode();
        result = 31 * result + question;
        result = 31 * result + userId;
        result = 31 * result + likes;
        result = 31 * result + (isBest ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", likes=" + likes +
                ", isBest=" + isBest +
                '}';
    }
}
