# 1. Этап сборки (используем легковесный Gradle-образ)
FROM gradle:8-jdk21 AS builder
WORKDIR /app
# Копируем файлы сборщика, чтобы закешировать зависимости
COPY build.gradle settings.gradle ./
# Копируем исходный код
COPY src ./src
# Собираем готовый .jar архив (пропуская тесты для скорости)
RUN gradle bootJar -x test

# 2. Этап запуска (минимальный образ только с JRE)
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
# Копируем собранный jar-ник из предыдущего этапа
COPY --from=builder /app/build/libs/*.jar app.jar
# Указываем команду для запуска приложения
ENTRYPOINT ["java", "-jar", "app.jar"]