#必看！
##依赖
本项目依赖于maven，请安装maven插件！

##子项目必须是maven项目

##当不新建子项目时添加模块
- 在子项目projar项目中的目录下添加符合规则的文件夹。
- 在projar/src/main/resources/${模块的完整路径，如：com/ws/ksoft}/下需要含有MANIFRST.MF.且文件中含有自定义属性packageName
- packageName设置为${模块的完整包名}.如：com.ws.ksoft
- 默认情况下，hibernate映射文件放在,${模块的完整路径}/hibernate文件夹下。如果要特使设置，请在${模块的完整路径}下添加jdbc.properties,并在该文件中添加hibernate.mapping以指定路径，多个路径用逗号隔开
- 默认情况下，spring的入口xml为：${模块的完整路径}/applicationContext.xml,如需特殊指定，请在${模块的完整路径}/jdbc.properties中添加spring.root以指定
- 默认情况下，可以不设置模块初始化类，如果需要设置，可设置为${模块的完整包名}.MainInit或者在${模块的完整路径}/jdbc.properties中通过initclass指定。
- 模块的功能接口需要在${模块的完整路径}/handler.properties中指定。
- 模块的语言设置(国际化)需要在${模块的完整路径}/lang(\_zh|\_en).properties中设置
- 完成以上步骤后，在本文件夹下建立${自定义名}/pom.xml，如：com.ws.ksoft/pom.xml.
- pom文件继承自本文件夹下的parent/pom.xml.pom中需要配置package.name以及package.path，分别存放${模块的完整包名}和${模块的完整路径}

##在本项目的pom中添加新建的模块artifactId

##当需要打包新模块时。在新建的pom.xml上执行maven package.需要全部重新打包时，在本项目的pom中执行该命令