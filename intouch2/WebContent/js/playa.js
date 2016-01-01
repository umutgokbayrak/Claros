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

var Playa = {};

// constants
Playa.VERSION = "1.5";
Playa.STATE_STOPPED = 0;
Playa.STATE_PLAYING = 1;

// private
Playa.__isStarted = false;

// events
Playa.onPlayStop = function() {};
Playa.onPlayStart = function() {};
Playa.onStateChange = function() {};

// public properties
Playa.title = "";
Playa.playListPosition = -1;
Playa.playlistSize = -1;
Playa.path = "";
Playa.state = Playa.STATE_STOPPED;
Playa.btnState = "Loading";

/**
  * Internal function to retrieve player-object
  * @private
  */
Playa.__getPlaya = function() {
	if (navigator.appName.indexOf ("Microsoft") !=-1) {
		var p = window["playa"];
	} else {
		var p = document["playa"];
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

Playa.setSong == function(song) {
	var p = Playa.__getPlaya();
	p.SetVariable("Response_path", "resources/newmsg.xml");
};

/**
  * This is the main loop
  * @private
  */
Playa.poll = function() {
	var p = Playa.__getPlaya();
	setTimeout("Playa.poll()", 200);
	if (p != null) {
		var previousState = Playa.state;
		var previousPath = Playa.path;

		Playa.title = p.GetVariable("Response_title");
		Playa.playListPosition = parseInt(p.GetVariable("Response_playListPosition"));
		Playa.path = p.GetVariable("Response_path");
		Playa.btnState = p.GetVariable("Response_btnState");
		Playa.playlistSize = parseInt(p.GetVariable("Response_playlistSize"));

		// update state
		if (Playa.btnState == "Play") {
			Playa.state = Playa.STATE_STOPPED;
		} else if (Playa.btnState == "Stop") {
			Playa.state = Playa.STATE_PLAYING;
		}

		// trigger events
		if (Playa.state != previousState || previousPath != Playa.path) {
			if (Playa.state == Playa.STATE_STOPPED) {
				Playa.onPlayStop();
			} else {
				Playa.onPlayStart();
			}
			Playa.onStateChange();
		}
	}
};

/**
  * Starts the playa
  * @public
  */
Playa.start = function() {
	if (Playa.__isStarted) {
		return;
	}
	Playa.poll();
	Playa.__isStarted = true;
};

/**
  * Fire this event when user press the play/stop button
  * Toggles between playing/stopped state.
  * @public
  */
Playa.doPlayStop = function() {
	var p = Playa.__getPlaya();
	if (p != null) {
		p.SetVariable("Request_command", "PlayStop");
	}
};

/**
  * Starts playing, if the playback is stopped
  * @public
  */
Playa.doPlay = function() {
	var p = Playa.__getPlaya();
	if (p != null) {
		p.SetVariable("Request_command", "Play");
	}
};

/**
  * Stops playback if it's playing
  * @public
  */
Playa.doStop = function() {
	var p = Playa.__getPlaya();
	if (p != null) {
		p.SetVariable("Request_command", "Stop");
	}
};

/**
  * Fire this event when user press the next button
  * @public
  */
Playa.doNext = function() {
	var p = Playa.__getPlaya();
	if (p != null) {
		p.SetVariable("Request_command", "Next");
	}
};

/**
  * Fire this event when user press the prev button
  * @public
  */
Playa.doPrev = function() {
	var p = Playa.__getPlaya();
	if (p != null) {
		p.SetVariable("Request_command", "Prev");
	}
};

/**
  * Plays a file.
  * @param    url    String
  * @public
  */
Playa.doPlayUrl = function(sUrl) {
	var p = Playa.__getPlaya();
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
Playa.doJump = function(nIndex) {
	var p = Playa.__getPlaya();
	if (p != null) {
		p.SetVariable("Request_args", "" + nIndex);
		p.SetVariable("Request_command", "PlayPosition");
	}
};

/*** Start processing */
Playa.start();
