-- Date: 2019-06-06 -- 
-- Version: 1.0 --
-- Base de données : postgresql --

DROP SCHEMA IF EXISTS dici CASCADE;
DROP TABLE IF EXISTS dici.dici CASCADE;

CREATE SCHEMA IF NOT EXISTS dici;

CREATE TABLE dici.dici (
	id             SERIAL          NOT NULL,
	code_ville     INT             NOT NULL,
	nom_ville      VARCHAR ( 255 ) NOT NULL,
	prix_m2        FLOAT           NOT NULL,
	age            INT             NOT NULL,
	niveau_diplome VARCHAR ( 255 ) NOT NULL,
	densite_pop    INT             NOT NULL,
	pop_active     INT             NOT NULL,
	taux_chomage   INT             NOT NULL,
	PRIMARY KEY ( id )
);