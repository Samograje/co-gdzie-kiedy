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

Wymagane do uruchomienia strony internetowej oraz aplikacji mobilnej:
1. Instalacja [node.js](https://nodejs.org/en/) w wersji 10.16
2. Pobranie plików bibliotek - polecenie `npm i` w katalogu `frontend`

Dodatkowe czynności wymagane do uruchomienia aplikacji mobilnej:
1. Wykonanie wszystkich czynności opisanych [na tej stronie](https://facebook.github.io/react-native/docs/getting-started) w zakładce 'React Native CLI Quickstart'
2. Ponowne uruchomienie komputera

Jeśli node.js jest w wersji nowszej niż 10.16, mogą pojawiać się problemy z uruchamianiem metro serwera. Instrukcje do naprawy tych błędów znajdują się na końcu tej dokumentacji.

Aby na stronie internetowej działało wyświetlanie plików pdf, należy w przeglądarce zezwolić na uruchamianie wyskakujących okienek.

## Uruchamianie

Aplikację można uruchomić na urządzeniu mobilnym lub w przeglądarce na komputerze. Obie wersje wysyłają żądania API, dlatego wymagają do działania uruchomionego API serwera pod adresem `http://localhost:8080`.

### Aplikacja mobilna

Instrukcje przygotowania emulatora znajdują [na tej stronie](https://facebook.github.io/react-native/docs/getting-started) pod hasłem 'Preparing the Android device'

Instrukcje przygotowania fizycznego urządzenia znajdują się [na tej stronie](https://facebook.github.io/react-native/docs/running-on-device)


Polecenie:
```
npm run android-dev
```
Instaluje aplikację na dostępnym urządzeniu z systemem Android (fizyczne urządzenie lub emulator) oraz uruchamia lokalny deweloperski serwer Metro. Aplikacja jest odświeżana przy każdej zmianie kodu źródłowego. Potrząśnięcie urządzeniem włącza opcje deweloperskie.

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


## Inne informacje:

Jeśli node.js jest w wersji nowszej niż 10.16, mogą pojawiać się problemy z uruchamianiem metro serwera. Wtedy można je rozwiązać następująco:
- zmodyfikować plik `frontend\node_modules\metro-config\src\defaults\blacklist.js`:
```
var sharedBlacklist = [
  /node_modules[\/\\]react[\/\\]dist[\/\\].*/,
  /website\/node_modules\/.*/,
  /heapCapture\/bundle\.js/,
  /.*\/__tests__\/.*/
];
```
