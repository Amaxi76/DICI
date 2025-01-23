package dici.controller;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;

import java.util.ArrayList;

import dici.classes.City;
import dici.views.MainView;

public class MainController {

	private static MainController  ctrl;
	private        MainView        view;
	private        ObservableList<City> listCity;

	public MainController() 
	{
		this.view      = new MainView();
		this.listCity = FXCollections.observableArrayList();
		MainController.ctrl = this;
		Application.launch(MainApp.class);
	}

	public static MainController get()
    {
        if (MainController.ctrl == null) MainController.ctrl = new MainController();

        return MainController.ctrl;
    }

	public ArrayList<String> getNameCity()
	{
		ArrayList<String> nameCity = new ArrayList<String>();

		System.out.println(this.listCity);
		
		for (City city : listCity) {
			System.out.println("je suis la");
			nameCity.add(city.getNameCity());
		}

		System.out.println(nameCity);
		return nameCity;
	}

	public void setListCity(ObservableList<City> listCity)
	{
		this.listCity = listCity;
	}
	
	public static void main(String[] args) {
		MainController.get();
	}
}
