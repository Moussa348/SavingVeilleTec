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
}
