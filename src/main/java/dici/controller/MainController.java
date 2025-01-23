package dici.controller;

import javafx.application.Application;
import dici.views.MainView;

public class MainController {

	private static MainController ctrl;
	private        MainView       view;

	public MainController() 
	{
		this.view = new MainView();
		Application.launch(MainApp.class);
	}

	public static MainController get()
    {
        if (MainController.ctrl == null) MainController.ctrl = new MainController();

        return MainController.ctrl;
    }
	
	public static void main(String[] args) {
		MainController.get();
	}
}
