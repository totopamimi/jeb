import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.io.ByteArrayInputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

/**
 * Created by qtfreet on 2016/11/29.
 */
public class Loader {
    public static void premain(String agentOps, Instrumentation inst) {
        inst.addTransformer(new ClassFileTransformer() {

            @Override
            public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
                className = className.replace("/", ".");
                if (className.equals("com.pnfsoftware.jeb.client.Licensing")) {
                    try {
                        ClassPool pool = ClassPool.getDefault();
                        CtClass ctClass = pool.makeClass(new ByteArrayInputStream(classfileBuffer));
                        CtMethod a = ctClass.getDeclaredMethod("getExpirationTimestamp", null);
                        System.out.println("loader加载成功100%~");
                        a.setBody("return 2000000000;");
                        return ctClass.toBytecode();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (className.equals("com.pnfsoftware.jeb.client.AbstractClientContext")) {
                    try {
                        ClassPool pool = ClassPool.getDefault();
                        CtClass ctClass = pool.makeClass(new ByteArrayInputStream(classfileBuffer));
                        CtMethod a = ctClass.getDeclaredMethod("startFloatingClient", null);
                        System.out.println("loader加载成功50%~");
                        a.setBody("return;");
                        return ctClass.toBytecode();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return new byte[0];
            }
        });
    }
}
