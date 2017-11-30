package com.mqunar.jsonsnow.issue;

import com.mqunar.jonsnow.service.gitlab.FileService;
import com.mqunar.jonsnow.net.Network;
import com.mqunar.jonsnow.utils.UrlUtils;
import org.junit.Test;

/**
 * Created by ironman.li on 2016/7/28.
 */
public class FileServiceTest {

    @Test
    public void testGetFile() {
        Network network = new Network();
        FileService fileService = new FileService(network, String.valueOf(UrlUtils.ADR_ATOM_FLIGHT_PROJECT_ID));
//        String filePath = "SRC/app/src/main/java/com/mqunar/atom/flight/activity/inland/FlightCityActivity.java";
        String filePath = "SRC/app/src/main/java/com/mqunar/atom/flight/activity/FlightListBaseActivity.java";
        fileService.getFileFromRepository(filePath);
    }
}
