SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE profile;
TRUNCATE TABLE tag;
TRUNCATE TABLE wish;
TRUNCATE TABLE wishlist;
TRUNCATE TABLE wish_tag;
SET FOREIGN_KEY_CHECKS = 1;


START TRANSACTION;

-- Tags
INSERT INTO tag (tag_id, title) VALUES
    (1, 'Udendørs'),
    (2, 'Indendørs'),
    (3, 'Keramik'),
    (4, 'Trædrejning'),
    (5, 'City Walk'),
    (6, 'Tegning'),
    (7, 'Strik'),
    (8, 'Syning'),
    (9, 'Svampe'),
    (10, 'Fællessang'),
    (11, 'Madlavning');

-- Profiler
INSERT INTO profile (profile_id, name, email, password) VALUES
    (1, 'Anna', 'anna@mail.dk', 'kodeord123'),
    (2, 'Mikkel', 'mikkel@mail.dk', 'hemmelig456'),
    (3, 'Sofie', 'sofie@mail.dk', 'passw0rd789');

-- Wishlists
INSERT INTO wishlist (wishlist_id, title, description, profile_id) VALUES
   (1, 'Kreative weekender', 'Oplevelser med hænderne i noget fedt', 1),
   (2, 'Byture', 'Gåture og kultur i København', 1),
   (3, 'Naturoplevelser', NULL, 1),
   (4, 'Håndværk og hobby', 'Alt det jeg aldrig får tid til', 2),
   (5, 'Madoplevelser', 'Kurser og smagninger', 2),
   (6, 'Adrenalin', 'Ting der gør ondt i maven', 2),
   (7, 'Hygge med venner', 'Oplevelser man deler', 3),
   (8, 'Alene-tid', 'Stille fordybelse for mig selv', 3),
   (9, 'Sommerdrømme', 'Ting jeg vil nå inden september', 3);

-- Wishes for Anna: Kreative weekender (wishlist 1)
INSERT INTO wish (wish_id, title, description, location, date, price, url, wishlist_id) VALUES
    (1, 'Keramik-workshop', 'Lær at dreje på skive hos en lokal keramiker', 'Ceramic Copenhagen, Vesterbro', '2026-05-10', 650.00, 'https://example.com/keramik', 1),
    (2, 'Trædrejning for begyndere', 'Heldagskursus i trædrejning – lav din egen skål', 'Københavns Træværksted, Valby', '2026-06-14', 895.00, 'https://example.com/traedrejning', 1),
    (3, 'Akvarel i botanisk have', 'Mal blomster udendørs med en kunstner som guide', 'Botanisk Have, København', '2026-07-05', 450.00, NULL, 1),
    (4, 'Strik og kaffe-aften', 'Drop-in strikkecafé med garn og varm kakao', 'Løkkeland, Nørrebro', NULL, 150.00, 'https://example.com/strik', 1);

-- Wishes for Anna: Byture (wishlist 2)
INSERT INTO wish (wish_id, title, description, location, date, price, url, wishlist_id) VALUES
    (5, 'Street art-tour i Nordvest', 'Guidet tur langs de bedste murals i NV', 'Nordvest, København', NULL, 175.00, 'https://example.com/streetart', 2),
    (6, 'Arkitekturvandring på Christianshavn', 'Renæssance, pakhuse og Vor Frelsers Kirke', 'Christianshavn, København', '2026-05-17', 0.00, NULL, 2),
    (7, 'Kælderbar-crawl i Indre By', 'Find de skjulte cocktailbarer under gadeniveau', 'Indre By, København', NULL, 500.00, 'https://example.com/barcrawl', 2);

-- Wishes for Anna: Naturoplevelser (wishlist 3)
INSERT INTO wish (wish_id, title, description, location, date, price, url, wishlist_id) VALUES
    (8, 'Svampetur i Dyrehaven', 'Guidet tur med svampeekspert – tag kurven med', 'Dyrehaven, Klampenborg', '2026-09-20', 225.00, 'https://example.com/svampe', 3),
    (9, 'Kajaktur i Københavns havn', 'Aftenpadling med solnedgang over Operaen', 'Kajak Republic, København', '2026-07-12', 350.00, NULL, 3),
    (10, 'Fuglekiggeri ved Amager Fælled', 'Morgenvandring med ornitolog', 'Amager Fælled, København', '2026-05-03', 0.00, NULL, 3),
    (11, 'Natløb i Hareskoven', 'Trailløb med pandelampe og kakao bagefter', 'Hareskoven, Ballerup', '2026-10-18', 175.00, 'https://example.com/natlob', 3),
    (12, 'Foraging-tur ved Utterslev Mose', 'Saml vilde urter og lav mad over bål', 'Utterslev Mose, København', '2026-06-22', 395.00, NULL, 3);

-- Wishes for Mikkel: Håndværk og hobby (wishlist 4)
INSERT INTO wish (wish_id, title, description, location, date, price, url, wishlist_id) VALUES
    (13, 'Sykursus – lav en tote bag', 'Lær at bruge en symaskine og gå hjem med en taske', 'Stof og Saks, Frederiksberg', '2026-05-24', 375.00, 'https://example.com/sytaske', 4),
    (14, 'Croquis-aften', 'Tegn levende model i et afslappet atelier', 'Kunsthal Charlottenborg, København', NULL, 200.00, NULL, 4),
    (15, 'Lær at binde fluer', 'Fluebinding for lystfiskere – roligt og meditativt', 'Fiskeklubben, Roskilde', '2026-08-03', 300.00, 'https://example.com/fluebinding', 4),
    (16, 'Trædrejning – skål og lysestage', 'Udvidet kursus med fokus på finish og olie', 'Københavns Træværksted, Valby', '2026-09-06', 1200.00, 'https://example.com/traedrejning2', 4);

-- Wishes for Mikkel: Madoplevelser (wishlist 5)
INSERT INTO wish (wish_id, title, description, location, date, price, url, wishlist_id) VALUES
    (17, 'Naturvin-smagning', 'Smag 8 naturvine med en sommelier', 'Rødder & Vin, Vesterbro', '2026-06-07', 495.00, 'https://example.com/naturvin', 5),
    (18, 'Ramen fra bunden', 'Lav dashi, nudler og toppings fra scratch', 'Copenhagen Cooking Class, Nørrebro', '2026-05-31', 650.00, NULL, 5),
    (19, 'Fermentering for nybegyndere', 'Kimchi, kombucha og miso på én eftermiddag', 'Meyers Madhus, København', '2026-07-19', 575.00, 'https://example.com/fermentering', 5),
    (20, 'Bålmad i skoven', 'Lær at lave surdejsbrød og simremad over åben ild', 'Vestskoven, Albertslund', '2026-08-16', 425.00, NULL, 5),
    (21, 'Ostekursus', 'Fra mælk til friskost – hands on', 'Arla Unika, København', '2026-09-13', 550.00, 'https://example.com/ost', 5),
    (22, 'Chokoladetempering-workshop', 'Lav praliner og trøfler med en chocolatier', 'Ro Chokolade, Frederiksberg', '2026-10-04', 475.00, NULL, 5);

-- Wishes for Mikkel: Adrenalin (wishlist 6)
INSERT INTO wish (wish_id, title, description, location, date, price, url, wishlist_id) VALUES
    (23, 'Tandemudspring', 'Faldskærmsudspring fra 4000 meter', 'Skydive Copenhagen, Slagelse', '2026-06-28', 2200.00, 'https://example.com/skydive', 6),
    (24, 'Klatring i Boulders', 'Intro til bouldering med instruktør', 'Boulders, Sydhavnen', NULL, 250.00, 'https://example.com/boulders', 6);

-- Wishes for Sofie: Hygge med venner (wishlist 7)
INSERT INTO wish (wish_id, title, description, location, date, price, url, wishlist_id) VALUES
    (25, 'Fællessang i Enghaveparken', 'Tag tæppet med og syng med til levende musik', 'Enghaveparken, Vesterbro', '2026-06-15', 0.00, NULL, 7),
    (26, 'Keramik-date med veninder', 'Freestyle med ler og glasur – tag jeres kopper med hjem', 'Ceramic Copenhagen, Vesterbro', '2026-05-18', 550.00, 'https://example.com/keramikdate', 7),
    (27, 'Escape room – sci-fi tema', 'Løs gåder og slipper ud inden tiden løber ud', 'Escape Copenhagen, Indre By', NULL, 280.00, 'https://example.com/escape', 7),
    (28, 'Filmmaraton på Grand Teatret', 'Tre film i træk med popcorn og plaid', 'Grand Teatret, København', '2026-11-08', 350.00, NULL, 7),
    (29, 'Quiz-aften på Café Sjæl', 'Pubquiz med venneholdet', 'Café Sjæl, Nørrebro', NULL, 50.00, NULL, 7);

-- Wishes for Sofie: Alene-tid (wishlist 8)
INSERT INTO wish (wish_id, title, description, location, date, price, url, wishlist_id) VALUES
    (30, 'Tegne-workshop i Cisternerne', 'Tegn i det underjordiske rum med stemningslys', 'Cisternerne, Frederiksberg', '2026-06-01', 325.00, 'https://example.com/cisternerne', 8),
    (31, 'Meditationsretreat – én dag', 'Stille dag med yoga, meditation og te', 'Kildeskovshallen Retreat, Gentofte', '2026-07-26', 750.00, NULL, 8),
    (32, 'Lyt til jazz alene på La Fontaine', 'Fredag aften, et glas rødvin, live jazz', 'La Fontaine, Indre By', NULL, 200.00, 'https://example.com/lafontaine', 8);

-- Wishes for Sofie: Sommerdrømme (wishlist 9)
INSERT INTO wish (wish_id, title, description, location, date, price, url, wishlist_id) VALUES
    (33, 'Svøm i Kastrup Søbad', 'Morgendykkeri med udsigt over Øresund', 'Kastrup Søbad, Amager', '2026-07-01', 0.00, NULL, 9),
    (34, 'Cykeltur langs Amager Strandpark', 'Picnic og sol hele vejen ned til Dragør', 'Amager Strand, København', '2026-07-15', 0.00, NULL, 9),
    (35, 'Open-air biograf i Tivoli', 'Sommerfilm under stjernerne', 'Tivoli, København', '2026-08-09', 275.00, 'https://example.com/tivolifilm', 9),
    (36, 'Havkajak ved Stevns Klint', 'Padl langs klinten og se kridtlagene fra vandet', 'Stevns Klint, Stevns', '2026-08-23', 450.00, NULL, 9),
    (37, 'Solnedgangskoncert på Papirøen', 'Livemusik ved havnefronten', 'Papirøen, København', '2026-08-30', 175.00, 'https://example.com/papiroen', 9);

-- Wish-tag relationer
INSERT INTO wish_tag (wish_id, tag_id) VALUES
   -- Anna: Kreative weekender
   (1, 2),   -- Keramik-workshop → Indendørs
   (1, 3),   -- Keramik-workshop → Keramik
   (2, 2),   -- Trædrejning → Indendørs
   (2, 4),   -- Trædrejning → Trædrejning
   (3, 1),   -- Akvarel i botanisk have → Udendørs
   (3, 6),   -- Akvarel i botanisk have → Tegning
   (4, 2),   -- Strik og kaffe → Indendørs
   (4, 7),   -- Strik og kaffe → Strik
   -- Anna: Byture
   (5, 1),   -- Street art-tour → Udendørs
   (5, 5),   -- Street art-tour → City Walk
   (6, 1),   -- Arkitekturvandring → Udendørs
   (6, 5),   -- Arkitekturvandring → City Walk
   (7, 5),   -- Kælderbar-crawl → City Walk
   -- Anna: Naturoplevelser
   (8, 1),   -- Svampetur → Udendørs
   (8, 9),   -- Svampetur → Svampe
   (9, 1),   -- Kajaktur → Udendørs
   (10, 1),  -- Fuglekiggeri → Udendørs
   (11, 1),  -- Natløb → Udendørs
   (12, 1),  -- Foraging → Udendørs
   (12, 9),  -- Foraging → Svampe
   (12, 11), -- Foraging → Madlavning
   -- Mikkel: Håndværk og hobby
   (13, 2),  -- Sykursus → Indendørs
   (13, 8),  -- Sykursus → Syning
   (14, 2),  -- Croquis → Indendørs
   (14, 6),  -- Croquis → Tegning
   (15, 2),  -- Fluebinding → Indendørs
   (16, 2),  -- Trædrejning → Indendørs
   (16, 4),  -- Trædrejning → Trædrejning
   -- Mikkel: Madoplevelser
   (18, 2),  -- Ramen → Indendørs
   (18, 11), -- Ramen → Madlavning
   (19, 2),  -- Fermentering → Indendørs
   (19, 11), -- Fermentering → Madlavning
   (20, 1),  -- Bålmad → Udendørs
   (20, 11), -- Bålmad → Madlavning
   (21, 2),  -- Ostekursus → Indendørs
   (21, 11), -- Ostekursus → Madlavning
   (22, 2),  -- Chokoladetempering → Indendørs
   (22, 11), -- Chokoladetempering → Madlavning
   -- Mikkel: Adrenalin
   (23, 1),  -- Tandemudspring → Udendørs
   (24, 2),  -- Klatring → Indendørs
   -- Sofie: Hygge med venner
   (25, 1),  -- Fællessang → Udendørs
   (25, 10), -- Fællessang → Fællessang
   (26, 2),  -- Keramik-date → Indendørs
   (26, 3),  -- Keramik-date → Keramik
   (27, 2),  -- Escape room → Indendørs
   -- Sofie: Alene-tid
   (30, 2),  -- Tegne-workshop → Indendørs
   (30, 6),  -- Tegne-workshop → Tegning
   -- Sofie: Sommerdrømme
   (33, 1),  -- Svøm i Kastrup → Udendørs
   (34, 1),  -- Cykeltur → Udendørs
   (36, 1),  -- Havkajak → Udendørs
   (37, 1);  -- Solnedgangskoncert → Udendørs
COMMIT;