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
import dhbw.pojo.search.track.Item;
import dhbw.pojo.search.track.SearchTrack;
import dhbw.pojo.search.track.Tracks;
import dhbw.spotify.RequestCategory;
import static dhbw.spotify.RequestCategory.ALBUM;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static org.springframework.http.converter.json.Jackson2ObjectMapperBuilder.json;


public class BuildOutputService {
    
    //Methode, die je nach ResultCategory eine weitere, der Kategorie entsprechende Methode aufruft
    //Sie liefert einen Json-String an die Hauptmethode Search der Klasse SearchService zurück
    public String categoryFilter(String query, String type, String json){
       
        String resultJson = null;

        //nach Kategorie filtern und entsprechende Methode aufrufen
        switch(type){
            case "track":{
                resultJson = getTrackJson(query, json);
                break;      
            }
            case "album":{
                resultJson = getAlbumJson(query, json);
                break; 
            }   
            case "artist":{
                resultJson = getArtistJson(query, json);
                break;
            }           
        }       
        return resultJson;
    }
    
    
    public String getTrackJson(String query, String json){
           
        ObjectMapper mapper = new ObjectMapper();
       
        String resultJson = null;
        List<SearchResultList> result = new ArrayList<SearchResultList>();

        SearchResultList resultList = null;
        SearchResult rs = null;
        
        SearchTrack st = null;
        
        //Json in SearchTrack-Objekt mappen
        try {
            st = mapper.readValue(json, SearchTrack.class);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        //SearchTrack enthält mehrere Tracks, diese in Tracks-Objekt festhalten
        Tracks tracks = st.getTracks();
        
        //Items der Klasse Tracks in ItemList speichern
        List<dhbw.pojo.search.track.Item> itemList = tracks.getItems();
        
        //durch itemList iterieren und für jedes Objekt Id, Name, Beschreibung und URL in SearchResultList speichern
        for (dhbw.pojo.search.track.Item item : itemList){ // Description wird erwartet, steht jedoch nicht im JSON
            resultList = new SearchResultList(item.getId(), item.getName(), "Hier könnte ihre Beschreibung stehen", item.getExternalUrls().getSpotify());
            result.add(resultList);
        }

        //aus query, ResultCategory und SearchResultList ein SearchResult Objekt erzeugen
        rs = new SearchResult(query, "track", result);

        //SearchResult als Json mappen
        try {
            resultJson = mapper.writeValueAsString(rs);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
        return resultJson;
    }
    
    public String getAlbumJson(String query, String json){
           
        ObjectMapper mapper = new ObjectMapper();
       
        String resultJson = null;
        List<SearchResultList> result = new ArrayList<SearchResultList>();

        SearchResultList resultList = null;
        SearchResult rs = null;
        
        SearchAlbum sal = null;
        
        //Json in SearchAlbum-Objekt mappen
        try {
            sal = mapper.readValue(json, SearchAlbum.class);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        //SearchAlbum enthält mehrere Alben, diese in Albums-Objekt festhalten
        Albums albums = sal.getAlbums();
        
        //Items der Klasse Albums in ItemList speichern
        List<dhbw.pojo.search.album.Item> itemList = albums.getItems();
        
        //durch itemList iterieren und für jedes Objekt Id, Name, Beschreibung und URL in SearchResultList speichern
        for (dhbw.pojo.search.album.Item item : itemList){ // Description wird erwartet, steht jedoch nicht im JSON
            resultList = new SearchResultList(item.getId(), item.getName(), "Hier könnte ihre Beschreibung stehen", item.getExternalUrls().getSpotify());
            result.add(resultList);
        }

        //aus query, ResultCategory und SearchResultList ein SearchResult Objekt erzeugen
        rs = new SearchResult(query, "album", result);

        //SearchResult Objekt als Json mappen
        try {
            resultJson = mapper.writeValueAsString(rs);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
        return resultJson;  
    }
    
    public String getArtistJson(String query, String json){
            
        ObjectMapper mapper = new ObjectMapper();
       
        String resultJson = null;
        List<SearchResultList> result = new ArrayList<SearchResultList>();
        SearchResultList resultList = null;
        SearchResult rs = null;
        
        SearchArtist sar = null;
        
        //Json in SearchArtist-Objekt mappen
        try {
            sar = mapper.readValue(json, SearchArtist.class);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        //SearchArtists enthält mehrere Artisten, diese in Artits-Objekt festhalten
        Artists artists = sar.getArtists();
        
        //Items der Klasse Artists in ItemList speichern
        List<dhbw.pojo.search.artist.Item> itemList = artists.getItems();
        
        //durch itemList iterieren und für jedes Objekt Id, Name, Beschreibung und URL in SearchResultList speichern
        for (dhbw.pojo.search.artist.Item item : itemList){ // Description wird erwartet, steht jedoch nicht im JSON
            resultList = new SearchResultList(item.getId(), item.getName(), "Hier könnte ihre Beschreibung stehen", item.getExternalUrls().getSpotify());
            result.add(resultList);
        }

        //aus query, ResultCategory und SearchResultList ein SearchResult Objekt erzeugen
        rs = new SearchResult(query, "artist", result);

        //SearchResult Objekt als Json mappen
        try {
            resultJson = mapper.writeValueAsString(rs);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
        return resultJson;
    }        
    
}
