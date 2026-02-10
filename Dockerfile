# Use Java 21
FROM eclipse-temurin:21-jdk-alpine

# Set working directory
WORKDIR /app

# Copy Maven wrapper & pom
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Download dependencies
RUN ./mvnw dependency:go-offline

# Copy source
COPY src src

# Build app
RUN ./mvnw clean package -DskipTests

# Expose port (Render provides PORT)
EXPOSE 8080

# Run app
CMD ["java", "-jar", "target/*.jar"]
