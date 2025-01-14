-- Date: 2019-06-06 -- 
-- Version: 1.0 --
-- Base de données : postgresql --

DROP TABLE IF EXISTS "immobilier" CASCADE;
DROP TABLE IF EXISTS "donnees_demo" CASCADE;

/* 
 * Table: immobilier
 */

CREATE TABLE "immobilier" (
	"id" SERIAL NOT NULL,
	"nom_ville" varchar(255) NOT NULL,
	"prix_m2" int NOT NULL,
	"type_zone" varchar(255) NOT NULL,
	PRIMARY KEY ("id")
);


/* 
 * Table: donnees_demo
 */

CREATE TABLE "donnees_demo" (
	"id" SERIAL NOT NULL,
	"age" int NOT NULL,
	"niveau_diplome" varchar(255) NOT NULL,
	"densite_pop" int NOT NULL,
	"pop_active" int NOT NULL,
	"revenu_median" int NOT NULL,
	"taux_chomage" int NOT NULL,
	PRIMARY KEY ("id")
);