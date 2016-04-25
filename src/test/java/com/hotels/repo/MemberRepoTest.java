package com.hotels.repo;

import com.hotels.model.HotelsModel;
import com.hotels.model.MembersModel;
import com.hotels.utility.CSVHelper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by narubordeesarnsuwan on 4/24/2016 AD.
 */
public class MemberRepoTest {
    @Mock
    CSVHelper csvHelper;

    @Autowired
    MembersRepo membersRepo;

    @Before
    public void setUp() throws FileNotFoundException {
        MockitoAnnotations.initMocks(this);

        List<String[]> memberCsv = new ArrayList();
        String[] row1 = new String[2];
        row1[0]= "AAAAA";
        row1[1]= "1";

        String[] row2 = new String[2];
        row2[0]= "BBBBB";
        row2[1]= "2";

        String[] row3 = new String[2];
        row3[0]= "CCCCC";
        row3[1]= "5";

        memberCsv.add(row1);
        memberCsv.add(row2);
        memberCsv.add(row3);

        when(csvHelper.parseCSV(anyString())).thenReturn(memberCsv);

        membersRepo = new MembersRepo(csvHelper);


    }

    @Test
    public void shouldFindAllCorrectly() throws FileNotFoundException {
        List<MembersModel> results = membersRepo.findAll();
        assertNotNull(results);
        assertEquals(3,results.size());
        assertEquals("AAAAA",results.get(0).getName());
        assertEquals(1,results.get(0).getLimit().intValue());
        assertEquals("BBBBB",results.get(1).getName());
        assertEquals(2,results.get(1).getLimit().intValue());
        assertEquals("CCCCC",results.get(2).getName());
        assertEquals(5,results.get(2).getLimit().intValue());
    }

}
