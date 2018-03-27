/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dhbw.webservices;

import dhbw.pojo.detail.track.DetailsTrack;
import dhbw.pojo.result.detail.DetailResult;
import dhbw.spotify.RequestCategory;
import dhbw.spotify.RequestType;
import dhbw.spotify.SpotifyRequest;
import dhbw.spotify.WrongRequestTypeException;
import java.io.IOException;
import java.util.Optional;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dhbw.pojo.detail.album.DetailsAlbum;
import dhbw.pojo.detail.artist.DetailsArtist;
import org.springframework.web.bind.annotation.*;


@RestController
public class DetailService {
    
     //liefert einen Json-String zurück
    @RequestMapping(value="/detail/{id}")
    public String detail(@PathVariable("id") String id,
                         @RequestParam(value="type") String type) throws IOException{
        
        //SpotifyRequest erstellen mit Typ DETAIL
        SpotifyRequest sr = new SpotifyRequest(RequestType.DETAIL);
        
        String json = null;
        
        //Typ der als String in Parameter steht in RequestCategory umwandeln
        RequestCategory category = RequestCategory.valueOf(type);
        
        //Methode performeRequestSearch der Klasse SpotifyRequest aufrufen
        //liefert Optional String zurück, deshalb mit Try-Catch Block umranden
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
       
         //gibt je nach ResultCategory eine andere, der Kategorie entsprechende Ausgabe aus
        switch(category){
            case TRACK:{
                DetailsTrack dt = null;
                //Json in DetailsTrack-Objekt mappen
                try {
                    dt = mapper.readValue(json, DetailsTrack.class);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                    //aus name und info DetailResult Objekt erzeugen
                    DetailResult dr = new DetailResult(dt.getName(), "Hier könnte ihre Info stehen.");
                    
                    //DetailResult als Json mappen
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
                //Json in DetailsAlbum-Objekt mappen
                try {
                    album = mapper.readValue(json, DetailsAlbum.class);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                    //aus name und info DetailResult Objekt erzeugen
                    DetailResult dr = new DetailResult(album.getName(), "Hier könnte ihre Info stehen.");
                    
                    //DetailResult als Json mappen
                    try{
                    resultJson = mapper.writeValueAsString(dr);}
                    catch(JsonProcessingException ex)
                    {
                      ex.printStackTrace();
                    }   
                     break;
            }
        
           case ARTIST:{
             DetailsArtist artist = null;
             //Json in DetailsArtist-Objekt mappen
                try {
                    artist = mapper.readValue(json, DetailsArtist.class);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                    //aus name und info DetailResult Objekt erzeugen
                    DetailResult dr = new DetailResult(artist.getName(), "Hier könnte ihre Info stehen.");
                    
                    //DetailResult als Json mappen
                    try{
                    resultJson = mapper.writeValueAsString(dr);}
                    catch(JsonProcessingException ex)
                    {
                      ex.printStackTrace();
                    }    
                    break;
           }
                    
        }            
    return resultJson;
    }
}