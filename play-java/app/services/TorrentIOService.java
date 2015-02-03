package services;

import java.io.File;
import java.io.IOException;
import java.util.*;

import play.Configuration;

/**
 * Created by olivier on 2015-01-19.
 * <olivier.pitton@kubity.com>
 */
public class TorrentIOService implements TorrentService {

    private String path;
    private Map<String, TorrentFile> redirects = new HashMap<>();

    public TorrentIOService() {
        path = Configuration.root().getString("app.download.folder");
    }


    @Override
    public List<String> getLocalFiles() {
        String[] children = new File(path).list();
        return Arrays.asList(children);
    }

    @Override
    public boolean hasLocalFile(String name) {
        return getLocalFiles().contains(name);
    }

    @Override
    public TorrentFile getRemoteFile(String name) {
        return redirects.get(name);
    }

    @Override
    public File getLocalFile(String name) {
        return new File(path + File.separator + name);
    }

    @Override
    public void index(String ip, int port, String name) {
        redirects.put(name, new TorrentFile(ip, name, port));
    }

    static public class TorrentFile {
        public String ip, name;
        public int port;

        public TorrentFile(String ip, String name, int port) {
            this.ip = ip;
            this.name = name;
            this.port = port;
        }


    }
}
