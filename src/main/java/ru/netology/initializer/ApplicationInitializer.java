package ru.netology.initializer;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

public class ApplicationInitializer implements WebApplicationInitializer {

    /**
     * Конфигурирует данный {@link ServletContext} со всеми сервецами, фильтрами, слушателями
     * контекст-параметров и атрибутами, необходимыми для инициализации этого веб-приложения. См.
     * примеры {@linkplain WebApplicationInitializer выше}.
     *
     * @param servletContext {@code ServletContext}, который инициализировать.
     * @throws ServletException если какой-то из вызовов на {@code ServletContext}
     *                          бросит {@code ServletException}
     */
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        final var context = new AnnotationConfigWebApplicationContext();
        context.scan("ru.netology");
        context.refresh();
        final var servlet = new DispatcherServlet(context);
        final var registration = servletContext.addServlet("app", servlet);
        registration.setLoadOnStartup(1);
        registration.addMapping("/");
    }
}
