package com.hotels.repo;

import com.hotels.model.HotelsModel;
import com.hotels.utility.CSVHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
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
        if (!Optional.ofNullable(this.inMemoryDB).isPresent()) {
            this.inMemoryDB = connectToDB();
        }
    };

    private List<HotelsModel> parseHotels() throws Exception {
        List<HotelsModel> rows = this.csvHelper.parseCSV(this.path.toString()).stream()
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


    public List<HotelsModel> findHotelByHotelIdAndCityName(Integer hotelId, String cityName, String sortKey, String direction) {
        List<HotelsModel> result = null;

        if (Optional.ofNullable(hotelId).isPresent()
                && !cityName.isEmpty()){
            result = this.inMemoryDB.stream().filter(
                    row -> (row.getHotelId().intValue() == hotelId.intValue()
                    && row.getCity().equals(cityName)))
                    .collect(Collectors.toList());
        }
        else if (!Optional.ofNullable(hotelId).isPresent()
                && !cityName.isEmpty()){
            result = this.inMemoryDB.stream().filter(
                    row -> (row.getCity().equals(cityName)))
                    .collect(Collectors.toList());
        }
        else if (Optional.ofNullable(hotelId).isPresent()
                && cityName.isEmpty()) {
            result = this.inMemoryDB.stream().filter(
                    row -> (row.getHotelId().intValue() == hotelId.intValue()))
                    .collect(Collectors.toList());
        }
        else {
            result = this.inMemoryDB;
        }

        if (Optional.ofNullable(sortKey).isPresent()
                && !sortKey.isEmpty()) {
            return this.sort(result, sortKey, direction);
        } else {
            return result;
        }


    }

    public List<HotelsModel> findAll(String sortKey,String direction) {
        return this.sort(this.inMemoryDB,sortKey,direction);
    }

    public List<HotelsModel> sort(List<HotelsModel> raw,String sortkey, String direction) {
        if ("price".equalsIgnoreCase(sortkey)) {
            if ("desc".equalsIgnoreCase(direction)) {
                return raw.stream().sorted((h1,h2) ->
                        Double.compare(h2.getPrice(),h1.getPrice()))
                        .collect(Collectors.toList());
            } else {
                return raw.stream().sorted((h1,h2) ->
                        Double.compare(h1.getPrice(),h2.getPrice()))
                        .collect(Collectors.toList());
            }
        }
        return raw;
    }



}