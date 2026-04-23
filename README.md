# Wish2Gather

> [Link til website](https://wish2gather-e2a6g6exhrf6gyfr.swedencentral-01.azurewebsites.net/)


Vi har lavet en platform til at facilitere samvær og opfordre folk til at komme ud i verden. Ligesom på Ønskeskyen kan du tilføje ønsker til dine lister, men her er vores platform oplagt til diverse begivenheder, kurser, events og hvad end hjertet begærer af ønskeoplevelser. 

Projektet er i en alpha udgave med et ambitiøs mål om at engang komme ud i den fagre verden og begære folk på tværs af aldre og målgrupper.

## Indhold
- [Features](#features)
- [Teknologier](#teknologier)
- [Værktøjer](#værktøjer)
- [Modeller og diagrammer](#modeller-og-diagrammer)
- [Installation](#installation)
- [Brug](#brug)
- [Bidrag](#bidrag)
- [Developers](#developers)

---
## Features
- Opret profil
- Opret lister
- Tilføj ønsker til lister

## Teknologier
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-%23005C0F.svg?style=for-the-badge&logo=Thymeleaf&logoColor=white)
![spring boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![mysql](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white)
## Værktøjer
![figma|87](https://img.shields.io/badge/Figma-F24E1E?style=for-the-badge&logo=figma&logoColor=white)
![intellij](https://img.shields.io/badge/IntelliJ_IDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)
![Penpot](https://img.shields.io/badge/penpot-%23FFFFFF.svg?style=for-the-badge&logo=penpot&logoColor=black)
![Claude](https://img.shields.io/badge/Claude-D97757?style=for-the-badge&logo=claude&logoColor=white)

Ai brugt til SQL test data og unit test af throws i service-laget. Vi har brugt PenPots HTML-genererede funktion.

## Modeller og diagrammer

### Domænemodel
Domænemodellen beskriver systemets datastruktur, hvor en Profile kan eje flere Wishlists. Hver ønskeliste fungerer som en container for flere Wishes, som hver især kan tildeles forskellige tags for bedre organisering og kategorisering.

[![Domænemodel](https://github.com/user-attachments/assets/1ec98c66-7d37-4bfe-b211-270cc6b9d6d2)](https://lucid.app/lucidchart/fdcd6789-f437-4ef4-abed-ded161da752c/edit?invitationId=inv_7616146a-ee9c-4d43-9881-2161bd95d854&page=HyMMb21TK6Mh#)

### ER-digram
ER-diagrammet definerer databasens struktur og sikrer dataintegritet gennem relationer mellem entiteterne profile, wishlist, wish og tag. En profile kan være knyttet til flere wishlists via profile_id, mens hver wishlist indeholder specifikke wishes forbundet gennem wishlist_id. For at understøtte en mange-til-mange relation mellem ønsker og tags anvendes binde-tabellen wish_tag, som kobler wish_id med tag_id.

[![ER-diagram](https://github.com/user-attachments/assets/47f67250-e0a6-4d3d-8ef0-032eae4e7882)](https://lucid.app/lucidchart/fdcd6789-f437-4ef4-abed-ded161da752c/edit?invitationId=inv_7616146a-ee9c-4d43-9881-2161bd95d854&page=HyMMb21TK6Mh#)

### Klassediagram før implementering 
Dette projekt er struktureret efter en lagdelt arkitektur (Layered Architecture), som fungerer som en udvidet MVC-model for at sikre en klar adskillelse af ansvar. Systemet er designet med afsæt i GRASP-principperne, hvor Controller-laget (f.eks. WishController og LoginController) fungerer som Information Expert for HTTP-trafik, mens forretningslogikken er uddelegeret til et Service-lag (Pure Fabrication) for at opnå høj kohærens. Her valideres data, før de sendes videre til Repository-laget, som via Spring JdbcTemplate håndterer alle fundamentale CRUD-operationer (Create, Read, Update, Delete) mod databasen. Denne opbygning fremmer Low Coupling, da de enkelte lag kun kommunikerer med deres nærmeste nabo, understøttet af et centraliseret Exception-lag og domæneobjekter i Model-laget, hvilket sikrer en robust og testbar applikation.

[![KlassediagramFør](https://github.com/user-attachments/assets/cda42935-3d56-4b31-bdde-8e558b11fd8f)](https://lucid.app/lucidchart/fdcd6789-f437-4ef4-abed-ded161da752c/edit?viewport_loc=1108%2C-231%2C1987%2C1145%2CHyMMb21TK6Mh&invitationId=inv_7616146a-ee9c-4d43-9881-2161bd95d854)

### Klassedigram efter implementering
LoginController og HomeController er nu implementeret. LoginController har fået tilføjet de nødvendige metoder til at håndtere sessioner og autentificering, mens HomeController fungerer som det centrale landingspunkt. Flere metoder i WishController og WishListController er blevet præciseret (f.eks. details, create og delete stier), så de matcher den faktiske kode-struktur.

[![KlassediagramFør](https://github.com/user-attachments/assets/47c09b9a-6d82-4fc3-afa1-d0d12e962d20)]([https://lucid.app/lucidchart/fdcd6789-f437-4ef4-abed-ded161da752c/edit?viewport_loc=1108%2C-231%2C1987%2C1145%2CHyMMb21TK6Mh&invitationId=inv_7616146a-ee9c-4d43-9881-2161bd95d854](https://lucid.app/lucidchart/9f28b085-9fb7-4f8a-ae00-c1e0b4bbc256/edit?invitationId=inv_7f52ff07-aeb6-40f7-aee4-e6678a658731&page=0_0#))

### Figma prototype

UI / UX er udarbejdet i Figma først mhb. på programmets første MVP. 
[Figma prototype](https://www.figma.com/site/lziT6ExBraO2ybOTYA8cgX/Wish2Gather?node-id=0-1&t=natwvYlfVXQUtl8g-1)
Dette har været flyttet i PenPot vha. imports for at få genereret relevant HTML+CSS.

## Installation

```
git clone https://github.com/Wishlist-gruppe-1/Wishlist#
cd Wishlist
mvn spring-boot:run
```

## Brug
Åbn i `localhost:8080`

## Bidrag
Se venligst [CONTRIBUTING.md](/CONTRIBUTING.md)

## Developers

- Erik Lindkvist Thomsen - [lindkvst](https://github.com/lindkvst)
- Anne-Sophie Phanseeda - [anph1000](https://github.com/anph1000)
- Johan Oliver Larsen - [johanlarsen](https://github.com/johanlarsen)
- Julie Jensine Juul Sundsdal - [jsdsdal](https://github.com/jsdsdal)

**Udarbejdet som miniprojekt for Datamatiker 2. semester på EK (Erhvervsakademi København) fra uge 15-17 i foråret 2026.**
