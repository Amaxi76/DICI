-- Date: 2019-06-06 -- 
-- Version: 1.0 --
-- Base de données : postgresql --
DROP SCHEMA IF EXISTS dici;

DROP SCHEMA IF EXISTS dici CASCADE;
CREATE SCHEMA IF NOT EXISTS dici;

DROP TABLE IF EXISTS dici.DICI CASCADE;

CREATE TABLE dici.DICI (
	"id" SERIAL NOT NULL,
	"code_ville" INT NOT NULL,
	"nom_ville" varchar(255) NOT NULL,
	"prix_m2" FLOAT NOT NULL,
	"age" int NOT NULL,
	"niveau_diplome" varchar(255) NOT NULL,
	"densite_pop" int NOT NULL,
	"pop_active" int NOT NULL,
	"taux_chomage" int NOT NULL,
	PRIMARY KEY ("id")
);