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
        
        SpotifyRequest sr = new SpotifyRequest(RequestType.SEARCH);
        
        String json = null;
        String resultJson = null;
     
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
        
        BuildOutputService build = new BuildOutputService();
        resultJson = build.categoryFilter(query, category, json);
                   
    return resultJson;
    }
}
