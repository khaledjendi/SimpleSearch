/**
 * @author Khaled Jendi
 */

package com.texty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class SearchImplTest {
    public SearchImplTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of setWordsToMatch method, of class SearchImpl.
     */
    @Test
    public void testSetWordsToMatch() {
        System.out.println("setWordsToMatch");
        String _words = "tech system computer software";
        SearchImpl instance = new SearchImpl();
        instance.setWordsToMatch(_words);
        List<String> actual = instance.getWords();
        List<String> expected = Arrays.asList("tech", "system", "computer", "software");

        assertThat(actual, is(expected));
    }

    /**
     * Test of setTxtFiles method, of class SearchImpl.
     */
    @Test
    public void testSetTxtFiles() {
        System.out.println("setTxtFiles");
        List<TxtFile> txtFiles = new ArrayList<TxtFile>() {
            {
                add(new TxtFile("file1.txt", "to be or not to be"));
                add(new TxtFile("file2.txt", "hi how are you?"));
            }
        };
        SearchImpl instance = new SearchImpl();
        instance.setTxtFiles(txtFiles);
        List<TxtFile> actual = instance.getTxtFiles();
        List<TxtFile> expected = new ArrayList<TxtFile>() {
            {
                add(new TxtFile("file1.txt", "to be or not to be"));
                add(new TxtFile("file2.txt", "hi how are you?"));
            }
        };

        for (int i = 0; i < actual.size(); i++) {
            assertThat(actual.get(i).getTxtFileName(), is(expected.get(i).getTxtFileName()));
            assertThat(actual.get(i).getTxtFileContent(), is(expected.get(i).getTxtFileContent()));
            assertThat(actual.get(i).getScore(), is(expected.get(i).getScore()));
        }
    }

    /**
     * Test of searchWords method, of class SearchImpl.
     */
    @Test
    public void testSearchWords() {
        System.out.println("searchWords");
        SearchImpl instance = new SearchImpl();

        List<TxtFile> txtFiles = new ArrayList<TxtFile>() {
            {
                add(new TxtFile("file1.txt", "to be or not to be"));
                add(new TxtFile("file2.txt", "hi how are you?"));
                add(new TxtFile("file3.txt", "xx yy zz is this a sentence ?"));
                add(new TxtFile("file4.txt", "I love tech ! and computer"));
                add(new TxtFile("file5.txt", "don't leave me but leave your computer"));
                add(new TxtFile("file6.txt", "I like software engineer but not the computer"));
            }
        };
        String _words = "tech system computer software";
        instance.setWordsToMatch(_words);
        instance.setTxtFiles(txtFiles);
        instance.searchWords();
    }
}
