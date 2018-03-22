/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dhbw.webservices;

import dhbw.pojo.detail.album.Tracks;
import dhbw.pojo.detail.track.DetailsTrack;
import dhbw.pojo.result.detail.DetailResult;
import dhbw.pojo.result.search.SearchResult;
import dhbw.pojo.result.search.SearchResultList;
import dhbw.spotify.RequestCategory;
import dhbw.spotify.RequestType;
import dhbw.spotify.SpotifyRequest;
import dhbw.spotify.WrongRequestTypeException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.websocket.server.PathParam;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class DetailService {
    
    @RequestMapping("/detail/{id}")
    public String detail(@PathParam("id") String id,
                         @RequestParam(value="type") String type) throws IOException{
        System.out.println("Hallo");
        
        SpotifyRequest sr = new SpotifyRequest(RequestType.DETAIL);
        
        String json = null;
        
        RequestCategory category = RequestCategory.valueOf(type);
        
        try{
            Optional <String> optional = sr.performeRequestDetail(category, id);
            if (optional.isPresent()){ 		//Prüfen, ob der String null ist 	
               json = optional.get(); //Auslesen des String im Optional
            }
        }
        catch (WrongRequestTypeException e){
            e.printStackTrace();    
        }
        
       ObjectMapper mapper = new ObjectMapper();
       
       String resultJson = null;

        
        switch(category){
            case TRACK:{
                DetailsTrack dt = null;
                try {
                    dt = mapper.readValue(json, DetailsTrack.class);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                
                DetailResult dr = new DetailResult(dt.getName(), "Hier könnte ihre Info stehen.");
                
                    resultJson = mapper.writeValueAsString(dr);
                
                /*
                Tracks tracks = dt.getTracks();
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
                }*/
                break;
                  
            }
//            case ALBUM:{
//                SearchAlbum sal = null;
//                try {
//                    sal = mapper.readValue(json, SearchAlbum.class);
//                } catch (IOException ex) {
//                  ex.printStackTrace();
//                }
//                Albums albums = sal.getAlbums();
//                List<dhbw.pojo.search.album.Item> itemList = albums.getItems();
//                for (dhbw.pojo.search.album.Item item : itemList){ // Description wird erwartet, steht jedoch nicht im JSON
//                    resultList = new SearchResultList(item.getId(), item.getName(), "Hier könnte ihre Beschreibung stehen", item.getExternalUrls().getSpotify());
//                    result.add(resultList);
//                }
//
//                rs = new SearchResult(query, type, result);
//
//                try {
//                    resultJson = mapper.writeValueAsString(rs);
//                } catch (JsonProcessingException ex) {
//                    ex.printStackTrace();
//                }
//                break; 
//            }
//                
//            case ARTIST:{
//                SearchArtist sar = null;
//                try {
//                    sar = mapper.readValue(json, SearchArtist.class);
//                } catch (IOException ex) {
//                  ex.printStackTrace();
//                }
//                Artists artists = sar.getArtists();
//                List<dhbw.pojo.search.artist.Item> itemList = artists.getItems();
//                for (dhbw.pojo.search.artist.Item item : itemList){ // Description wird erwartet, steht jedoch nicht im JSON
//                    resultList = new SearchResultList(item.getId(), item.getName(), "Hier könnte ihre Beschreibung stehen", item.getExternalUrls().getSpotify());
//                    result.add(resultList);
//                }
//
//                rs = new SearchResult(query, type, result);
//
//                try {
//                    resultJson = mapper.writeValueAsString(rs);
//                } catch (JsonProcessingException ex) {
//                    ex.printStackTrace();
//                }
//            }
                    
        }
                
    return resultJson;
    }
}