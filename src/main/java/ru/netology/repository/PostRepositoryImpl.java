package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

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
        /*
         можно при создании также указать в отдельное поле адрес сохранения
         тогда эта инициализация добавляется в MainServlet.init()
        */

        try (ObjectInputStream extractor = new ObjectInputStream(new FileInputStream("STORAGE"))) {
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
     * Возвращает все существующие неудалённые посты в виде списка.
     *
     * @return список всех доступных постов.
     */
    @Override
    public List<Post> all() {
        return posts.values().stream()
                .filter(p -> !p.isDeleted())
                .collect(Collectors.toList());
    }

    /**
     * Опционально возвращает пост с указанным номером.
     *
     * @param id запрашиваемый номер поста.
     * @return опционально пост с указанным номером, либо пустую опциональ,
     * если такой пост не присутствует или помечен как удалённый.
     */
    @Override
    public Optional<Post> getById(long id) {
        Post target = posts.get(id);
        if (target == null || target.isDeleted())
            return Optional.empty();
        else
            return Optional.of(target);
    }

    /**
     * Сохраняет полученный пост в карту: при этом если это новый пост (т.е. id = 0),
     * ему назначается наименьший номер, превосходящий количество существующих постов.
     * В ином случае содержимое поста с таким номером обновляется,
     * при этом если он был помечен как удалённый, отметка снимается,
     * а если поста с таким номером не существовало вовсе, он создаётся.
     *
     * @param post сохраняемый пост.
     * @return пост в том виде, в котором он был сохранён
     */
    @Override
    public synchronized Post save(Post post) {
        if (post.getId() == 0)
//            var newId = (long) posts.size() + 1;              // и переменная тоже не нужна
//            while (posts.containsKey(newId)) newId++;   // если посты не будут изыматься из карты*, эта строка более не нужна!
                                                         // * и если нет задачи поддерживать форумы со старым принципом нумерации
            post.setId((long) posts.size() + 1);                // и скобки условному оператору не нужны теперь тоже

        /*
         как клиент вообще может послать POST-запрос на отсутствующий пост?   //, но теперь возможны коллизии, нужен контроль
         если он ранее его удалил, то пусть такое сохранение отменит удаление
        */
        posts.put(post.getId(), post);
        post.restore();                 // кстати это способ восстановить пост почти без дополнительного API
        return post;
    }

    /**
     * Помечает удалённым пост с указанным номером или, если такого нет,
     * бросает ошибку 404.
     *
     * @param id номер поста удалить.
     * @throws NotFoundException если поста с указанным номером не существует или он уже удалён.
     */
    @Override
    public void removeById(long id) {
        Post target = posts.get(id);
        if (target == null || target.isDeleted())
            throw new NotFoundException("Пост не найден.");
        target.delete();
    }
}
