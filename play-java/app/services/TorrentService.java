package services;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by olivier on 2015-01-19.
 * <olivier.pitton@kubity.com>
 */
public interface TorrentService {

    List<String> getLocalFiles();
    
    void index(String ip, int port, String name);

    boolean hasLocalFile(String name);

    File getLocalFile(String name);

    TorrentIOService.TorrentFile getRemoteFile(String name);
}
