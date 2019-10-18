# backend

Aplikacja służąca do obsługi zapytań REST API oraz do tworzenia i komunikacji z bazą danych, utworzona w Spring Boot (Java). Tworzy tabele w wybranej bazie danych oraz udostępnia REST API.

## Przygotowanie środowiska deweloperskiego

Aplikacja wymaga zainstalowanego JDK w wersji co najmniej 1.8.

Po sklonowaniu repozytorium i przejściu do katalogu `backend`, należy skopiować plik `application-example.properties`, nadając mu nazwę `application.properties`. Ten plik zawiera lokalną konfigurację bazy danych, zatem nie powinien być dodawany do repozytorium. W tym pliku należy ustawić adres, nazwę użytkownika i hasło dotyczące wybranej przez siebie bazy danych. Przykładowe wartości dotyczą połączenia z deweloperską bazą danych PostgreSQL.

## Uruchamianie

Aplikację można uruchomić, wykonując w katalogu `backend` polecenie:
```
mvn spring-boot:run
```
W rezultacie:
- aktualizowane są wszystkie tabele w bazie danych na podstawie encji zamodelowanych w katalogu `src/main/java/org/polsl/backend/entity`
- aplikacja jest uruchamiana na serwerze wbudowanym w Spring Boot, udostępniając REST API (domyślnie na porcie 8080).

Do pracy z REST API można wykorzystać dedykowane narzędzie, np. [Postman](https://www.getpostman.com/).

## Konfiguracja w IntelliJ IDEA

Po sklonowaniu repozytorium przy użyciu opcji *Check out from Version Control* należy wykonać następujące czynności:
- PPM na pliku `pom.xml`, wybrać *Add as a Maven project*
- Ustawić *Project SDK* na zainstalowaną wersję JDK, odpowiednie okno pojawia się po przejściu do dowolnej klasy
- Dodać konfigurację *SpringBoot*, podając nazwę oraz klasę wejściową

## Deweloperska baza danych

Dane dostępowe do deweloperskiej bazy danych (Heroku Postgres):
- Host: `ec2-54-247-178-166.eu-west-1.compute.amazonaws.com`
- Database: `d3uas1to80du9o`
- User: `mxllikxohqtggj`
- Port: `5432`
- Password: przesłane innym kanałem
- URI: `postgres://mxllikxohqtggj:4b24b7c68d4b969a155368e1ffc342690ce0167f045aaa4decda1c39d25455fd@ec2-54-247-178-166.eu-west-1.compute.amazonaws.com:5432/d3uas1to80du9o`
- Heroku CLI: `heroku pg:psql postgresql-animated-81903 --app co-gdzie-kiedy`

Tryb działania na strukturze bazy danych znajduje się w niewersjonowanym pliku `src/main/resources/application.properties` pod kluczem `spring.jpa.hibernate.ddl-auto`.

## Testy

TODO
