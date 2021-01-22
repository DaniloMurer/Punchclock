#Punchclock Backend
Dies ist eine Beispielapplikation für das Modul M223.

## Server Starten
Folgende Schritte befolgen um loszulegen:
1. Sicherstellen, dass JDK 14 installiert und in der Umgebungsvariable `path` definiert ist und Gradle 6.7 muss korrekt installiert sein.
1. Ins Verzeichnis der Applikation wechseln und über die Kommandozeile mit `./gradlew bootRun` oder `./gradlew.bat bootRun` starten
1. Unittest mit `./gradlew test` oder `./gradlew.bat test` ausführen.
1. Ein ausführbares JAR kann mit `./gradlew bootJar` oder `./gradlew.bat bootJar` erstellt werden.

Folgende Dienste existieren
- REST-Schnittstellen der Applikation: http://localhost:8080
- Dashboard der H2 Datenbank: http://localhost:8080/h2-console

## Seed Daten

In der H2 Datenbank sind schon beim starten:
- 2 Entries vorhanden - veränderbar
- 2 Rolles (ADMINISTRATOR, EMPLOYEE) - nicht veränderbar
- 2 ApplicationUser, 1 Administrator (username: root, password: toor), 1 Benutzer (username: danjak, password: test) - veränderbar
- 1 Category - veränderbar

