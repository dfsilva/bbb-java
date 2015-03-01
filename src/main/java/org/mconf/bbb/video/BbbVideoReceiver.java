/*
 * GT-Mconf: Multiconference system for interoperable web and mobile
 * http://www.inf.ufrgs.br/prav/gtmconf
 * PRAV Labs - UFRGS
 * 
 * This file is part of Mconf-Mobile.
 *
 * Mconf-Mobile is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Mconf-Mobile is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Mconf-Mobile.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.mconf.bbb.video;

import org.mconf.bbb.BigBlueButtonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flazr.rtmp.client.ClientOptions;
import com.flazr.rtmp.message.Video;
import com.flazr.util.Utils;

public class BbbVideoReceiver {

	protected class VideoConnection extends VideoReceiverConnection {

		public VideoConnection(ClientOptions options,
				BigBlueButtonClient context) {
			super(options, context);
		}

		@Override
		protected void onVideo(Video video) {
			BbbVideoReceiver.this.onVideo(video);
		}

	}

	private static final Logger log = LoggerFactory
			.getLogger(BbbVideoReceiver.class);

	private String streamName;
	private VideoConnection videoConnection;

	public BbbVideoReceiver(BigBlueButtonClient context, int streamToShow) {

		ClientOptions opt = new ClientOptions();
		opt.setClientVersionToUse(Utils.fromHex("00000000"));
		opt.setHost("192.168.1.3");
		opt.setAppName("live");

		streamName = "test";

		opt.setWriterToSave(null);
		opt.setStreamName(streamName);

		videoConnection = new VideoConnection(opt, context);
	}

	protected void onVideo(Video video) {
		log.debug("received video package: {}", video.getHeader().getTime());
	}

	public void start() {
		if (videoConnection != null)
			videoConnection.connect();
	}

	public void stop() {
		if (videoConnection != null)
			videoConnection.disconnect();
	}

	public String getStreamName() {
		return streamName;
	}

	public float getAspectRatio() {
		return getAspectRatio(streamName);
	}

	public static float getAspectRatio(String streamName) {
		/*
		 * if (streamName != null) {
		 * 
		 * Pattern streamNamePattern = Pattern
		 * .compile("(\\d+)[x](\\d+)[-]\\d+[-]\\d+"); Matcher matcher =
		 * streamNamePattern.matcher(streamName);
		 * 
		 * if (matcher.matches()) { String widthStr = matcher.group(1); String
		 * heightStr = matcher.group(2); int width = Integer.parseInt(widthStr);
		 * int height = Integer.parseInt(heightStr); return width / (float)
		 * height; }
		 * 
		 * streamNamePattern = Pattern.compile("(\\d+)[x](\\d+)([-]\\d+)?");
		 * matcher = streamNamePattern.matcher(streamName); if
		 * (matcher.matches()) { String widthStr = matcher.group(1); String
		 * heightAndId = matcher.group(2); String heightStr =
		 * heightAndId.substring(0, heightAndId.le); int width =
		 * Integer.parseInt(widthStr); int height = Integer.parseInt(heightStr);
		 * return width / (float) height; } }
		 */
		return -1;
	}
}
