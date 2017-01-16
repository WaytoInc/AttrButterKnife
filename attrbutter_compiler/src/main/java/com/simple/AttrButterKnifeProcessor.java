package com.simple;


import com.google.auto.common.MoreElements;
import com.google.auto.common.SuperficialValidation;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.LinkedHashMap;
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

/**
 * Created by simple on 16/11/1.
 */
@AutoService(Processor.class)
public class AttrButterKnifeProcessor extends AbstractProcessor {

    private static final ClassName VIEW = ClassName.get("android.view", "View");
    private static final ClassName TYPEARRAY = ClassName.get("android.content.res", "TypedArray");
    private Map<String, List<VariableElement>> varMap = new LinkedHashMap<>(); // key 是类名，value 是该类的注解元素
    public static final String CLASS_SUFFIX = "_ViewBinder";
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
     * 获取支持的注解
     *
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

        annotations.add(AttrString.class);
        annotations.add(AttrBool.class);
        annotations.add(AttrDimen.class);
        annotations.add(AttrFloat.class);
        annotations.add(AttrColor.class);
        annotations.add(AttrInt.class);

        return annotations;
    }

    /**
     * process是整个注解处理器的重头戏，你所有扫描和处理注解的代码以及生成Java源文件的代码都写在这里面，
     * 这个也是我们将要重点分析的方法
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        //遍历所有被注解了的元素
        try {
            findAndParseTargets(roundEnv);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        Map<String, List<VariableElement>> varMap = findAndParseTargets(roundEnv);
        // 生成辅助类
        generateCode();
        return true;
    }

    private Map<String, List<VariableElement>> findAndParseTargets(RoundEnvironment roundEnv) {

        //解析 AttrString 注解
        for (Element element : roundEnv.getElementsAnnotatedWith(AttrString.class)) {
            if (!SuperficialValidation.validateElement(element)) continue;
            try {
                parseBindVariable(element, varMap);
            } catch (Exception e) {

            }
        }
        //解析 AttrBool
        for (Element element : roundEnv.getElementsAnnotatedWith(AttrBool.class)) {
            if (!SuperficialValidation.validateElement(element)) continue;
            try {
                parseBindVariable(element, varMap);
            } catch (Exception e) {

            }
        }
        //解析 AttrColor
        for (Element element : roundEnv.getElementsAnnotatedWith(AttrColor.class)) {
            if (!SuperficialValidation.validateElement(element)) continue;
            try {
                parseBindVariable(element, varMap);
            } catch (Exception e) {

            }
        }
        //解析 AttrDimen
        for (Element element : roundEnv.getElementsAnnotatedWith(AttrDimen.class)) {
            if (!SuperficialValidation.validateElement(element)) continue;
            try {
                parseBindVariable(element, varMap);
            } catch (Exception e) {

            }
        }
        //解析 AttrFloat
        for (Element element : roundEnv.getElementsAnnotatedWith(AttrFloat.class)) {
            if (!SuperficialValidation.validateElement(element)) continue;
            try {
                parseBindVariable(element, varMap);
            } catch (Exception e) {

            }
        }
        //解析 AttrInt
        for (Element element : roundEnv.getElementsAnnotatedWith(AttrInt.class)) {
            if (!SuperficialValidation.validateElement(element)) continue;
            try {
                parseBindVariable(element, varMap);
            } catch (Exception e) {

            }
        }
        return varMap;
    }

    /**
     * 解析被注解的元素
     *
     * @param element
     * @param map
     */
    private void parseBindVariable(Element element, Map<String, List<VariableElement>> map) {
        // 给属性添加的注解
        VariableElement variableElement = (VariableElement) element;
        // 获取属性所在的类名
        String className = element.getEnclosingElement().getSimpleName().toString();
        List<VariableElement> variableElementList = map.get(className);
        if (variableElementList == null) {
            variableElementList = new ArrayList<>();
            map.put(className, variableElementList);
        }
        variableElementList.add(variableElement);
    }

    /**
     * 生成代码
     */
    private void generateCode() {
        log("开始生成");
        if (null == varMap || varMap.size() == 0) {
            return;
        }

        for (String className : varMap.keySet()) {
            List<VariableElement> variableElementList = varMap.get(className);
            if (variableElementList == null || variableElementList.isEmpty()) {
                continue;
            }
            // 获取包名
            String packageName = MoreElements.getPackage(variableElementList.get(0))
                    .getQualifiedName().toString();

            //method
            MethodSpec.Builder builder = MethodSpec.methodBuilder("bind");
            builder.addModifiers(Modifier.PUBLIC, Modifier.STATIC);
            builder.addParameter(Object.class, "target");
            builder.addParameter(Object.class, "typeArray");
            builder.addStatement(className + " view =(" + className + ")target");
            builder.addStatement("$T ta =($T)typeArray", TYPEARRAY, TYPEARRAY);
            builder.beginControlFlow("try");
            //遍历所有的属性元素
            for (VariableElement variableElement : variableElementList) {

                String varName = variableElement.getSimpleName().toString();

                AttrString attrString = variableElement.getAnnotation(AttrString.class);
                if (attrString != null) {
                    builder.addStatement("view." + varName +
                            " = ta.getString(" + attrString.value() + ")");
                }

                AttrBool attrBool = variableElement.getAnnotation(AttrBool.class);
                if (attrBool != null) {
                    builder.addStatement("view." + varName +
                            " = ta.getBoolean(" + attrBool.id() + ","
                            + attrBool.defValue() + ")");
                }

                AttrColor attrColor = variableElement.getAnnotation(AttrColor.class);
                if (attrColor != null) {
                    builder.addStatement("view." + varName +
                            " = ta.getColor(" + attrColor.id() + ","
                            + attrColor.defValue() + ")");
                }

                AttrDimen attrDimen = variableElement.getAnnotation(AttrDimen.class);
                if (attrDimen != null) {
                    builder.addStatement("view." + varName +
                            " = ta.getDimension(" + attrDimen.id() + ","
                            + attrDimen.defValue() + "f)");
                }

                AttrFloat attrFloat = variableElement.getAnnotation(AttrFloat.class);
                if (attrFloat != null) {
                    builder.addStatement("view." + varName +
                            " = ta.getFloat(" + attrFloat.id() + ","
                            + attrFloat.defValue() + "f)");
                }

                AttrInt attrInt = variableElement.getAnnotation(AttrInt.class);
                if (attrInt != null) {
                    builder.addStatement("view." + varName +
                            " = ta.getInteger(" + attrInt.id() + ","
                            + attrInt.defValue() + ")");
                }

            }
            builder.nextControlFlow("finally");
            builder.addStatement("ta.recycle()");
            builder.endControlFlow();
            MethodSpec methodSpec = builder.build();
            //class
            TypeSpec typeSpec = createTypeSpec(methodSpec, className + CLASS_SUFFIX);
            //file
            brewJava(packageName, typeSpec);
        }
    }

    /**
     * 生成类
     *
     * @param methodSpec
     * @param className
     * @return
     */
    private TypeSpec createTypeSpec(MethodSpec methodSpec, String className) {
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

    private void log(String msg) {
        messager.printMessage(Diagnostic.Kind.WARNING, msg);
    }

}
