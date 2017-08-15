package org.longjiang.test;

import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.alan.gen.Java2PbMessage;
import org.alan.utils.ClassUtils;
import org.alan.utils.FileHelper;
import org.longjiang.core.data.Protobuf;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created on 2017/6/13.
 *
 * @author Alan
 * @since 1.0
 */
public class CreatePbSchema {
    public static void main(String[] args) {
        Map<String, Java2PbMessage> messages = new HashMap<>();
        String dir = "longjiang2_protocol/proto";
        Set<Class<?>> classes = ClassUtils.getAllClassByAnnotation("org.longjiang", Protobuf.class);
        classes.forEach(clazz -> {
            System.out.println(clazz);
            Schema<?> schema = RuntimeSchema.getSchema(clazz);
            Java2PbMessage pbGen = new Java2PbMessage(schema).gen();
            messages.put(clazz.getSimpleName(), pbGen);
            String fileName = dir + "/" + clazz.getSimpleName() + ".proto";
            System.out.println(fileName);
            String content = pbGen.toMesage();
            FileHelper.saveFile(fileName, content, false);
        });

        messages.values().forEach(pbGen -> pbGen.dependencyMessages.forEach(s -> {
            if (!messages.containsKey(s)) {
                System.err.println("找不到依赖消息,message=" + pbGen.outerClassName + ",dependencyMessage=" + s);
            }
        }));

    }
}
