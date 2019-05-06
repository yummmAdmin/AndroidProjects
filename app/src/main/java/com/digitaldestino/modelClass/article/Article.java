package com.digitaldestino.modelClass.article;

public class Article
{
    private String _id;

    private String subject;

    private String description;

    private String name;

    private String language;

    private String identifier;

    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String get_id ()
    {
        return _id;
    }

    public void set_id (String _id)
    {
        this._id = _id;
    }

    public String getSubject ()
    {
        return subject;
    }

    public void setSubject (String subject)
    {
        this.subject = subject;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getLanguage ()
    {
        return language;
    }

    public void setLanguage (String language)
    {
        this.language = language;
    }

    public String getIdentifier ()
    {
        return identifier;
    }

    public void setIdentifier (String identifier)
    {
        this.identifier = identifier;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [_id = "+_id+", subject = "+subject+", description = "+description+", name = "+name+", language = "+language+", identifier = "+identifier+"]";
    }
}
			
			