package dici.views.components;

import javafx.scene.control.Button;

//TODO: voir pour mettre en class interne

public class City
{
	private String nameCity;
	private Button deleteBtn;
	
	public City(String cityName)
	{
		this.nameCity = cityName;
		this.deleteBtn = new Button("🗑");
		this.deleteBtn.getStyleClass().add("delete-button");
	}
	
    public String getNameCity () { return nameCity;  }
    public Button getDeleteBtn() { return deleteBtn; }
}