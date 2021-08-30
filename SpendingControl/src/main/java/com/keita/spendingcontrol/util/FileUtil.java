package com.keita.spendingcontrol.util;

import java.io.FileInputStream;
import java.io.IOException;

public abstract class FileUtil {

    public static byte[] setDefaultProfilePicture() throws IOException {
        FileInputStream fileInputStream = new FileInputStream("./docs/noUser.jpg");
        return fileInputStream.readAllBytes();
    }
}
