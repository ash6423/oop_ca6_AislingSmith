/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oop_ca6_aislingsmith.opp_ca5_aislingsmith;

import oop_ca6_aislingsmith.DTOs.TollEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class opp_ca5_aislingsmith { 
   
    public static void main(String[] args) {
        Set<String> set;
        ArrayList<String> invalidRegistrationsList = new ArrayList<>();
        set = load("vehicles.csv");

        HashMap<String, ArrayList<TollEvent>> map = new HashMap<>();

        TollEvent event = new TollEvent("201LH309", 222222, 1562537493);

        if (set.contains(event.getRegistration())) {
            System.out.println("Is valid ");
             // then process the TollEvent object
            // i.e. write TollEvent object to a 
            // map<String(registration) ,List of TollEvents (ArrayList)>

             // map( KEy , VALUE );
            if (map.get(event.getRegistration()) == null) // it's not there
            {
                ArrayList<TollEvent> list = new ArrayList<>();
                list.add(event);
                map.put(event.getRegistration(), list);
            } else // reg is already there
            {
                ArrayList<TollEvent> list = map.get(event.getRegistration());
                list.add(event);  // adds to ArrayList in the map

            }

            System.out.println("from map:" + map.get("201LH309"));

        } else {
            System.out.println("Is NOT a valid Registration");
            // add to a list of Invalid registrations
            invalidRegistrationsList.add(event.getRegistration());
        }

        System.out.println("List of invalid registrations:"
                + invalidRegistrationsList);

    }

    public static Set load(String fileName) {
        System.out.println("Reading from " + fileName);

        Set set = new HashSet<String>();

        try {
            Scanner sc = new Scanner(new File(fileName));
            // default delimeter is whitespace and newlines
            while (sc.hasNext()) {
                String reg = sc.next();
                set.add(reg);
                System.out.println(reg);
            }
            sc.close();

        } catch (IOException e) {
        }

        return set; // of valid registrations
    }
}


