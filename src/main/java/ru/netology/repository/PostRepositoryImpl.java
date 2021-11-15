package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
* Обеспечивает хранение и представление постов.
*/
@Repository
public class PostRepositoryImpl implements PostRepository {
private final Map<Long, Post> posts;

    /**
     * Создаёт новый репозиторий, пытаясь инициализировать его содержимое файлом STORAGE.
     */
public PostRepositoryImpl() {
    posts = new ConcurrentHashMap<>();
    // можно при создании также указать в отдельное поле адрес сохранения
    // тогда эта инициализация добавляется в MainServlet.init()

    try (ObjectInputStream extractor = new ObjectInputStream(new    FileInputStream("STORAGE"))) {
        PostStorage preceding = (PostStorage) extractor.readObject();
        for (Post post : preceding.getPosts())
            posts.put(post.getId(), post);

    } catch (Exception e) {
        System.out.println("Данные из STORAGE не добавлены по той или иной причине.");
        e.printStackTrace();
    }
}

    /**
     * Сохраняет посты в виде списка в файл STORAGE.
     */
@Override
public void store() {
    try (ObjectOutputStream conservator = new ObjectOutputStream(new FileOutputStream("STORAGE"))) {
        conservator.writeObject(new PostStorage(all()));
    } catch (IOException e) {
        System.out.println("Данные в STORAGE не сохранены по той или иной причине.");
        e.printStackTrace();
    }
}

    /**
     * Возвращает все существующие посты в виде списка.
     * @return  список всех существующих постов.
     */
@Override
public List<Post> all() {
    List<Post> postList = new ArrayList<>();
    for (Map.Entry<Long, Post> entry : posts.entrySet())
        postList.add(entry.getValue());
    return postList;
}

    /**
     * Опционально возвращает пост с указанным номером.
     * @param id запрашиваемый номер поста.
     * @return  опционально пост с указанным номером.
     */
@Override
public Optional<Post> getById(long id) {
    return Optional.ofNullable(posts.get(id));
}

    /**
     * Сохраняет полученный пост в карту: при этом если пост с таким же номером
     * существует, он обновляется, если же это новый пост (т.е. id = 0),
     * ему назначается наименьший незанятый номер, превосходящий количество существующих постов.
     * @param post  сохраняемый пост.
     * @return  пост в том виде, в котором он был сохранён
     */
@Override
public Post save(Post post) {
    if (post.getId() == 0) {
        var newId = (long) posts.size() + 1;
        while (posts.containsKey(newId)) newId++;
        post.setId(newId);
    }
    // как клиент вообще может послать POST-запрос на отсутствующий пост?
    // если он ранее его удалил, то пусть такое сохранение отменит удаление
    posts.put(post.getId(), post);
    return post;
}

    /**
     * Удаляет пост с указанным номером или, если такого нет,
     * бросает ошибку 404.
     * @param id номер поста удалить.
     * @throws NotFoundException если поста с указанным номером не существует
     */
@Override
public void removeById(long id) {
    if (!posts.containsKey(id))
        throw new NotFoundException("Пост не найден.");
    posts.remove(id);
}
}
