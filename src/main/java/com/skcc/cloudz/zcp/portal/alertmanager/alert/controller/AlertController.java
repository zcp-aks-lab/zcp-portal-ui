package com.skcc.cloudz.zcp.portal.alertmanager.alert.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.skcc.cloudz.zcp.portal.iam.user.UserController;

@Controller
@RequestMapping(value = AlertController.RESOURCE_PATH)
public class AlertController {

	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	static final String RESOURCE_PATH = "/alertmanager";

	@GetMapping(value = "/alert", consumes = MediaType.ALL_VALUE, produces = MediaType.TEXT_HTML_VALUE)
	public String userList(Model model) throws Exception {
		return "content/alertmanager/alert/alerting";
	}

}
