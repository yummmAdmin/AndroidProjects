package com.digitaldestino.modelClass.get_card;

import java.util.ArrayList;

public class Response_data
{
    private String default_card;

    private ArrayList<Card_list>card_list;

    public ArrayList<Card_list> getCard_list() {
        return card_list;
    }

    public void setCard_list(ArrayList<Card_list> card_list) {
        this.card_list = card_list;
    }

    public String getDefault_card ()
    {
        return default_card;
    }

    public void setDefault_card (String default_card)
    {
        this.default_card = default_card;
    }



    @Override
    public String toString()
    {
        return "ClassPojo [default_card = "+default_card+", card_list = "+card_list+"]";
    }
}