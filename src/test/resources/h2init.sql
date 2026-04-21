-- H2-kompatibelt schema + testdata til Wishlist-projektet
-- Bruges med: @Sql(scripts = "classpath:h2init.sql", executionPhase = BEFORE_TEST_METHOD)

-- Drop i omvendt afhængighedsrækkefølge (barn → forælder)
DROP TABLE IF EXISTS wish_tag;
DROP TABLE IF EXISTS tag;
DROP TABLE IF EXISTS wish;
DROP TABLE IF EXISTS wishlist;
DROP TABLE IF EXISTS profile;

-- Schema
CREATE TABLE profile (
    profile_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(50) NOT NULL
);

CREATE TABLE wishlist (
    wishlist_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(50) NOT NULL,
    description VARCHAR(500),
    profile_id INT NOT NULL,
    FOREIGN KEY (profile_id) REFERENCES profile (profile_id)
      ON DELETE CASCADE
);

CREATE TABLE wish (
    wish_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(50) NOT NULL,
    description VARCHAR(500),
    location VARCHAR(200),
    date DATE,
    price DECIMAL(10,2),
    url VARCHAR(2083),
    wishlist_id INT NOT NULL,
    FOREIGN KEY (wishlist_id) REFERENCES wishlist (wishlist_id)
      ON DELETE CASCADE
);

CREATE TABLE tag (
    tag_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE wish_tag (
    wish_id INT NOT NULL,
    tag_id INT NOT NULL,
    PRIMARY KEY (wish_id, tag_id),
    FOREIGN KEY (wish_id) REFERENCES wish (wish_id)
      ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tag (tag_id)
      ON DELETE RESTRICT
);

-- === Testdata (1 ønskeliste per profil) ===

-- Tags
INSERT INTO tag (tag_id, title) VALUES
    (1, 'Udendørs'),
    (2, 'Indendørs'),
    (3, 'Keramik'),
    (4, 'Trædrejning'),
    (6, 'Tegning'),
    (7, 'Strik'),
    (9, 'Svampe'),
    (11, 'Madlavning');

-- Profiler
INSERT INTO profile (profile_id, name, email, password) VALUES
    (1, 'Anna', 'anna@mail.dk', 'kodeord123'),
    (2, 'Mikkel', 'mikkel@mail.dk', 'hemmelig456'),
    (3, 'Sofie', 'sofie@mail.dk', 'passw0rd789');

-- Ønskelister (1 per profil)
INSERT INTO wishlist (wishlist_id, title, description, profile_id) VALUES
    (1, 'Kreative weekender', 'Oplevelser med hænderne i noget fedt', 1),
    (2, 'Madoplevelser', 'Kurser og smagninger', 2),
    (3, 'Sommerdrømme', 'Ting jeg vil nå inden september', 3);

-- Wishes: Anna → Kreative weekender
INSERT INTO wish (wish_id, title, description, location, date, price, url, wishlist_id) VALUES
    (1, 'Keramik-workshop', 'Lær at dreje på skive hos en lokal keramiker', 'Ceramic Copenhagen, Vesterbro', '2026-05-10', 650.00, 'https://example.com/keramik', 1),
    (2, 'Trædrejning for begyndere', 'Heldagskursus i trædrejning – lav din egen skål', 'Københavns Træværksted, Valby', '2026-06-14', 895.00, 'https://example.com/traedrejning', 1),
    (3, 'Akvarel i botanisk have', 'Mal blomster udendørs med en kunstner som guide', 'Botanisk Have, København', '2026-07-05', 450.00, NULL, 1),
    (4, 'Strik og kaffe-aften', 'Drop-in strikkecafé med garn og varm kakao', 'Løkkeland, Nørrebro', NULL, 150.00, 'https://example.com/strik', 1);

-- Wishes: Mikkel → Madoplevelser
INSERT INTO wish (wish_id, title, description, location, date, price, url, wishlist_id) VALUES
    (5, 'Ramen fra bunden', 'Lav dashi, nudler og toppings fra scratch', 'Copenhagen Cooking Class, Nørrebro', '2026-05-31', 650.00, NULL, 2),
    (6, 'Fermentering for nybegyndere', 'Kimchi, kombucha og miso på én eftermiddag', 'Meyers Madhus, København', '2026-07-19', 575.00, 'https://example.com/fermentering', 2),
    (7, 'Bålmad i skoven', 'Lær at lave surdejsbrød og simremad over åben ild', 'Vestskoven, Albertslund', '2026-08-16', 425.00, NULL, 2);

-- Wishes: Sofie → Sommerdrømme
INSERT INTO wish (wish_id, title, description, location, date, price, url, wishlist_id) VALUES
    (8, 'Svøm i Kastrup Søbad', 'Morgendykkeri med udsigt over Øresund', 'Kastrup Søbad, Amager', '2026-07-01', 0.00, NULL, 3),
    (9, 'Cykeltur langs Amager Strandpark', 'Picnic og sol hele vejen ned til Dragør', 'Amager Strand, København', '2026-07-15', 0.00, NULL, 3),
    (10, 'Havkajak ved Stevns Klint', 'Padl langs klinten og se kridtlagene fra vandet', 'Stevns Klint, Stevns', '2026-08-23', 450.00, NULL, 3);

-- Wish-tag relationer
INSERT INTO wish_tag (wish_id, tag_id) VALUES
    (1, 2),   -- Keramik-workshop → Indendørs
    (1, 3),   -- Keramik-workshop → Keramik
    (2, 2),   -- Trædrejning → Indendørs
    (2, 4),   -- Trædrejning → Trædrejning
    (3, 1),   -- Akvarel → Udendørs
    (3, 6),   -- Akvarel → Tegning
    (4, 2),   -- Strik og kaffe → Indendørs
    (4, 7),   -- Strik og kaffe → Strik
    (5, 2),   -- Ramen → Indendørs
    (5, 11),  -- Ramen → Madlavning
    (6, 2),   -- Fermentering → Indendørs
    (6, 11),  -- Fermentering → Madlavning
    (7, 1),   -- Bålmad → Udendørs
    (7, 11),  -- Bålmad → Madlavning
    (8, 1),   -- Svøm → Udendørs
    (9, 1),   -- Cykeltur → Udendørs
    (10, 1);  -- Havkajak → Udendørs


-- Reset auto-increment tællere så nye inserts ikke kolliderer
ALTER TABLE profile ALTER COLUMN profile_id RESTART WITH 100;
ALTER TABLE wishlist ALTER COLUMN wishlist_id RESTART WITH 100;
ALTER TABLE wish ALTER COLUMN wish_id RESTART WITH 100;
ALTER TABLE tag ALTER COLUMN tag_id RESTART WITH 100;