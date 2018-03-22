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
import dhbw.spotify.RequestType;
import dhbw.spotify.SpotifyRequest;
import dhbw.spotify.WrongRequestTypeException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.springframework.http.converter.json.Jackson2ObjectMapperBuilder.json;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class SearchService {
    
    @RequestMapping("/search")
    public String search(@RequestParam(value="query") String query, 
            @RequestParam(value="type") String type){
        
        SpotifyRequest sr = new SpotifyRequest(RequestType.SEARCH);
        
        String json = null;
        
        RequestCategory category = RequestCategory.valueOf(type);
        
        try{
            Optional <String> optional = sr.performeRequestSearch(category, query);
            if (optional.isPresent()){ 		//Prüfen, ob der String null ist 	
               json = optional.get(); //Auslesen des String im Optional
            }
        }
        catch (WrongRequestTypeException e){
            e.printStackTrace();    
        }
        
       ObjectMapper mapper = new ObjectMapper();
       
       String resultJson = null;
       List<SearchResultList> result = new ArrayList<SearchResultList>();

       SearchResultList resultList = null;
       SearchResult rs = null;
        
        switch(category){
            case TRACK:{
                SearchTrack st = null;
                try {
                    st = mapper.readValue(json, SearchTrack.class);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                Tracks tracks = st.getTracks();
                List<Item> itemList = tracks.getItems();
                for (Item item : itemList){ // Description wird erwartet, steht jedoch nicht im JSON
                    resultList = new SearchResultList(item.getId(), item.getName(), "Hier könnte ihre Beschreibung stehen", item.getExternalUrls().getSpotify());
                    result.add(resultList);
                }

                rs = new SearchResult(query, type, result);

                try {
                    resultJson = mapper.writeValueAsString(rs);
                } catch (JsonProcessingException ex) {
                    ex.printStackTrace();
                }
                break;
                  
            }
            case ALBUM:{
                SearchAlbum sal = null;
                try {
                    sal = mapper.readValue(json, SearchAlbum.class);
                } catch (IOException ex) {
                  ex.printStackTrace();
                }
                Albums albums = sal.getAlbums();
                List<dhbw.pojo.search.album.Item> itemList = albums.getItems();
                for (dhbw.pojo.search.album.Item item : itemList){ // Description wird erwartet, steht jedoch nicht im JSON
                    resultList = new SearchResultList(item.getId(), item.getName(), "Hier könnte ihre Beschreibung stehen", item.getExternalUrls().getSpotify());
                    result.add(resultList);
                }

                rs = new SearchResult(query, type, result);

                try {
                    resultJson = mapper.writeValueAsString(rs);
                } catch (JsonProcessingException ex) {
                    ex.printStackTrace();
                }
                break; 
            }
                
            case ARTIST:{
                SearchArtist sar = null;
                try {
                    sar = mapper.readValue(json, SearchArtist.class);
                } catch (IOException ex) {
                  ex.printStackTrace();
                }
                Artists artists = sar.getArtists();
                List<dhbw.pojo.search.artist.Item> itemList = artists.getItems();
                for (dhbw.pojo.search.artist.Item item : itemList){ // Description wird erwartet, steht jedoch nicht im JSON
                    resultList = new SearchResultList(item.getId(), item.getName(), "Hier könnte ihre Beschreibung stehen", item.getExternalUrls().getSpotify());
                    result.add(resultList);
                }

                rs = new SearchResult(query, type, result);

                try {
                    resultJson = mapper.writeValueAsString(rs);
                } catch (JsonProcessingException ex) {
                    ex.printStackTrace();
                }
            }
                    
        }
                
    return resultJson;
    }
}
