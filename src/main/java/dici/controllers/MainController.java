package dici.controllers;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

import dici.views.components.City;
import dici.models.MainModele;
import dici.models.dto.DICI;
import dici.views.MainView;

public class MainController {

	private MainView        view;
	private MainModele	    modele;
	//private        ObservableList<City> listCity;

	public MainController() 
	{
		this.modele    = new MainModele (      );
		this.view      = new MainView   ( this );
		//this.listCity = FXCollections.observableArrayList();
	}


	// public ArrayList<String> getNameCity()
	// {
	// 	ArrayList<String> nameCity = new ArrayList<String>();

		
	// 	for (City city : listCity) {
	// 		nameCity.add(city.getNameCity());
	// 	}

	// 	return nameCity;
	// }

	public DICI getCityByName ( String cityName ) {
		return this.modele.getCityByName ( cityName );
	}

	// public void setListCity(ObservableList<City> listCity)
	// {
	// 	this.listCity = listCity;
	// }
	
	public static void main(String[] args) {
		new MainController ( );
	}
}
