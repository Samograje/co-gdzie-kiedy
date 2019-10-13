# co-gdzie-kiedy
System do inwentaryzacji sprzętu i oprogramowania komputerowego - strona internetowa oraz aplikacja mobilna.
- Umożliwia wydruk kart inwentarzowych dla lokalizacji i użytkowników
- Obsługuje kody QR i kody kreskowe w aplikacji mobilnej

Baza danych zawiera:
- rodzaj sprzętu
- specyfikacje
- numery seryjne
- klucze instalacyjne
- daty zakupu
- daty złomowania
- gwarancje
- lokalizacje
- użytkowników
- osoby
- itd

## Moduły

System składa się z dwóch aplikacji:
- `backend`
- `frontend` (TODO)

Ich dokumentacje znajdują się w plikach `README.md` w katalogach tych modułów.

Moduły mogą być uruchamiane niezależnie od siebie, jednak moduł `frontend` do pełnego działania potrzebuje działającego modułu `backend`.

Komunikacja między modułami odbywa się przy pomocy REST API (TODO).
