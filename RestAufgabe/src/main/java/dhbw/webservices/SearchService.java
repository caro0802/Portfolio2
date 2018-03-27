/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dhbw.webservices;


import dhbw.spotify.*;
import java.util.*;
import org.springframework.web.bind.annotation.*;


@RestController
public class SearchService {
    
    @RequestMapping("/search")
    public String search(@RequestParam(value="query") String query, 
            @RequestParam(value="type") String type){
        
        //SpotifyRequest erstellen mit Typ SEARCH
        SpotifyRequest sr = new SpotifyRequest(RequestType.SEARCH);
        
        String json = null;
        String resultJson = null;
     
        //Typ der als String in Parameter steht in RequestCategory umwandeln
        RequestCategory category = RequestCategory.valueOf(type);
        
        
        //Methode performeRequestSearch der Klasse SpotifyRequest aufrufen
        //liefert Optional String zurück, deshalb mit Try-Catch Block umranden
        try{
            Optional <String> optional = sr.performeRequestSearch(category, query);
            if (optional.isPresent()){ 		//Prüfen, ob der String null ist 	
               json = optional.get(); //Auslesen des String im Optional
            }
        }
        catch (WrongRequestTypeException e){
            e.printStackTrace();    
        }
        
        //Suchanfrage an Klasse BuildOutputService weiterleiten
        BuildOutputService build = new BuildOutputService();
        resultJson = build.categoryFilter(query, category, json);
         
    return resultJson;
    }
}
