/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dhbw.webservices;

import dhbw.pojo.detail.album.Tracks;
import dhbw.pojo.detail.track.DetailsTrack;
import dhbw.pojo.result.detail.DetailResult;
import dhbw.spotify.RequestCategory;
import dhbw.spotify.RequestType;
import dhbw.spotify.SpotifyRequest;
import dhbw.spotify.WrongRequestTypeException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dhbw.pojo.detail.album.DetailsAlbum;
import dhbw.pojo.detail.artist.DetailsArtist;
import org.springframework.web.bind.annotation.*;


//import org.springframework.web.bind.annotation.PathVariable;
//import static org.springframework.web.bind.annotation.RequestMethod.GET;

//import org.codehaus.jackson.map.ObjectMapper;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;



@RestController
public class DetailService {
    
    @RequestMapping(value="/detail/{id}")
    public String detail(@PathVariable("id") String id,
                         @RequestParam(value="type") String type) throws IOException{
        
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
                     try{
                    resultJson = mapper.writeValueAsString(dr);}
                    catch(JsonProcessingException ex)
                    {
                      ex.printStackTrace();
                    }   
                

                break;
                  
            }
            case ALBUM:{
                DetailsAlbum album = null;
                try {
                    album = mapper.readValue(json, DetailsAlbum.class);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                    DetailResult dr = new DetailResult(album.getName(), "Hier könnte ihre Info stehen.");
                     try{
                    resultJson = mapper.writeValueAsString(dr);}
                    catch(JsonProcessingException ex)
                    {
                      ex.printStackTrace();
                    }   
                     break;
            }
//         
           case ARTIST:{
             DetailsArtist artist = null;
                try {
                    artist = mapper.readValue(json, DetailsArtist.class);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                    DetailResult dr = new DetailResult(artist.getName(), "Hier könnte ihre Info stehen.");
                    try{
                    resultJson = mapper.writeValueAsString(dr);}
                    catch(JsonProcessingException ex)
                    {
                      ex.printStackTrace();
                    }    
                    break;
           }
                    
        }
    System.out.println("Hallo");            
    return resultJson;
    }
}