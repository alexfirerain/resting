package ru.netology.repository;

import ru.netology.model.Post;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * Сериализуемая структура данных, соответствующая списку постов.
 */
public class PostStorage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final List<Post> posts;

    /**
     * Создаёт новое хранилище постов из полученного списка.
     * @param posts список постов, который нужно сохранить.
     */
    public PostStorage(List<Post> posts) {
        this.posts = posts;
    }

    /**
     * Отдаёт список постов.
     * @return содержимое хранилища.
     */
    public List<Post> getPosts() {
        return posts;
    }

}
