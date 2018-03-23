/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dhbw.webservices;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dhbw.pojo.result.search.SearchResult;
import dhbw.pojo.result.search.SearchResultList;
import dhbw.pojo.search.album.Albums;
import dhbw.pojo.search.album.SearchAlbum;
import dhbw.pojo.search.artist.Artists;
import dhbw.pojo.search.artist.SearchArtist;
import dhbw.pojo.search.track.SearchTrack;
import dhbw.pojo.search.track.Tracks;
import dhbw.spotify.RequestCategory;
import static dhbw.spotify.RequestCategory.ALBUM;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class BuildOutputService {
    
        ObjectMapper mapper = new ObjectMapper();
       
        String resultJson = null;
        List<SearchResultList> resultList = new ArrayList<SearchResultList>();
        SearchResultList result = null;
        SearchResult searchresult = null;
        SearchTrack track = null;
        
    //Methode, die je nach ResultCategory eine weitere, der Kategorie entsprechende Methode aufruft
    //Sie liefert einen Json-String an die Hauptmethode Search der Klasse SearchService zurück
    public String categoryFilter(String query, RequestCategory category, String json){
       
        String resultJson = null;
        String type= String.valueOf(category);

        //nach Kategorie filtern und entsprechende Methode aufrufen
        switch(category){
            case TRACK:{
                resultJson = getTrackJson(query, type, json);
                break;      
            }
            case ALBUM:{
                resultJson = getAlbumJson(query, type, json);
                break; 
            }   
            case ARTIST:{
                resultJson = getArtistJson(query, type, json);
                break;
            }           
        }       
        return resultJson;
    }
    
    
    public String getTrackJson(String query, String type, String json){
            
        //Json in SearchTrack-Objekt mappen
        try {
            track = mapper.readValue(json, SearchTrack.class);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        //SearchTrack enthält mehrere Tracks, diese in Tracks-Objekt festhalten
        Tracks tracks = track.getTracks();
        
        //Items der Klasse Tracks in ItemList speichern
        List<dhbw.pojo.search.track.Item> itemList = tracks.getItems();
        
        //durch itemList iterieren und für jedes Objekt Id, Name, Beschreibung und URL in SearchResultList speichern
        for (dhbw.pojo.search.track.Item item : itemList){ // Description wird erwartet, steht jedoch nicht im JSON
            result = new SearchResultList(item.getId(), item.getName(), "Hier könnte ihre Beschreibung stehen", item.getExternalUrls().getSpotify());
            resultList.add(result);
        }
        
        //aus query, ResultCategory und SearchResultList ein SearchResult Objekt erzeugen
        searchresult = new SearchResult(query, type, resultList);

        //SearchResult als Json mappen
        try {
            resultJson = mapper.writeValueAsString(searchresult);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
        return resultJson;
    }
    
    public String getAlbumJson(String query, String type, String json){
          
        SearchAlbum album = null;
        
        //Json in SearchAlbum-Objekt mappen
        try {
            album = mapper.readValue(json, SearchAlbum.class);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        //SearchAlbum enthält mehrere Alben, diese in Albums-Objekt festhalten
        Albums albums = album.getAlbums();
        
        //Items der Klasse Albums in ItemList speichern
        List<dhbw.pojo.search.album.Item> itemList = albums.getItems();
        
        //durch itemList iterieren und für jedes Objekt Id, Name, Beschreibung und URL in SearchResultList speichern
        for (dhbw.pojo.search.album.Item item : itemList){ // Description wird erwartet, steht jedoch nicht im JSON
            result = new SearchResultList(item.getId(), item.getName(), "Hier könnte ihre Beschreibung stehen", item.getExternalUrls().getSpotify());
            resultList.add(result);
        }

        //aus query, ResultCategory und SearchResultList ein SearchResult Objekt erzeugen
        searchresult = new SearchResult(query, type, resultList);

        //SearchResult Objekt als Json mappen
        try {
            resultJson = mapper.writeValueAsString(searchresult);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
        return resultJson;  
    }
    
    public String getArtistJson(String query, String type, String json){
        
        SearchArtist artist = null;
        
        //Json in SearchArtist-Objekt mappen
        try {
            artist = mapper.readValue(json, SearchArtist.class);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        //SearchArtists enthält mehrere Artisten, diese in Artits-Objekt festhalten
        Artists artists = artist.getArtists();
        
        //Items der Klasse Artists in ItemList speichern
        List<dhbw.pojo.search.artist.Item> itemList = artists.getItems();
        
        //durch itemList iterieren und für jedes Objekt Id, Name, Beschreibung und URL in SearchResultList speichern
        for (dhbw.pojo.search.artist.Item item : itemList){ // Description wird erwartet, steht jedoch nicht im JSON
            result = new SearchResultList(item.getId(), item.getName(), "Hier könnte ihre Beschreibung stehen", item.getExternalUrls().getSpotify());
            resultList.add(result);
        }

        //aus query, ResultCategory und SearchResultList ein SearchResult Objekt erzeugen
        searchresult = new SearchResult(query, type, resultList);

        //SearchResult Objekt als Json mappen
        try {
            resultJson = mapper.writeValueAsString(searchresult);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
        return resultJson;
    }        
    
}
