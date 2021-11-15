package ru.netology.repository;

import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {

    /**
     * Сохраняет посты в виде списка в файл STORAGE.
     */
    void store();

    /**
     * Возвращает все существующие посты в виде списка.
     * @return  список всех существующих постов.
     */
    List<Post> all();


    /**
     * Опционально возвращает пост с указанным номером.
     * @param id запрашиваемый номер поста.
     * @return  опционально пост с указанным номером.
     */
    Optional<Post> getById(long id);

    /**
     * Сохраняет полученный пост в карту: при этом если пост с таким же номером
     * существует, он обновляется, если же это новый пост (т.е. id = 0),
     * ему назначается наименьший незанятый номер, превосходящий количество существующих постов.
     * @param post  сохраняемый пост.
     * @return  пост в том виде, в котором он был сохранён
     */
    Post save(Post post);

    /**
     * Удаляет пост с указанным номером или, если такого нет,
     * бросает ошибку 404.
     * @param id номер поста удалить.
     * @throws NotFoundException если поста с указанным номером не существует
     */
    void removeById(long id) throws NotFoundException;

}
