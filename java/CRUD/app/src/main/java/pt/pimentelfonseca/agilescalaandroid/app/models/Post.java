package pt.pimentelfonseca.agilescalaandroid.app.models;

import java.util.Date;

public class Post {

    public String Title = "title";
    public int numberOfLikes;
    public Date date = new Date();

    @Override
    public boolean equals(Object to)
    {
        if (to instanceof Post == false)
        {
            return false;
        }
        Post that = (Post) to;
        return this.Title == that.Title && this.numberOfLikes == that.numberOfLikes && this.date.getTime() == that.date.getTime();
    }
}
