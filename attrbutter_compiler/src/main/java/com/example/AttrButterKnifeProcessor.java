package com.example;


import com.google.auto.common.SuperficialValidation;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

/**
 * Created by simple on 16/11/1.
 */
@AutoService(Processor.class)
public class AttrButterKnifeProcessor extends AbstractProcessor {

    private static final ClassName VIEW = ClassName.get("android.view", "View");
    private static final ClassName TYPEARRAY = ClassName.get("android.content.res", "TypedArray");

    public static final String SUFFIX = "$ViewBinder";
    private Messager messager;
    private Filer filer;

    /**
     * 通过输入ProcessingEnvironment参数，你可以在得到很多有用的工具类，比如Elements，Types，Filer等。
     * Elements是可以用来处理Element的工具类，可以理解为Java Annotation Tool扫描过程中扫描到的所有的元素，
     * 比如包（PackageElement）、类(TypeElement)、方法(ExecuteableElement)等
     * Types是可以用来处理TypeMirror的工具类，它代表在JAVA语言中的一种类型，我们可以通过TypeMirror配合
     * Elements来判断某个元素是否是我们想要的类型
     * Filer是生成JAVA源代码的工具类，能不能生成java源码就靠它啦
     */
    @Override
    public synchronized void init(ProcessingEnvironment env) {
        super.init(env);
        messager = processingEnv.getMessager();
        filer = env.getFiler();
    }

    /**
     * @return
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        for (Class<? extends Annotation> annotation : getSupportedAnnotations()) {
            types.add(annotation.getCanonicalName());
        }
        return types;
    }

    private Set<Class<? extends Annotation>> getSupportedAnnotations() {
        Set<Class<? extends Annotation>> annotations = new LinkedHashSet<>();

        annotations.add(AttrBindString.class);
        annotations.add(AttrBindBoolean.class);

        return annotations;
    }

    /**
     * process是整个注解处理器的重头戏，你所有扫描和处理注解的代码以及生成Java源文件的代码都写在这里面，
     * 这个也是我们将要重点分析的方法
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Map<String, List<VariableElement>> map = new HashMap<>(); // key 是类名，value 是该类的注解元素
//         //遍历AttrBindString注解的所有元素
        for (Element element : roundEnv.getElementsAnnotatedWith(AttrBindString.class)) {
//            if (!SuperficialValidation.validateElement(element)) continue;
            if (element == null || !(element instanceof VariableElement)) {
                continue;
            }
            // 给属性添加的注解
            VariableElement variableElement = (VariableElement) element;
            // 获取属性所在的类名
//            String className = element.getEnclosingElement().getSimpleName().toString();
            String simpleName = AttrBindString.class.getSimpleName();
            List<VariableElement> variableElementList = map.get(simpleName);
            if (variableElementList == null) {
                variableElementList = new ArrayList<>();
                map.put(simpleName, variableElementList);
            }
            variableElementList.add(variableElement);
        }
// 遍历AttrBindBoolean注解的所有元素
        for (Element element : roundEnv.getElementsAnnotatedWith(AttrBindBoolean.class)) {
            if (element == null || !(element instanceof VariableElement)) {
                continue;
            }
            // 给属性添加的注解
            VariableElement variableElement = (VariableElement) element;

            // 获取属性所在的类名
//            String className = element.getEnclosingElement().getSimpleName().toString();
            String simpleName = AttrBindBoolean.class.getSimpleName();
            List<VariableElement> variableElementList = map.get(simpleName);
            if (variableElementList == null) {
                variableElementList = new ArrayList<>();
                map.put(simpleName, variableElementList);
            }
            variableElementList.add(variableElement);
        }

        // 生成辅助类
//        generateBindView(map);
        generateBindString(map);
        return true;
    }

    private void findAndParseTargets(RoundEnvironment roundEnv) {
        Map<String, List<VariableElement>> map = new HashMap<>(); // key 是类名，value 是该类的注解元素
        //
        for (Element element : roundEnv.getElementsAnnotatedWith(AttrBindString.class)) {
            if (element == null || !(element instanceof VariableElement)) {
                continue;
            }

        }
    }

    private void generateBindString(Map<String, List<VariableElement>> map) {
        log("开始生成");
        if (null == map || map.size() == 0) {
            return;
        }
        //method
        MethodSpec.Builder builder = MethodSpec.methodBuilder("bind");
        String packageName = null;
        String className = null;
        builder.addModifiers(Modifier.PUBLIC, Modifier.STATIC);
        builder.addParameter(Object.class, "target");
        builder.addParameter(Object.class, "typeArray");
        builder.addStatement(className + " view =(" + className + ")target");
        builder.addStatement("$T ta =($T)typeArray", TYPEARRAY, TYPEARRAY);

        for (String annoName : map.keySet()) {
            List<VariableElement> variableElementList = map.get(annoName);
            if (variableElementList == null || variableElementList.size() <= 0) {
                continue;
            }
            // 获取包名

            packageName = variableElementList.get(0).getEnclosingElement()
                    .getEnclosingElement().toString();


            className = variableElementList.get(0).getEnclosingElement()
                    .getSimpleName().toString();

            for (VariableElement variableElement : variableElementList) {

                AttrBindString attrBindString = variableElement.getAnnotation(AttrBindString.class);
                if (attrBindString != null) {
                    builder.addStatement("view." + variableElement.getSimpleName().toString() +
                            " = ta.getString(" + attrBindString.value() + ")");
                }

                AttrBindBoolean attrBindBoolean = variableElement.getAnnotation(AttrBindBoolean.class);
                if (attrBindBoolean != null) {
                    builder.addStatement("view." + variableElement.getSimpleName().toString() +
                            " = ta.getBoolean(" + attrBindBoolean.value() + "," + attrBindBoolean.default_value() + ")");
                }

            }
        }
        MethodSpec methodSpec = builder.build();
        //class
        TypeSpec typeSpec = creatTypeSpec(methodSpec, className + SUFFIX);
        //file
        brewJava(packageName, typeSpec);
    }

    /**
     * 生成类
     *
     * @param methodSpec
     * @param className
     * @return
     */
    private TypeSpec creatTypeSpec(MethodSpec methodSpec, String className) {
        TypeSpec typeSpec = TypeSpec.classBuilder(className)
                .addModifiers(Modifier.PUBLIC)
                .addMethod(methodSpec)
                .build();
        return typeSpec;
    }

    /**
     * 生成java文件
     *
     * @param packageName
     * @param typeSpec
     */
    private void brewJava(String packageName, TypeSpec typeSpec) {
        JavaFile javaFile = JavaFile.builder(packageName, typeSpec)
                .build();

        try {
            javaFile.writeTo(filer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void generateBindView(Map<String, List<VariableElement>> map) {
        if (null == map || map.size() == 0) {
            return;
        }
        for (String className : map.keySet()) {
            List<VariableElement> variableElementList = map.get(className);
            if (variableElementList == null || variableElementList.size() <= 0) {
                continue;
            }
            // 获取包名
            String packageName = variableElementList.get(0).getEnclosingElement()
                    .getEnclosingElement().toString();
            StringBuilder builder = new StringBuilder()
                    .append("package ").append(packageName).append(";\n\n")
                    .append("public class ").append(className).append(SUFFIX).append("{\n") // open class
                    .append("    public void bind(Object target) {\n")
                    .append("        ").append(className).append(" activity = (")
                    .append(className).append(")target;\n");

            for (VariableElement variableElement : variableElementList) {
                BindView bindView = variableElement.getAnnotation(BindView.class);
                log(bindView.toString());
                builder.append("        activity.").append(variableElement.getSimpleName()
                        .toString()).append("=(").append(variableElement.asType())
                        .append(")activity.findViewById(").append(bindView.value()).append(");\n");
            }
            builder.append("    }\n}\n");
            // write the file
            try {
                String bindViewClassName = packageName + "." + className + SUFFIX;
                JavaFileObject source = processingEnv.getFiler().createSourceFile(bindViewClassName);
                Writer writer = source.openWriter();
                writer.write(builder.toString());
                writer.flush();
                writer.close();
            } catch (IOException e) {
                log(e.getMessage());
            }
        }
    }

    private void log(String msg) {
        messager.printMessage(Diagnostic.Kind.WARNING, msg);
    }

    private void fileLog(String msg) {

    }

}