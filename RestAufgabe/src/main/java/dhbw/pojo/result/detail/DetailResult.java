package dhbw.pojo.result.detail;

import dhbw.pojo.detail.track.Album;
import dhbw.pojo.detail.track.Artist;
import java.util.List;



public class DetailResult {

    private String title;
    private String info;
    private Album album;
    private List<Artist> artists;

    public DetailResult(String title, String info) {
        this.title = title;
        this.info = info;
    }
    
    public DetailResult(String title, String info, Album album, List<Artist> artists) {
        this.title = title;
        this.info = info;
        this.album = album;
        this.artists = artists;
    }
    
//    public DetailResult(String title, String info) {
//        this.title = title;
//        this.info = info;
//    }
//    
//    public DetailResult(String title, String info) {
//        this.title = title;
//        this.info = info;
//    }

    public DetailResult() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
    
        public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artist) {
        this.artists = artists;
    }
}

