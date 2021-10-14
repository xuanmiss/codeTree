package com.miss.codetree.context;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miss.codetree.CodeTreeApplication;
import com.miss.codetree.constant.CodeProjectConstant;
import com.miss.codetree.constant.ImageConstant;
import com.miss.codetree.entity.CodeProject;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @project: CodeTree
 * @package: com.miss.codetree.context
 * @author: miss
 * @date: 2021/9/13 17:40
 * @since:
 * @history: 1.2021/9/13 created by miss
 */
public class ProjectContext {

    private static final ObjectMapper objectMapper = new ObjectMapper();
//    private static final File f = new File(CodeTreeApplication.class.getResource("/config.json").getFile());

    private static final String userHome = System.getProperties().getProperty("user.home");

    private static final File f = new File(userHome + File.separator + ".codeTreeConfig.json");

    public static CodeProject rootCodeProject = initRootProject();

    public static List<CodeProject> projectList = initProjectList();

    public static Map<String, CodeProject> projectMap = initProjectMap();


    public static TreeItem<CodeProject> treeItem = initTreeRootItem();



    private static TreeItem<CodeProject> initTreeRootItem() {
        Node imageIcon = new ImageView(ImageConstant.addImage);
        TreeItem<CodeProject> rootItem = new TreeItem<>(rootCodeProject, imageIcon);
        initTreeData(rootItem, rootCodeProject.getSubProjectList());
        return rootItem;
    }

    private static void initTreeData(TreeItem<CodeProject> rootNode, List<CodeProject> codeProjects) {

        if (codeProjects != null && !codeProjects.isEmpty()) {
            for (CodeProject project : codeProjects) {
                TreeItem<CodeProject> item = new TreeItem<>(project);
                if (CodeProjectConstant.PROJECT_TYPE_CATALOG.equalsIgnoreCase(project.getProjectType())) {
                    Node imageIcon = new ImageView(ImageConstant.addImage);
                    item.setGraphic(imageIcon);
                }
                rootNode.getChildren().add(item);
                List<CodeProject> projectList = project.getSubProjectList();
                initTreeData(item, projectList);

            }
        }
    }

    private static CodeProject initRootProject() {
        CodeProject codeProject = new CodeProject();
        if (f.exists()) {
            try {
                codeProject = objectMapper.readValue(f, CodeProject.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return codeProject;
        }else {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return codeProject;
        }
    }


    private static List<CodeProject> initProjectList() {
        List<CodeProject> projectList = new ArrayList<>();
        if (rootCodeProject != null ) {
            tileProject(rootCodeProject, projectList);
        }
        return projectList;
    }

    private static Map<String, CodeProject> initProjectMap() {
        Map<String, CodeProject> codeProjectMap = new HashMap<>();
        for(CodeProject codeProject : projectList) {
            if (codeProject.getProjectCode() == null || codeProject.getProjectCode().isEmpty()) {
                codeProject.setProjectCode(UUID.randomUUID().toString());
            }
            codeProjectMap.put(codeProject.getProjectCode(), codeProject);
        }

        return codeProjectMap;
    }


    public static void refreshProjects(CodeProject codeProject) {
       projectList.add(codeProject);
       addCodeProjectToRoot(codeProject);
//        treeItem = initTreeRootItem();
       projectMap.put(codeProject.getProjectCode(), codeProject);
       saveCodeProjectConfig(rootCodeProject);

    }

    public static void saveCodeProjectConfig(CodeProject rootCodeProject) {
        try {
            objectMapper.writeValue(f, rootCodeProject);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void tileProject(CodeProject rootCodeProject, List<CodeProject> projectList) {
        projectList.add(rootCodeProject);
        if (rootCodeProject.getSubProjectList() != null) {
            for (CodeProject codeProject : rootCodeProject.getSubProjectList()) {
                tileProject(codeProject, projectList);
            }
        }
    }

    private static void addCodeProjectToRoot(CodeProject codeProject) {

        for(CodeProject codeProject1 : projectList) {
            if (codeProject1.getProjectCode().equalsIgnoreCase(codeProject.getParentProjectCode())) {
                if (codeProject1.getSubProjectList() == null) {
                    LinkedList<CodeProject> codeProjects = new LinkedList<>();
                    codeProject1.setSubProjectList(codeProjects);
                }
                codeProject1.getSubProjectList().add(codeProject);
            }
        }
    }

    public static void updateItem(CodeProject codeProject) {

        CodeProject exitProject = projectMap.get(codeProject.getProjectCode());
        if (exitProject != null) {
            exitProject.setProjectAbstract(codeProject.getProjectAbstract());
            exitProject.setProjectDir(codeProject.getProjectDir());
            exitProject.setProjectName(codeProject.getProjectName());
            saveCodeProjectConfig(rootCodeProject);
        }else {
            exitProject = codeProject;
            refreshProjects(exitProject);
        }

    }

    public static void removeCodeProject(CodeProject codeProject) {
        if (projectMap.get(codeProject.getProjectCode()) != null) {
            projectMap.remove(codeProject.getProjectCode());
        }
        projectList = projectList.stream()
                .filter(codeProject1 -> !codeProject1.getProjectCode().equals(codeProject.getProjectCode()))
                .collect(Collectors.toList());
        deleteFromRootCodeProject(Collections.singletonList(rootCodeProject), codeProject);
        saveCodeProjectConfig(rootCodeProject);
    }

    private static void deleteFromRootCodeProject(List<CodeProject> rootCodeProjectList, CodeProject codeProject) {

        for (CodeProject project: rootCodeProjectList) {
            List<CodeProject> subProjectList = project.getSubProjectList();
            if (subProjectList != null && !subProjectList.isEmpty()) {
                for (int i = 0; i < subProjectList.size(); i++) {
                    if (subProjectList.get(i).getProjectCode().equalsIgnoreCase(codeProject.getProjectCode())) {
                        if (subProjectList.get(i).getSubProjectList() != null && !subProjectList.get(i).getSubProjectList().isEmpty()) {
                            LinkedList<CodeProject> ssubProjects = subProjectList.get(i).getSubProjectList();
                            subProjectList.remove(i);
                            subProjectList.addAll(ssubProjects);
                        }else {
                            subProjectList.remove(i);
                        }
                        return;
                    }
                }
                deleteFromRootCodeProject(subProjectList, codeProject);
            }
        }
    }
}
