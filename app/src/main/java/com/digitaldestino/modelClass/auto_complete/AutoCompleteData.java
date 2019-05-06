package com.digitaldestino.modelClass.auto_complete;

public class AutoCompleteData {
    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    private String _id;

    private String food_item_name;

    public String getFood_item_id() {
        return food_item_id;
    }

    public void setFood_item_id(String food_item_id) {
        this.food_item_id = food_item_id;
    }

    String food_item_id;
    public String getFood_item_name() {
        return food_item_name;
    }

    public void setFood_item_name(String food_item_name) {
        this.food_item_name = food_item_name;
    }

    @Override
    public String toString() {
        return food_item_name;
    }
}
			
	