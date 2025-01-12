# CodeTree

*codeTree 是一个使用javafx 开发的本地项目和代码仓库管理工具。*

### CodeTree's codeTree

```shell
.
└── CodeTree
    ├── CodeTree.iml
    ├── LICENSE
    ├── README.md
    ├── pom.xml
    └── src
        └── main
            ├── java
            │   ├── com
            │   │   └── miss
            │   │       └── codetree
            │   └── module-info.java
            └── resources
                ├── com
                │   └── miss
                │       └── codetree
                ├── config.json
                ├── icon
                │   ├── add.png
                │   ├── expandImage.png
                │   ├── foldImage.png
                │   ├── pressedAdd.png
                │   └── project.png
                └── static
                    ├── css
                    │   ├── github-markdown.css
                    │   └── index.min.css
                    ├── index.html
                    └── js
                        ├── index.min.js
                        ├── plugin-gfm.js
                        └── plugin-highlight.js
```

### 版本说明

- JavaFx 16
- openjdk version "16.0.2" 2021-07-20
- Apache Maven 3.6.3


### feature
#### 1.0版本
- [x] 菜单树展示和折叠收起
- [x] 详情面板开发
- [x] git仓库信息获取和展示
- [x] README的文件展示
- [x] 添加新项目弹窗

1.1
- [x] 允许调整目录结构，上下级
- [x] 新建项目时，支持从当前文件系统选择文件夹

1.2
- [x] 增加展开全部操作，先加在根节点上，可以考虑加载每个文件夹上
- [x] 新增项目的文件选择器支持记住上次打开路径

1.3
- [x] 增加如果没有README.md可以编辑MD文件，并保存 

1.4
- [x] 增加上次选中的项目，下次启动软件时自动展开选中
- [x] 新增添加父级目录，自动导入子目录和项目
  - [x] bugfix: 添加子目录和项目时，忽略 `.` 开头的目录
  - [x] feat: 调价子目录的项目时，自动初始化git仓库信息，并使用一个异步线程池处理
- [x] 添加搜索框，可以根据关键字搜索项目，搜索后平铺展示，如果没有搜索添加，自动恢复树状视图
- [x] 新增重置项目配置按钮，用来全部删除已导入的项目和配置
- [x] 新增，删除目录时，可以勾选自动删除子项目
- [ ] 升级代码的基础框架，修复安全漏洞
- [ ] 统一代码规范和风格，依照sonar插件规范调整 
- [ ] 增加刷新目录，自动更新所有子项目信息

#### 2.0版本
2.0版本可能可以使用`flutter`或者`Electron + React/Vue`重构整个桌面端应用

### 示例图

- CodeProject Detail

![img.png](https://github.com/xuanmiss/codeTree/blob/main/img.png?raw=true)

- Add A New CodeProject

![img_1.png](https://github.com/xuanmiss/codeTree/blob/main/img_1.png?raw=true)