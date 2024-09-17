package com.miss.codetree.context;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miss.codetree.constant.CodeProjectConstant;
import com.miss.codetree.constant.ImageConstant;
import com.miss.codetree.entity.CodeProject;
import com.miss.codetree.entity.CodeProjectConfig;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;

import java.io.File;
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

    public static CodeProject rootCodeProject;
//    = initRootProject();

    public static LinkedList<CodeProject> projectList;
//    = initProjectList();

    public static Map<String, CodeProject> projectMap;
//    = initProjectMap();


    public static TreeItem<CodeProject> treeItem;
//    = initTreeRootItem();

    public static String dirChooseBaseDir = System.getProperties().getProperty("user.home");

    public static DirectoryChooser directoryChooser = new DirectoryChooser();

    public static CodeProjectConfig codeProjectConfig;

    public static TreeItem<CodeProject> selectedItem;

    static {
        initProjectContext();


    }

    private static void initProjectContext() {
        codeProjectConfig = new CodeProjectConfig();
        loadCodeProjectConfig();
        rootCodeProject = codeProjectConfig.getCodeProject();
        projectList = initProjectList();
        projectMap = initProjectMap();
        treeItem = initTreeRootItem();
    }

    private static void loadCodeProjectConfig() {
        CodeProject codeProject = new CodeProject();
        if (f.exists() && f.length() > 0) {
            try {
                codeProjectConfig = objectMapper.readValue(f, CodeProjectConfig.class);
            } catch (IOException e) {
                e.printStackTrace();
                codeProject.setProjectType(CodeProjectConstant.PROJECT_TYPE_CATALOG);
                codeProject.setProjectName("root");
                codeProject.setProjectCode(UUID.randomUUID().toString());
                codeProjectConfig.setCodeProject(codeProject);
            }
        }else {
            try {
                f.createNewFile();
                codeProject.setProjectType(CodeProjectConstant.PROJECT_TYPE_CATALOG);
                codeProject.setProjectName("root");
                codeProject.setProjectCode(UUID.randomUUID().toString());
                codeProjectConfig.setCodeProject(codeProject);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static TreeItem<CodeProject> initTreeRootItem() {
        Node imageIcon = new ImageView(ImageConstant.addImage);
        TreeItem<CodeProject> rootItem = new TreeItem<>(rootCodeProject, imageIcon);
//        rootItem.addEventHandler(TreeItem.branchExpandedEvent(), new EventHandler() {
//            @Override
//            public void handle(Event e) {
//                TreeItem<CodeProject> source = (TreeItem<CodeProject>) e.getSource();
//                System.out.println(source.getValue().getProjectName());
//            }
//        });
        initTreeData(rootItem, rootCodeProject.getSubProjectList());
        return rootItem;
    }

    private static void initTreeData(TreeItem<CodeProject> rootNode, List<CodeProject> codeProjects) {
        // 这里需要添加上次选中的事件触发。看看是初始化之后遍历触发一次选择事件
        if (codeProjects != null && !codeProjects.isEmpty()) {
            for (CodeProject project : codeProjects) {
                TreeItem<CodeProject> item = new TreeItem<>(project);
                if (project.getProjectCode().equals(codeProjectConfig.getSelectedProjectCode())) {
                    ProjectContext.selectedItem = item;
                }
                if (CodeProjectConstant.PROJECT_TYPE_CATALOG.equalsIgnoreCase(project.getProjectType())) {
                    Node imageIcon = new ImageView(ImageConstant.addImage);
                    item.setGraphic(imageIcon);
                    item.setExpanded(project.isExpandStatus());
                }
                rootNode.getChildren().add(item);
                List<CodeProject> projectList = project.getSubProjectList();
                initTreeData(item, projectList);

            }
        }
    }

    public static void expandAllNode(TreeItem<CodeProject> treeItem) {
        if (CodeProjectConstant.PROJECT_TYPE_CATALOG.equals(treeItem.getValue().getProjectType())) {
            treeItem.setExpanded(true);
            treeItem.getValue().setExpandStatus(true);
            for (TreeItem<CodeProject> treeItem1 : treeItem.getChildren()) {
                expandAllNode(treeItem1);
            }
        }
    }

    public static void packupAllNode(TreeItem<CodeProject> treeItem) {
        if (CodeProjectConstant.PROJECT_TYPE_CATALOG.equals(treeItem.getValue().getProjectType())) {
            treeItem.setExpanded(false);
            treeItem.getValue().setExpandStatus(false);
            for (TreeItem<CodeProject> treeItem1 : treeItem.getChildren()) {
                packupAllNode(treeItem1);
            }
        }
    }

    private static CodeProject initRootProject() {
        CodeProject codeProject = new CodeProject();
        if (f.exists() && f.length() > 0) {
            try {
                codeProject = objectMapper.readValue(f, CodeProjectConfig.class).getCodeProject();
            } catch (IOException e) {
                e.printStackTrace();
                codeProject.setProjectType(CodeProjectConstant.PROJECT_TYPE_CATALOG);
                codeProject.setProjectName("root");
                codeProject.setProjectCode(UUID.randomUUID().toString());
            }
            return codeProject;
        }else {
            try {
                f.createNewFile();
                codeProject.setProjectType(CodeProjectConstant.PROJECT_TYPE_CATALOG);
                codeProject.setProjectName("root");
                codeProject.setProjectCode(UUID.randomUUID().toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return codeProject;
        }
    }


    private static LinkedList<CodeProject> initProjectList() {
        LinkedList<CodeProject> projectList = new LinkedList<>();
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
            codeProjectConfig.setCodeProject(rootCodeProject);
            objectMapper.writeValue(f, codeProjectConfig);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void updateSelectedProject(String selectedProjectCode) {
        try {
            codeProjectConfig.setSelectedProjectCode(selectedProjectCode);

            objectMapper.writeValue(f, codeProjectConfig);
        }catch (IOException e) {
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
                .collect(Collectors.toCollection(LinkedList::new));
        deleteFromRootCodeProject(Collections.singletonList(rootCodeProject), codeProject, false);
        saveCodeProjectConfig(rootCodeProject);
    }

    public static void removeCodeProjectWithSubProject(CodeProject codeProject) {
        List<CodeProject> subProjects = new ArrayList<>();
        tileProject(codeProject, subProjects);
        for(CodeProject codeProject1 : subProjects) {
            projectMap.remove(codeProject1.getProjectCode());
            projectList.remove(codeProject1);
        }
        deleteFromRootCodeProject(Collections.singletonList(rootCodeProject), codeProject, true);
        saveCodeProjectConfig(rootCodeProject);
    }

    private static void deleteFromRootCodeProject(List<CodeProject> rootCodeProjectList, CodeProject codeProject, Boolean includeSubProject) {

        for (CodeProject project: rootCodeProjectList) {
            List<CodeProject> subProjectList = project.getSubProjectList();
            if (subProjectList != null && !subProjectList.isEmpty()) {
                for (int i = 0; i < subProjectList.size(); i++) {
                    if (subProjectList.get(i).getProjectCode().equalsIgnoreCase(codeProject.getProjectCode())) {
                        if (subProjectList.get(i).getSubProjectList() != null && !subProjectList.get(i).getSubProjectList().isEmpty() && !includeSubProject) {
                            LinkedList<CodeProject> ssubProjects = subProjectList.get(i).getSubProjectList();
                            subProjectList.remove(i);
                            subProjectList.addAll(ssubProjects);
                        }else {
                            subProjectList.remove(i);
                        }
                        return;
                    }
                }
                deleteFromRootCodeProject(subProjectList, codeProject, includeSubProject);
            }
        }
    }

    public static void clearCodeProject() {
        f.delete();
        initProjectContext();

    }


//    public static void expandSelectPreviousItem() {
//        TreeItem<CodeProject> selectedProject = ProjectContext.selectedItem;
//        // 从目标节点开始向上遍历，直到找到根节点
//        while (selectedProject != null && !selectedProject.equals(ProjectContext.treeItem)) {
//            treeItem.setExpanded(true);
//            treeItem.getValue().setExpandStatus(true);
//            treeItem = treeItem.getParent();
//        }
//    }
}
