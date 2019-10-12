# backend

Aplikacja służąca do obsługi zapytań REST API oraz do tworzenia i komunikacji z bazą danych, utworzona w Spring Boot (Java). Na chwilę obecną (TODO) tworzy tabele w istniejącej już bazie danych, zgodnie z modelem zawartym w klasach katalogu `entity`.

## Instalacja

Aplikacja wymaga zainstalowanego JDK.

## Konfiguracja (IntelliJ IDEA)

Po sklonowaniu repozytorium przy użyciu opcji *Check out from Version Control* należy wykonać następujące czynności:
- PPM na pliku `pom.xml`, wybrać *Add as a Maven project*
- Ustawić *Project SDK* na zainstalowaną wersję JDK, odpowiednie okno pojawia się po przejściu do dowolnej klasy
- Dodać konfigurację *SpringBoot*, podając nazwę oraz klasę wejściową
- Skopiować plik `application-example.properties`, nadając mu nazwę `application.properties`. Ten plik zawiera lokalną konfigurację bazy danych, zatem nie powinien być dodawany do repozytorium. W tym pliku należy ustawić ades, nazwę użytkownika i hasło dotyczące wybranej przez siebie bazy danych. Domyślne wartości z pliku `application-example.properties` dotyczą lokalnej bazy danych o nazwie *test*, utworzonej z użyciem [Xampp](https://www.apachefriends.org/pl/index.html) oraz [PhpMyAdmin](https://www.phpmyadmin.net/).

TODO: pliki źródłowe znajdują się w pakiecie `org.polsl.backend`. Propozycja, aby zamienić `polsl` na jakąś inną nazwę.