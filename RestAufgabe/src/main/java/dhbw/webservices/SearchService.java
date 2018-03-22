/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dhbw.webservices;

import com.mashape.unirest.http.ObjectMapper;
import dhbw.spotify.RequestCategory;
import dhbw.spotify.RequestType;
import dhbw.spotify.SpotifyRequest;
import dhbw.spotify.WrongRequestTypeException;
import java.util.Optional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class SearchService {
    
    @RequestMapping("/search")
    public String search(@RequestParam(value="query") String query, 
            @RequestParam(value="type") String type){
        
        String hallo ="hallo";
//        SpotifyRequest sr = new SpotifyRequest(requestType.SEARCH);
//        try{
//            Optional <String> optional = sr.performeRequestSearch(requestCategory, query);
//        }
//        catch (WrongRequestTypeException e){
//            e.printStackTrace();    
//        }
//        ObjectMapper mapper = new ObjectMapper();
//        
//        switch(requestCategory){
//            case "TRACK":
//                break;
//            case "ALBUM":
//                break;
//            case "ARTIST":
//                break;
//        }

        System.out.println("Hallo Welt");
        
        return hallo;
    }

}
