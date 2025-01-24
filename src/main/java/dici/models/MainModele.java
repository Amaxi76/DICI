package dici.models;

import java.sql.Connection;
import java.sql.SQLException;

import dici.models.dao.DICIDao;
import dici.models.dto.DICI;

public class MainModele {
	
	private Connection database;

	public DICI getCityByName ( String cityName ) {
		try {
			return DICIDao.getByCityName ( database, cityName );
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}