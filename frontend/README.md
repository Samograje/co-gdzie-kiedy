# frontend

Stona kliencka systemu - aplikacja hybrydowa:
 - strona internetowa (wspierane przeglądarki są wymienione w pliku `package.json`)
 - aplikacja mobilna dla systemu Android (w wersji co najmniej 4.1)

Wykorzystywane technologie:
- [React](https://pl.reactjs.org/)
- [React Native](https://facebook.github.io/react-native/)
- [React Native Web](http://necolas.github.io/react-native-web/docs/?path=/docs/overview-getting-started--page)
- [React Router Web](https://reacttraining.com/react-router/web/guides/quick-start)
- [React Router Native](https://reacttraining.com/react-router/native/guides/quick-start)

## Przygotowanie środowiska deweloperskiego

Aplikacja wymaga zainstalowanych:
- [node.js](https://nodejs.org/en/)
- Wszystkich zależności wypisanych [na tej stronie](https://facebook.github.io/react-native/docs/getting-started) w zakładce 'React Native CLI Quickstart'

Przed uruchomieniem aplikacji należy pobrać pliki bibliotek, wykonując poniższe polecenie:
```
npm i
```

## Uruchamianie

Aplikację można uruchomić na urządzeniu mobilnym lub w przeglądarce na komputerze. Obie wersje wysyłają żądania API, dlatego wymagają do działania uruchomionego API serwera pod adresem `http://localhost:8080`.

### Aplikacja mobilna

Polecenie:
```
npm run android-dev
```
Instaluje aplikację na podłączonym urządzeniu z systemem Android oraz uruchamia lokalny deweloperski serwer Metro. Aplikacja jest odświeżana przy każdej zmianie kodu źródłowego. Potrząśnięcie urządzeniem włącza opcje deweloperskie.

Na chwilę obecną nie ma informacji o sposobie uzyskania połączenia z api-serwerem oraz o sposobach debugowania aplikacji na urządzeniu.

Uruchamianie na emulatorze nie jest obecnie wspierane. Ponadto, aplikacja nie jest dostępna dla systemu iOS.

### Strona internetowa

Polecenie:
```
npm run start-web
```
Uruchamia lokalny serwer udostępniający stronę internetową. Do debugowania warto użyć narzędzi deweloperskich zawartych w przeglądarce internetowej.


## Tworzenie wersji produkcyjnej

Istnieje możliwość stworzenia wersji produkcyjnych aplikacji. Takie wersje są zoptymalizowane pod kątem wydajności oraz nie udostępniają narzędzi deweloperskich.

### Aplikacja mobilna

Polecenie:
```
npm run android-release
```
Instaluje na urządzeniu wersję produkcyjną aplikacji.

### Strona internetowa

Polecenie:
```
npm run build-web
```
Tworzy wersję produkcyjną w katalogu `/build`.
