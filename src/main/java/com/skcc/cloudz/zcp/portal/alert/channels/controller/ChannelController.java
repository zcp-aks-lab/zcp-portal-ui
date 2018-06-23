package com.skcc.cloudz.zcp.portal.alert.channels.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = ChannelController.RESOURCE_PATH)
public class ChannelController {

	private static final Logger log = LoggerFactory.getLogger(ChannelController.class);

	static final String RESOURCE_PATH = "/alert";

	@GetMapping(value = "/channels", consumes = MediaType.ALL_VALUE, produces = MediaType.TEXT_HTML_VALUE)
	public String channelList(Model model) throws Exception {
		return "content/alertmanager/channel/channel";
	}

}
