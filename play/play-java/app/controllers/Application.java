package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.F.Function;
import play.libs.Json;
import play.libs.ws.WS;
import play.libs.ws.WSRequestHolder;
import play.libs.ws.WSResponse;
import play.mvc.Controller;
import play.mvc.Result;
import services.TorrentIOService;
import services.TorrentService;

public class Application extends Controller {

	private static final TorrentService service = new TorrentIOService();

	public static Result download(String name) {
        if(service.hasLocalFile(name)) {
            return ok(service.getLocalFile(name));
        }
        TorrentIOService.TorrentFile remoteFile = service.getRemoteFile(name);
        if(remoteFile == null) {
            return notFound(name);
        }
		return redirect(Json.toJson(remoteFile).toString());
	}

	public static Result getFiles() {
		return ok(Json.toJson(service.getLocalFiles()));
	}

	public static Result index(final String ip, final int port) {
		WSRequestHolder url = WS.url("http://" + ip + ":" + port + "/files");
		url.get().map(
				new Function<WSResponse, JsonNode>() {
					public JsonNode apply(WSResponse response) {
						JsonNode json = response.asJson();
						for(JsonNode node : json) {
							service.index(ip, port, node.asText());
						}
						return json;
					}
				});
		return ok();
	}
}
