package com.skcc.cloudz.zcp.member.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.internal.LinkedTreeMap;
import com.skcc.cloudz.zcp.member.dao.MemberKeycloakDao;
import com.skcc.cloudz.zcp.member.dao.MemberKubeDao;
import com.skcc.cloudz.zcp.member.vo.KubeDeleteOptionsVO;
import com.skcc.cloudz.zcp.member.vo.MemberVO;
import com.skcc.cloudz.zcp.member.vo.RoleVO;
import com.skcc.cloudz.zcp.member.vo.ServiceAccountVO;

import io.kubernetes.client.ApiException;
import io.kubernetes.client.models.V1ClusterRole;
import io.kubernetes.client.models.V1ClusterRoleBinding;
import io.kubernetes.client.models.V1ObjectMeta;
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
	
//	public List<String> serviceAccountList() throws IOException, ApiException{
//		List<LinkedTreeMap> c = (List<LinkedTreeMap>)KubeDao.serviceAccountList("zcp-demo").values().toArray()[3];
//		List<String> serviceAccountList = new ArrayList();
//		for(LinkedTreeMap data : c) {
//			LinkedTreeMap account =(LinkedTreeMap) data.values().toArray()[0];
//			String serviceAccount = (String)account.get("name");
//			serviceAccountList.add(serviceAccount);
//					
//		}
//		return serviceAccountList;
//	}
	
	public void createServiceAccount(ServiceAccountVO data) throws IOException, ApiException{
		LinkedTreeMap c = KubeDao.createServiceAccount(data.getNamespace(), data);
	}
	
	
	public void createClusterRoleBinding(V1ClusterRoleBinding data) throws IOException, ApiException{
		V1ObjectMeta metadata = new V1ObjectMeta();
		V1ClusterRoleBinding v = new V1ClusterRoleBinding();
		List<V1Subject> subjects = new ArrayList();
		V1RoleRef roleRef = new V1RoleRef();
		V1Subject subject = new V1Subject();
		
		//example
//		subject.setKind("ServiceAccount");
//		subject.setName("zcp-cluster-admin-kilsoo75");
//		subject.setNamespace("bk-service");
//		roleRef.setApiGroup("rbac.authorization.k8s.io");
//		roleRef.setKind("ClusterRole");
//		roleRef.setName("view");
//		metadata.setName("zcp-adminTest1");
//		v.setApiVersion("rbac.authorization.k8s.io/v1");
//		v.setKind("ClusterRoleBinding");
		
		subjects.add(subject);
		v.setSubjects(subjects);
		v.setRoleRef(roleRef);
		v.setMetadata(metadata);
		
		
//		jsonData="{" + 
//				"			\"apiVersion\": \"rbac.authorization.k8s.io/v1\"," + 
//				"			\"kind\": \"ClusterRoleBinding\"," + 
//				"			\"metadata\": {" + 
//				"				\"name\": \"zcp-adminTest\"," + 
//				"			}," + 
//				"			\"roleRef\": {" + 
//				"				\"apiGroup\": \"rbac.authorization.k8s.io\"," + 
//				"				\"kind\": \"ClusterRole\"," + 
//				"				\"name\": \"view\"" + 
//				"			}," + 
//				"			\"subjects\": " +//[" + 
//				"				{" + 
//				"					\"kind\": \"ServiceAccount\"," + 
//				"					\"name\": \"zcp-cluster-admin-kilsoo75\"," + 
//				"					\"namespace\": \"bk-service\"" + 
//				"				}" + 
//				//"			]" + 
//				"		}";
		
		List<LinkedTreeMap> c = KubeDao.createClusterRoleBinding(data);
		
	}
	
	public void deleteClusterRoleBinding(KubeDeleteOptionsVO data) throws IOException, ApiException{
		LinkedTreeMap status = KubeDao.deleteClusterRoleBinding(data.getName(), data);
	}
	
	public void createClusterRole(V1ClusterRole data) throws IOException, ApiException{
		LinkedTreeMap c = KubeDao.createClusterRole(data);
	}
	
	public void createRole(RoleVO data) throws IOException, ApiException{
		LinkedTreeMap c = KubeDao.createRole(data.getNamespace(), data);
	}
	
	public void deleteRole(KubeDeleteOptionsVO data) throws IOException, ApiException{
		LinkedTreeMap status = KubeDao.deleteRole(data.getNamespace(), data.getName(), data);
	}
}
