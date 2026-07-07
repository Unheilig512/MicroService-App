FROM eclipse-temurin:21-jdk-alpine

# Указываем рабочую директорию внутри контейнера
WORKDIR /app

# Копируем скомпилированный jar-файл (после ./gradlew build)
COPY build/libs/*.jar app.jar

# Команда для запуска
ENTRYPOINT ["java", "-jar", "app.jar"]