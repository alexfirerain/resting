package ru.netology.model;

/**
 * Структура данных, соответсвующая посту.
 */
public class Post {
    private long id;
    private String content;
    private boolean isDeleted;

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

    /**
     * Сообщает номер поста.
     * @return номер поста.
     */
    public long getId() {
        return id;
    }

    /**
     * Сообщает номер посту.
     * @param id номер посту.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Сообщает содержимое поста.
     * @return текстовое содержимое поста.
     */
    public String getContent() {
        return content;
    }

    /**
     * Устанавливает посту содержимое.
     * @param content новое содержимое.
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Сообщает, помечен ли пост как удалённый.
     * @return {@code истинно}, если пост помечен как удалённый, иначе {@code ложно}.
     */
    public boolean isDeleted() {
        return isDeleted;
    }

    /**
     * Помечает пост как удалённый.
     */
    public void delete() {
        isDeleted = true;
    }

    /**
     * Помечает пост как не удалённый.
     */
    public void restore() {
        isDeleted = false;
    }
}
