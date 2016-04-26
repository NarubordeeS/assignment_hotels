package com.hotels.repo;

import com.hotels.model.HotelsModel;
import com.hotels.model.MembersModel;
import com.hotels.utility.CSVHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.validation.constraints.Null;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by narubordeesarnsuwan on 4/23/2016 AD.
 */
@Component
public class HotelsRepo {

    public HotelsRepo(){

    }

    public HotelsRepo(CSVHelper csvHelper){
        this.csvHelper = csvHelper;
        InitDB();
    }

    Path path = Paths.get(getClass().getResource("/file/").getPath() + "hoteldb.csv");

    private List<HotelsModel> inMemoryDB = null;

    @Autowired
    CSVHelper csvHelper;

    @PostConstruct
    private void InitDB() {
        if (inMemoryDB == null) {
            inMemoryDB = connectToDB();
        }
    };

    private List<HotelsModel> parseHotels() throws Exception {
        List<HotelsModel> rows = csvHelper.parseCSV(path.toString()).stream()
                .map(row -> {
                    HotelsModel hotelsModel = new HotelsModel();
                    hotelsModel.setCity(row[0]);
                    hotelsModel.setHotelId(new Integer(row[1]));
                    hotelsModel.setRoom(row[2]);
                    hotelsModel.setPrice(new Double(row[3]));
                    return hotelsModel;
                }).collect(Collectors.toList());
        return rows;
    }

    private List<HotelsModel> connectToDB()
    {
        List<HotelsModel> hotelsDB = null;

        try {
            hotelsDB = parseHotels();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return hotelsDB;

    }

//    public List<HotelsModel> findHotelById(Integer hotelId) {
//        return inMemoryDB.stream().filter(
//                row -> row.getHotelId().intValue() == hotelId.intValue())
//                .collect(Collectors.toList());
//    }

    public List<HotelsModel> findHotelByHotelIdAndCityName(Integer hotelId, String cityName) {
        if (hotelId != null && !cityName.isEmpty()){
            return inMemoryDB.stream().filter(
                    row -> (row.getHotelId().intValue() == hotelId.intValue()
                    && row.getCity().equals(cityName)))
                    .collect(Collectors.toList());
        }
        else if (hotelId == null && !cityName.isEmpty()){
            return inMemoryDB.stream().filter(
                    row -> (row.getCity().equals(cityName)))
                    .collect(Collectors.toList());
        }
        else if (cityName.isEmpty() && hotelId != null) {
            return inMemoryDB.stream().filter(
                    row -> (row.getHotelId().intValue() == hotelId.intValue()))
                    .collect(Collectors.toList());
        }
        else {
            return findAll("","");
        }
    }

    public List<HotelsModel> findAll(String sortKey,String direction) {
        if ("price".equalsIgnoreCase(sortKey)) {
            if ("desc".equalsIgnoreCase(direction)) {
                return inMemoryDB.stream().sorted((h1,h2) ->
                        Double.compare(h2.getPrice(),h1.getPrice()))
                        .collect(Collectors.toList());
            } else {
                return inMemoryDB.stream().sorted((h1,h2) ->
                        Double.compare(h1.getPrice(),h2.getPrice()))
                        .collect(Collectors.toList());
            }
        }
        return inMemoryDB;
    }



}