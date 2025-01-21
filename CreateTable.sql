-- Date: 2025-01-21 -- 
-- Version: 1.1 --
-- Base de donnÃ©es : postgresql --

DROP TABLE IF EXISTS "DICI" CASCADE;

/* 
 * Table: DICI
 */

CREATE TABLE "DICI" (
"id" SERIAL NOT NULL,
"code_ville" INT NOT NULL,
"nom_ville" varchar(255) NOT NULL,
"prix_m2" FLOAT NOT NULL,
"age" int NOT NULL,
"niveau_diplome" varchar(255) NOT NULL,
"densite_pop" int NOT NULL,
"pop_active" int NOT NULL,
"revenu_median" int NOT NULL,
"taux_chomage" int NOT NULL,
PRIMARY KEY ("id")
);