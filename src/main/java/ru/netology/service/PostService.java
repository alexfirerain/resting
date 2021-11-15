package ru.netology.service;

import org.springframework.stereotype.Service;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;
import ru.netology.repository.PostRepository;

import java.util.List;

/**
 * Обеспечивает специфическую логику работы сервера.
 */
@Service
public class PostService {
    private final PostRepository repository;

    /**
     * Создаёт новый сервис для указанного репозитория.
     *
     * @param repository репозиторий, который будет обслуживаться сервисом.
     */
    public PostService(PostRepository repository) {
        this.repository = repository;
    }

    /**
     * Отдаёт список постов, полученный из репозитория.
     *
     * @return список всех постов, возвращаемый репозиторием.
     */
    public List<Post> all() {
        return repository.all();
    }

    /**
     * Отдаёт пост по запрошенному номеру, либо, в случае неуспеха, кидает исключение.
     *
     * @param id запрашиваемый номер.
     * @return пост, имеющий указанный номер.
     * @throws NotFoundException если поста с таким номером в репозитории не найдено.
     */
    public Post getById(long id) {
        return repository.getById(id).orElseThrow(NotFoundException::new);
    }

    /**
     * Отдаёт репозиторию переданный пост на сохранение.
     *
     * @param post записываемый пост.
     * @return исходный пост в том виде, как он сохранён в репозитории.
     */
    public Post save(Post post) {
        return repository.save(post);
    }

    /**
     * Распоряжается репозиторию удалить пост с указанным номером.
     *
     * @param id номер поста, который требуется удалить.
     */
    public void removeById(long id) {
        repository.removeById(id);
    }
}

