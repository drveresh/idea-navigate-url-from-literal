package net.ishchenko.idea.navigateurlfromliteral;

import java.io.IOException;
import java.util.Properties;
import java.util.Set;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.startup.StartupActivity;
import com.intellij.openapi.vfs.VirtualFile;

import static net.ishchenko.idea.navigateurlfromliteral.OneWayPsiFileReferenceBase.linkRules;

/**
 * This is our sample StartupActivity used to execute code on project open.
 */
public class PluginStartupActivity implements StartupActivity {

    
    @Override
    public void runActivity(Project project) {
        // This code is executed after the project was opened.
        VirtualFile[] contentRootsFromAllModules = ProjectRootManager.getInstance(project).getContentRootsFromAllModules();
        for (VirtualFile contentRoot : contentRootsFromAllModules) {
            VirtualFile file = contentRoot.findFileByRelativePath("navigate-url-plugin.properties");
            if (file != null) {
                Properties properties = new Properties();
                try {
                    properties.load(file.getInputStream());
                    Set<String> stringPropertyNames = properties.stringPropertyNames();
                    for (int i = 0; i < stringPropertyNames.size(); i++) {
                        String navigateTo;
                        String startsWith = null;
                        String navigateToKey = "url" + i + ".navigateTo";
                        if (stringPropertyNames.contains(navigateToKey)) {
                            String startsWithKey = "url" + i + ".startsWith";
                            if (stringPropertyNames.contains(startsWithKey)) {
                                navigateTo = properties.getProperty(navigateToKey);
                                startsWith = properties.getProperty(startsWithKey);
                                linkRules.add(new LinkRule(navigateTo, startsWith));
                            }
                        }
                    }
                    break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

//        System.out.println("Hello World! Loaded project: " + project.getName());
    }

}