package ru.netology.model;

/**
 * Структура данных, соответсвующая посту.
 */
public class Post {
    private long id;
    private String content;

    /**
     * Создаёт пустой пост.
     */
    public Post() {
    }

    /**
     * Создаёт пост с указанными полями.
     *
     * @param id      идентификационный номер поста.
     * @param content содержимое поста.
     */
    public Post(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
