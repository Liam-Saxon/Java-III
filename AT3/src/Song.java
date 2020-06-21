public class Song implements Comparable<Song>
{
    private String songName;
    private String songURL;

    public Song() {
    }

    //getters
    public String getSongName()
    {
        return songName;
    }
    public String getSongURL()
    {
        return  songURL;
    }
    //setters
    public void setSongName(String newSongName)
    {
        this.songName = newSongName;
    }
    public void setSongURL(String newSongURL)
    {
        this.songURL = newSongURL;
    }

    @Override
    public int compareTo(Song song)
    {
        return this .getSongName().compareTo(song.getSongName());
    }
}
