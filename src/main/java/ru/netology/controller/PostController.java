package ru.netology.controller;

import org.springframework.web.bind.annotation.*;
import ru.netology.model.Post;
import ru.netology.service.PostService;

import java.util.List;

/**
 * Предоставляет методы, соответствующие функционалу сервера,
 * осуществляет трансформацию представления данных между форматом запроса
 * и форматом хранения-обработки и записывает данные в ответ.
 */
@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService service;

    /**
     * Инициализует контроллер над указанным сервисом.
     * @param service сервис, над которым будет властвовать контроллер.
     */
    public PostController(PostService service) {
        this.service = service;
    }

    /**
     * Отдаёт в ответ список постов, полученный от сервиса.
     */
    @GetMapping
    public List<Post> all() {
        return service.all();
    }

    /**
     * Отдаёт в ответ пост с запрошенным номером, получив его от сервиса (если не будет прервано исключением).
     * @param id       запрашиваемый номер поста.
     */
    @GetMapping("/{id}")
    public Post getById(@PathVariable long id) {
        return service.getById(id);
    }

    /**
     * Передаёт сервису полученный пост на сохранение, получает его обратно
     * в сохранённом виде и отдаёт в ответ.
     */
    @PostMapping
    public Post save(@RequestBody Post post) {
        return service.save(post);
    }

    /**
     * Распоряжается контроллеру удалить пост с указанным номером.
     * @param id    номер поста, который удалить.
     */
    @DeleteMapping("/{id}")
    public void removeById(@PathVariable long id) {
        service.removeById(id);
    }
}
