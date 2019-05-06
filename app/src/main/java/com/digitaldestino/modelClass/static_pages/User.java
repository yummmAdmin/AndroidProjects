package com.digitaldestino.modelClass.static_pages;

public class User
{
    private String _id;

    private String page_description;

    private String name;

    private String email_subject;

    private String language;

    private String meta_title;

    private String meta_keyword;

    private String identifier;

    public String get_id ()
    {
        return _id;
    }

    public void set_id (String _id)
    {
        this._id = _id;
    }

    public String getPage_description ()
    {
        return page_description;
    }

    public void setPage_description (String page_description)
    {
        this.page_description = page_description;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getEmail_subject ()
    {
        return email_subject;
    }

    public void setEmail_subject (String email_subject)
    {
        this.email_subject = email_subject;
    }

    public String getLanguage ()
    {
        return language;
    }

    public void setLanguage (String language)
    {
        this.language = language;
    }

    public String getMeta_title ()
    {
        return meta_title;
    }

    public void setMeta_title (String meta_title)
    {
        this.meta_title = meta_title;
    }

    public String getMeta_keyword ()
    {
        return meta_keyword;
    }

    public void setMeta_keyword (String meta_keyword)
    {
        this.meta_keyword = meta_keyword;
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
        return "ClassPojo [_id = "+_id+", page_description = "+page_description+", name = "+name+", email_subject = "+email_subject+", language = "+language+", meta_title = "+meta_title+", meta_keyword = "+meta_keyword+", identifier = "+identifier+"]";
    }
}