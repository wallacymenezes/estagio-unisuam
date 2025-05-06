# Etapa 1: Construção do Backend (Spring Boot) com Java 24
FROM openjdk:24-jdk-slim as build

# Definir diretório de trabalho
WORKDIR /workspace/app

# Copiar arquivos necessários para o Maven e o código fonte
COPY mvnw ./
COPY .mvn .mvn
COPY pom.xml ./
COPY src src/

# Conceder permissão ao script Maven Wrapper
RUN chmod -R 777 ./mvnw

# Instalar dependências e construir o projeto
RUN ./mvnw clean install -DskipTests

# Extrair as dependências do JAR
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

# Etapa 2: Rodar o Backend (Spring Boot)
FROM openjdk:24-jdk-slim

# Criar volume temporário para o Spring Boot
VOLUME /tmp

# Definir as variáveis de caminho
ARG DEPENDENCY=/workspace/app/target/dependency

# Copiar o backend (Spring Boot) para a imagem final
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

# Expor a porta 8080 para acesso ao backend
EXPOSE 8080

# Entrypoint para rodar a aplicação Spring Boot
ENTRYPOINT ["java", "-cp", "app:app/lib/*", "com.wallacy.projetoestagio.ProjetoEstagioApplication"]
