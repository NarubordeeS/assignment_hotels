package com.hotels.repo;

import com.hotels.model.HotelsModel;
import com.hotels.model.MembersModel;
import com.hotels.utility.CSVHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by narubordeesarnsuwan on 4/23/2016 AD.
 */
@Component
public class MembersRepo {

    private List<MembersModel> inMemoryDB = null;

    final Path path = Paths.get(getClass().getResource("/file/").getPath() + "memberdb.csv");

    @Autowired
    CSVHelper csvHelper;

    @PostConstruct
    private void InitDB() throws FileNotFoundException {
        if (inMemoryDB == null) {
            inMemoryDB = connectToDB();
        }
    }

    public MembersRepo(){

    }

    public MembersRepo(CSVHelper csvHelper) throws FileNotFoundException {
        this.csvHelper = csvHelper;
        InitDB();
    }


    public List<MembersModel> parseMember() throws Exception {
        List<MembersModel> rows = csvHelper.parseCSV(path.toString()).stream()
                .map(row -> {
                    MembersModel membersModel = new MembersModel();
                    membersModel.setName (row[0]);
                    membersModel.setLimit(new Integer(row[1]));
                    return membersModel;
                }).collect(Collectors.toList());
        return rows;
    }

    private List<MembersModel> connectToDB() throws FileNotFoundException {
        List<MembersModel> hotelsDB = null;

        try {
            hotelsDB = parseMember();
        }
        catch (FileNotFoundException e) {
           throw e;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return hotelsDB;
    }

    public List<MembersModel> findAll() {
        return inMemoryDB;
    }


    public List<MembersModel> findByApiKey(String key) {
        return inMemoryDB.stream().filter(
                row -> row.getName().equals(key))
                .collect(Collectors.toList());
    }
}