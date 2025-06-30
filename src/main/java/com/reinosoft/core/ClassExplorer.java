package com.reinosoft.core;

import com.reinosoft.utils.SLogger;
import com.reinosoft.web.SBoot;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ClassExplorer {

    private ClassExplorer() {}

    public static List<String> retrieveClasses(Class<?> clazz) {
        return packageExplorer(clazz.getPackageName());
    }

    private static List<String> packageExplorer(String packageName) {
        final var classNames = new ArrayList<String>();

        String packagePath = packageName.replace(".", File.separator);
        final var inputStream = ClassExplorer.class.getClassLoader().getResourceAsStream(packagePath);

        assert inputStream != null : "Input stream for package " + packageName + " is null. Check if the package exists.";

        try (final var reader = new BufferedReader(new InputStreamReader(inputStream));) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.endsWith(".class")) {
                    final var className = String.format("%s.%s", packageName, line.replace(".class", ""));
                    classNames.add(className);
                } else {
                    classNames.addAll(packageExplorer(packageName + "." + line));
                }
            }
            return classNames;
        } catch (Exception e) {
            SLogger.error(SBoot.class, e);
            return null;
        }
    }
}
