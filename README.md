# Java-explore-with-me

https://github.com/OlgaMordvinkina/java-explore-with-me/pull/5

Приложение написано с использованием:
Java 11, имеет многомодульную структуру. В разработке использованы REST API, PostgreSQL, Hibernate ORM, Lombok, Docker.

Это приложение — афиша, она помогает выбрать куда и с кем сходить. В этой афише можно предложить какое-либо событие 
от выставки до похода в кино и собрать компанию для участия в нём, а также обсудить в комментариях предстоящее времяпровождение.

### Диаграмма для сервиса «main»:

![diagramm.png](diagramm.png)

#### Варианты запуска приложения:
Командой «docker-compose up» (Для запуска через докер);
Запустить модули:
- «main»: main/src/main/java/ru/practicum/main/ExploreWithMeMain -> "run";
- «stats»: stats/service/src/main/java/ru/practicum/service/ExploreWithMeStats -> "run".

