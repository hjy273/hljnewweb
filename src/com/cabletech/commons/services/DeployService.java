package com.cabletech.commons.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipInputStream;

import javax.annotation.Resource;

import org.apache.commons.fileupload.FileItem;
import org.jbpm.api.ExecutionService;
import org.jbpm.api.HistoryService;
import org.jbpm.api.IdentityService;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessDefinitionQuery;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.RepositoryService;
import org.jbpm.api.TaskService;
import org.jbpm.api.model.ActivityCoordinates;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cabletech.ctf.exception.ServiceException;

@Service
public class DeployService {
	@Resource(name = "processEngine")
	private ProcessEngine processEngine;
	@Resource(name = "repositoryService")
	private RepositoryService repositoryService;
	@Resource(name = "executionService")
	private ExecutionService executionService;
//	@Resource(name = "identityService")
//	private IdentityService identityService;
	@Resource(name = "historyService")
	private HistoryService historyService;
	@Resource(name = "taskService")
	private TaskService taskService;
//	@Resource(name = "processInstance")
//	private ProcessInstance processInstance;

	@Transactional
	public void deploy(List fileItems) throws ServiceException {
		Iterator iter = fileItems.iterator();
		try {
			if (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				if (!item.isFormField()) {
					String name = item.getName();
					long size = item.getSize();
					System.out.println("name: " + name);
					if (name != null && !name.equals("") && size > 0) {
						repositoryService.createDeployment().addResourcesFromZipInputStream(
								new ZipInputStream(item.getInputStream())).deploy();
					}
				}
			}
		} catch (IOException e) {
			throw new ServiceException("读取文件异常:" + e.getMessage());
		}
	}

	public List<ProcessDefinition> getLatestProcessDefinition() throws ServiceException {
		List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery().orderAsc(
				ProcessDefinitionQuery.PROPERTY_NAME).list();
		Map<String, ProcessDefinition> map = new LinkedHashMap<String, ProcessDefinition>();
		for (ProcessDefinition pd : processDefinitions) {
			String key = pd.getKey();
			ProcessDefinition definition = map.get(key);
			if ((definition == null) || (definition.getVersion() < pd.getVersion())) {
				map.put(key, pd);
			}
		}
		return new ArrayList(map.values());
	}
	public List<ProcessInstance> getProcessInstanceById(String pdId) throws ServiceException{
		return executionService.createProcessInstanceQuery().processDefinitionId(pdId).list();
	}
	public List<ActivityCoordinates> drawImage(String piId) throws ServiceException{
		Set<String> activityNames = executionService.findExecutionById(piId).findActiveActivityNames();
		List<ActivityCoordinates> coordinateList = new ArrayList<ActivityCoordinates>();
		ProcessInstance processInstance = executionService.findProcessInstanceById(piId);
		String pId = processInstance.getProcessDefinitionId();
		for (String activityName : activityNames) {
			ActivityCoordinates coordinates = repositoryService.
									getActivityCoordinates(pId, activityName);
			coordinateList.add(coordinates);
		}
		return coordinateList;
	}
}
