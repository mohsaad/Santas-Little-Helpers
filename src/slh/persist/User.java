package slh.persist;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User
{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    long   id;

    @Basic
    @Column(name = "username")
    String name;

    @Basic
    @Column(name = "email")
    String email;

    @Basic
    @Column(name = "reddit_key")
    String redditKey;

    @Basic(optional = true)
    @Column(name = "facebook_key")
    String facebookKey;

    @Basic(optional = true)
    @Column(name = "reddit_key")
    String twitterKey;

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getRedditKey()
    {
        return redditKey;
    }

    public void setRedditKey(String redditKey)
    {
        this.redditKey = redditKey;
    }

    public String getFacebookKey()
    {
        return facebookKey;
    }

    public void setFacebookKey(String facebookKey)
    {
        this.facebookKey = facebookKey;
    }

    public String getTwitterKey()
    {
        return twitterKey;
    }

    public void setTwitterKey(String twitterKey)
    {
        this.twitterKey = twitterKey;
    }
}
