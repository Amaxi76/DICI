package dici.classes;

import javafx.scene.control.Button;

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