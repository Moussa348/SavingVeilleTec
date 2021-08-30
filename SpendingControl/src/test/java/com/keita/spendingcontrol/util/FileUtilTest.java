package com.keita.spendingcontrol.util;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class FileUtilTest {

    @Test
    void setDefaultProfilePicture() throws IOException {
        //ACT
        byte[] pictureInBytes = FileUtil.setDefaultProfilePicture();

        //ASSERT
        assertNotNull(pictureInBytes);
    }

    @Test
    void isFileAnImage(){
        //ARRANGE
        String fileName1 = "dsadasdasd.jpg";
        String fileName2 = "dasdasd.py";

        //ACT
        boolean fileIsAnImage = FileUtil.isFileAnImage(fileName1);
        boolean fileIsNotAnImage = FileUtil.isFileAnImage(fileName2);

        //ASSERT
        assertTrue(fileIsAnImage);
        assertFalse(fileIsNotAnImage);
    }
}
