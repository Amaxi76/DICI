-- Date: 2019-06-06 -- 
-- Version: 1.0 --
-- Base de données : postgresql --
DROP SCHEMA IF EXISTS dici;

DROP TABLE IF EXISTS dici.immobilier   CASCADE;
DROP TABLE IF EXISTS dici.donnees_demo CASCADE;

CREATE SCHEMA dici;

/* 
 * Table: DICI
 */

CREATE TABLE dici.immobilier (
	id        char    ( 5   ) NOT NULL,
	prix_m2   float           NOT NULL,
	PRIMARY KEY ( id )
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