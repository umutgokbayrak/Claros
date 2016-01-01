/**
  * mp3playa - Streaming Flash Mp3 player
  *
  * Copyright (C) 2004,2005  Troels Knak-Nielsen <troels@kyberfabrikken.dk>
  * 
  * This library is free software; you can redistribute it and/or
  * modify it under the terms of the GNU Lesser General Public
  * License as published by the Free Software Foundation;
  * version 2.1 of the License.
  * 
  * This library is distributed in the hope that it will be useful,
  * but WITHOUT ANY WARRANTY; without even the implied warranty of
  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
  * Lesser General Public License for more details.
  * 
  * You should have received a copy of the GNU Lesser General Public
  * License along with this library; if not, write to the Free Software
  * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
  *
  * @version 1.5
  *
  */

var Playa2 = {};

// constants
Playa2.VERSION = "1.5";
Playa2.STATE_STOPPED = 0;
Playa2.STATE_PLAYING = 1;

// private
Playa2.__isStarted = false;

// events
Playa2.onPlayStop = function() {};
Playa2.onPlayStart = function() {};
Playa2.onStateChange = function() {};

// public properties
Playa2.title = "";
Playa2.playListPosition = -1;
Playa2.playlistSize = -1;
Playa2.path = "";
Playa2.state = Playa2.STATE_STOPPED;
Playa2.btnState = "Loading";

/**
  * Internal function to retrieve player-object
  * @private
  */
Playa2.__getPlaya2 = function() {
	if (navigator.appName.indexOf ("Microsoft") !=-1) {
		var p = window["playa2"];
	} else {
		var p = document["playa2"];
	}
	if (typeof(p) != "undefined" ) {
		try {
			if (p.PercentLoaded() == 100) {
				return p;
			}
		} catch (e) {
//			alert(e.message);
		};
	}
	return null;
};

Playa2.setSong == function(song) {
	var p = Playa2.__getPlaya2();
	p.SetVariable("Response_path", "resources/newmsg.xml");
};

/**
  * This is the main loop
  * @private
  */
Playa2.poll = function() {
	var p = Playa2.__getPlaya2();
	setTimeout("Playa2.poll()", 200);
	if (p != null) {
		var previousState = Playa2.state;
		var previousPath = Playa2.path;

		Playa2.title = p.GetVariable("Response_title");
		Playa2.playListPosition = parseInt(p.GetVariable("Response_playListPosition"));
		Playa2.path = p.GetVariable("Response_path");
		Playa2.btnState = p.GetVariable("Response_btnState");
		Playa2.playlistSize = parseInt(p.GetVariable("Response_playlistSize"));

		// update state
		if (Playa2.btnState == "Play") {
			Playa2.state = Playa2.STATE_STOPPED;
		} else if (Playa2.btnState == "Stop") {
			Playa2.state = Playa2.STATE_PLAYING;
		}

		// trigger events
		if (Playa2.state != previousState || previousPath != Playa2.path) {
			if (Playa2.state == Playa2.STATE_STOPPED) {
				Playa2.onPlayStop();
			} else {
				Playa2.onPlayStart();
			}
			Playa2.onStateChange();
		}
	}
};

/**
  * Starts the playa
  * @public
  */
Playa2.start = function() {
	if (Playa2.__isStarted) {
		return;
	}
	Playa2.poll();
	Playa2.__isStarted = true;
};

/**
  * Fire this event when user press the play/stop button
  * Toggles between playing/stopped state.
  * @public
  */
Playa2.doPlayStop = function() {
	var p = Playa2.__getPlaya2();
	if (p != null) {
		p.SetVariable("Request_command", "PlayStop");
	}
};

/**
  * Starts playing, if the playback is stopped
  * @public
  */
Playa2.doPlay = function() {
	var p = Playa2.__getPlaya2();
	if (p != null) {
		p.SetVariable("Request_command", "Play");
	}
};

/**
  * Stops playback if it's playing
  * @public
  */
Playa2.doStop = function() {
	var p = Playa2.__getPlaya2();
	if (p != null) {
		p.SetVariable("Request_command", "Stop");
	}
};

/**
  * Fire this event when user press the next button
  * @public
  */
Playa2.doNext = function() {
	var p = Playa2.__getPlaya2();
	if (p != null) {
		p.SetVariable("Request_command", "Next");
	}
};

/**
  * Fire this event when user press the prev button
  * @public
  */
Playa2.doPrev = function() {
	var p = Playa2.__getPlaya2();
	if (p != null) {
		p.SetVariable("Request_command", "Prev");
	}
};

/**
  * Plays a file.
  * @param    url    String
  * @public
  */
Playa2.doPlayUrl = function(sUrl) {
	var p = Playa2.__getPlaya2();
	if (p != null) {
		p.SetVariable("Request_args", sUrl);
		p.SetVariable("Request_command", "PlayUrl");
	}
};

/**
  * Jumps to a position in the playlist.
  * @param    index    Integer
  * @public
  */
Playa2.doJump = function(nIndex) {
	var p = Playa2.__getPlaya2();
	if (p != null) {
		p.SetVariable("Request_args", "" + nIndex);
		p.SetVariable("Request_command", "PlayPosition");
	}
};

/*** Start processing */
Playa2.start();
