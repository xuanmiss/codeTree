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
1.0
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
- [] 增加上次选中的项目，下次启动软件时自动展开选中
- [] 新增添加父级目录，自动导入子目录和项目

### 示例图

- CodeProject Detail

![img.png](https://github.com/xuanmiss/codeTree/blob/main/img.png?raw=true)

- Add A New CodeProject

![img_1.png](https://github.com/xuanmiss/codeTree/blob/main/img_1.png?raw=true)