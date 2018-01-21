package wang.study.jvm.lesson11;

import org.objectweb.asm.Opcodes;

/**
 * Created by Administrator on 2018/1/12.
 */
public class MyClassLoader  extends ClassLoader implements Opcodes {
    public MyClassLoader() {
        super();
    }

    public MyClassLoader(ClassLoader parent) {
        super(parent);
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        return super.loadClass(name);
    }

    public Class<?> defineClass(String name, byte[] b){
        return super.defineClass(name, b, 0, b.length);
    }
}
