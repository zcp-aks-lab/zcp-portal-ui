package com.skcc.cloudz.zcp.member.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.internal.LinkedTreeMap;
import com.skcc.cloudz.zcp.member.dao.MemberKeycloakDao;
import com.skcc.cloudz.zcp.member.dao.MemberKubeDao;
import com.skcc.cloudz.zcp.member.vo.MemberVO;

import io.kubernetes.client.ApiException;
import io.kubernetes.client.models.V1ClusterRoleBinding;
import io.kubernetes.client.models.V1ClusterRoleBindingList;
import io.kubernetes.client.models.V1ListMeta;
import io.kubernetes.client.models.V1RoleRef;
import io.kubernetes.client.models.V1Subject;

@Service
public class MemberService {

	@Autowired
	MemberKeycloakDao keycloakDao;
	
	@Autowired
	MemberKubeDao KubeDao;
	
	public Object getUserList() {
		return keycloakDao.getUserList();
	}
	
	public void modifyUserAttribute(MemberVO vo){
		keycloakDao.modifyUserAttribute(vo);
	}
	
	public void deleteUser(MemberVO vo) {
		keycloakDao.deleteUser(vo);
	}
	
	public void createUser(MemberVO vo) {
		keycloakDao.createUser(vo);
	}
	
//	public ClusterRole getClusterRoles() throws IOException, ApiException{
//	    return KubeDao.getClusterRoles();
//	}
//
//	public ClusterRoleBinding clusterrolebindings() throws IOException, ApiException{
//		return KubeDao.clusteRroleBindings();
//	}
	
//	public ServiceAccount serviceAccount() throws IOException, ApiException{
//		return KubeDao.serviceAccount();
//	}
	
	public List<String> clusterRoleList() throws ApiException, ParseException{
		List<LinkedTreeMap> c = (List<LinkedTreeMap>) KubeDao.clusterRoleList().values().toArray()[3];
		List<String> clusterRole = new ArrayList();
		for(LinkedTreeMap data : c) {
			LinkedTreeMap cluster =(LinkedTreeMap) data.values().toArray()[0];
			String clusterName = (String)cluster.get("name");
			clusterRole.add(clusterName);
					
		}
        
        return clusterRole;
		
	}
	
	public LinkedTreeMap clusterRoleBindingList() throws ApiException{
		return KubeDao.clusterRoleBindingList();
	}
	
	public List<String> serviceAccountList() throws IOException, ApiException{
		List<LinkedTreeMap> c = (List<LinkedTreeMap>)KubeDao.serviceAccountList("zcp-demo").values().toArray()[3];
		List<String> serviceAccountList = new ArrayList();
		for(LinkedTreeMap data : c) {
			LinkedTreeMap account =(LinkedTreeMap) data.values().toArray()[0];
			String serviceAccount = (String)account.get("name");
			serviceAccountList.add(serviceAccount);
					
		}
		return serviceAccountList;
	}
	
	public List<String> createClusterRoleBinding(String jsonData) throws IOException, ApiException{
		V1ClusterRoleBindingList clusterrolebinding = new V1ClusterRoleBindingList();
		V1ListMeta metadata = new V1ListMeta();
		List<V1ClusterRoleBinding> bindingList = new ArrayList();
		V1ClusterRoleBinding v = new V1ClusterRoleBinding();
		List<V1Subject> subjects = new ArrayList();
		V1RoleRef roleRef = new V1RoleRef();
		V1Subject subject = new V1Subject();
		subject.setApiGroup("ServiceAccount");
		subject.setName("zcp-cluster-admin-kilsoo75");
		subject.setNamespace("bk-service");
		roleRef.setApiGroup("rbac.authorization.k8s.io");
		roleRef.setKind("ClusterRole");
		roleRef.setName("view");
		subjects.add(subject);
		v.setSubjects(subjects);
		v.setRoleRef(roleRef);
		bindingList.add(v);
		clusterrolebinding.setApiVersion("rbac.authorization.k8s.io/v1");
		clusterrolebinding.setKind("ClusterRoleBinding");
		clusterrolebinding.setItems(bindingList);
		jsonData="{\r\n" + 
				"			\"apiVersion\": \"rbac.authorization.k8s.io/v1\",\r\n" + 
				"			\"kind\": \"ClusterRoleBinding\",\r\n" + 
				"			\"metadata\": {\r\n" + 
				"				\"creationTimestamp\": \"2018-03-29T15:08:53Z\",\r\n" + 
				"				\"name\": \"zcp-admin\",\r\n" + 
				"				\"namespace\": \"\",\r\n" + 
				"				\"resourceVersion\": \"7449484\",\r\n" + 
				"				\"selfLink\": \"/apis/rbac.authorization.k8s.io/v1/clusterrolebindings/zcp-admin\",\r\n" + 
				"				\"uid\": \"18192276-3363-11e8-8e38-0699d760d333\"\r\n" + 
				"			},\r\n" + 
				"			\"roleRef\": {\r\n" + 
				"				\"apiGroup\": \"rbac.authorization.k8s.io\",\r\n" + 
				"				\"kind\": \"ClusterRole\",\r\n" + 
				"				\"name\": \"view\"\r\n" + 
				"			},\r\n" + 
				"			\"subjects\": [\r\n" + 
				"				{\r\n" + 
				"					\"kind\": \"ServiceAccount\",\r\n" + 
				"					\"name\": \"zcp-cluster-admin-kilsoo75\",\r\n" + 
				"					\"namespace\": \"bk-service\"\r\n" + 
				"				}\r\n" + 
				"			]\r\n" + 
				"		}".replaceAll("\\t", "").replaceAll("\\r\\n", "");
		
		List<LinkedTreeMap> c = (List<LinkedTreeMap>)KubeDao.createClusterRoleBinding(jsonData).values();
		List<String> serviceAccountList = new ArrayList();
//		for(LinkedTreeMap data : c) {
//			LinkedTreeMap account =(LinkedTreeMap) data.values().toArray()[0];
//			String serviceAccount = (String)account.get("name");
//			serviceAccountList.add(serviceAccount);
//					
//		}
		return serviceAccountList;
	}
}
