package com.skcc.cloudz.zcp.portal.management.namespace.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skcc.cloudz.zcp.api.iam.domain.vo.ApiResponseVo;
import com.skcc.cloudz.zcp.api.iam.service.impl.IamRestClient;
import com.skcc.cloudz.zcp.common.constants.ApiResult;
import com.skcc.cloudz.zcp.common.security.service.SecurityService;
import com.skcc.cloudz.zcp.common.util.NumberUtil;
import com.skcc.cloudz.zcp.portal.alert.channels.vo.ChannelDtlVo;
import com.skcc.cloudz.zcp.portal.management.namespace.vo.EnquryNamespaceVO;
import com.skcc.cloudz.zcp.portal.management.namespace.vo.SecretDockerVO;
import com.skcc.cloudz.zcp.portal.management.namespace.vo.SecretTlsVO;
import com.skcc.cloudz.zcp.portal.management.namespace.vo.SecretVO;
import com.skcc.cloudz.zcp.portal.management.user.service.UserService;
import com.skcc.cloudz.zcp.portal.management.user.vo.ZcpNamespace;
import com.skcc.cloudz.zcp.portal.management.user.vo.ZcpNamespaceList;

@Service
public class NamespaceService {

	private final Logger logger = (Logger) LoggerFactory.getLogger(NamespaceService.class);

	@Value("${props.iam.baseUrl}")
	private String iamBaseUrl;

	@Autowired
	private IamRestClient client;

	@Autowired
	private SecurityService securityService;

	public Object getResourceQuota(EnquryNamespaceVO vo) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String param = "userId=" + securityService.getUserDetails().getUserId();
		ApiResponseVo response = client.request("/iam/metrics/namespaces?" + param, null);

		if (response.getCode().equals("ZCP-0001")) {
			logger.debug(response.getMsg());
			List<Object> empty = new ArrayList<>();
			resultMap.put("items", empty);
			resultMap.put("errorMsg", response.getMsg());
			return resultMap;
		}

		if (!response.getCode().equals(ApiResult.SUCCESS.getCode())) {
			throw new Exception(response.getMsg());
		}

		ObjectMapper mapper = new ObjectMapper();
		ZcpNamespaceList namespaceList = mapper.convertValue(response.getData(), ZcpNamespaceList.class);
		List<ZcpNamespace> listQuotas = namespaceList.getItems();
		Stream<ZcpNamespace> stream = namespaceList.getItems().stream();

		if (!StringUtils.isEmpty(vo.getSortItem())) {
			switch (vo.getSortItem()) {
			case "namespace":
				if (vo.isSortOrder())
					stream = stream.sorted((a, b) -> a.getName().compareTo(b.getName()));// asc
				else
					stream = stream.sorted((a, b) -> b.getName().compareTo(a.getName()));
				break;
			case "cpuR":
				if (vo.isSortOrder())
					stream = stream.sorted((a, b) -> a.getUsedCpuRequests().compareTo(b.getUsedCpuRequests()));
				else
					stream = stream.sorted((a, b) -> b.getUsedCpuRequests().compareTo(a.getUsedCpuRequests()));
				break;
			case "cpuL":
				if (vo.isSortOrder())
					stream = stream.sorted((a, b) -> a.getUsedCpuLimits().compareTo(b.getUsedCpuLimits()));
				else
					stream = stream.sorted((a, b) -> b.getUsedCpuLimits().compareTo(a.getUsedCpuLimits()));
				break;
			case "memoryR":
				if (vo.isSortOrder())
					stream = stream.sorted((a, b) -> a.getUsedMemoryRequests().compareTo(b.getUsedMemoryRequests()));
				else
					stream = stream.sorted((a, b) -> a.getUsedMemoryRequests().compareTo(b.getUsedMemoryRequests()));
				break;
			case "memoryL":
				if (vo.isSortOrder())
					stream = stream.sorted((a, b) -> a.getUsedMemoryLimits().compareTo(b.getUsedMemoryLimits()));
				else
					stream = stream.sorted((a, b) -> a.getUsedMemoryLimits().compareTo(b.getUsedMemoryLimits()));
				break;
			case "user":
				if (vo.isSortOrder())
					stream = stream.sorted((a, b) -> NumberUtil.compare(a.getUserCount(), b.getUserCount()));
				else
					stream = stream.sorted((a, b) -> NumberUtil.compare(b.getUserCount(), a.getUserCount()));
				break;
			case "status":
				if (vo.isSortOrder())
					stream = stream.sorted((a, b) -> a.getStatus().compareTo(b.getStatus()));
				else
					stream = stream.sorted((a, b) -> b.getStatus().compareTo(a.getStatus()));
				break;
			case "createTime":
				if (vo.isSortOrder())
					stream = stream.sorted((a, b) -> a.getCreationDate().compareTo(b.getCreationDate()));
				else
					stream = stream.sorted((a, b) -> b.getCreationDate().compareTo(a.getCreationDate()));
				break;
			}
		}

		if (!StringUtils.isEmpty(vo.getNamespace())) {
			stream = stream.filter((namespace) -> {
				if (namespace != null)
					return namespace.getName().indexOf(vo.getNamespace()) > -1;
				else
					return false;
			});
		}

		if (stream != null)
			listQuotas = stream.collect(Collectors.toList());

		namespaceList.setItems(listQuotas);
		return namespaceList;
	}

	public Map<String, Object> getResourceLabel() throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		ApiResponseVo response = client.request(HttpMethod.GET, "/iam/namespace/labels", null);
		if (!response.getCode().equals(ApiResult.SUCCESS.getCode())) {
			throw new Exception(response.getMsg());
		}

		resultMap.putAll(response.getData());

		return resultMap;
	}

	public void deleteNamespace(String namespace) throws Exception {
		// Map<String, String> param = new HashMap<>();
		// param.put("userId", securityService.getUserDetails().getUserId());
		String param = "?userId=" + securityService.getUserDetails().getUserId();
		String url = "/iam/namespace/" + namespace + param;
		ApiResponseVo response = client.request(HttpMethod.DELETE, url, null);
		if (!response.getCode().equals(ApiResult.SUCCESS.getCode())) {
			throw new Exception(response.getMsg());
		}
	}

	public Map<String, Object> getNamespaceResource(String namespace) throws Exception {
		if (StringUtils.isEmpty(namespace))
			return null;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String param = "userId=" + securityService.getUserDetails().getUserId();
		String url = "/iam/namespace/{namespace}/resource?" + param;
		ApiResponseVo response = client.request(HttpMethod.GET, url.replace("{namespace}", namespace), null);
		if (!response.getCode().equals(ApiResult.SUCCESS.getCode())) {
			throw new Exception(response.getMsg());
		}

		resultMap.putAll(response.getData());

		return resultMap;
	}

	public void createNamespace(HashMap<String, Object> data) throws Exception {
		ApiResponseVo response = client.request(HttpMethod.POST, "/iam/namespace", data);
		if (!response.getCode().equals(ApiResult.SUCCESS.getCode())) {
			logger.debug(response.getMsg());
			throw new Exception(response.getMsg());
		}
	}

	public Map<String, Object> getUsers(HashMap<String, String> data) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String namespace = data.get("namespace");
		ApiResponseVo response = null;
		if (StringUtils.isEmpty(namespace)) {
			response = client.request(HttpMethod.GET, "/iam/users", null);
			if (!response.getCode().equals(ApiResult.SUCCESS.getCode())) {
				throw new Exception(response.getMsg());
			}

		} else {
			response = client.request(HttpMethod.GET, "/iam/namespace/" + namespace + "/users", null);
			if (!response.getCode().equals(ApiResult.SUCCESS.getCode())) {
				throw new Exception(response.getMsg());
			}
		}
		String searchWord = data.get("searchWord");
		if (StringUtils.isNoneEmpty(searchWord)) {
			List<Object> newUser = new ArrayList();
			List<Map<String, String>> users = (List<Map<String, String>>) ((Map<String, Object>) response.getData())
					.get("items");
			for (Map<String, String> user : users) {
				if (user.get("username").indexOf(searchWord) > -1) {
					newUser.add(user);
				}
				if (user.get("email") != null && user.get("email").indexOf(searchWord) > -1) {
					newUser.add(user);
				}
				if (user.get("lastName") != null && user.get("lastName").indexOf(searchWord) > -1) {
					newUser.add(user);
				}
				if (user.get("firstName") != null && user.get("firstName").indexOf(searchWord) > -1) {
					newUser.add(user);
				}
				if (user.get("firstName") != null && user.get("lastName") != null
						&& (user.get("firstName") + user.get("lastName")).indexOf(searchWord) > -1) {
					newUser.add(user);
				}
				newUser = newUser.stream().distinct().collect(Collectors.toList());
			}
			Map<String, Object> rtnData = new HashMap<>();
			rtnData.put("items", newUser);

			return rtnData;
		}

		resultMap.putAll(response.getData());

		return resultMap;
	}

	public void addUserInNamespace(HashMap<String, Object> data) throws Exception {
		ApiResponseVo resUser = client.request(HttpMethod.POST,
				"/iam/namespace/" + data.get("namespace") + "/roleBinding", data);
		if (!resUser.getCode().equals(ApiResult.SUCCESS.getCode())) {
			throw new Exception(resUser.getMsg());
		}

	}

	public void modifyNamespaceRole(HashMap<String, Object> data) throws Exception {
		ApiResponseVo resUser = client.request(HttpMethod.PUT,
				"/iam/namespace/" + data.get("namespace") + "/roleBinding", data);
		if (!resUser.getCode().equals(ApiResult.SUCCESS.getCode())) {
			throw new Exception(resUser.getMsg());
		}

	}

	public void delNamespaceRole(HashMap<String, Object> data) throws Exception {
		ApiResponseVo resUser = client.request(HttpMethod.DELETE,
				"/iam/namespace/" + data.get("namespace") + "/roleBinding", data);
		if (!resUser.getCode().equals(ApiResult.SUCCESS.getCode())) {
			throw new Exception(resUser.getMsg());
		}

	}

	public void addLableOfNamespace(HashMap<String, Object> data) throws Exception {
		ApiResponseVo resUser = client.request(HttpMethod.POST, "/iam/namespace/" + data.get("namespace") + "/label",
				data);
		if (!resUser.getCode().equals(ApiResult.SUCCESS.getCode())) {
			throw new Exception(resUser.getMsg());
		}

	}

	public Map<String, Object> getLableOfNamespace(HashMap<String, Object> data) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		ApiResponseVo response = client.request(HttpMethod.GET, "/iam/namespace/" + data.get("namespace") + "/labels",
				null);
		if (!response.getCode().equals(ApiResult.SUCCESS.getCode())) {
			throw new Exception(response.getMsg());
		}
		resultMap.putAll(response.getData());
		return resultMap;

	}

	public void delLableOfNamespace(HashMap<String, Object> data) throws Exception {
		ApiResponseVo resUser = client.request(HttpMethod.DELETE, "/iam/namespace/" + data.get("namespace") + "/label",
				data);
		if (!resUser.getCode().equals(ApiResult.SUCCESS.getCode())) {
			throw new Exception(resUser.getMsg());
		}
	}

	public List<String> getNamespaceRoles() throws Exception {
		List<String> clusterRoles = new ArrayList<String>();
		ApiResponseVo apiResponseVo = client.request(HttpMethod.GET, "/iam/rbac/clusterRoles?type=namespace", null);
		if (!apiResponseVo.getCode().equals(ApiResult.SUCCESS.getCode())) {
			throw new Exception(apiResponseVo.getMsg());
		}
		Map<String, Object> data = apiResponseVo.getData();
		List<HashMap<String, Object>> items = (List<HashMap<String, Object>>) data.get("items");

		for (HashMap<String, Object> item : items) {
			clusterRoles.add(((HashMap<String, Object>) item.get("metadata")).get("name").toString());
		}

		return clusterRoles;

	}

	@SuppressWarnings("unchecked")
	public List<SecretVO> getSecrets(String namespace) throws Exception {
		ApiResponseVo response = client.request(HttpMethod.GET, "/iam/namespace/" + namespace + "/secrets", null);

		if (!response.getCode().equals(ApiResult.SUCCESS.getCode())) {
			throw new Exception(response.getMsg());
		}

		Map<String, Object> data = response.getData();
		List<HashMap<String, Object>> items = (List<HashMap<String, Object>>) data.get("items");

		List<SecretVO> resultList = new ArrayList<SecretVO>();

		for (HashMap<String, Object> item : items) {
			SecretVO secretVO = new SecretVO();

			secretVO.setName(((HashMap<String, Object>) item.get("metadata")).get("name").toString());

			if ("kubernetes.io/dockerconfigjson".equals(item.get("type"))) {
				secretVO.setType("Docker Registry");
			} else if ("kubernetes.io/tls".equals(item.get("type"))) {
				secretVO.setType("TLS");
			} else {
				secretVO.setType("");
			}

			if (((HashMap<String, Object>) item.get("metadata")).get("labels") != null) {
				secretVO.setLabel(((HashMap<String, Object>) item.get("metadata")).get("labels").toString());
			} else {
				secretVO.setLabel("");
			}
			String date = ((HashMap<String, Object>) ((HashMap<String, Object>) item.get("metadata"))
					.get("creationTimestamp")).get("year")
					+ "/"
					+ String.format("%02d",
							((HashMap<String, Object>) ((HashMap<String, Object>) item.get("metadata"))
									.get("creationTimestamp")).get("monthOfYear"))
					+ "/"
					+ String.format("%02d",
							((HashMap<String, Object>) ((HashMap<String, Object>) item.get("metadata"))
									.get("creationTimestamp")).get("dayOfMonth"))
					+ " "
					+ String.format("%02d",
							((HashMap<String, Object>) ((HashMap<String, Object>) item.get("metadata"))
									.get("creationTimestamp")).get("hourOfDay"))
					+ ":"
					+ String.format("%02d",
							((HashMap<String, Object>) ((HashMap<String, Object>) item.get("metadata"))
									.get("creationTimestamp")).get("minuteOfHour"))
					+ ":"
					+ String.format("%02d", ((HashMap<String, Object>) ((HashMap<String, Object>) item.get("metadata"))
							.get("creationTimestamp")).get("secondOfMinute"));

			secretVO.setDate(date);
			resultList.add(secretVO);
		}

		return resultList;
	}

	public SecretDockerVO createDockerSecret(Map<String, Object> params) {
		String url = UriComponentsBuilder.fromUriString(iamBaseUrl).path("/iam/namespace/{namespace}/secret/new/docker")
				.buildAndExpand(params.get("pNamespace")).toString();

		SecretDockerVO secretDockerParam = new SecretDockerVO();

		secretDockerParam.setEmail(params.get("pDocker_email").toString());
		secretDockerParam.setName(params.get("pSecret_name").toString());
		secretDockerParam.setPassword(params.get("pDocker_password").toString());
		secretDockerParam.setServer(params.get("pDocker_server").toString());
		secretDockerParam.setType("kubernetes.io/dockerconfigjson");
		secretDockerParam.setUsername(params.get("pDocker_username").toString());

		HttpHeaders headers = new HttpHeaders();

		headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<SecretDockerVO> entity = new HttpEntity<SecretDockerVO>(secretDockerParam, headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<SecretDockerVO> response = restTemplate.exchange(url, HttpMethod.POST, entity,
				SecretDockerVO.class);

		HttpStatus statusCode = response.getStatusCode();

		SecretDockerVO secretDockerVO = null;
		if (statusCode == HttpStatus.OK) {
			secretDockerVO = response.getBody();
		}

		return secretDockerVO;
	}

	public String createTlsSecret(HttpServletRequest request) throws Exception {
		String param = "?name=" + request.getParameter("pSecret_name") + "&type=kubernetes.io/tls";

		String url = UriComponentsBuilder.fromUriString(iamBaseUrl)
				.path("/iam/namespace/{namespace}/secret/new/tls" + param)
				.buildAndExpand(request.getParameter("pNamespace")).toString();

		SecretTlsVO secretTlsParam = new SecretTlsVO();

		MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
		Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
		MultipartFile multipartFile = null;

		int count = 0;
		while (iterator.hasNext()) {
			multipartFile = multipartHttpServletRequest.getFile(iterator.next());

			if (multipartFile.isEmpty() == false) {
				if (count == 0) {
					secretTlsParam.setCrt(multipartFile.getBytes());
					secretTlsParam.setCrtFileName(multipartFile.getOriginalFilename());

				} else {
					secretTlsParam.setKey(multipartFile.getBytes());
					secretTlsParam.setKeyFileName(multipartFile.getOriginalFilename());
				}
			}
			count++;
		}

		ByteArrayResource crt = new ByteArrayResource(secretTlsParam.getCrt()) {
			@Override
			public String getFilename() {
				return secretTlsParam.getCrtFileName();
			}
		};

		ByteArrayResource key = new ByteArrayResource(secretTlsParam.getKey()) {
			@Override
			public String getFilename() {
				return secretTlsParam.getKeyFileName();
			}
		};

		LinkedMultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<>();

		bodyMap.add("crt", crt);
		bodyMap.add("key", key);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);

		HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(bodyMap, headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity,
				String.class);

		HttpStatus statusCode = response.getStatusCode();

		String resultSecretTls = "";
		if (statusCode == HttpStatus.OK) {
			resultSecretTls = response.getBody();
		}

		return resultSecretTls;
	}

}
