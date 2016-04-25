package com.hotels.repo;

import com.hotels.model.HotelsModel;
import com.hotels.utility.CSVHelper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by narubordeesarnsuwan on 4/24/2016 AD.
 */
public class HotelsRepoTest {
    @Mock
    CSVHelper csvHelper;

    @Autowired
    HotelsRepo hotelsRepo;

    @Before
    public void setUp() throws FileNotFoundException {
        MockitoAnnotations.initMocks(this);

        List<String[]> hotelCsv = new ArrayList();
        String[] row1 = new String[4];
        row1[0]= "Bangkok";
        row1[1]= "1";
        row1[2]= "Deluxe";
        row1[3]= "100";

        String[] row2 = new String[4];
        row2[0]= "Tokyo";
        row2[1]= "2";
        row2[2]= "Superior";
        row2[3]= "900";

        String[] row3 = new String[4];
        row3[0]= "Hongkong";
        row3[1]= "3";
        row3[2]= "Single";
        row3[3]= "250";

        hotelCsv.add(row1);
        hotelCsv.add(row2);
        hotelCsv.add(row3);

        when(csvHelper.parseCSV(anyString())).thenReturn(hotelCsv);

        hotelsRepo = new HotelsRepo(csvHelper);


    }

    @Test
    public void shouldfindHotelByIdCorrectly() throws FileNotFoundException {
        List<HotelsModel> results = hotelsRepo.findHotelById(1);
        assertNotNull(results);
        HotelsModel result = results.get(0);
        assertEquals("Bangkok",result.getCity());
        assertEquals(1,result.getHotelId().intValue());
        assertEquals("Deluxe",result.getRoom());
        assertEquals(new Double("100"),result.getPrice());
    }

    @Test
    public void shouldFindAllWithEmptyFilterCorrectly() throws FileNotFoundException {
        List<HotelsModel> results = hotelsRepo.findAll("","");
        assertNotNull(results);
        assertEquals(3,results.size());
        assertEquals("Bangkok",results.get(0).getCity());
        assertEquals("Tokyo",results.get(1).getCity());
        assertEquals("Hongkong",results.get(2).getCity());
    }

    @Test
    public void shouldFindAllWithSortingPriceCorrectly() throws FileNotFoundException {
        List<HotelsModel> results = hotelsRepo.findAll("price","");
        assertNotNull(results);
        assertEquals(3,results.size());
        assertEquals("Bangkok",results.get(0).getCity());
        assertEquals("Hongkong",results.get(1).getCity());
        assertEquals("Tokyo",results.get(2).getCity());
    }

    @Test
    public void shouldFindAllWithSortingAscendingPriceCorrectly() throws FileNotFoundException {
        List<HotelsModel> results = hotelsRepo.findAll("price","asc");
        assertNotNull(results);
        assertEquals(3,results.size());
        assertEquals("Bangkok",results.get(0).getCity());
        assertEquals("Hongkong",results.get(1).getCity());
        assertEquals("Tokyo",results.get(2).getCity());
    }

    @Test
    public void shouldFindAllWithSortingDescendingPriceCorrectly() throws FileNotFoundException {
        List<HotelsModel> results = hotelsRepo.findAll("price","desc");
        assertNotNull(results);
        assertEquals(3,results.size());
        assertEquals("Tokyo",results.get(0).getCity());
        assertEquals("Hongkong",results.get(1).getCity());
        assertEquals("Bangkok",results.get(2).getCity());
    }
}
